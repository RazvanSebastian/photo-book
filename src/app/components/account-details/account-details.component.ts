import { Component, OnInit } from '@angular/core';
import {UserService, UserLogin} from "../login/user.service";
import { User} from "../register/registration.service";
import * as CryptoJS from 'crypto-js';



@Component({
  moduleId: module.id,
  selector: 'app-account-details',
  templateUrl: 'account-details.component.html',
  styleUrls: ['account-details.component.css'],
  providers: [UserService]
})
export class AccountDetailsComponent implements OnInit {

  oldPassword: string;
  newPassword: string;
  retypeNewPassword: string;
  newPasswordEncripted: string;

  openModal: string;

  userName: string;
  newUserDetails: User;


  constructor(private _userService: UserService) { }

  ngOnInit() {
    this.userName = localStorage.getItem("username");
    this.newUserDetails = new User('', "", "", "", null);

    function padding(password) {
      var padChar = ' ';
      var passPadd = password;
      var rest = passPadd.length % 16;
      var size = 16 - rest;
      for (var i = 1; i <= size; i++)
        passPadd += padChar;
      return passPadd;
    }

    var base64Key = "MDEyMzQ1Njc4OWFiY2RlZg==";
    console.log( "base64Key = " + base64Key );

    // this is the actual key as a sequence of bytes
    var key = CryptoJS.enc.Base64.parse(base64Key);
    console.log( "key = " + key );

    // this is the plain text
    var plaintText = "Hello, World!";
    console.log( "plaintText = " + plaintText );

    // this is Base64-encoded encrypted data
    var encryptedData = CryptoJS.AES.encrypt(plaintText, key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    console.log( "encryptedData = " + encryptedData );

    // this is the decrypted data as a sequence of bytes
    var decryptedData = CryptoJS.AES.decrypt( encryptedData, key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    } );
    console.log( "decryptedData = " + decryptedData );

    // this is the decrypted data as a string
    var decryptedText = decryptedData.toString( CryptoJS.enc.Utf8 );
    console.log( "decryptedText = " + decryptedText );



  }

  checkProfileInput() {
    if (this.newUserDetails.email === "" && this.newUserDetails.firstName === "" && this.newUserDetails.lastName === "" && this.newUserDetails.birthDay == null)
      return false;
    return true;
  }

  onChangeProfile() {
    if (this.checkProfileInput() == true) {
      this.openModal = "Your profile is update!";
      this._userService.updatePersonalInfo(this.newUserDetails, this.userName).subscribe(
        data => console.log(data),
        err => console.log(err)
      );
      this.newUserDetails = new User('', "", "", "", null);
    }
    else
      this.openModal = "You must complete at least one field!";
  }

  checkPasswordFields() {
    if (this.newPassword == this.retypeNewPassword && this.newPassword !== "" && this.oldPassword !== "")
      return true;
    return false;
  }

  onChangePassword() {
    //  this.newPasswordEncripted=CryptoJS.AES.encrypt(this.newPassword, this.key).toString();
    this._userService.changePassword("this.oldPassword", this.newPasswordEncripted, this.userName).subscribe(
      data => console.log(data),
      err => console.log(err)
    );
  }
}
