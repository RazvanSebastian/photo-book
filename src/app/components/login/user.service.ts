import { Injectable ,OnInit} from '@angular/core';
import { Http, Headers, HTTP_PROVIDERS, RequestOptions,Response} from '@angular/http';
import { User } from "../register/registration.service";
import {LocalUserService} from "../global/local-service.service";

export class UserLogin{
  constructor(
    public username:string,
    public password:string
  ){}
}

@Injectable()
export class UserService {


  constructor(private _http : Http) {
    }


  getAccessStatus(){
    let headers = new Headers({"X-AUTH-TOKEN":(localStorage.getItem("X-AUTH-TOKEN")?localStorage.getItem("X-AUTH-TOKEN"):sessionStorage.getItem("X-AUTH-TOKEN"))});
    let options = new RequestOptions({headers:headers});
    return this._http.head("http://localhost:8080/api/login-guard/check",options);
  }

  loginUser(user){
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers })
    return this._http.post('http://localhost:8080/api/login', JSON.stringify(user));
  }

  updatePersonalInfo(user:User,userName){
    let headers = new Headers({'UserName':userName, 'Content-Type': 'application/json',"X-AUTH-TOKEN":(localStorage.getItem("X-AUTH-TOKEN")?localStorage.getItem("X-AUTH-TOKEN"):sessionStorage.getItem("X-AUTH-TOKEN"))});
    let options = new RequestOptions({headers:headers});
    return this._http.put('http://localhost:8080/api/account/change-personal-info',JSON.stringify(user),options);
  }

  changePassword(ivBase64:string,oldPassword,newPasswordEncripted,userName:string){
    let headers = new Headers({"required":true,"IV":ivBase64,"UserName":userName,"OldPassword":oldPassword,"NewPassword":newPasswordEncripted,"X-AUTH-TOKEN":(localStorage.getItem("X-AUTH-TOKEN")?localStorage.getItem("X-AUTH-TOKEN"):sessionStorage.getItem("X-AUTH-TOKEN"))});
    let options = new RequestOptions({headers:headers});
    return this._http.put('http://localhost:8080/api/account/change-password',JSON.stringify(ivBase64),options);
  }
  getIv(){
    let headers = new Headers({"required":false,"X-AUTH-TOKEN":(localStorage.getItem("X-AUTH-TOKEN")?localStorage.getItem("X-AUTH-TOKEN"):sessionStorage.getItem("X-AUTH-TOKEN"))});
    let options = new RequestOptions({headers:headers});
    return this._http.get("http://localhost:8080/api/account/change-password",options);
  }

  onResetPassword(email){
    let headers = new Headers({'Content-Type':'application/json','email':email});
    let options=new RequestOptions({headers:headers});
    return this._http.put("http://localhost:8080/api/reset-password",JSON.stringify(""),options);

  }
}
