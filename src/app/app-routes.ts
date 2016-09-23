import { provideRouter, RouterConfig } from '@angular/router';

//Here we are importing the componenrets used to routing
import {HomeComponent} from "./components/home/home.component";
import {RegisterComponent} from "./components/register/register.component";
import {LoginComponent} from "./components/login/login.component";
import { AppComponent } from "./app.component";
import {PhotoAlbumComponent } from "./components/photo-album/photo-album.component";
import { LoginGuard } from "./components/global/LoginGuard.service";

export const APP_ROUTES = [

  //here we are declaring the path in URL and the component to load
  { path: 'home', component: HomeComponent ,/*canActivate:[LoginGuard]*/},
  { path: 'register', component: RegisterComponent },
  { path: 'login',component : LoginComponent },
  // {path: 'account/:id',component:MyAccountComponent,canActivate:[LoginGuard]},
   {path: 'account/:id/new-album',component:PhotoAlbumComponent,canActivate:[LoginGuard]},
  {path: '' , component : HomeComponent , useAsDefault: true }
];

// the class which is imported in the maint.ts
//  we can declarering in @Component, the directive : ROUTER_DIRECTIVE to made the routing main
export const APP_ROUTES_PROVIDER = [
  provideRouter(APP_ROUTES)
];
