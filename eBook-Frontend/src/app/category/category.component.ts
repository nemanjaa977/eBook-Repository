import { Component, OnInit } from '@angular/core';
import { RouterLink, Router } from '@angular/router';
import { CategoryService } from '../service/category/category.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {

  categories;
  isAdmin = false;

  constructor(private router:Router, private categoryService:CategoryService) { }

  ngOnInit() {

    var l = JSON.parse(localStorage.getItem("logged"));
    if(l != null){
      if(l.type == 'Admin'){
        this.isAdmin = true;
        console.log(this.isAdmin);
      }
    }

    this.categoryService.getCategories().subscribe(result => {
      this.categories = result;
    });

  }

}
