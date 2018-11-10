$(document).ready(function(){
	
	$(document).on("click", "#logo",function() {
		window.location.replace('../index.html');
	});
	
	$('body').on('click', '#registerSubmit',function(event){
		
		var firstName = $('#inputFirstName').val();
		var lastName = $('#inputLastName').val();
		var username = $('#inputUsername').val();
		var password = $('#inputPassword').val();
		
		var param = {
				'firstName': firstName,
				'lastName': lastName,
				'username': username,
				'password': password
		}
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url :"http://localhost:8080/api/users",
			data :  JSON.stringify(param),
			dataType : 'json',
			success : function(result) {
				window.location.replace('../index.html');
			},
			error : function(e) {
				console.log("ERROR: ", e);
				alert("Something's wrong!");
			}
		});
		
		event.preventDefault();
		return false;
	});
	
});