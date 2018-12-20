import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../service/user/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username;
  password;
  
  constructor(private router:Router, private userService:UserService) { }
  ngOnInit() {
  }

  login(){
    if(this.username != "" && this.password != ""){
      this.userService.login(this.username,this.password).subscribe(result=>{
        // console.log(result.access_token);
        localStorage.setItem("token",JSON.stringify(result.access_token));
        this.userService.getLogged(result.access_token).subscribe(data =>{
          localStorage.setItem("logged",JSON.stringify(data));
          // this.router.navigate([""]);
        }); 
        window.location.reload(true);
        this.router.navigate([""]);
      });
    }else{
      alert("You must to fill all fields!");
    }
  }
}
