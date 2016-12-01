import { Component, OnInit  } from '@angular/core';
import {PhotoService,Photo} from '../photos/photo.service';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
  moduleId: module.id,
  selector: 'app-new-photo',
  templateUrl: 'new-photo.component.html',
  styleUrls: ['new-photo.component.css'],
  providers : [PhotoService]
})
export class NewPhotoComponent implements OnInit  {

    albumId:any;
    newPhoto:Photo;
    imageSrc:any;
    message:string;
    status:boolean;

    constructor(private route: ActivatedRoute,private router: Router, private _photoService: PhotoService) {
    }

  ngOnInit() {
    this.newPhoto=new Photo(1,"","","",0,0,new Date,'');
    this.route.params.forEach((params: Params) => {
      this.albumId = +params['id']; // (+) converts string 'id' to a number
    });
  }

  onSucces(data){
    this.status=true;
    this.router.navigateByUrl("my-collection/"+this.albumId+"/photos");
  }
  onFail(err){
    console.log(err);
    this.status=false;
    this.message=err._body;
  }

  onSaveNewPhoto(){
    this._photoService.saveNewPhoto(this.albumId,this.newPhoto).subscribe(
      data=>this.onSucces(data),
      err=> this.onFail(err)
    );
  }

  attachFile(event) {
    var reader = new FileReader();
    let _self = this;

    reader.onload = function(e) {
      _self.imageSrc = reader.result;
      _self.newPhoto.image = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
  }

}
