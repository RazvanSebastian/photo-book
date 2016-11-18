import { Injectable } from '@angular/core';
import { Http, Headers, HTTP_PROVIDERS, RequestOptions,Response} from '@angular/http';
import { User } from "../register/registration.service";

export class UserLogin{
  constructor(
    public username:string,
    public password:string
  ){}
}

@Injectable()
export class UserService {

  constructor(private _http : Http) { }

  getAccessStatus(){
    let headers = new Headers({"X-AUTH-TOKEN":localStorage.getItem("X-AUTH-TOKEN")});
    let options = new RequestOptions({headers:headers});
    return this._http.head("http://localhost:8080/api/login-guard/check",options);
  }

  loginUser(user){
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers })
    return this._http.post('http://localhost:8080/api/login', JSON.stringify(user));
  }

  updatePersonalInfo(user:User,userName){
    let headers = new Headers({'UserName':userName, 'Content-Type': 'application/json',"X-AUTH-TOKEN":localStorage.getItem("X-AUTH-TOKEN")});
    let options = new RequestOptions({headers:headers});
    return this._http.put('http://localhost:8080/api/account/change-personal-info',JSON.stringify(user),options);
  }

  changePassword(oldPassword,newPasswordEncripted,userName){
    let headers = new Headers({"UserName":userName,"OldPassword":oldPassword,"NewPassword":newPasswordEncripted,"X-AUTH-TOKEN":localStorage.getItem("X-AUTH-TOKEN")});
    let options = new RequestOptions({headers:headers});
    return this._http.put('http://localhost:8080/api/account/change-password',JSON.stringify(""),options);
  }
}
