import { Injectable } from '@angular/core';
import { Http, Headers, HTTP_PROVIDERS, RequestOptions} from '@angular/http';

export class User{

  constructor(
    public email : string,
		public password : string,
		public firstName : string,
		public lastName : string,
		public birthDay : Date,
		public avatar : any
  ){}
}

@Injectable()
export class RegistrationService {

  //Http need injected in constructor
  constructor(private http: Http) { }

  registerUser(user) {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post('http://localhost:8080/api/register', JSON.stringify(user), options);
  }
}
