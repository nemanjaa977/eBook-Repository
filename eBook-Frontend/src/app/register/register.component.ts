import { Component, OnInit } from '@angular/core';
import { RouterLink, Router} from '@angular/router';
import { UserService } from '../service/user/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  user={
    'username':'',
    'password':'',
    'firstName':'',
    'lastName':''
  };

  constructor(private router:Router, private userService: UserService) { }

  ngOnInit() {
  }

  register(){
    this.userService.register(this.user).subscribe(result =>{
      this.router.navigate(['']);
    });
  }

}
