<#import "parts/header.ftl" as header>
<#import "parts/footer.ftl" as footer>

<@header.page>
<link href="static/css/custom.css" rel="stylesheet">

<!-- jQuery -->
<script src="static/js/jquery.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="static/js/bootstrap.min.js"></script>


<script>
	function deleteItem(temp, div, itemID){
		temp.addEventListener("click", function(){
			div.parentNode.removeChild(div);
			changeTotalPrice();
			 $.ajax({
		    	    url: "/deleteItemFromCart",
		    	    data: { 
		    	    	itemID : itemID
		    	    },
		    	    cache: false,
		    	    type: "POST",
		    	    success: function(data) {
		    	    	if(data.localeCompare("SUCCES")){
		    	    		console.log(data);
		    	    	}
		    	    },
		    	    error: function(xhr) {
		    	    	console.log("nERROR");
		    	    }
			     });
		});
	}
	
	function changeAmount(itemID, qty){
		changeTotalPrice();
		$.ajax({
    	    url: "/changeAmount",
    	    data: { 
    	    	itemID : itemID,
    	    	qty : qty
    	    },
    	    cache: false,
    	    type: "POST",
    	    success: function(data) {
    	    	if(data.localeCompare("SUCCES")){
    	    		console.log(data);
    	    	}
    	    },
    	    error: function(xhr) {
    	    	console.log("nERROR");
    	    }
	     });
	}
</script>
<!-- Page Content -->
    <div id="content" class="container" >
		<!-- Page Heading/Breadcrumbs -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Shopping Cart</h1>
                <ol class="breadcrumb">
                    <li><a href="/">Home</a>
                    </li>
                    <li class="active">Shopping Cart</li>
                </ol>
            </div>
        </div>
        <!-- /.row -->
		<div class="shopping-cart">
	  		<!-- Title  background: -->
	 		 <div class="title">
		 		 <div >
		 		 	<h4 style="margin-top: 0%; float: left;" align="left">Your products</h4>
		   		 	<h4 id="totalprice" style="margin-top: 0%; float: right;" id="total" align="right" > Total price: $ 
		   		 		<#if totalPrice??>
	   		 				${totalPrice} 
 		 				<#else>
	   		 				0
		   		 		</#if>
	   		 		</h4>
		 		 </div>
	  		</div>
	  		
	  		<#if items??>
				<#list items as item> 
				<#if item.product??>
					<#assign product = item.getProduct()>
					<#if item.getPrs().getDiscountPrice() == 0>
						<#assign priceperone = item.getPrs().getPrice()>
						<#else>
						<#assign priceperone = item.getPrs().getDiscountPrice()>
					</#if>
		  			<div id="div${item?index}" class="item">
		    			<div class="buttons">
		      				<span id="span${item?index}" class="delete-btn"></span>
		    			</div>
		 
	 					<script>
							deleteItem(document.getElementById("span${item?index}"), document.getElementById("div${item?index}"), ${item.itemID?c});
						</script>
		 
		    			<div class="image" style="margin-right: 20px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.5); ">
		      				<img id="${product.main_img}" src="${product.main_img}" style="box-shadow: 0 0 10px rgba(0,0,0,0.5);  max-width: 100%; max-height: 100%; width: 100%; height: 100%;  top: 0; bottom: 0;
    								left: 0; right: 0; margin: auto; object-fit: cover;" />
		    			</div>
		 				
		    			<div class="description" style="width:20%">
		      				<span style="white-space: nowrap; overflow: hidden; text-overflow:ellipsis">${product.name}</span>
		      				<span style="white-space: nowrap; overflow: hidden; text-overflow:ellipsis">${item.size}</span>
		     				<span style="white-space: nowrap; overflow: hidden; text-overflow:ellipsis">${product.product_type}</span>
		    			</div>
		 
		    			<div class="quantity">
		    				<div class="main-div">
			    				<select id="cd2_${item?index}" class="child-div2" style="text-align: center; width: 60px;" >
  									<#assign priceSize = item.prs>
				    				<#list 1..priceSize.quantity as index>
	               						<#if index==item.quantity>
	               							<option selected value='${index}'>${index}</option>
						                <#else>
						                	<option value='${index}'>${index}</option>	
						                </#if>
	                 				</#list>
			    				</select> 
			    				
			    				<script>
			    					var selectWidget = document.getElementById("cd2_${item?index}");
			    					selectWidget.addEventListener("change", function(){
			    						var newPrice = parseFloat("${priceperone}".replace(",", ".")) * document.getElementById("cd2_${item?index}").value;
			    						document.getElementById("tp_${item?index}").innerText = "$ " + newPrice.toFixed(1).replace(".", ",");
			    						changeAmount(${item.itemID?c}, document.getElementById("cd2_${item?index}").value);
		    						});
			    				</script>
		    				</div>
		  				</div>
		   		 		<div id="tp_${item?index}" class="total-price">$ ${item.price}</div>
		  			</div>
		  			</#if>
		 		</#list>
	 		</#if>
	 		<div class="title" style="padding: 13px 30px;">
	 			<button id="buybtn" type="submit" class="btn btn-primary" style="width:150px; float: right;">Purchase</button>
	   		 	<script>
	   		 		document.getElementById("buybtn").addEventListener("click", function(){
	   		 		$.ajax({
			    	    url: "/paypal/make/payment",
			    	    data: { 
			    	    	sum : "20"
			    	    },
			    	    cache: false,
			    	    type: "POST",
			    	    success: function(data) {
		    	    		if(data.status == "success"){
		    	    			window.location = data.redirect_url;
		    	    		}
			    	    },
			    	    error: function(xhr) {
			    	    	console.log("nERROR");
			    	    }
				     });
	   		 		});
	   		 	</script>
	  		</div>
		</div>
		<hr>
		<@footer.page>
		</@footer.page>
	</div>
</@header.page>

<script>
function changeTotalPrice(){
	var total = document.getElementById("totalprice");
	
	<#if items??>
		var counter = 0;
		<#list items as item>
			var w = document.getElementById("tp_${item?index}");
			if(w != null){
				var p = w.innerText.split(" ")[1];
				var price = parseFloat(p.replace(",", "."));
				counter += price;
			}
		</#list>
		total.innerText = "Total price: $ " + counter.toFixed(1).replace(".", ",");
	</#if>
}
</script>
</body>
