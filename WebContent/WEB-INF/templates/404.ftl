<#import "parts/header.ftl" as header>
<#import "parts/footer.ftl" as footer>
<@header.page>
    <!-- Page Content -->
    <div class="container">

        <!-- Page Heading/Breadcrumbs -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">404
                    <small>Page Not Found</small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="index.html">Home</a>
                    </li>
                    <li class="active">404</li>
                </ol>
            </div>
        </div>
        <!-- /.row -->

        <div class="row">

            <div class="col-lg-12">
                <div class="jumbotron">
                    <h1 align="center"><span class="error-404">404</span>
                    </h1>
                    <p style="text-align:center;"> 
	                    <#if exceptionMsg??>
	                    	${exceptionMsg}
	                    	<#else>
	                    	The page you're looking for could not be found. Here are some helpful links to get you back on track:
	                    </#if>
                    </p>
                    <ul>
                        <li>
                            <a href="/">Home</a>
                        </li>
                        <li>
                            <a href="/catalog">Catalog</a>
                        </li>
                        <li>
                            <a href="/about">About</a>
                        </li>
                        <li>
                            <a href="contact.html">Blog</a>
                        </li>
                    </ul>
                </div>
            </div>

        </div>

        <hr>

      <@footer.page>
		 </@footer.page>

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="static/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="static/js/bootstrap.min.js"></script>
</body>
</@header.page>
</html>
