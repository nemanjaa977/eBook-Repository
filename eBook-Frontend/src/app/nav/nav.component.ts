import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {

  isAdmin = false;
  token = localStorage.getItem("token");

  constructor(private router:Router) {
   }

  ngOnInit() {
    
    var l = JSON.parse(localStorage.getItem("logged"));
    if(l != null){
      if(l.type == 'Admin'){
        this.isAdmin = true;
      }
    }

  }

  logout(){
    localStorage.clear();
    this.router.navigate(["/login"]);
  }

}
