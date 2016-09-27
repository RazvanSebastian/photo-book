import { Component, OnInit } from '@angular/core';
import { LocalUserService}  from "../global/local-service.service";
import { Router, ActivatedRoute, Params } from '@angular/router';
import {PhotoService, Photo} from "./photo.service";
import {AlbumService, PhotoAlbum} from "../photo-album/album.service";

@Component({
  moduleId: module.id,
  selector: 'app-photos',
  templateUrl: 'photos.component.html',
  styleUrls: ['photos.component.css'],
  providers: [PhotoService, AlbumService]
})
export class PhotosComponent implements OnInit {

  albumId: any;
  myAlbum: PhotoAlbum;
  photoGallery: Array<Photo>;
  id: any;

  constructor(private route: ActivatedRoute, private router: Router, private _photoService: PhotoService, private _albumService: AlbumService) {
    this.photoGallery = new Array<Photo>();
  }

  ngOnInit() {
    this.myAlbum = new PhotoAlbum(1, "", "", "", new Date, "");
    //receive paramter using router
    this.route.params.forEach((params: Params) => {
      this.id = +params['id']; // (+) converts string 'id' to a number
    });
    this.onGetPhotos(this.id);
    this.onGetAlbum(this.id);
  }

  onGetSucces(data) {
    let photo: Photo;
    console.log(data);
    //Parsing a JSON to array of album
    JSON.parse(data.text()).forEach(photo => {
      this.photoGallery.push(photo);
    });
  }

  onGetPhotos(idAlbum) {
    this._photoService.getPhotoGallery(idAlbum).subscribe(
      data => this.onGetSucces(data),
      err => console.log(err)
    );
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


  // when client doing click on image with +(new photo) navigate to new-photo componenet to add new photo
  onClickNewPhoto() {
    this.router.navigate(['my-album/', this.id, '/new-photo'])
  }

}
