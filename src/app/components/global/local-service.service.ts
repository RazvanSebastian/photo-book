import { Injectable, Inject  } from '@angular/core';
import { Http, Headers, HTTP_PROVIDERS, RequestOptions } from '@angular/http';



@Injectable()
export class LocalUserService {
  public tokenName: string = "X-AUTH-TOKEN";
  public headerTokenName: string = "X-AUTH-TOKEN";
  public username: string;
  public email: string;
  public authority: any = [];
  public token: string;
  public image: any;
  public userId: string;
  public rememberMe: boolean = false;


  initiateByRememberMe() {
    if (localStorage.getItem(this.tokenName)) {
      this.username = localStorage.getItem("username");
      this.userId = localStorage.getItem("userId");
      this.token = localStorage.getItem(this.tokenName);
      this.email = localStorage.getItem("email");
      if (localStorage.getItem("authority") != null)
        this.authority = localStorage.getItem("authority").split(",");
    }
    if (sessionStorage.getItem(this.tokenName)) {
      this.username = sessionStorage.getItem("username");
      this.userId = sessionStorage.getItem("userId");
      this.token = sessionStorage.getItem(this.tokenName);
      this.email = sessionStorage.getItem("email");
      if (sessionStorage.getItem("authority") != null)
        this.authority = sessionStorage.getItem("authority").split(",");
    }
  }

  clearData() {
    localStorage.removeItem(this.tokenName);
    localStorage.removeItem("REMEMBER-ME");
    localStorage.clear();
    sessionStorage.clear();
    this.rememberMe = false;
  }

  logOut() {
    this.token = "";
    this.clearData();
  }

  userDetailsStoring(token: string, userDetails: any, remember: boolean) {

    this.userId = userDetails.id;
    this.username = userDetails.username;
    this.email = userDetails.email;
    this.authority = userDetails.roles;
    // this.image=userDetails.image;
    this.token = token;
    this.rememberMe = remember;

    if (remember == true) {
      localStorage.setItem("username", this.username);
      localStorage.setItem("userId", this.userId);
      localStorage.setItem("email", this.email);
      localStorage.setItem(this.tokenName, token);
      localStorage.setItem('authority', JSON.stringify(this.authority));
      localStorage.setItem("REMEMBER-ME", "true");
    }
    else {
      sessionStorage.setItem("username", this.username);
      sessionStorage.setItem("userId", this.userId);
      sessionStorage.setItem("email", this.email);
      sessionStorage.setItem(this.tokenName, token);
      sessionStorage.setItem('authority', JSON.stringify(this.authority));
      sessionStorage.setItem("REMEMBER-ME", "false");
    }
  }
}
