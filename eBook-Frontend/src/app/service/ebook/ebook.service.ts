import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpClientModule  } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EbookService {

  constructor(private http:HttpClient) { }

  url="http://localhost:8080/api/ebooks";

  getEbooks(){
    return this.http.get(this.url);
  }

  getEbooksForCategory(categoryName){
    return this.http.get(this.url + "/categories/" + categoryName);
  }

}
