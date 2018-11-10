$(document).ready(function(){
	
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
	
	// load all category in list
	var listt = $('#listCategory');
	$.get("http://localhost:8080/api/categories",{},function(data){
		for(var i=0; i<data.length; i++){
			category = data[i];
			listt.append("<tr>" +
							"<td><a class='nav-link' href='../html/books.html'>"+category.name+"</a></td>" +
							"<td id='deleteTagg'><button type='button' class='btn btn-danger'><i class='fa fa-trash' aria-hidden='true'></i></button></td>" +
						 "</tr>");
		}
	});
	
	//logout
	$(document).on("click", "#logoutButton",function() {
		localStorage.clear();
		window.location.replace('../html/login.html');
	});
	
});