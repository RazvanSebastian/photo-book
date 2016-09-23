import { Injectable } from '@angular/core';
import { Http, Headers, HTTP_PROVIDERS, RequestOptions,Response } from "@angular/http";

export class PhotoAlbum {
  constructor(
    public name: string,
    public description: string,
    public category: string,
    public date: Date
  ) { }
}

@Injectable()
export class AlbumService {

  constructor(private _http:Http) { }

  createNewAlbum(id,photoAlbum){
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    this._http.post("http://localhost:8080/api/account/"+id+"/photoAlbum", JSON.stringify(photoAlbum),options);
  }

}
