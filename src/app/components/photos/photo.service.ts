import { Injectable } from '@angular/core';
import { Http, Headers, HTTP_PROVIDERS, RequestOptions,Response } from "@angular/http";

export class Photo{
  constructor(
    public id:number,
    public name:string,
    public description:string,
    public category:string,
    public visualisations:any,
    public rating:any,
    public date:Date,
    public image:any
  ){}
}

@Injectable()
export class PhotoService {

  constructor(private _http:Http) { }

  saveNewPhoto(idAlbum,photo){
    let headers = new Headers({'Content-Type': 'application/json'});
    let options=new RequestOptions({headers:headers});
    return this._http.post('http://localhost:8080/api/'+'my-album/'+idAlbum+'/new-photo',JSON.stringify(photo),options);
  }

  getPhotoGallery(idAlbum){
      let headers = new Headers({ 'Content-Type': 'application/json' });
      let options = new RequestOptions({ headers: headers })
      return this._http.get("http://localhost:8080/api/"+"my-album/"+idAlbum+"/client-photos",headers);
  }

  deletePhotoSelected(idPhoto){
    return this._http.delete("http://localhost:8080/api/"+"my-album/delete-photo/"+idPhoto);
  }
}
