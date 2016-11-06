import { Injectable} from '@angular/core';
import { CanActivate } from '@angular/router';
import { LocalUserService } from "../global/local-service.service";
import { Router } from "@angular/router";

@Injectable()
export class LoginGuard implements CanActivate {

  constructor(private _router:Router){}

  canActivate() {
    return this.checkIfLoggedIn();
  }

  private checkIfLoggedIn(): boolean {
    // A call to the actual login service would go here
    // For now we'll just randomly return true or false
    if (sessionStorage.getItem("X-AUTH-TOKEN") || localStorage.getItem("X-AUTH-TOKEN"))
      return true;
    this._router.navigateByUrl("");
    return false;
  }

}
