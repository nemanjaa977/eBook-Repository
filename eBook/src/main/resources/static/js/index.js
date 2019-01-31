$(document).ready(function () {

    var logged = JSON.parse(localStorage.getItem("loggedUser"));
    console.log(logged);
    var token = localStorage.getItem("token");

    var navbar = $('#navbar');
    var dropDown = $('#dropp');
    var nav = $('#navv');

    nav.append("<a class='flex-sm-fill text-sm-center nav-link' href='../index.html'>Home</a>" +
        "<a class='flex-sm-fill text-sm-center nav-link' href='../html/category.html'>Category</a>" +
        "<a class='flex-sm-fill text-sm-center nav-link' href='../html/books.html'>Books</a>");

    if (logged != null) {
        dropDown.append("<a class='nav-link dropdown-toggle' href='#' id='navbarDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>Profile</a>" +
            "<div class='dropdown-menu' aria-labelledby='navbarDropdown' id='dropDDD'>" +
            "<a class='dropdown-item' href='../html/user.html?id=" + logged.id + "'>My Profile</a>" +
            "<a class='dropdown-item' href='../html/addBook.html' id='addBoook'>Add book</a>" +
            "<a class='dropdown-item' href='#' id='logoutButton'>Logout</a>" +
            "</div>");

        if (logged.type == "Admin") {
            nav.append("<a class='flex-sm-fill text-sm-center nav-link' href='../html/users.html'>Users</a>");
            document.getElementById('addBoook').style.display = 'block';
        }


    } else {
        navbar.append("<li class='nav-item'>" +
            "<a class='nav-link' href='html/login.html' id='navII'>Login</a>" +
            "</li>" +
            "<li class='nav-item'>" +
            "<a class='nav-link' href='html/register.html' id='navRR'>Register</a>" +
            "</li>");
    }

    //logout
    $(document).on("click", "#logoutButton", function () {
        localStorage.clear();
        window.location.replace('../html/login.html');
    });
    
    //click search 
    $(document).on("click", "#searchButtonOk", function (event) {
    	var text = $('#inputSearchText').val();
    	var selectedSearchBy = $('#searchByy').val();
    	var selectedSearchUse = $('#SearchUsee').val();
    	
    	if (text == ""){
    		alert("You must enter a text");
    		return;
    	}
    	
    	var query={
    		'field': selectedSearchBy,
    		'value': text
    	};
    	
    	var url="http://localhost:8080/api/search/";
    	if(selectedSearchUse == "byRegularQuery"){
    		url += "term";
    	}else if(selectedSearchUse == "byPhrazeQuery"){
    		url += "phrase";
    	}else{
    		url += "fuzzy";
    	}
    	
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : url,
			data :  JSON.stringify(query),
			dataType : 'json',
			success : function(result) {
				console.log(result);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				
			}
		});
		
        event.preventDefault();
        return false;
    });


});