
import { Injectable } from '@angular/core';
import {Http} from "@angular/http";

@Injectable()
export class HomeService{

  constructor(private _http:Http){}

  getStatsNumbers(){
    return this._http.get("http://localhost:8080/api/stats");
  }

}
