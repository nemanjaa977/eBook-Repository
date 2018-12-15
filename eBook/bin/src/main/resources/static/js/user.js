$(document).ready(function(){
	
	var userID = window.location.search.slice(1).split('&')[0].split('=')[1];
	
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
	
	// load one user
	var userData = $('#divDataUser');
	$.get("http://localhost:8080/api/users/"+userID,{},function(data){
		user = data;
		userData.append("<div id='userDiv'>" +
							"<a href='../html/user.html?id="+user.id+"' id='dataUsername'>"+user.username+"</a>" +
							"<p id='firstName'>First name: "+user.firstName+"</p>" +
							"<p id='lastName'>Last name: "+user.lastName+"</p>" +
							"<p id='roleUser'>Role: "+user.type+"</p>" +
							"<div id='editDataButton'>" +
								"<button type='button' class='btn btn-primary editButtonP' id='"+user.id+"'>Edit Profile</button>" +
								"<button type='button' class='btn btn-primary editButton' id='"+user.id+"'>Change Password</button>" +
							"</div>" +
					   "</div>");
	});
	
	// open edit profile file
	$(document).on("click", ".editButtonP",function() {
		$('#dataEditName').fadeIn();
		var userrID = $(this).attr("id");
		$.get("http://localhost:8080/api/users/"+userrID,{},function(data){
			console.log(data);
			$('#formEditzz').empty();
			$('#formEditzz').append("<div class='form-group'>" +
										"<label for='inputFirstName' id='labela'>First Name</label>" +
										"<input type='text' class='form-control' id='inputFirstName'>" +
									"</div>" +
									"<div class='form-group'>" +
								    	"<label for='inputLastName' id='labela'>Last Name</label>" +
								    	"<input type='text' class='form-control' id='inputLastName'>" +
								    "</div>" +
								    "<button class='btn btn-light' id='nameEditClose'>Close</button>" +
								    "<button type='button' class='btn btn-danger submitDataNameNew' id='"+data.id+"'>Submit</button>");
			oldFirstName = $('#inputFirstName');
			oldLastName = $('#inputLastName');
			oldFirstName.val(data.firstName);
			oldLastName.val(data.lastName);
		}).fail(function(){
		alert("Something's wrong!");
		});
	});
	
	// close edit profile file
	$('body').on('click', '#nameEditClose',function(event){
		$('#dataEditName').fadeOut();
		
		event.preventDefault();
		return false;
	});
	
	// click submit button for edit user data
//	$(document).on("click", ".submitDataNameNew",function(event) {
//		var useeerID = $(this).attr("id");
//		var firstNamee = oldFirstName.val();
//		var lastNamee = oldLastName.val();
//		
//		var json = {
//				'firstName': firstNamee,
//				'lastName': lastNamee
//		}
//		
//		$.ajax({
//			type : "PUT",
//			contentType : "application/json",
//			url :"http://localhost:8080/api/users/update/"+useeerID,
//			data :  JSON.stringify(json),
//			dataType : 'json',
//			success : function(result) {
//				
//			},
//			error : function(e) {
//				console.log("ERROR: ", e);
//				
//			}
//		});
//		
//		event.preventDefault();
//		return false;
//		
//	});
	
	// open change password div
	$(document).on("click", ".editButton",function(event) {
		$('#editDivData').fadeIn();
		var korisnikID = $(this).attr("id");
		$.get("http://localhost:8080/api/users/"+korisnikID,{},function(data){
			console.log(data);
			oldFirstName.val(data.firstName);
			oldLastName.val(data.lastName);
			$('#formEdittt').empty();
			$('#formEdittt').append("<div class='form-group'>" +
					    				"<label for='inputPasswordNew' id='labela'>New Password</label>" +
					    				"<input type='password' class='form-control' id='inputPasswordNew'>" +
					    			"</div>" +
					    			"<div class='form-group'>" +
					    				"<label for='inputPasswordNewConfirm' id='labela'>Confirm New Password</label>" +
					    				"<input type='password' class='form-control' id='inputPasswordNewConfirm'>" +
					    			"</div>" +
					    			"<button class='btn btn-light' id='closeEditDataa'>Close</button>" +
					    			"<button type='button' class='btn btn-danger submitNewDataUser' id='"+data.id+"'>Submit</button>");
		}).fail(function(){
		alert("Something's wrong!");
		});
		
		event.preventDefault();
		return false;
	});
	
	// close change password div
	$('body').on('click', '#closeEditDataa',function(event){
		$('#editDivData').fadeOut();
		
		event.preventDefault();
		return false;
	});
	
//	// click submit button for change password
//	$(document).on("click", ".submitNewDataUser",function(event) {
//		var editUserId = $(this).attr("id");
//		var newPassword = $('#inputPasswordNew').val();
//		var confirmPassword = $('#inputPasswordNewConfirm').val();
//		
//		var json = {
//
//		}
//		
//		$.ajax({
//			type : "PUT",
//			contentType : "application/json",
//			url :"http://localhost:8080/api/users/update/"+editUserId,
//			data :  JSON.stringify(json),
//			dataType : 'json',
//			success : function(result) {
//				
//			},
//			error : function(e) {
//				console.log("ERROR: ", e);
//				
//			}
//		});
//		
//		event.preventDefault();
//		return false;
//	});
	
	//logout
	$(document).on("click", "#logoutButton",function() {
		localStorage.clear();
		window.location.replace('../html/login.html');
	});	
	
});