import {  Directive,Component, OnInit } from '@angular/core';
import {SearchService} from "./search-service.service";

@Component({
  moduleId: module.id,
  selector: 'app-search-photo',
  templateUrl: 'search-photo.component.html',
  styleUrls: ['search-photo.component.css'],
  providers: [SearchService]
})
export class SearchPhotoComponent implements OnInit {

  date:string;
  category:string;
  search:string;

  constructor(private _searchService:SearchService) { }

  ngOnInit() {
  }

  onSearch(){
    console.log(this.date,this.category,this.search);
    this._searchService.receiveSearch(this.category, this.date, this.search).subscribe(
      data => console.log(data),
      err => console.log(err)
    );
  }
}
