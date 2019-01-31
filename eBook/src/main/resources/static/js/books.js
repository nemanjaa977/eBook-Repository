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


    // load all book in list
    var allBook = $('#divB');
    $.get("http://localhost:8080/api/ebooks", {}, function (data) {
        for (var i = 0; i < data.length; i++) {
            book = data[i];
            allBook.append("<div id='oneBook' class='col-12'>" +
            	"<div id='imageDD'>" +
            		"<img src='../photo/photo67.jpeg' alt='Book image' id='imageBook'>" +
            	"</div>" +
            	"<div id='boook' class='col-8'>" +
	                "<a id='bookTitle' href='../html/book.html?id=" + book.id + "'>" + book.title + "</a>" +
	                "<p id='authorBook'>" + book.author + "</p>" +
	                "<button type='button' class='btn btn-success download-Book'><i class='fa fa-download' aria-hidden='true'></i> Download</button>" +
	                "<div class='popup' onclick='myFunction()'><i class='fa fa-download' aria-hidden='true'></i> Download" +
	                	"<a href='../html/register.html' class='popuptext' id='myPopup'>Register now!</a>" +
	                "</div>"+
                "</div>"+
                "</div>");
            if(logged == null) {
                $('.download-Book').hide();
            }else{
            	$('.popup').hide();
            }
        }
    });

    // load all category in list
    var listt = $('#nameC');
    $.get("http://localhost:8080/api/categories", {}, function (data) {
        for (var i = 0; i < data.length; i++) {
            category = data[i];
            listt.append("<a class='nav-link categoty-link' id=" + category.name + " href='#'>" + category.name + "</a>");
        }
    });

    // load books for one category
    var allBook = $('#divB');
    $(document).on("click", ".categoty-link", function (event) {
        var categoryName = $(this).attr("id");
        $.get("http://localhost:8080/api/ebooks/categories/" + categoryName, {}, function (data) {
            allBook.empty();
            for (var i = 0; i < data.length; i++) {
                console.log(data);
                book = data[i];
                allBook.append("<div id='oneBook' class='col-12'>" +
                    	"<div id='imageDD'>" +
                    		"<img src='../photo/photo67.jpeg' alt='Book image' id='imageBook'>" +
                    	"</div>" +
                    	"<div id='boook' class='col-8'>" +
        	                "<a id='bookTitle' href='../html/book.html?id=" + book.id + "'>" + book.title + "</a>" +
        	                "<p id='authorBook'>" + book.author + "</p>" +
        	                "<button type='button' class='btn btn-success download-Book'><i class='fa fa-download' aria-hidden='true'></i> Download</button>" +
        	                "<div class='popup' onclick='myFunction()'><i class='fa fa-download' aria-hidden='true'></i> Download" +
        	                	"<a href='../html/register.html' class='popuptext' id='myPopup'>Register now!</a>" +
        	                "</div>"+
                        "</div>"+
                        "</div>");
                if(logged == null) {
                    $('.download-Book').hide();
                }else{
                	$('.popup').hide();
                }
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

function myFunction() {
    var popup = document.getElementById("myPopup");
    popup.classList.toggle("show");
}