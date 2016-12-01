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

  // forgot paasword fields
  email:string;
  responseText:string;
  statusResponse:number=0;
  color:string;

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

    //here we are storing the user details in localStorage
    this. _localService.userDetailsStoring(token, userDetails, this.remember);

    this.loginStatus = true;
    // this._router.navigateByUrl("/home");
     window.location.replace('/home');
  }

  onLogFailed(error) {
    this.loginStatus = false;
  }

  onLogin() {
    this._loginService.loginUser(this.userLogin).
      subscribe(
      data => this.onLoginSucces(data),
      err => this.onLogFailed(err)
      );
  }

  onResetResponse(data){
    if(data.status==200){
      this.statusResponse=200;
      this.responseText="Your password was changed! Please check your email!";
      this.color="green";
    }

  }
  onResetFail(err){
    if(err.status==406){
      this.statusResponse=406;
      this.responseText=err._body;
      this.color="red";
    }
    if(err.status==400){
      this.color="red";
      this.statusResponse=400;
      this.responseText="This email is invalid!";
    }
  }

  forgotPassword(){
    this._loginService.onResetPassword(this.email).subscribe(
      data => this.onResetResponse(data),
      err=>this.onResetFail(err)
    );

    }
}
