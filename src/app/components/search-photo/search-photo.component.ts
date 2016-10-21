import {  Directive, Component, OnInit } from '@angular/core';
import {PhotoService, Photo} from "../photos/photo.service";
import {AlbumService } from "../photo-album/album.service";

@Component({
  moduleId: module.id,
  selector: 'app-search-photo',
  templateUrl: 'search-photo.component.html',
  styleUrls: ['search-photo.component.css'],
  providers: [PhotoService, AlbumService]
})
export class SearchPhotoComponent implements OnInit {

  displayLoader:boolean=false;

  date: string;
  category: string;
  search: string;

  categories: Array<String>;
  photoGallery: Array<Photo>;

  checkStatus: boolean = false;
  ErrorMessageIsVisible: boolean;

  constructor(private _searchService: PhotoService, private _albumService: AlbumService) { }

  ngOnInit() {
    this.date = "";
    this.category = "";
    this.search = "";
    this.categories = new Array<String>();
    this.getAlbumCategories();
    this.photoGallery = new Array<Photo>();
  }

  initCategoryList(data) {
    JSON.parse(data.text()).forEach(category => {
      this.categories.push(category);
    });
  }

  getAlbumCategories() {
    this._albumService.receviceAllCategories().subscribe(
      data => this.initCategoryList(data),
      err => console.log(err)
    );
  }

  onSucces(data) {
    this.displayLoader=false;
    this.photoGallery = new Array<Photo>();
    JSON.parse(data.text()).forEach(photo => {
      this.photoGallery.push(photo);
    });
  }

  checkInputs() {
    if (this.date === "" && this.category === "" && this.search === "")
      return false;
    return true;
  }

  onSearch() {
    if (this.checkInputs() == true) {
      this.displayLoader=true;
      this._searchService.receiveSearch(this.category, this.date, this.search).subscribe(
        data => this.onSucces(data),
        err => this.showErrorMessage()
      );
    }
    else
      this.showErrorMessage();
  }

  showErrorMessage() { this.ErrorMessageIsVisible = true; }

  hideErrorMsg() { this.ErrorMessageIsVisible = false; }
}
