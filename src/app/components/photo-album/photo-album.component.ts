import { Component, OnInit } from '@angular/core';
import { AlbumService, PhotoAlbum } from "./album.service";
import { LocalUserService } from "../global/local-service.service";
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-photo-album',
  templateUrl: 'photo-album.component.html',
  styleUrls: ['photo-album.component.css'],
  providers: [AlbumService]
})
export class PhotoAlbumComponent implements OnInit {

  newPhotoAlbum: PhotoAlbum;
  imageSrc: any;
  status: boolean;
  message: string;

  constructor(private _albumnService: AlbumService, private _localService: LocalUserService,private _router:Router) { }

  ngOnInit() {
    this.newPhotoAlbum = new PhotoAlbum("", "", "", new Date, "");
    this._localService.initiateByRememberMe();
  }

  onSaveSucces(data) {
    this.status = true;
    console.log(data);
    this._router.navigateByUrl("account/"+this._localService.userId+"/my-collection");

  }
  onSaveFailed(err) {
    this.status = false;
    this.message = err._body;
    console.log(err);
  }

  onGenerateAlbum() {
    this._albumnService.createNewAlbum(this._localService.userId, this.newPhotoAlbum).subscribe(
      data => this.onSaveSucces(data),
      err => this.onSaveFailed(err)
    );
  }

  attachFile(event) {

    var reader = new FileReader();
    let _self = this;
    _self.imageSrc = reader.result;

    reader.onload = function(e) {
      _self.imageSrc = reader.result;
      _self.newPhotoAlbum.coverImage = reader.result;
    };

    reader.readAsDataURL(event.target.files[0]);
    console.log(this.newPhotoAlbum.coverImage);
  }


}
