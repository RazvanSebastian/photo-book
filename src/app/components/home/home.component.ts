import {EventEmitter, Component, OnInit, Input, Output} from '@angular/core';
import { ROUTER_DIRECTIVES } from "@angular/router";
import {EmailMessage, EmailServiceService} from "./email-service.service";
import { HomeService } from "./home-service.service";
import { RuntimeCompiler} from '@angular/compiler';

@Component({
  moduleId: module.id,
  selector: 'app-home',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.css'],
  directives: [ROUTER_DIRECTIVES],
  providers: [HomeService,EmailServiceService]
})
export class HomeComponent implements OnInit {

  status: any;
  emailSenderErrorMessage: string;
  padding: any;
  emailMessage: EmailMessage;
  target: any;

  statsNumbers : number[];
  clients:number;downloads:number;photos:number;albums:number;

  constructor(private _emailService: EmailServiceService,private _homeService:HomeService,
  private _templateCompiler:RuntimeCompiler) { }

  ngOnInit() {
    this._templateCompiler.clearCache();
    this.emailMessage = new EmailMessage("", "", "", "");
    var myDiv: HTMLDivElement;
    if (localStorage.getItem("X-AUTH-TOKEN") || sessionStorage.getItem("X-AUTH-TOKEN"))
      this.padding = 'paddingIn'
    else {
      this.padding = 'paddingOut';
    }

    this.getAllStatsNumbers();
  }

  onEmailSendSuccess(data) {
    console.log(data.status);
    this.emailSenderErrorMessage = "Send email success!";
  }
  onEmailSendFail(err) {
    console.log(err.status);
    this.emailSenderErrorMessage = "You can send maxim 3 email/day! Wait 24 hours while your rights are reset!"
  }

  checkEmailFields() {
    if (this.emailMessage.name === "")
      return false;
    if (this.emailMessage.email === "")
      return false;
    if (this.emailMessage.company === "")
      return false;
    if (this.emailMessage.message === "")
      return false;
    return true;
  }

  onSend() {
    if (this.checkEmailFields()==false) {
      this.emailSenderErrorMessage = "There are empty fields!";
    }
    else {
      this._emailService.sendEmailMessage(this.emailMessage).subscribe(
        data => this.onEmailSendSuccess(data),
        err => this.onEmailSendFail(err)
      );
    }
  }

  arrayStoreOnSuccess(data){
    this.statsNumbers=JSON.parse(data._body);
    this.clients=this.statsNumbers[0];
    this.downloads=this.statsNumbers[1];
    this.photos=this.statsNumbers[3];
    this.albums=this.statsNumbers[2];
  }

  getAllStatsNumbers(){
    this._homeService.getStatsNumbers().subscribe(
      data => this.arrayStoreOnSuccess(data),
      err => console.log(err)
    );
  }

}
