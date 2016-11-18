import {Component, Injectable} from '@angular/core';
import { CanActivate } from '@angular/router';
import { LocalUserService } from "../global/local-service.service";
import { Router } from "@angular/router";
import { UserService } from "../login/user.service";


@Injectable()
export class LoginGuard implements CanActivate {

  status:any;

  constructor(private _router:Router, private _loginService:UserService){}

  canActivate() {
    return this.checkIfLoggedIn();
  }

  onGetStatus(data){
    this.status=data.status;
  }

  getAccessLevel(){
    this._loginService.getAccessStatus().subscribe(
      data => this.onGetStatus(data)
    );
  }

  private checkIfLoggedIn(): boolean {
    // A call to the actual login service would go here
    // For now we'll just randomly return true or false
    this.getAccessLevel();
    if (this.status==202)
      return true;

    this._router.navigateByUrl("home");
    return false;
  }

}
