<#import "parts/header.ftl" as header>
<#import "parts/footer.ftl" as footer>
<@header.page>
<link href="static/css/modal.css" rel="stylesheet">
<link href="static/js/modalanim.js" rel="js">

    <!-- Page Content -->
    <div class="container">
        <!-- Page Heading/Breadcrumbs -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">${product.name}
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/">Home</a></li>
                    <li><a href="/catalog">Catalog</a></li>
                    <li class="active">Listing</li>
                </ol>
            </div>
        </div>
        <!-- /.row -->

        <!-- Portfolio Item Row -->
        <div class="row">

            <div class="col-md-8">
                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                    <!-- Indicators -->
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                        <#list product.images as image>
	                        <#if image.url??>
	                        	<li data-target='#carousel-example-generic' data-slide-to='${image?index}'></li>
	                        <#else>
	                        	<li data-target='#carousel-example-generic' data-slide-to='0'></li>
	                        </#if>
                        	
       				  	</#list>
                    
                    </ol>

                    <!-- Wrapper for slides -->
                    <div class="carousel-inner">
                        <div style="height: 600px;" class="item active">
                        	<img style="max-width: 100%; max-height: 100%; width: 100%; height: 100%;  top: 0; bottom: 0;
    								left: 0; right: 0; margin: auto; object-fit: cover;"  class="img-responsive" src="${product.main_img}" width="750" height="500" alt="">
                        </div>
                        <#list product.images as image>
                        	<div style="height: 600px;" class="item">
	                        	<#if image??>
	                        		<img style="max-width: 100%; max-height: 100%; width: 100%; height: 100%;  top: 0; bottom: 0;
    								left: 0; right: 0; margin: auto; object-fit: cover;" 
    								class="img-responsive" src="${image.url}" width="750" height="500" alt="">
	                        		<#else>
	                        		<img class="img-responsive" src="${product.images[0].url}" width="750" height="500" alt="">
                        		</#if>
                       		</div>
                      	</#list>
                    </div>

                    <!-- Controls -->
                    <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left"></span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right"></span>
                    </a>
                </div>
                <h3>Product Description</h3>
                ${product.description}
            </div>

			<div class='col-md-4'>
				 <h4 id="price" style="color:#228D57;"> $
	                <#if product.price[0].discountPrice??>
	                	${product.price[0].discountPrice}
	                	<h5 id = "youSave">
	                		You save <s id="discount" style="color:#228D57;">$ ${product.price[0].price - product.price[0].discountPrice}</s>
	                	</h5>
	                <#else>
	                	${product.price[0].price}
	                </#if>
                </h4>
				
                <h3>Product Materials</h3>
                <ul>
         			<#list product.material?split(" | ") as sValue>
    					<li>${sValue}</li>
     				</#list>
                 </ul>
                 
                 <ul>
                 	Quantity:
                 	<select id="quantity" style='width:40px'>
                 		<#list 1..product.price[0].quantity as index>
               				<option value='${index}'>${index}</option>
                 		</#list>
                 	</select>
                 </ul>
                 
                 <ul>Size: 
                 	<select id="size" style='width:100%'>
                 		<#list product.price as priceSize>
                 			<option value='${priceSize.size}'>${priceSize.size}</option>
                 			<script>
	                 			var sizeListener = document.getElementById("size");
	                 			sizeListener.addEventListener("change", function() {
	                 				
	                 				if(sizeListener.value == "${priceSize.size}"){
		                 				var priceListener = document.getElementById("price");
		                 				var quantity = ${priceSize.quantity};
		                 				<#if product.price[priceSize?index].discountPrice??>
		                 					var discount = document.getElementById("discount");
		                 					var discountText = "$ ${product.price[priceSize?index].price - product.price[priceSize?index].discountPrice}";
		                 					var priceListenerText = "$ ${product.price[priceSize?index].discountPrice}";
		                 					changePrice(priceListener, priceListenerText, discount, discountText, null, null, quantity);
		                 				<#else>
		                 					var youSave = document.getElementById("youSave");
		                 					var youSaveText = "";
		                 					var priceListenerText = "$ ${product.price[priceSize?index].price}";
		                 					changePrice(priceListener, priceListenerText, null, null, youSave, youSaveText, quantity);
	                 					</#if>
	                 				}
	                 			});
                 			</script>
     					</#list>
     				</select>
   				 </ul>
   				 <ul>
   				 	<button id="AddToCart" class="btn btn-primary" style="width:100%;">Add to cart</button>
                 </ul>
			</div>
        </div>
        <!-- /.row -->
        <!-- Related Projects Row -->
        <div class="row">
            <div class="col-lg-12">
                <h3 class="page-header">Related Projects</h3>
            </div>
			<#if related??>
				<#list related as prd>
					<div style="height: 120px;" class="col-sm-3 col-xs-6">
		                <a href="/listing?product_id=${prd.getProduct_id()}">
		                    <img style="max-width: 100%; max-height: 100%; width: 100%; height: 100%;  top: 0; bottom: 0;
	    								left: 0; right: 0; margin: auto; object-fit: cover;" 
									 class="img-responsive img-hover img-related" src="${prd.getMain_Img()}" alt="">
		                </a>
	            	</div>
				</#list>
			</#if>
        </div>
        <!-- /.row -->
        <hr>
        <!-- Footer -->
       <@footer.page>
       </@footer.page>
	<div id="myModal" class="modal">
	
	  <!-- Modal content -->
	  <div class="modal-content">
	    <div class="modal-header">
	      <span class="close">&times;</span>
	      <h2>Product was added at your cart</h2>
	    </div>
	  </div>
	
	</div>
    </div>
    <!-- /.container -->
    <!-- jQuery -->
    <script src="static/js/jquery.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="static/js/bootstrap.min.js"></script>
    
    <script>
		function changePrice(priceListener, priceListenerText, discount, discountText, youSave, youSaveText, quantity){
			priceListener.innerText = priceListenerText;
			if(youSave == null){
				if(discount == null){
					var newS = document.createElement("s");
					newS.id = "discount";
					newS.style.cssText = "color:#228D57;";
					newS.innerText = discountText;
					
					var save = document.getElementById("youSave");
					save.innerText = "You save: ";
					save.appendChild(newS);
				}else{
					discount.innerText = discountText;
				}
			}else if(discount == null){
				youSave.innerText = youSaveText;
			}
			var quantitySelect = document.getElementById("quantity");
			$('#quantity')
		    .empty()
		    .end();
			
			for (i = 1; i <= quantity; i++) {
				var option = document.createElement("option");
				option.text = i;
				quantitySelect.add(option);
			}
		}
	</script>
    
    
     <script>
    var temp = document.getElementById("AddToCart");
    temp.addEventListener("click", function() {
    	var activitySize = document.getElementById("size");
    	$.ajax({
    	    url: "/addToCart",
    	    data: { 
    	    	id : ${product.product_id},
    	    	quantity : document.getElementById("quantity").value,
    	    	size: document.getElementById("size").value,
    	    },
    	    cache: false,
    	    type: "POST",
    	    success: function(data) {
    	    	if(data.localeCompare("SUCCES")){
    	    		var modal = document.getElementById("myModal");
    	    		modal.style.display = "block";
    	    		
    	    		var span = document.getElementsByClassName("close")[0];
    	    		span.onclick = function() {
    	    		  modal.style.display = "none";
    	    		}
    	    		
    	    		window.onclick = function(event) {
    	    		  if (event.target == modal) {
    	    		    modal.style.display = "none";
    	    		  }
    	    		}
    	    	}else{
    	    		alert("Something went wrong! Please try again later or let us know.");
    	    	}
    	    },
    	    error: function(xhr) {
    	    	console.log("error");
    	    	alert("Something went wrong! Please try again later or let us know.");
    	    }
	     });
   	});
    
    </script>
    
</@header.page>
</body>
</html>