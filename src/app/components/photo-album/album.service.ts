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
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    console.log(userId);
    console.log(photoAlbum);
    return this._http.post("http://localhost:8080/api/"+"account/"+userId+"/photoAlbum", JSON.stringify(photoAlbum),options);
  }

  receviceAllAlbums(userId){
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers })
    return this._http.get("http://localhost:8080/api/"+"account/"+userId+"/clientAlbums",headers);
  }

  receiveAlbum(albumId){
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers })
    return this._http.get("http://localhost:8080/api/"+"album/"+albumId+"/details",headers);
  }
}
