import { Component, Input, OnInit} from '@angular/core';
import { HomeComponent} from './components/home/index';
import { ROUTER_DIRECTIVES, Router} from "@angular/router";
import { LocalUserService} from "../app/components/global/local-service.component"


@Component({
  moduleId: module.id,
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'],
  directives: [HomeComponent, ROUTER_DIRECTIVES],
  providers: [LocalUserService]
})
export class AppComponent implements OnInit {

  title = 'app works!';
  switchBool: boolean;
  loginAsUser: boolean;

  constructor(private _router: Router, private _localService: LocalUserService) { };

  ngOnInit() {
    this.switchBool = true;
    this._localService.initiateByRememberMe();
    if (this._localService.token)
      this.loginAsUser = true;
    else
      this.loginAsUser = false;
  }


  toLogin() {
    this.switchBool = false;
    this._router.navigateByUrl("/login");
  }

  toRegister() {
    this.switchBool = false;
    this._router.navigateByUrl("/register");
  }

  toMyAccount() {
  }

  toSignOut() {
    this.switchBool = false;
    this.loginAsUser=false;
    this._localService.logOut();
    this._router.navigateByUrl("");
  }
}
