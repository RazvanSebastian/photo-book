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

  getPageNumber(id){
    let headers = new Headers({ "X-AUTH-TOKEN":(localStorage.getItem("X-AUTH-TOKEN")?localStorage.getItem("X-AUTH-TOKEN"):sessionStorage.getItem("X-AUTH-TOKEN")) });
    let options = new RequestOptions({ headers: headers });
    return this._http.get('http://localhost:8080/api/'+'photo-number/album='+id,options);
  }

  saveNewPhoto(idAlbum,photo){
    let headers = new Headers({'Content-Type': 'application/json',"X-AUTH-TOKEN":(localStorage.getItem("X-AUTH-TOKEN")?localStorage.getItem("X-AUTH-TOKEN"):sessionStorage.getItem("X-AUTH-TOKEN")) });
    let options=new RequestOptions({headers:headers});
    return this._http.post('http://localhost:8080/api/'+'my-album/'+idAlbum+'/new-photo',JSON.stringify(photo),options);
  }

  getPhotoGallery(idAlbum,pageNumb){
      let headers = new Headers({ "X-AUTH-TOKEN":(localStorage.getItem("X-AUTH-TOKEN")?localStorage.getItem("X-AUTH-TOKEN"):sessionStorage.getItem("X-AUTH-TOKEN"))  });
      let options = new RequestOptions({ headers: headers });
      return this._http.get("http://localhost:8080/api/"+"my-album/"+idAlbum+"/client-photos/page="+pageNumb,options);
  }

  deletePhotoSelected(idPhoto){
    let headers = new Headers({"X-AUTH-TOKEN":(localStorage.getItem("X-AUTH-TOKEN")?localStorage.getItem("X-AUTH-TOKEN"):sessionStorage.getItem("X-AUTH-TOKEN"))  });
    let options = new RequestOptions({ headers: headers });
    return this._http.delete("http://localhost:8080/api/"+"my-album/delete-photo/"+idPhoto,options);
  }

  receiveSearch(category, date, search) {
    let headers = new Headers({ 'Content-Type': 'application/json' ,"X-AUTH-TOKEN":(localStorage.getItem("X-AUTH-TOKEN")?localStorage.getItem("X-AUTH-TOKEN"):sessionStorage.getItem("X-AUTH-TOKEN"))});
    let options = new RequestOptions({ headers: headers });
    return this._http.get('http://localhost:8080/api/' + 'search-photo/category=' + category + '/date=' + date + '/search=' + search,options);
  }

  getOriginalPhoto(id){
    let headers = new Headers({ 'Content-Type': 'application/json' ,"X-AUTH-TOKEN":(localStorage.getItem("X-AUTH-TOKEN")?localStorage.getItem("X-AUTH-TOKEN"):sessionStorage.getItem("X-AUTH-TOKEN"))});
    let options = new RequestOptions({ headers: headers });
    return this._http.get('http://localhost:8080/api/'+'my-album/download/original='+id,options);
  }
}
