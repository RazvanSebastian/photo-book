import { Component, OnInit, ChangeDetectionStrategy} from '@angular/core';
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

  displayLoader:boolean=true;
  displayContent:boolean=false;

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
    private _albumService: AlbumService,private _runtimeCompiler: RuntimeCompiler) {
    this.photoGallery = new Array<Photo>();
  }

  ngOnInit() {
    this.paginationConfig = { itemsPerPage: 8, currentPage: 1 };
    this.myAlbum = new PhotoAlbum(1, "", "", "", new Date, "");
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
    this.displayLoader=true;
    var photosPerPage = new Array<Photo>();
    JSON.parse(data.text()).forEach(photo => {
      photosPerPage.push(photo);
    });
    this.storedPages[this.paginationConfig.currentPage] = photosPerPage;
    console.log(this.storedPages[this.paginationConfig.currentPage]);
    console.log(this.paginationConfig.currentPage);
    this.displayLoader=false;
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
  deletePagePhoto:number;
  photoForDelete:Photo;

  onDelete(photo,id,page) {
    this.photoForDelete=photo;
    console.log(this.photoForDelete);
    this.deletePagePhoto=page;
    this.deleteId = id;
  }

  onModalClose() {
    if(this.deletePagePhoto==this.paginationConfig.totalItems/6){
      if(this.storedPages[this.deletePagePhoto].length==1)
        delete this.storedPages[this.deletePagePhoto];
      else{
        var index=this.storedPages[this.deletePagePhoto].indexOf(this.photoForDelete);
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

  private getImageFormat(image):String{
    if (image.substring(11, 14)==="jpg")
			return image.substring(11, 14);
		if (image.substring(11, 15)==="jpeg")
			return image.substring(11, 15);
		if (image.substring(11, 14)==="png")
			return image.substring(11, 14);
    return "";
  }

  b64toBlob(b64Data, contentType, sliceSize) {
    contentType = contentType || '';
    sliceSize = sliceSize || 512;

    var byteCharacters = atob(b64Data);
    var byteArrays = [];

    for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
      var slice = byteCharacters.slice(offset, offset + sliceSize);

      var byteNumbers = new Array(slice.length);
      for (var i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }

      var byteArray = new Uint8Array(byteNumbers);

      byteArrays.push(byteArray);
    }

    var blob = new Blob(byteArrays, { type: contentType });
    return blob;
  }

  onGetOriginalPhotoSucces(data){
    console.log(JSON.parse(data._body));
  }

  downloadFile(id:number,name:String) {
    // this._runtimeCompiler.clearCache();
    var image;
    this._photoService.getOriginalPhoto(id).subscribe( data =>this.onGetOriginalPhotoSucces(data));
    var type=this.getImageFormat(image);
    var blob = this.b64toBlob(image.split(',')[1], "image/"+type, 2048);
    saveAs(blob, name+"."+type);
    image=null;
    blob=null;
    type=null;
  }

}
