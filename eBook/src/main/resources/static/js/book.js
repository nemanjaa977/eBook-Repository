$(document).ready(function () {

    var bookID = window.location.search.slice(1).split('&')[0].split('=')[1];

    var logged = JSON.parse(localStorage.getItem("loggedUser"));
    console.log(logged);
    var token = localStorage.getItem("token");

    var navbar = $('#navbar');
    var dropDown = $('#dropp');
    var nav = $('#navv');
    
    var filenameForEdit = null;
    var mimeForEdit = null;

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
    } else {
        navbar.append("<li class='nav-item'>" +
            "<a class='nav-link' href='../html/login.html' id='navII'>Login</a>" +
            "</li>" +
            "<li class='nav-item'>" +
            "<a class='nav-link' href='../html/register.html' id='navRR'>Register</a>" +
            "</li>");
    }

    // load one book
    var bookInformation = $('#bookInformation');
    $.get("http://localhost:8080/api/ebooks/" + bookID, {}, function (data) {
        book = data;
        bookInformation.append("<div id='bookDiv'>" +
            "<a href='../html/book.html?id=" + book.id + "' id='titleBook'>" + book.title + "</a>" +
            "<p id='authorBook'>Author: " + book.author + "</p>" +
            "<p id='yearBook'>Year: " + book.publicationYear + "</p>" +
            "<button type='button' class='btn btn-success download-Book'><i class='fa fa-download' aria-hidden='true'></i> Download</button>" +
            "<div id='bookDataButton'>" +
	            "<button type='button' class='btn btn-primary editButtonBook' id='" + book.id + "'>Edit Book</button>" +
	            "<button type='button' class='btn btn-danger deleteButtonBook' id='" + book.id + "'>Delete Book</button>" +
            "</div>" +
            "</div>");
        if (logged == null) {
            $('#bookDiv').append("<div class='popup' onclick='myFunction()'><i class='fa fa-download' aria-hidden='true'></i> Download" +
                "<a href='../html/register.html' class='popuptext' id='myPopup'>Register now!</a>" +
                "</div>");
            $('.download-Book').hide();
            $('.editButtonBook').hide();
            $('.deleteButtonBook').hide();
        }
        
        $('#inputTitle').val(book.title);
        $('#inputAuthor').val(book.author);
        $('#inputKeywords').val(book.keywords);
        $('#inputYear').val(book.publicationYear);
        
        filenameForEdit = book.filename;
        mimeForEdit = book.mime;
    });


    //logout
    $(document).on("click", "#logoutButton", function () {
        localStorage.clear();
        window.location.replace('../html/login.html');
    });
    
    //click to open edit form
    $(document).on("click", ".editButtonBook", function (event) {
        $('#bookEdit').fadeIn();
        
		event.preventDefault();
		return false;
    });
    
    // click submit button for edit book
    $('body').on('click', '#editSubmit', function (event) {

        var bookTitle = $('#inputTitle').val();
        var bookAuthor = $('#inputAuthor').val();
        var bookKeywords = $('#inputKeywords').val();
        var bookYear = $('#inputYear').val();

        var param = {
            'title': bookTitle,
            'author': bookAuthor,
            'keywords': bookKeywords,
            'publicationYear': bookYear,
            'filename': filenameForEdit,
            'mime': mimeForEdit
        }

        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "http://localhost:8080/api/ebooks/update/" + bookID,
            data: JSON.stringify(param),
            headers: { "Authorization": "Bearer " + token},
			contentType : "application/json",
            dataType: 'json',
            success: function (result) {
            	window.location.reload();
            },
            error: function (e) {
                console.log("ERROR: ", e);
                alert("Something's wrong!");
            }
        });

        event.preventDefault();
        return false;
    });
    
    //click to close edit form
    $(document).on("click", "#closeEdit", function (event) {
        $('#bookEdit').fadeOut();
        
		event.preventDefault();
		return false;
    });
    
    //delete book
    $(document).on("click", ".deleteButtonBook", function (event) {
        var bbbID = $(this).attr("id");       
    		
		$.ajax({
			type : "DELETE",
			url :"http://localhost:8080/api/ebooks/delete/" + bbbID,
            headers: { "Authorization": "Bearer " + token},
			contentType : "application/json",
			dataType : 'json',
			success : function() {
				window.location.replace("books.html");
			},
			error : function(e) {
				window.location.replace("books.html");
				console.log("ERROR: ", e);
			}
		});
        
		event.preventDefault();
		return false;
    });

});

function myFunction() {
    var popup = document.getElementById("myPopup");
    popup.classList.toggle("show");
}