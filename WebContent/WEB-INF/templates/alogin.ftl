<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>SB Admin 2 - Login</title>

  <!-- Custom fonts for this template-->
  <link href="http://localhost:8080/static/admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
<!-- jQuery -->
<script src="static/js/jquery.js"></script>
  <!-- Custom styles for this template-->
  <link href="http://localhost:8080/static/admin/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body class="bg-gradient-primary">

  <div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">Welcome to admin panel!</h1>
                  </div>
                    <div class="form-group">
                      <input type="email" class="form-control form-control-user" id="name" aria-describedby="emailHelp" placeholder="Enter your Name">
                    </div>
                    <div class="form-group">
                      <input type="password" class="form-control form-control-user" id="pass" placeholder="Password">
                    </div>
                    <div class="form-group">
                      <div class="custom-control custom-checkbox small">
                        <input type="checkbox" class="custom-control-input" id="ownPC">
                        <label class="custom-control-label" for="ownPC">Not own computer</label>
                      </div>
                    </div>
                    <a id="loginBtn" class="btn btn-primary btn-user btn-block">
                      Login
                    </a>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>

    </div>

  </div>
  
  <script>
  document.getElementById("loginBtn").addEventListener("click", function(){
     		var name = document.getElementById("name").value;
     		var pass = document.getElementById("pass").value;
     		var notOwnPC = document.getElementById("ownPC").checked;
     		
     		$.ajax({
   	    url: "/admin/alogin",
   	    data: { 
   	    	name : name,
   	    	pass : pass,
   	    	notOwnPC : notOwnPC
   	    },
   	    cache: false,
   	    type: "POST",
   	    success: function(data) {
   	    	console.log(data);
   	    	if(data.localeCompare("SUCCES")){
   	    		window.location = "/admin";
   	    	}
   	    },
   	    error: function(xhr) {
   	    	console.log("nERROR");
   	    }
     });
     	});
     </script>
</body>

</html>
