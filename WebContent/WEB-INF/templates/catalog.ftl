<#import "parts/header.ftl" as c>
<#import "parts/footer.ftl" as d>
<#import "parts/pager.ftl" as p>

<@c.page>

<!-- jQuery -->
    <script src="static/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="static/js/bootstrap.min.js"></script>

    <!-- Page Content -->
    <div id="content" class="container">

        <!-- Page Heading/Breadcrumbs -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Catalog</h1>
                <ol class="breadcrumb">
                    <li><a href="/">Home</a>
                    </li>
                    <li class="active">Catalog</li>
                    
                    <select id="sort" style="float: right; width: 15%; margin-left: 5px;">
	                    <option ${(sorting == "All")?then("selected",'')} value="All">All</option>
						<option ${(sorting == "By price: Low to high")?then("selected",'')} value="By price: Low to high">By price: Low to High</option>
						<option ${(sorting == "By price: High to low")?then("selected",'')} value="By price: High to low">By price: High to low</option>
						<option ${(sorting == "By date: Old to new")?then("selected",'')} value="By date: Old to new">By date: Old to new</option>
						<option ${(sorting == "By date: New to old")?then("selected",'')} value="By date: New to old">By date: New to old</option>
                    </select>
                    <h20 style="float: right">Sort by:</h20>
                    
                    <select id="filter" style="float: right; width: 15%; margin-right: 10px; margin-left: 5px;">
                    	<option ${(filtering == "All pieces")?then("selected", '')} value="All pieces">All pieces</option>
						<option ${(filtering == "Gemstone Jewelry")?then("selected", '')} value="Gemstone Jewelry">Gemstone Jewelry</option>
						<option ${(filtering == "Epoxy Resin Jewelry")?then("selected", '')} value="Epoxy Resin Jewelry">Epoxy Resin Jewelry</option>
						<option ${(filtering == "Earrings")?then("selected", '')} value="Earrings">Earrings</option>
						<option ${(filtering == "Bracelets")?then("selected", '')} value="Bracelets">Bracelets</option>
						<option ${(filtering == "Rings")?then("selected", '')} value="Rings">Rings</option>
						<option ${(filtering == "Necklaces")?then("selected", '')} value="Necklaces">Necklaces</option>
                    </select>
                    <h21 style="float: right">Filter by:</h21>
                </ol>
            </div>
        </div>
        <!-- /.row -->
		
		 <!-- Projects Row -->
        <div class="row">
        	<#list products as product>
	            <div class="col-md-4 img-portfolio" >
					<div style="overflow: hidden; height: 240px; box-shadow: 0 0 10px rgba(0,0,0,0.5);">
		                <a href="/listing?product_id=${product.product_id}" id="img + ${product?index}" >
		                    <img style="max-width: 100%; max-height: 100%; width: 100%; height: 100%;  top: 0; bottom: 0;
    								left: 0; right: 0; margin: auto; object-fit: cover;" 
    								class="img-responsive img-hover" src="${product.main_img}"  alt="" >
		                </a>
	                </div>
	                <h3 style="white-space: nowrap; overflow: hidden; text-overflow:ellipsis">
	                    <a href="/listing?product_id=${product.product_id}" src="${product.main_img}" class="listenersClass">${product.name}</a>
	                </h3>
	                <h4 style="color:#228D57;">
                		$
		                <#if product.price[0].discountPrice??>
		                	<b>${product.price[0].discountPrice}</b> <s>${product.price[0].price}</s>
		                <#else>
		                	<b>${product.price[0].price}</b>
		                </#if>
	                </h4>
	            </div>
            </#list>
        </div>
        <!-- /.row -->
		
		
        <!-- Pagination -->
        <@p.page>
        </@p.page>
        <!-- /.row -->

        <hr>

        <!-- Footer -->
        <@d.page>
		</@d.page>

    </div>
    <!-- /.container -->

    <script>
    var activities = document.getElementById("sort");
    activities.addEventListener("change", function() {
        if(activities.value != "All")
        {
        	var newUrl = "";
        	var currentUrl = window.location.href;
        	var index = currentUrl.includes('sort');
        	if(index){
        		newUrl += currentUrl.split("sort")[0];
        		var temp = currentUrl.split("sort")[1];
        		if(temp.includes("&")){
					var position = temp.search("&");
					var newString = temp.substring(position, temp.length);
					newUrl+= "&sort=" + activities.value + newString;
					console.log(newUrl);
					window.location = newUrl;
				}else{
					newUrl += "sort=" +  activities.value;
					window.location = newUrl;
				}
        	}else{
        		var temp = currentUrl.includes('?');
        		if(temp){
					newUrl+= currentUrl + "&sort=" + activities.value;
					window.location = newUrl;
				}else{
					newUrl += currentUrl + "?sort=" +  activities.value;
					window.location = newUrl;
				}
        	}
        }else{
        	window.location = "/catalog";
        }
    });
    
    
    var activities1 = document.getElementById("filter");
    activities1.addEventListener("change", function() {
    	
        if(activities1.value != "All pieces")
        {
        	var str = activities1.value;
        	
        	var newUrl = "";
        	var currentUrl = window.location.href;
        	var index = currentUrl.includes('filter');
        	if(index){
        		newUrl += currentUrl.split("filter")[0];
        		var temp = currentUrl.split("filter")[1];
        		if(temp.includes("&")){
					var position = temp.search("&");
					var newString = temp.substring(position, temp.length);
					newUrl+= "&filter=" + str + newString;
					console.log(newUrl);
					window.location = newUrl;
				}else{
					newUrl += "filter=" +  str;
					window.location = newUrl;
				}
        	}else{
        		var temp = currentUrl.includes('?');
        		if(temp){
					newUrl+= currentUrl + "&filter=" + str;
					window.location = newUrl;
				}else{
					newUrl += currentUrl + "?filter=" +  str;
					window.location = newUrl;
				}
        	}
        	
        	 var select = document.getElementById("filter");
        	 select.value = str;
        }else{
        	window.location = "/catalog";
        }
    });
    </script>    
    
</@c.page>
</body>

</html>