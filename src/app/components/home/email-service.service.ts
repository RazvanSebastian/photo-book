import { Injectable } from '@angular/core';
import {Http,Headers,RequestOptions} from "@angular/http";

export class EmailMessage{
  constructor(
    name:String,
    email:String,
    company:String,
    message:String
  )
  {}
}

@Injectable()
export class EmailServiceService {

  constructor(private _http:Http) { }

  sendEmailMessage(emailMessage)
  {
    let headers=new Headers({"Email":emailMessage.email,"Name":emailMessage.name,"Company":emailMessage.company,"Message":emailMessage.message});
    let options = new RequestOptions({headers:headers});
    return this._http.head("http://localhost:8080/api/send-email",options);
  }
}
