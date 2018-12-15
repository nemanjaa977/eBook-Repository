import { Component, OnInit } from '@angular/core';
import { RouterLink, Router } from '@angular/router';
import { CategoryService } from '../service/category/category.service';
import { EbookService } from '../service/ebook/ebook.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  categories;
  books;

  constructor(private router:Router, private categoryService:CategoryService, private bookService:EbookService) { }

  ngOnInit() {

    this.categoryService.getCategories().subscribe(data => {
      this.categories = data;
    });

    this.bookService.getEbooks().subscribe(data => {
      this.books = data;
    });

  }

  loadAllBookForOneCategory(){
    this.bookService.getEbooksForCategory(this.categories.name).subscribe(data => {
      console.log(data);
      
    });
  }

}
