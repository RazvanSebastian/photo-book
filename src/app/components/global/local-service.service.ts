import { Injectable, Inject  } from '@angular/core';
import { Http, Headers, HTTP_PROVIDERS, RequestOptions } from '@angular/http';

@Injectable()
export class LocalUserService {
  public tokenName: string = "X-AUTH-TOKEN";
  public headerTokenName: string = "X-AUTH-TOKEN";
  public email: string;
  public authority: any = [];
  public token: string;
  public image: any;
  public userId: string;
  public rememberMe: boolean = false;


  initiateByRememberMe() {
    if (localStorage.getItem(this.tokenName) && localStorage.getItem("REMEMBER-ME")) {
      sessionStorage.setItem(this.tokenName, localStorage.getItem(this.tokenName));
      this.rememberMe = true;
    }
    else {
      this.clearData();
    }
    this.userId = localStorage.getItem("userId");
    this.token = sessionStorage.getItem(this.tokenName);
    this.email = localStorage.getItem("email");
    if (localStorage.getItem("authority") != null)
      this.authority = localStorage.getItem("authority").split(",");
  }

  clearData() {
    localStorage.removeItem(this.tokenName);
    localStorage.removeItem("REMEMBER-ME");
    localStorage.clear();
    this.rememberMe = false;
  }

  logOut() {
    sessionStorage.removeItem(this.tokenName);
    this.token = "";
    this.clearData();
  }

  userDetailsStoring(token: string, userDetails: any, remember: boolean) {

    this.userId = userDetails.id;
    localStorage.setItem("userId", this.userId);

    this.email = userDetails.email;
    localStorage.setItem("email", this.email);

    this.authority = userDetails.roles;
    console.log(userDetails.roles[0]);

    // this.image=userDetails.image;

    this.token = token;
    sessionStorage.setItem(this.tokenName, this.token);

    this.rememberMe = remember;
    if (remember) {
      localStorage.setItem(this.tokenName, token);
      localStorage.setItem("REMEMBER-ME", "true");
    }
    else{
      sessionStorage.setItem(this.tokenName, token);

    }
  }
}
