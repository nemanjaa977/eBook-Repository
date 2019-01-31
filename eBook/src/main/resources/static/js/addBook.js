$(document).ready(function () {
	
	var indexUnit=null;

    var bookID = window.location.search.slice(1).split('&')[0].split('=')[1];

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

    //logout
    $(document).on("click", "#logoutButton", function () {
        localStorage.clear();
        window.location.replace('../html/login.html');
    });
   
    // upload file and put
    $(document).on('change','#uploadd', function(event){
    	
    	$.get('http://localhost:8080/api/categories', {}, function(data){
    		for(i in data){
    			$('#categoryID').append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
    		}
    	});

       	$.ajax({
 	        type: "GET",
 	        url: "http://localhost:8080/api/languages",
 			headers: { "Authorization": "Bearer " + token},
 			contentType : "application/json",
 	        success: function (data) {
 	        	console.log(data);
 	    		for(i in data){
 	    			$('#languageID').append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
 	    		}
 
 	        }
       	});
    	
    	var file=$(this)[0].files[0];
    	console.log(file.type);
    	var data=new FormData();
    	var title = $('#titlee');
    	var keyword = $('#keywordd');
    	data.append("file",file);
    	
    	 $.ajax({
 	        type: "POST",
 	        enctype: 'multipart/form-data',
 	        url: "http://localhost:8080/api/lucene/index/add",
 			headers: { "Authorization": "Bearer " + token},
 			contentType : "application/json",
 	        data: data,
 	        processData: false, //prevent jQuery from automatically transforming the data into a query string
 	        contentType: false,
 	        cache: false,
 	        success: function (data) {
 	        	var keywordString="";
 	        	for( var item of data.keywords){
 	        		keywordString+=item+' ';
 	        	}
 	        	title.fadeIn();
 	        	keyword.fadeIn();
 	        	title.val(data.title);
 	        	keyword.val(keywordString);
 	        	
 	        	$('#author').val(data.author);
 	    	    $('#categoryID').val(data.categoryDTO);
 	    	    $('#languageID').val(data.languageDTO);
 	        	$('#author').fadeIn();
 	    	    $('#categoryID').fadeIn();
 	    	    $('#languageID').fadeIn();
 	    	    $('#labelCategory').fadeIn();
 	    	    $('#labelLanguage').fadeIn();
 	    	    
 	    	    $('#btnSubmit').fadeIn();
 	        	
 	        	indexUnit=data;
 
 	        },
 	        error: function (e) {
 	        console.log(e);
 
 	        }
 	    });
    	 
       event.preventDefault();
       return false;
    	
    });
    
    // click add ebook
    $("#btnSubmit").click(function (event) {
    	
    	indexUnit.title = $('#titlee').val();
    //	indexUnit.keywords = $('#keywordd').val();
    	var keywordsList=$('#keywordd').val().trim().split(" ");
    	indexUnit.keywords = keywordsList;
    	indexUnit.author = $('#author').val();
    	indexUnit.categoryDTO = $('#categoryID').val();
    	indexUnit.languageDTO = $('#languageID').val();
    	console.log(indexUnit);
	    $.ajax({
	        type: "POST",
	        url: "http://localhost:8080/api/ebooks",
			headers: { "Authorization": "Bearer " + token},
			contentType : "application/json",
			data: JSON.stringify(indexUnit),
	        success: function (data) {
	            window.location.replace("books.html");

	        },
	        error: function (e) {
	            console.log("ERROR : ", e);
	        }
	    });
    	
        event.preventDefault();
        return false;

    });

});
