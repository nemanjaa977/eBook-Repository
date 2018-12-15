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
							"<a class='dropdown-item' href='../html/addBook.html' id='addBoook'>Add book</a>" +
							"<a class='dropdown-item' href='#' id='logoutButton'>Logout</a>" +
						"</div>");
		
		if(logged.type == "Admin"){
			nav.append("<a class='flex-sm-fill text-sm-center nav-link' href='../html/users.html'>Users</a>");
			document.getElementById('addBoook').style.display='block';
		}
	}
	
	// load all users
	var cardDiv = $('#divCards');
	$.get("http://localhost:8080/api/users",{},function(data){
		for(var i=0; i<data.length; i++){
			user = data[i];
			cardDiv.append("<div class='card'>" +
								"<img class='card-img-top' src='../photo/photo3.jpg' alt='Card image'>" +
								"<div class='card-body'>" +
									"<a class='card-title' href='../html/user.html?id="+user.id+"'>"+user.username+"</a>" +
									"<p class='card-text'>"+user.firstName+"</p>" +
									"<p class='card-text'>"+user.lastName+"</p>" +
									"<p class='card-text' id='roleD'>Role: "+user.type+"</p>" +
									"<a class='btn btn-danger' style='color:white;'><i class='fa fa-trash' aria-hidden='true'></i></a>" +
								"</div>" +
						   "</div>");
		}
	});
	
	
	//logout
	$(document).on("click", "#logoutButton",function() {
		localStorage.clear();
		window.location.replace('../html/login.html');
	});
	
});