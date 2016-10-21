import { Component, OnInit } from '@angular/core';
import { AlbumService,PhotoAlbum } from "../../components/photo-album/album.service";
import { LocalUserService } from "../global/local-service.service";
import { Photo } from "../photos/photo.service";
import { Router,Route,ActivatedRoute ,Params} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-album-collection',
  templateUrl: 'album-collection.component.html',
  styleUrls: ['album-collection.component.css'],
  providers: [AlbumService]
})
export class AlbumCollectionComponent implements OnInit {
displayLoader:boolean=true;
  albumGallery: Array<PhotoAlbum>;
  length:any;
  albumIdToDelete:number;
  changes:boolean=true;
  userId:number;

  constructor(private _albumService:AlbumService, private _localService:LocalUserService, private _router:Router) {
    this.albumGallery=new Array<PhotoAlbum>();
  }

  ngOnInit() {
    this.onReceiveAlbums();
    this._localService.initiateByRememberMe();
  }

  onModalClose() {
    this.albumGallery.forEach((item,index)=>
    {
      if(item.id==this.albumIdToDelete)
        this.albumGallery.splice(index,1);
    }
  );
    console.log(this.albumGallery);
     //window.location.reload()
    var t=true;
    setTimeout(()=>{ t=false } ,10);

  }

  onSucces(data){
    //Parsing a JSON to array of album
    this.albumGallery=new Array<PhotoAlbum>();
    JSON.parse(data.text()).forEach(album => {
    this.albumGallery.push(album);
    });
    this.length=this.albumGallery.length;
    this.displayLoader=false;
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
