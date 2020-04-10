<#import "parts/header.ftl" as c>
<#import "parts/footer.ftl" as d>
<#import "parts/pager.ftl" as p>
<@c.page>

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
                    	<#list sorts as sort>
                    		${sort}
                    	</#list>
                    </select>
                    <h20 style="float: right">Sort by:</h20>
                    
                    <select id="filter" style="float: right; width: 15%; margin-right: 10px; margin-left: 5px;">
                    	<#list filters as filter>
                    		${filter}
                    	</#list>
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
	                <a href="/listing?product_id=${product.product_id}" id="img + ${product?index}" >
	                    <img class="img-responsive img-hover" src="${product.main_img}" style="box-shadow: 0 0 10px rgba(0,0,0,0.5);" alt="" width:"700" height:"300">
	                </a>
	                <h3 style="white-space: nowrap; overflow: hidden; text-overflow:ellipsis">
	                    <a class="listenersClass">${product.name}</a>
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
		
		
        <hr>

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

    <!-- jQuery -->
    <script src="static/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="static/js/bootstrap.min.js"></script>
    
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
        	
        	if(str == "Epoxy Resin Jewelry"){
        		str = "Resin";
        	}else if(str == "Gemstone Jewelry"){
        		str = "Gemstone";
        	}
	
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
