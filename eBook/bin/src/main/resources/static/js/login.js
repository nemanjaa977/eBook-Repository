$(document).ready(function(){
	
	var message = $('#message');
	
	$('body').on('click', '#loginSubmit',function(event){
		
		var username = $('#inputUsername').val();
		var password = $('#inputPassword').val();
		
		var json = {
				'username': username,
				'password': password
		}
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url :"http://localhost:8080/auth/login",
			data :  JSON.stringify(json),
			dataType : 'json',
			success : function(data) {
				console.log(data);
				var token = data.access_token;
				$.ajax({
					url: "http://localhost:8080/api/users/whoami",
					type: 'GET',
					headers: { "Authorization": "Bearer " + token},
					contentType : "application/json",
					success : function(data) {
						console.log('Logged: ', data);
						localStorage.setItem("token", token);
						localStorage.setItem("loggedUser", JSON.stringify(data));
					    window.location.replace('../index.html');
					},
					error : function(e) {
						message.show();
						message.text("Wrong data!");
					}
				});	
			},
			error : function(e) {
				console.log("ERROR: ", e);
				message.show();
				message.text("You don't exist!");
			}
		});
		
		event.preventDefault();
		return false;
	});
	
	$(document).on("click", "#logo",function() {
		window.location.replace('../index.html');
	});
	
});