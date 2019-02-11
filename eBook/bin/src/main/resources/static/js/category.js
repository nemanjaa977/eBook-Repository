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
            $('#buttonCADD').append("<button type='button' class='btn btn-success' id='buttonAddCategory'><i class='fa fa-plus' aria-hidden='true'></i> New Category</button>");
            document.getElementById('addBoook').style.display = 'block';

            // click edit button for category open
            $(document).on("click", ".editt-button", function (event) {
                $('#categoryEditt').fadeIn();
                var categoryID = $(this).attr("id");
                $.get("http://localhost:8080/api/categories/" + categoryID, {}, function (data) {
                    console.log(data);
                    $('#categoryEditt').empty();
                    $('#categoryEditt').append("<form id='formEditCategory'>" +
                        "<div class='form-group'>" +
                        "<label for='inputNameCategoryEdit' id='labela'>Category name</label>" +
                        "<input type='text' class='form-control' id='inputNameCategoryEdit'>" +
                        "</div>" +
                        "<button type='submit' class='btn btn-danger editt' id='" + data.id + "' style='margin-right: 10px;'>Submit</button>" +
                        "<button class='btn btn-light' id='editEditClose'>Close</button>" +
                        "</form>");
                    oldName = $('#inputNameCategoryEdit');
                    oldName.val(data.name);
                }).fail(function () {
                    alert("Something's wrong!");
                });
                $('#buttonAddCategory').prop('disabled', true);
                $('.deletee-button').prop('disabled', true);

                event.preventDefault();
                return false;
            });
        }
    } else {
        navbar.append("<li class='nav-item'>" +
            "<a class='nav-link' href='../html/login.html' id='navII'>Login</a>" +
            "</li>" +
            "<li class='nav-item'>" +
            "<a class='nav-link' href='../html/register.html' id='navRR'>Register</a>" +
            "</li>");
    }

    // load all category in list
    var listt = $('#listCategory');
    $.get("http://localhost:8080/api/categories", {}, function (data) {
        for (var i = 0; i < data.length; i++) {
            category = data[i];
            listt.append("<tr>" +
                "<td><a class='nav-link' href='../html/books.html'>" + category.name + "</a></td>" +
                "<td id='editTagg'><button type='button' class='btn btn-info editt-button' id='" + category.id + "'><i class='far fa-edit'></i></button></td>" +
                "<td id='deleteTagg'><button type='button' class='btn btn-danger deletee-button' id='" + category.id + "'><i class='fa fa-trash'></i></button></td>" +
                "</tr>");
            if(logged == null || logged.type == 'User'){
            	$('.editt-button').hide();
            	$('.deletee-button').hide();
            }
        }
    });

    // open file for add new category
    $(document).on("click", "#buttonAddCategory", function (event) {
        $('#categoryNew').fadeIn();

        event.preventDefault();
        return false;
    });

    // close file for add new category
    $('body').on('click', '#closeCategory', function (event) {
        $('#categoryNew').fadeOut();

        event.preventDefault();
        return false;
    });

    // click submit button for add new category
    $('body').on('click', '#newCategorySubmit', function (event) {

        var nameCategory = $('#inputNameCategory').val();

        var param = {
            'name': nameCategory
        }

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/api/categories",
            data: JSON.stringify(param),
            headers: { "Authorization": "Bearer " + token},
			contentType : "application/json",
            dataType: 'json',
            success: function (result) {
                $('#inputNameCategory').val('');
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

    // click submit edit button for edit category
    $('body').on('click', '.editt', function (event) {
        var categoryIDD = $(this).attr("id");

        var newNameCategory = $('#inputNameCategoryEdit').val();
        var param = {
            'name': newNameCategory
        };
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "http://localhost:8080/api/categories/update/" + categoryIDD,
            data: JSON.stringify(param),
            headers: { "Authorization": "Bearer " + token},
			contentType : "application/json",
            dataType: 'json',
            success: function (data) {
                window.location.reload();
            },
            error: function (e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });

        event.preventDefault();
        return false;
    });

    // close file for edit category
    $('body').on('click', '#editEditClose', function (event) {
        $('#categoryEditt').fadeOut();
        $('#buttonAddCategory').prop('disabled', false);
        $('.deletee-button').prop('disabled', false);

        event.preventDefault();
        return false;
    });
    
    //delete category
    $(document).on("click", ".deletee-button", function (event) {
        var cccID = $(this).attr("id"); 
    		
		$.ajax({
			type : "DELETE",
			url :"http://localhost:8080/api/categories/delete/" + cccID,
            headers: { "Authorization": "Bearer " + token},
			contentType : "application/json",
			dataType : 'json',
			success : function() {
				window.location.replace("category.html");
			},
			error : function(e) {
				window.location.replace("category.html");
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