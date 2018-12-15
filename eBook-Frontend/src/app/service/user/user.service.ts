import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpClientModule  } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }
  
  url="http://localhost:8080/api/users";
  loginUrl="http://localhost:8080/auth/";

  login(username,password):any{
   return this.http.post(this.loginUrl+"login",{'username':username,'password':password});
  }

  getLogged(token){
    var head;
    if(token){
      head={
          "Authorization": "Bearer " + token,
          'Content-Type': 'application/json'
        };
      }else{
          head={
              'Content-Type': 'application/json'
          };
      }
     let  httpOptions= {
          header: new  HttpHeaders(head)
      };

      return this.http.get(this.url+"/whoami",{headers:httpOptions.header});
  }

  register(user){
    return this.http.post(this.url, user);
  }
}
