import { bootstrap } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { AppComponent, environment } from './app/';
import { APP_ROUTES_PROVIDER } from "./app/app-routes";
import { HTTP_PROVIDERS} from '@angular/http';
import { LoginGuard } from "./app/components/global/LoginGuard.service";
import { UserService } from "./app/components/login/user.service";
import {AlbumService} from "./app/components/photo-album/album.service";
import {LocalUserService} from "./app/components/global/local-service.service";

if (environment.production) {
  enableProdMode();
}

bootstrap(AppComponent, [APP_ROUTES_PROVIDER,HTTP_PROVIDERS,LoginGuard,UserService,AlbumService,LocalUserService]);
