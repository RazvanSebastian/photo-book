import { Component, OnInit } from '@angular/core';
import { AlbumService , PhotoAlbum } from "./album.service";

@Component({
  moduleId: module.id,
  selector: 'app-photo-album',
  templateUrl: 'photo-album.component.html',
  styleUrls: ['photo-album.component.css'],
  providers: [AlbumService]
})
export class PhotoAlbumComponent implements OnInit {

  newPhotoAlbum:PhotoAlbum;

  constructor(private _albumnService:AlbumService) { }

  ngOnInit() {
    this.newPhotoAlbum=new PhotoAlbum("","","",new Date());
  }

}
