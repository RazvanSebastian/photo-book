import {EventEmitter, Component, OnInit, Input ,Output} from '@angular/core';
import { ROUTER_DIRECTIVES } from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-home',
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.css'],
  directives: [ROUTER_DIRECTIVES]
})
export class HomeComponent implements OnInit {

  padding:any;

  constructor() { }

  ngOnInit() {
  var myDiv: HTMLDivElement;
    if(localStorage.getItem("X-AUTH-TOKEN") || sessionStorage.getItem("X-AUTH-TOKEN"))
      this.padding='paddingIn'
    else{
    this.padding='paddingOut';
  }
  }

}
