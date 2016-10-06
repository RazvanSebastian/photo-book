import { Component, OnInit } from '@angular/core';
import { AlbumService,PhotoAlbum } from "../../components/photo-album/album.service";
import { LocalUserService } from "../global/local-service.service";
import { Photo } from "../photos/photo.service";
import { Router,ActivatedRoute ,Params} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-album-collection',
  templateUrl: 'album-collection.component.html',
  styleUrls: ['album-collection.component.css'],
  providers: [AlbumService]
})
export class AlbumCollectionComponent implements OnInit {

  albumGallery: Array<PhotoAlbum>;
  length:any;
  albumIdToDelete:number;
  active:boolean;
  userId:number;

  constructor(private _albumService:AlbumService, private _localService:LocalUserService, private _router:Router) {
    this.albumGallery=new Array<PhotoAlbum>();
  }

  ngOnInit() {
    this.onReceiveAlbums();
    this._localService.initiateByRememberMe();
  }

  onModalClose() {
    this._router.navigateByUrl("/account/my-collection/refresh");
  }

  onSucces(data){
    let album:PhotoAlbum;
    //Parsing a JSON to array of album
    JSON.parse(data.text()).forEach(album => {
    this.albumGallery.push(album);
    });
    this.length=this.albumGallery.length;
    console.log(this.albumGallery);
  }

  onReceiveAlbums(){
    this._albumService.receviceAllAlbums(this._localService.userId).subscribe(
      data => this.onSucces(data),
      err=>console.log(err)
    );
  }

  moveToPhotoGallery(album){
    this._router.navigate(['my-collection/'+album.id+'/photos']);
  }

  onAlbumSelected(id){
    this.albumIdToDelete=id;
  }

  onDeleteAlbum(){
    this._albumService.deleteAlbum(this.albumIdToDelete).subscribe(
      data=>{this.onModalClose()},
      err=>console.log(err)
    );
  }


}
