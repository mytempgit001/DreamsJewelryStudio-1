<#macro page>
<#nested>
<!-- Pagination -->
    <div class="row text-center">
        <div class="col-lg-12">
            <ul class="pagination">
                <li>
                    <a href="
                    <#if activePage lte 2>
                    	/catalog
                    <#else>
						/catalog?page=#{activePage-1}                    	
                    </#if>">&laquo;</a>
                </li>
                <#list 1..pagesAmount as i>
	                <#if i=activePage>
	                	<li class="active">
	                    	<a href="/catalog?page=${i}">${i}</a>
	                	</li>
					<#else>	                
	                	<li>
                    		<a href="/catalog?page=${i}">${i}</a>
                		</li>
	                </#if>
                
                </#list>
                
                <li>
                    <a href="<#if activePage gte pagesAmount>
                    	/catalog
                    <#else>
						/catalog?page=#{activePage+1}                    	
                    </#if>">&raquo;</a>
                </li>
            </ul>
        </div>
    </div>
<#nested>
</#macro>