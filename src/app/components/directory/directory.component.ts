import { Component, OnInit } from '@angular/core';

// used to for the paramaters from ulr
import { ActivatedRoute } from '@angular/router';

@Component({
  moduleId: module.id,
  selector: 'app-directory',
  templateUrl: 'directory.component.html',
  styleUrls: ['directory.component.css']
})
export class DirectoryComponent implements OnInit {

  stringText = "Mama";
  myName: string;

  heroes: [
    { name: "razvan", type: "admin" },
    { name: "andrei", type: "user" },
    { name: "paul", type: "admin" }
  ];

  constructor(private route: ActivatedRoute) {
    this.myName = route.snapshot.params['myName'];
  }

  ngOnInit() {
  }

}
