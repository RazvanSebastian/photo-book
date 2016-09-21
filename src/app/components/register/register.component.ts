import { Component, OnInit } from '@angular/core';
import {RegistrationService, User} from './registration.service'
import { ROUTER_DIRECTIVES, Router } from '@angular/router';

@Component({
  moduleId: module.id,
  selector: 'app-register',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.css'],
  providers: [RegistrationService],
  directives: [ROUTER_DIRECTIVES]
})
export class RegisterComponent implements OnInit {

  user: User;
  date: Date;
  validation: boolean = true;
  validationMessage: string;
  secondPassword: string;
  imageSrc: any;

  constructor(private _registerService: RegistrationService, private _router: Router) { }

  ngOnInit() {
    this.user = new User("", "", "", "", this.date, "avatar");
  }

  onRegisterSucces(data) {
    this.validation = true;
    this._router.navigateByUrl("/login");
  }

  onRegisterFailed(error) {
    this.validation = false;
    this.validationMessage = error._body;
  }

  validationFields() {
    if (this.user.email === "" || this.user.password === "" || this.user.firstName === "" ||
      this.user.lastName === "" || this.user.birthDay === null) {
      this.validationMessage = "All input are required!";
      this.validation = false;
      return;
    }
    if (this.user.password !== this.secondPassword) {
      this.validationMessage = "The passwords are not same!";
      this.validation = false;
      return;
    }
    this.validation = true;
  }

  registerUser() {
    this.validationFields();
    console.log(this.validation);
    if (this.validation == true) {
      this._registerService.registerUser(this.user)
        .subscribe(
        data => this.onRegisterSucces(data),
        error => this.onRegisterFailed(error)
        // TODO implement
        );
    }
  }

  attachFile(event) {

    var reader = new FileReader();
    let _self = this;
    _self.imageSrc = reader.result;

    reader.onload = function(e) {
      _self.imageSrc = reader.result;
      _self.user.avatar=reader.result;
    };

    console.log(this.user.avatar);
    reader.readAsDataURL(event.target.files[0]);
  }

}
