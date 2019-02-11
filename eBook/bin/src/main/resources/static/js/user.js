$(document).ready(function () {

    var userID = window.location.search.slice(1).split('&')[0].split('=')[1];

    var logged = JSON.parse(localStorage.getItem("loggedUser"));
    console.log(logged);
    var token = localStorage.getItem("token");

    var navbar = $('#navbar');
    var dropDown = $('#dropp');
    var nav = $('#navv');
    var userForEditPassword = null;
    var userForEditType = null;

    nav.append("<a class='flex-sm-fill text-sm-center nav-link' href='../index.html'>Home</a>" +
        "<a class='flex-sm-fill text-sm-center nav-link' href='../html/category.html'>Category</a>" +
        "<a class='flex-sm-fill text-sm-center nav-link' href='../html/books.html'>Books</a>");

    if (logged != null) {
        dropDown.append("<a class='nav-link dropdown-toggle' href='#' id='navbarDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>Profile</a>" +
            "<div class='dropdown-menu' aria-labelledby='navbarDropdown'>" +
            "<a class='dropdown-item' href='../html/user.html?id=" + logged.id + "'>My Profile</a>" +
            "<a class='dropdown-item' href='../html/addBook.html' id='addBoook'>Add book</a>" +
            "<a class='dropdown-item' href='#' id='logoutButton'>Logout</a>" +
            "</div>");

        if (logged.type == "Admin") {
            nav.append("<a class='flex-sm-fill text-sm-center nav-link' href='../html/users.html'>Users</a>");
            document.getElementById('addBoook').style.display = 'block';
        }
    }

    // load one user
    var userData = $('#divDataUser');
    $.ajax({
		url: "http://localhost:8080/api/users/" + userID,
		type: 'GET',
		headers: { "Authorization": "Bearer " + token},
		contentType : "application/json",
		success : function(data) {
	        user = data;
	        userForEditPassword = user.password;
	        userForEditType = user.type;
	        userData.append("<div id='userDiv'>" +
	            "<a href='../html/user.html?id=" + user.id + "' id='dataUsername'>" + user.username + "</a>" +
	            "<p id='firstName'>First name: " + user.firstName + "</p>" +
	            "<p id='lastName'>Last name: " + user.lastName + "</p>" +
	            "<p id='roleUser'>Role: " + user.type + "</p>" +
	            "<div id='editDataButton'>" +
	            "<button type='button' class='btn btn-success editButtonP' id='" + user.id + "'>Edit Profile</button>" +
	            "</div>" +
	            "</div>");	
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
    });

    // open edit profile file
    $(document).on("click", ".editButtonP", function () {
        $('#dataEditName').fadeIn();
        var userrID = $(this).attr("id");
        $.ajax({
    		url: "http://localhost:8080/api/users/" + userrID,
    		type: 'GET',
    		headers: { "Authorization": "Bearer " + token},
    		contentType : "application/json",
    		success : function(data) {
    			$('#formEditzz').empty();
                $('#formEditzz').append("<div class='form-group'>" +
                        "<label for='inputUsername' id='labela'>First Username</label>" +
                        "<input type='text' class='form-control' id='inputUsername' disabled>" +
                        "</div>" +
	                	"<div class='form-group'>" +
	                    "<label for='inputFirstName' id='labela'>First Name</label>" +
	                    "<input type='text' class='form-control' id='inputFirstName'>" +
	                    "</div>" +
	                    "<div class='form-group'>" +
	                    "<label for='inputLastName' id='labela'>Last Name</label>" +
	                    "<input type='text' class='form-control' id='inputLastName'>" +
	                    "</div>" +
	                    "<button class='btn btn-light' id='nameEditClose'>Close</button>" +
	                    "<button type='button' class='btn btn-danger submitDataNameNew' id='" + data.id + "'>Submit</button>");
                oldUsername = $('#inputUsername');
                oldFirstName = $('#inputFirstName');
                oldLastName = $('#inputLastName');
                oldUsername.val(data.username);
                oldFirstName.val(data.firstName);
                oldLastName.val(data.lastName);	
    		},
    		error : function(e) {
    			console.log("ERROR: ", e);
    		}
        });
    });

    // close edit profile file
    $('body').on('click', '#nameEditClose', function (event) {
        $('#dataEditName').fadeOut();

        event.preventDefault();
        return false;
    });

    // click submit button for edit user data
	$(document).on("click", ".submitDataNameNew",function(event) {
		var useeerID = $(this).attr("id");
		var firstNamee = oldFirstName.val();
		var lastNamee = oldLastName.val();
		var userrName = oldUsername.val();
		
		var json = {
				'firstName': firstNamee,
				'lastName': lastNamee,
				'username': userrName,
				'password': userForEditPassword,
				'type': userForEditType
		}
		
		$.ajax({
			type : "PUT",
			contentType : "application/json",
			url :"http://localhost:8080/api/users/update/"+useeerID,
			data :  JSON.stringify(json),
			headers: { "Authorization": "Bearer " + token},
			contentType : "application/json",
			dataType : 'json',
			success : function(result) {
				window.location.reload();
			},
			error : function(e) {
				console.log("ERROR: ", e);
				
			}
		});
		
		event.preventDefault();
		return false;
		
	});

    //logout
    $(document).on("click", "#logoutButton", function () {
        localStorage.clear();
        window.location.replace('../html/login.html');
    });

});