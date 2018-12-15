import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpClientModule  } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  url="http://localhost:8080/api/categories";

  getCategories(){
    return this.http.get(this.url);
  }
  
}
