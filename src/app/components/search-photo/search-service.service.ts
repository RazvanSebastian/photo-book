import { Injectable } from '@angular/core';
import { Http} from "@angular/http";

@Injectable()
export class SearchService {

  constructor(private _http: Http) { }

  receiveSearch(category, date, search) {
    return this._http.get('http://localhost:8080/api/' + 'search-photo/category=' + category + '/date=' + date + '/search=' + search);
  }
}
