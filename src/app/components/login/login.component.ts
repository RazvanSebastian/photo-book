import { Component, OnInit, Input } from '@angular/core';
import { ROUTER_DIRECTIVES, Router } from '@angular/router';
import { UserService, UserLogin} from './user.service';
import { LocalUserService } from '../../components/global/local-service.service';

@Component({
  moduleId: module.id,
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.css'],
  directives: [ROUTER_DIRECTIVES],
  providers: [UserService,LocalUserService]
})
export class LoginComponent implements OnInit {

  userLogin: UserLogin;
  loginStatus: boolean = true;
  loggedIn: boolean = false;
  remember: boolean = false;
  constructor(private _loginService: UserService, private _router: Router, private _localService:LocalUserService) { }

  ngOnInit() {
    this.userLogin = new UserLogin("", "");
  }

  onLoginSucces(response) {
    console.log(response.header);
    //We are extracting the token(encrypted) and after we are pasring to JSON and recevice user details(email, autohiry, etc)
    let tokenName = this. _localService.headerTokenName;
    let token = response.headers._headersMap.get(tokenName);
    let userDetails = JSON.parse(response.headers._headersMap.get("USER-DETAILS"));

    //here we are store the user details in localStorage
    this. _localService.userDetailsStoring(token, userDetails, this.remember);

    this.loginStatus = true;
    // this._router.navigateByUrl("/home");
     window.location.replace('/home');
  }

  onLogFailed(error) {
    console.log(error.status);
    this.loginStatus = false;
  }

  onLogin() {
    this._loginService.loginUser(this.userLogin).
      subscribe(
      data => this.onLoginSucces(data),
      err => this.onLogFailed(err)
      );
  }
}
