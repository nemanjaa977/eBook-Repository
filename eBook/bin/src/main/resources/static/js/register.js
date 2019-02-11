$(document).ready(function () {

    $(document).on("click", "#logo", function () {
        window.location.replace('../index.html');
    });
    
	$.get('http://localhost:8080/api/categories', {}, function(data){
		for(i in data){
			$('#categoryID').append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
		}
	});

    $('body').on('click', '#registerSubmit', function (event) {

        var firstName = $('#inputFirstName').val();
        var lastName = $('#inputLastName').val();
        var username = $('#inputUsername').val();
        var password = $('#inputPassword').val();
        var categoryId = $('#categoryID').val();

        var param = {
            'firstName': firstName,
            'lastName': lastName,
            'username': username,
            'password': password,
            'category_id': categoryId
        }

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/api/users",
            data: JSON.stringify(param),
            dataType: 'json',
            success: function (result) {
                window.location.replace('../index.html');
            },
            error: function (e) {
                console.log("ERROR: ", e);
                alert("Something's wrong!");
            }
        });

        event.preventDefault();
        return false;
    });

});