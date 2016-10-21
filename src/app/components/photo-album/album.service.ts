import { Injectable } from '@angular/core';
import { Http, Headers, HTTP_PROVIDERS, RequestOptions,Response } from "@angular/http";
import { LocalUserService } from "../global/local-service.service";

export class PhotoAlbum {
  constructor(
    public id: any,
    public name: string,
    public description: string,
    public category: string,
    public date: Date,
    public coverImage: any
  ) { }
}

@Injectable()
export class AlbumService {

  constructor(private _http:Http,private _localService:LocalUserService) { }

  createNewAlbum(userId,photoAlbum){
    let headers = new Headers({ 'Content-Type': 'application/json' , "X-AUTH-TOKEN":localStorage.getItem("X-AUTH-TOKEN")});
    let options = new RequestOptions({ headers: headers });
    return this._http.post("http://localhost:8080/api/"+"account/"+userId+"/photoAlbum", JSON.stringify(photoAlbum),options);
  }

  receviceAllAlbums(userId){
    let headers = new Headers({"X-AUTH-TOKEN":localStorage.getItem("X-AUTH-TOKEN")});
    let options = new RequestOptions({ headers: headers })
    return this._http.get("http://localhost:8080/api/"+"account/"+userId+"/clientAlbums",options);
  }

  receiveAlbum(albumId){
    let headers = new Headers({ "X-AUTH-TOKEN":localStorage.getItem("X-AUTH-TOKEN") });
    let options = new RequestOptions({ headers: headers })
    return this._http.get("http://localhost:8080/api/"+"album/"+albumId+"/details",options);
  }

  deleteAlbum(albumId){
    let headers = new Headers({ "X-AUTH-TOKEN":localStorage.getItem("X-AUTH-TOKEN") });
    let options = new RequestOptions({ headers: headers })
    return this._http.delete("http://localhost:8080/api/"+"album-collection/delete-album/"+albumId,options);
  }

  receviceAllCategories(){
    let headers = new Headers({ "X-AUTH-TOKEN":localStorage.getItem("X-AUTH-TOKEN") });
    let options = new RequestOptions({ headers: headers })
    return this._http.get("http://localhost:8080/api/get-category",options);
  }
}
