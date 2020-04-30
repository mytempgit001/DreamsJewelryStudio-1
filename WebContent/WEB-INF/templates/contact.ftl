<#import "parts/header.ftl" as header>
<#import "parts/footer.ftl" as footer>

<@header.page>

<script>
function selectOnlyThis(id) {
    for (var i = 1;i <= 3; i++)
    {
        document.getElementById("Check" + i).checked = false;
    }
    document.getElementById(id).checked = true;
}

function sendMessage(msgType, name, email, msg){
	 $.ajax({
 	    url: "/sendMessage",
 	    data: { 
 	    	msgType : msgType,
 	    	name : name,
 	    	email : email,
 	    	msg : msg
 	    },
 	    cache: false,
 	    type: "POST",
 	    success: function(data) {
 	    // Success message
            $('#success').html("<div class='alert alert-success'>");
            $('#success > .alert-success').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
                .append("</button>");
            $('#success > .alert-success')
                .append("<strong>Your message has been sent. </strong>");
            $('#success > .alert-success')
                .append('</div>');

            //clear all fields
            $('#contactForm').trigger("reset");
 	    },
 	    error: function(xhr) {
 	    	 // Fail message
            $('#success').html("<div class='alert alert-danger'>");
            $('#success > .alert-danger').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
                .append("</button>");
            $('#success > .alert-danger').append("<strong>Sorry " + name + " it seems that mail server is not responding...</strong> Could you please email me directly to <a href='mailto:me@example.com?Subject=Message_Me from myprogrammingblog.com;>me@example.com</a> ? Sorry for the inconvenience!");
            $('#success > .alert-danger').append('</div>');
            //clear all fields
            $('#contactForm').trigger("reset");
 	    }
     });
}
</script>

    <!-- Page Content -->
    <div class="container">

        <!-- Page Heading/Breadcrumbs -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Contact
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/">Home</a>
                    </li>
                    <li class="active">Contact</li>
                </ol>
            </div>
        </div>
        <!-- /.row -->

        <!-- Content Row -->
        <div class="row">
           
        </div>
        <!-- /.row -->

        <!-- Contact Form -->
        <!-- In order to set the email address and subject line for the contact form go to the bin/contact_me.php file. -->
        <div class="row">
            <div style="margin-left: 20%; margin-right: 20%;">
                <h3>Send us a Message</h3>
                <form name="sentMessage" id="contactForm" novalidate>
                <div class="control-group form-group">
                	<div class="controls">
                		<label>Type of message:</label>
                		<p>
	                	<input id="Check1" type="checkbox" name="option1" value="Review" checked>Review<Br>
					   	<input id="Check2" type="checkbox" name="option2" value="Reclamation">Reclamation<Br>
					   	<input id="Check3" type="checkbox" name="option3" value="Desire">Desire<Br> 
					   	<script>
					   		document.getElementById("Check1").addEventListener("change", function(){selectOnlyThis("Check1")});
					   		document.getElementById("Check2").addEventListener("change", function(){selectOnlyThis("Check2")});
					   		document.getElementById("Check3").addEventListener("change", function(){selectOnlyThis("Check3")});
					   	</script>
					   	
               	 	</div>
                </div>
                    <div class="control-group form-group">
                        <div class="controls">
                            <label>Full Name:</label>
                            <input type="text" class="form-control" id="name" required data-validation-required-message="Please enter your name.">
                            <p class="help-block"></p>
                        </div>
                    </div>
                    
                    <div class="control-group form-group">
                        <div class="controls">
                            <label>Email Address:</label>
                            <input type="email" class="form-control" id="email" required data-validation-required-message="Please enter your email address.">
                        </div>
                    </div>
                    <div class="control-group form-group">
                        <div class="controls">
                            <label>Message:</label>
                            <textarea rows="10" cols="100" class="form-control" id="message" required data-validation-required-message="Please enter your message" maxlength="999" style="resize:none"></textarea>
                        </div>
                    </div>
                    <div id="success"></div>
                    <!-- For success/fail messages -->
                    <button id="sendMsg" type="submit" class="btn btn-primary">Send Message</button>
                    <script>
                    	document.getElementById("sendMsg").addEventListener("click", function(){
                    		var name = document.getElementById("name").value;
                    		var email = document.getElementById("email");
                    		var msg = document.getElementById("message");
                    		
                    		if(name.replace(/\s/g, '').length==0 || email.validationMessage.length > 0 || msg.validationMessage.length > 0){
                    			return;
                    		}
                    		
                   			var msgType = "";
                   			for (var i = 1; i <= 3; i++)
                   		    {
                   				var checkBox = document.getElementById("Check" + i);
                   		        if(checkBox.checked == true){
                   		        	msgType = checkBox.value;
                   		        }
                   		    }
                   			console.log(msgType + " " + name + " " + email.value + " " + msg.value);
                   			sendMessage(msgType, name, email.value, msg.value);
                    	});
                    </script>
                </form>
            </div>

        </div>
        <!-- /.row -->
	<hr>
		<@footer.page>
		</@footer.page>
    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="static/js/jquery.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="static/js/bootstrap.min.js"></script>

    <!-- Contact Form JavaScript -->
    <!-- Do not edit these files! In order to set the email address and subject line for the contact form go to the bin/contact_me.php file. -->
    <script src="static/js/jqBootstrapValidation.js"></script>
    <script src="static/js/contact_me.js"></script>
</@header.page>
</body>
</html>
