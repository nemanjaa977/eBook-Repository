import { Component, OnInit } from '@angular/core';
import { RouterLink, Router } from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {

  logged = false;

  constructor(private router:Router) { }

  ngOnInit() {

    var token = localStorage.getItem("token");
    if(token != null){
      this.logged = true;
    }

  }

  logout(){
    localStorage.clear();
    this.router.navigate["/login"]
  }

}
