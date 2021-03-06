import { provideRouter, RouterConfig } from '@angular/router';

//Here we are importing the componenrets used to routing
import {HomeComponent} from "./components/home/home.component";
import {RegisterComponent} from "./components/register/register.component";
import {LoginComponent} from "./components/login/login.component";
import { AppComponent } from "./app.component";
import {PhotoAlbumComponent } from "./components/photo-album/photo-album.component";
import { AlbumCollectionComponent } from "./components/album-collection/album-collection.component";
import { PhotosComponent } from "./components/photos/photos.component";
import {NewPhotoComponent} from "./components/new-photo/new-photo.component";
import { SearchPhotoComponent } from "./components/search-photo/search-photo.component";
import {AccountDetailsComponent } from "./components/account-details/account-details.component";
//LoginGuard imported to secure my routes
import { LoginGuard } from "./components/global/LoginGuard.service";

export const APP_ROUTES = [

  //here we are declaring the path in URL and the component to load
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'account/:id/new-album', component: PhotoAlbumComponent, canActivate: [LoginGuard] },

  { path: 'account/:id/my-collection', component: AlbumCollectionComponent, canActivate: [LoginGuard] },
  { path: 'account/my-collection/refresh', component: AlbumCollectionComponent, canActivate: [LoginGuard] },

  { path: 'my-collection/:id/photos', component: PhotosComponent, canActivate: [LoginGuard] },
  { path: 'my-collection/photos/refresh', component: PhotosComponent, canActivate: [LoginGuard] },

  { path: 'my-account', component: AccountDetailsComponent, canActivate: [LoginGuard] },

  { path: 'my-album/:id/new-photo', component: NewPhotoComponent, canActivate: [LoginGuard] },
  {path: 'search-photos',component: SearchPhotoComponent, canActivate: [LoginGuard] },
  { path: '', component: HomeComponent, useAsDefault: true }
];

// the class which is imported in the maint.ts
//  we can declarering in @Component, the directive : ROUTER_DIRECTIVE to made the routing main
export const APP_ROUTES_PROVIDER = [
  provideRouter(APP_ROUTES)
];
