import { Injectable } from '@angular/core';
import { Http, Headers, HTTP_PROVIDERS, RequestOptions,Response} from '@angular/http';

export class UserLogin{
  constructor(
    public username:string,
    public password:string
  ){}
}

@Injectable()
export class LoginService {

  constructor(private _http : Http) { }

  loginUser(user){
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers })
    return this._http.post('http://localhost:8080/api/login', JSON.stringify(user));
  }
}
