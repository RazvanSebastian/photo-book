import {EventEmitter, Component, OnInit, Input ,Output} from '@angular/core';
import { ROUTER_DIRECTIVES } from "@angular/router";
import {EmailMessage,EmailServiceService} from "./email-service.service";

@Component({
  moduleId: module.id,
  selector: 'app-home',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.css'],
  directives: [ROUTER_DIRECTIVES],
  providers:[EmailServiceService]
})
export class HomeComponent implements OnInit {

  padding:any;
  emailMessage:EmailMessage;

  constructor(private _emailService:EmailServiceService) { }

  ngOnInit() {
    this.emailMessage=new EmailMessage("","","","");
  var myDiv: HTMLDivElement;
    if(localStorage.getItem("X-AUTH-TOKEN") || sessionStorage.getItem("X-AUTH-TOKEN"))
      this.padding='paddingIn'
    else{
    this.padding='paddingOut';
  }
  }

  onSend(){
    this._emailService.sendEmailMessage(this.emailMessage).subscribe(
      data=>console.log(data),
      err=>console.log(err)
    );
  }

}
