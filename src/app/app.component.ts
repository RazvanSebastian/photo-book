import { Component, Input, OnInit} from '@angular/core';
import { HomeComponent} from './components/home/index';
import { ROUTER_DIRECTIVES, Router} from "@angular/router";
import { LocalUserService} from "../app/components/global/local-service.service";


@Component({
  moduleId: module.id,
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'],
  directives: [HomeComponent, ROUTER_DIRECTIVES],
  providers: [LocalUserService]
})
export class AppComponent implements OnInit {

  loginAsUser: boolean;
  userId : any;

  constructor(private _router: Router, private _localService: LocalUserService) { };

  ngOnInit() {
    this._localService.initiateByRememberMe();
    if (localStorage.getItem("X-AUTH-TOKEN") || sessionStorage.getItem("X-AUTH-TOKEN")){
      this.loginAsUser = true;
      this.userId=this._localService.userId;
    }
    else
      this.loginAsUser = false;
  }

  toMyAccount() {
  }

  toSignOut() {
    this.loginAsUser=false;
    this._localService.logOut();
    this._router.navigateByUrl("/home");
  }
}
