$(document).ready(function(){
	
	var bookID = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	var logged=JSON.parse(localStorage.getItem("loggedUser"));
	console.log(logged);
	
	var navbar = $('#navbar');
	var dropDown = $('#dropp');
	var nav = $('#navv');
	
	nav.append("<a class='flex-sm-fill text-sm-center nav-link' href='../index.html'>Home</a>" +
		       "<a class='flex-sm-fill text-sm-center nav-link' href='../html/category.html'>Category</a>" +
		       "<a class='flex-sm-fill text-sm-center nav-link' href='../html/books.html'>Books</a>");
	
	if(logged != null){
		dropDown.append("<a class='nav-link dropdown-toggle' href='#' id='navbarDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>Profile</a>" +
						"<div class='dropdown-menu' aria-labelledby='navbarDropdown'>" +
							"<a class='dropdown-item' href='../html/user.html?id="+logged.id+"'>My Profile</a>" +
							"<a class='dropdown-item' href='#' id='logoutButton'>Logout</a>" +
						"</div>");
		
		if(logged.type == "Admin"){
			nav.append("<a class='flex-sm-fill text-sm-center nav-link' href='../html/users.html'>Users</a>");
		}
	}else{
		navbar.append("<li class='nav-item'>" +
				"<a class='nav-link' href='../html/login.html' id='navII'>Login</a>" +
			  "</li>" +
			  "<li class='nav-item'>" +
				"<a class='nav-link' href='../html/register.html' id='navRR'>Register</a>" +
			  "</li>");
	}
	
	// load one user
	var bookInformation = $('#bookInformation');
	$.get("http://localhost:8080/api/ebooks/"+bookID,{},function(data){
		book = data;
		bookInformation.append("<div id='bookDiv'>" +
							"<a href='../html/book.html?id="+book.id+"' id='titleBook'>"+book.title+"</a>" +
							"<p id='authorBook'>Author: "+book.author+"</p>" +
							"<p id='yearBook'>Year: "+book.publicationYear+"</p>" +
							"<p id='filenameBook'>"+book.filename+"</p>" +
							"<div id='bookDataButton'>" +
								"<button class='editButtonBook' id='"+book.id+"'>Edit Book</button>" +
							"</div>" +
					   "</div>");
	});
	
	
	//logout
	$(document).on("click", "#logoutButton",function() {
		localStorage.clear();
		window.location.replace('../html/login.html');
	});	
	
});