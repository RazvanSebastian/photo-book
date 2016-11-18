import {Compiler, Component, OnInit, ChangeDetectionStrategy} from '@angular/core';
import { LocalUserService}  from "../global/local-service.service";
import { Router, ActivatedRoute, Params } from '@angular/router';
import {PhotoService, Photo} from "./photo.service";
import {AlbumService, PhotoAlbum} from "../photo-album/album.service";
import { PaginatePipe, PaginationControlsCmp, PaginationService, IPaginationInstance} from 'ng2-pagination';
import { RuntimeCompiler} from '@angular/compiler';

@Component({
  moduleId: module.id,
  selector: 'app-photos',
  templateUrl: 'photos.component.html',
  styleUrls: ['photos.component.css'],
  providers: [PhotoService, AlbumService, PaginationService],
  pipes: [PaginatePipe],
  directives: [PaginationControlsCmp],
})

export class PhotosComponent implements OnInit {
  originalImage: Photo;

  photoPrepare: boolean;
  loadingMessage: string;

  displayLoader: boolean = true;
  displayContent: boolean = false;

  albumId: any;
  myAlbum: PhotoAlbum;
  photoGallery: Array<Photo>;
  id: any;

  photoSelected: any;
  descriptionZoom: string
  nameZoom: string;

  workaround: boolean;
  deleteId: number;

  //Dictionary for pagination( if the user want to see a previous page the client side will no make a request if we have stored that page
  // and it will search in dictionary by page where is sotred an array)
  storedPages: { [pageNumber: number]: any; } = {};
  paginationConfig: IPaginationInstance;
  totalPhotos: number;
  page: number;

  constructor(private route: ActivatedRoute, private _router: Router, private _photoService: PhotoService,
    private _albumService: AlbumService, private _runtimeCompiler: RuntimeCompiler,
    private _templateCompiler: Compiler) {
    this.photoGallery = new Array<Photo>();
  }

  ngOnInit() {
    this._templateCompiler.clearCache();

    this.paginationConfig = { itemsPerPage: 8, currentPage: 1 };
    this.myAlbum = new PhotoAlbum(1, "", "", "", new Date, "");
    this.originalImage = new Photo(1, "", "", "", "", "", null, "");
    //receive paramter using router
    this.route.params.forEach((params: Params) => {
      this.id = +params['id']; // (+) converts string 'id' to a number
    });
    this.onGetPhotos(this.id);
    this.onGetAlbum(this.id);
    this.getTotalPhotos(this.id);


    // var photoPage=new Array<Photo>();
    // photoPage.push(new Photo(1,"photo1","","","","",new Date,""));
    // photoPage.push(new Photo(2,"photo2","","","","",new Date,""));
    // photoPage=new Array<Photo>();
    // var  photoPage1=new Array<Photo>();
    // photoPage1.push(new Photo(3,"photo3","","","","",new Date,""));
    // this.storedPages[1]={photoPage};
    // this.storedPages[2]={photoPage1};
    // console.log(this.storedPages[1]);
  }

  getTotalPhotos(albumId) {
    this._photoService.getPageNumber(albumId).subscribe(
      data => { this.paginationConfig.totalItems = JSON.parse(data.text()) },
      err => console.log(err)
    );
  }

  onGetSucces(data) {
    this.displayLoader = true;
    var photosPerPage = new Array<Photo>();
    JSON.parse(data.text()).forEach(photo => {
      photosPerPage.push(photo);
    });
    this.storedPages[this.paginationConfig.currentPage] = photosPerPage;
    // console.log(this.storedPages[this.paginationConfig.currentPage]);
    // console.log(this.paginationConfig.currentPage);
    this.displayLoader = false;
  }

  onGetPhotos(idAlbum) {
    if (this.storedPages[this.paginationConfig.currentPage] == null) {
      this._photoService.getPhotoGallery(idAlbum, this.paginationConfig.currentPage).subscribe(
        data => this.onGetSucces(data),
        err => console.log(err)
      );
    }
  }


  onGetAlbumSucces(response) {
    //Convert body response to object
    this.myAlbum.name = JSON.parse(response._body).name;
    this.myAlbum.description = JSON.parse(response._body).description;
    this.myAlbum.date = JSON.parse(response._body).date;
    this.myAlbum.coverImage = JSON.parse(response._body).coverImage;

  }
  onGetAlbum(idAlbum) {
    this._albumService.receiveAlbum(idAlbum).subscribe(
      data => this.onGetAlbumSucces(data),
      err => console.log(err)
    )
  }


  // on click this will send yout to url
  onClickNewPhoto() {
    this._runtimeCompiler.clearCache();
    this._router.navigate(['my-album/', this.id, '/new-photo'])
  }

  onZoom(name, photo, description) {
    this.nameZoom = name;
    this.photoSelected = photo;
    this.descriptionZoom = description;
  }

  //dELETE METHOD
  deletePagePhoto: number;
  photoForDelete: Photo;

  onDelete(photo, id, page) {
    this.photoForDelete = photo;
    // console.log(this.photoForDelete);
    this.deletePagePhoto = page;
    this.deleteId = id;
  }

  onModalClose() {
    if (this.deletePagePhoto == this.paginationConfig.totalItems / 6) {
      if (this.storedPages[this.deletePagePhoto].length == 1)
        delete this.storedPages[this.deletePagePhoto];
      else {
        var index = this.storedPages[this.deletePagePhoto].indexOf(this.photoForDelete);
        this.storedPages[this.deletePagePhoto].splice(index, 1);
      }
    }

  }

  onDeletePhoto() {
    this._photoService.deletePhotoSelected(this.deleteId).subscribe(
      data => this.onModalClose(),
      err => console.log(err)
    );
  }

  //################# Pagination event ######################
  changePage(event) {
    this.paginationConfig.currentPage = event;
    this.onGetPhotos(this.id);
  }

  //################ Download image on local disk ###########

  private getImageFormat(image): String {
    if (image.substring(11, 14) === "jpg")
      return image.substring(11, 14);
    if (image.substring(11, 15) === "jpeg")
      return image.substring(11, 15);
    if (image.substring(11, 14) === "png")
      return image.substring(11, 14);
    return "";
  }

  b64toBlob(b64Data, contentType, sliceSize) {
    contentType = contentType || '';
    var byteCharacters = atob(b64Data);
    var bytesLength = byteCharacters.length;
    var slicesCount = Math.ceil(bytesLength / sliceSize);
    var byteArrays = new Array(slicesCount);

    for (var sliceIndex = 0; sliceIndex < slicesCount; ++sliceIndex) {
      var begin = sliceIndex * sliceSize;
      var end = Math.min(begin + sliceSize, bytesLength);

      var bytes = new Array(end - begin);
      for (var offset = begin, i = 0; offset < end; ++i, ++offset) {
        bytes[i] = byteCharacters[offset].charCodeAt(0);
      }
      byteArrays[sliceIndex] = new Uint8Array(bytes);
    }
    return new Blob(byteArrays, { type: contentType });
  }

  onGetOriginalImageSuccess(data, name) {
    this.loadingMessage = "We are preparing your photo in original format!";
    this.originalImage.image = JSON.parse(data._body).image;

    var type = this.getImageFormat(this.originalImage.image);
    var blob = this.b64toBlob(this.originalImage.image.split(',')[1], "image/" + type, 1000000);
    saveAs(blob, name + "." + type);
    blob.msClose();
    this.loadingMessage = "You can download your photo!";
  }

  downloadFile(id, name: String) {
    this._photoService.getOriginalPhoto(id).subscribe(
      data => this.onGetOriginalImageSuccess(data, name),
      err => console.log("err")
    );
  }
}
