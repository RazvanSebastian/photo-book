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

  openModal: string;

  userName: string;
  newUserDetails: User;

  base64Key = "MDEyMzQ1Njc4OWFiY2RlZg==";

  constructor(private _userService: UserService) { }

  ngOnInit() {
    this.userName = localStorage.getItem("username");
    this.newUserDetails = new User('', "", "", "", null);
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


  /*------------------------------------------------------------------------------------------
  --------------------------- AES encryption using CryptoJS ----------------------------------
  ------------------------------------------------------------------------------------------*/
  checkPasswordFields() {
    if (this.newPassword == this.retypeNewPassword && this.newPassword !== "" && this.oldPassword !== "")
      return true;
    return false;
  }

  aesEncryptionAsBase64(password,ivBase64){

    function padding(password) {
      var padChar = ' ';
      var passPadd = password;
      var rest = passPadd.length % 16;
      var size = 16 - rest;
      for (var i = 1; i <= size; i++)
        passPadd += padChar;
      return passPadd;
    }

    var iv=CryptoJS.enc.Base64.parse("eVlKMlRoRHAwNTNUQUxSdw==");
    // this is the actual key as a sequence of bytes
    var key = CryptoJS.enc.Base64.parse(this.base64Key);
    // this is the plain text
    var plaintText = padding(password);
    // this is Base64-encoded encrypted data
    var encryptedData = CryptoJS.AES.encrypt(plaintText, key, {iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7
    });
    return encryptedData;
  }

  onSendEncryptedPasswords(data){
    //receive iv as base64 encoded
    var ivBase64=JSON.stringify(data._body);
    //check the fields
    // if(this.checkPasswordFields()==true){
      //encrypt old password and new password
      var encryptedOldPassword=this.aesEncryptionAsBase64(this.oldPassword,ivBase64);
      var encryptedNewPassword=this.aesEncryptionAsBase64(this.newPassword,ivBase64);
      //send iv as base64 , encrypted passwords and username
      this._userService.changePassword(ivBase64,encryptedOldPassword, encryptedNewPassword, this.userName).subscribe(
        data => console.log(data),
        err => console.log(err)
      );
//    }
  }

  onChangePassword() {
    //  this.newPasswordEncripted=CryptoJS.AES.encrypt(this.newPassword, this.key).toString();
    this._userService.getIv().subscribe(
      data =>this.onSendEncryptedPasswords(data),
      err =>console.log(err)
    );

  }
}
