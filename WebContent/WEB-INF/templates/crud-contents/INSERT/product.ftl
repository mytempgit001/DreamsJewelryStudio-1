<#macro page>
	<#nested>
	<div align="center">
       	<div class="container">
		  <div class="row">
		    <div class="col">
			    <div>
				<h4>PRODUCT</h4>
					<div id="requisitesBlock" align="right">
						<div style="vertical-align: middle;">
							<label>category:</label> <code>*</code>
							<div>
								<input onclick="selectOnlyCategory('category1')" id="category1" type="checkbox" name="option1" value="Earrings" checked> Earrings 
								<input onclick="selectOnlyCategory('category2')" id="category2" type="checkbox" name="option2" value="Bracelets"> Bracelets
								<input onclick="selectOnlyCategory('category3')" id="category3" type="checkbox" name="option3" value="Rings"> Rings 
								<input onclick="selectOnlyCategory('category4')" id="category4" type="checkbox" name="option4" value="Necklaces"> Necklaces
							</div>
						</div>
						<br>
						<label>product_type:</label> <code>*</code> 
						<input onclick="selectOnlyProdType('Check1')" id="Check1" type="checkbox" name="option1" value="Resin" checked> Resin 
						<input onclick="selectOnlyProdType('Check2')" id="Check2" type="checkbox" name="option2" value="Gemstone"> Gemstone 
						<br>
						<label>name:</label> <code>*</code> 
						<input id="name" class="requiredInputs"></input> 
						<br>
						<label>description:</label> <code>*</code> 
						<input id="description" class="requiredInputs"></input> 
						<br>
						<label>main_img:</label> <code>*</code> 
						<input id="main_img" class="requiredInputs"></input> 
						<br><div class='materials'>
						<label>material:</label> <code>*</code> 
						<input class='materialValue' id="material" class="requiredInputs"></input> </div>
						<br>
					</div>
					<br>
					<button onclick="removeMaterial()" id="-">-</button>
				 	<button onclick="addMaterial()" id="+">+</button>
					<br>
					<script>
					function removeMaterial(){
						var arr = document.querySelectorAll(".materials");
			 			if(arr!=null && arr.length>1)
			 				arr[arr.length-1].remove();
					}
					
					function addMaterial(){
						var newDiv = document.createElement("div");
						newDiv.innerHTML = "<div class='materials'><label>material:</label> <code>*</code> "
							+ "<input class='materialValue' id='material' class='requiredInputs'></input> </div>";
						var element = document.getElementById("requisitesBlock");
						element.appendChild(newDiv);
					}
					
					function selectOnlyProdType(id) {
					    for (var i = 1;i <= 2; i++)
					    {
					        document.getElementById("Check" + i).checked = false;
					    }
					    document.getElementById(id).checked = true;
					}
					
					function selectOnlyCategory(id) {
					    for (var i = 1;i <= 4; i++)
					    {
					        document.getElementById("category" + i).checked = false;
					    }
					    document.getElementById(id).checked = true;
					}
					</script>
				</div>
		    </div>
		    <div class="col">
  				<div>
				<h4>PRICE SIZE</h4>
					<div id="priceSizeBlock" align="right">
						<div class='myPriceSize'>
							<br>
							<label>price:</label> <code style='color:blue;'>I</code> <code>*</code>
							<input class="requiredInputs priceSize numberic"></input> 
							<br>
							<label>size:</label> <code>*</code> 
							<input class="requiredInputs priceSize"></input> 
							<br>
							<label>discount_price:</label> <code style="color:blue;">I</code>
							<input class="priceSize numberic float"></input> 
							<br>
							<label>quantity:</label> <code style="color:blue;">I</code> <code>*</code>
							<input class="requiredInputs priceSize numberic"></input> 
						</div>
					</div>
					<br>
					<br>
					 <button onclick="remove()" id="-">-</button>
					 <button onclick="add()" id="+">+</button>
					<script>
						function add(){
							var newDiv = document.createElement("div");
							newDiv.innerHTML = "<div class='myPriceSize' align='right'><br><br> "+
											"<label>price:</label> <code style='color:blue;'>I</code> <code>*</code> "+
											"<input class='requiredInputs priceSize numberic'> </input> "+
											"<br>"+
											"<label>size:</label> <code>*</code> "+
											"<input class='requiredInputs priceSize'></input> "+
											"<br>"+
											"<label>discount_price:</label> <code style='color:blue;'>I</code> "+
											"<input class='priceSize numberic float'></input> "+
											"<br>"+
											"<label>quantity:</label> <code style='color:blue;'>I</code> <code>*</code> "+
											"<input class='requiredInputs priceSize numberic'></input> </div>";
							var element = document.getElementById("priceSizeBlock");
							element.appendChild(newDiv);
						}		
						function remove(){
							var arr = document.querySelectorAll(".myPriceSize");
				 			if(arr!=null && arr.length>1)
				 				arr[arr.length-1].remove();
						}
					</script>
				</div>
		    </div>
		    <div class="col">
		    	<div>
					<h4>IMAGES</h4>
					<div id="urlBlock">
						<div class='myurls'>
						  	<br>
							<label>url:</label> <code>*</code> 
							<input class="requiredInputs urls"></input> 
					 	</div>
				 	</div>
				 	<br>
					<button onclick="removeUrl()" id="-">-</button>
					<button onclick="addUrl()" id="+">+</button>
				 	<script>
				 		function addUrl(){
				 			var newDiv = document.createElement("div");
							newDiv.innerHTML = "<div class='myurls'>" +
							"<label>url:</label> <code>*</code> "+
							"<input class='requiredInputs urls'></input> "+
						  	"<br> </div>";
							var element = document.getElementById("urlBlock");
							element.appendChild(newDiv);
				 		}
				 		
				 		function removeUrl(){
				 			var arr = document.querySelectorAll(".myurls");
				 			if(arr!=null && arr.length>1)
				 				arr[arr.length-1].remove();
				 		}
				 	</script>
				</div>
		    </div>
		  </div>
		</div>
	</div>
	
	<div align="right">
		<button onclick="insert()" style="width:20%">INSERT</button>
		<script>
		
			function getImages(){
				var data = [];
				var urls = document.querySelectorAll(".urls");
				for(var i = 0; i < urls.length; i++){
					data.push({
						url : urls[i].value,
					});
				}
				return data;
			}
		
			function getPriceSize(){
				var data = [];
				var divs = document.querySelectorAll(".myPriceSize");	
				for(var i = 0; i < divs.length; i++){
					var priceSize = divs[i].querySelectorAll(".priceSize");
					var price = priceSize[0].value;
					var size = priceSize[1].value;
					var discountPrice = (priceSize[2].value.trim().length==0? 0 : priceSize[2].value);
					var quantity = priceSize[3].value;
					data.push({
						price : price,
						size : size,
						discountPrice : discountPrice,
						quantity : quantity
					});
					
					if(discountPrice==0) priceSize[2].value = 0;
				}
				return data;
			}
			
			function getProductType(){
				var prodType = "";
				for (var i = 1; i <= 2; i++){
       				var checkBox = document.getElementById("Check" + i);
       		        if(checkBox.checked == true){
       		        	prodType = checkBox.value;
       		        }
       		    }
				return prodType;
			}
			
			function getCategory(){
				var category = "";
				for (var i = 1; i <= 4; i++){
       				var checkBox = document.getElementById("category" + i);
       		        if(checkBox.checked == true){
       		        	category = checkBox.value;
       		        }
       		    }
				return category;
			}
			
			function getMaterials(){
				var str = "";
				var arr = document.querySelectorAll(".materialValue");
				for(var i = 0; i < arr.length; i++){
					if(i != arr.length-1)
						str += arr[i].value + " | ";
					else
						str += arr[i].value;
				}
				return str;
			}
		
			function insert(){
				var arr = document.querySelectorAll(".requiredInputs");
				var bool = true;
				for(var i = 0; i < arr.length; i++){
					if(arr[i].value.length==0){
						bool = false;
						break;
					}
				}
				
				if(!bool){
					alert("You have to fill all values that marked as *");
					return;
				}
				
				var product_type = getProductType();
				var priceSize = getPriceSize();
				var images = getImages();
				var category = getCategory();
				var materials = getMaterials();
				
				if(!checkForNumbers())
					return;
				
				$.ajax({
			 	    url: "/admin/modify",
			 	    dataType: "json",
		            contentType: "application/json",
			 	    data: JSON.stringify({
			 	    	name : document.getElementById("name").value,
						description : document.getElementById("description").value,
						main_img : document.getElementById("main_img").value,
						material : materials,
						product_type : product_type,
						category : category,
						images : images, 
						price : priceSize
			 	    }),
			 	    cache: false,
			 	    type: "POST",
			 	    success: function(data) {
				 		if(data == "SUCCESS"){
				 			alert("New item was inserted");
				 			window.location = "/admin/modifying?table=product&operation=INSERT";
				 		}
			 	    },
			 	    error: function(xhr) {
			 	    	console.log(xhr.responseText);
			 	    }
			     });  
			}
			
			function checkForNumbers(){
				var numbers = /^[-+]?[0-9]+$/;
				var inputs = document.querySelectorAll(".numberic");	
				for(var i = 0; i < inputs.length; i++){
					if(!inputs[i].value.match(numbers)){
						if(inputs[i].className.includes("float")){
							if(!inputs[i].value.includes("/\./") && inputs[i].value.match(/^[^a-zA-Z]+$/))
								continue;
						}
						
						alert("Fields that marked as I are able to contain only numberic value");
						return false;
					}
				}
				return true;
			}
		</script>
	</div>
	
	<#nested>
</#macro>