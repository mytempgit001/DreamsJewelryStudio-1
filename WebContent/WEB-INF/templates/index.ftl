<#import "parts/header.ftl" as c>
<#import "parts/footer.ftl" as d>
<@c.page>

    <!-- Header Carousel -->
    <header id="myCarousel" class="carousel slide">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner">
            <div class="item active">
                <div class="fill" style="background-image:url('static/images/test.jpg');"></div>
                <div class="carousel-caption">
                </div>
            </div>
            <div class="item">
                <div class="fill" style="background-image:url('https://i.etsystatic.com/isbl/172a93/40391109/isbl_3360x840.40391109_h1gv8tog.jpg?version=0');"></div>
                <div class="carousel-caption">
                    <h2>Second caption</h2>
                </div>
            </div>
            <div class="item">
                <div class="fill" style="background-image:url('https://placehold.it/1900x1080&text=Image Three');"></div>
                <div class="carousel-caption">
                    <h2>Third caption comes here</h2>
                </div>
            </div>
        </div>

        <!-- Controls -->
        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
            <span class="icon-prev"></span>
        </a>
        <a class="right carousel-control" href="#myCarousel" data-slide="next">
            <span class="icon-next"></span>
        </a>
    </header>

    <!-- Page Content -->
    <div class="container">

        <!-- Marketing Icons Section -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header" style="color: #111934">
                    Updates
                </h1>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-check"></i> Bootstrap v3.3.7</h4>
                    </div>
                    <div class="panel-body">
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Itaque, optio corporis quae nulla aspernatur in alias at numquam rerum ea excepturi expedita tenetur assumenda voluptatibus eveniet incidunt dicta nostrum quod?</p>
                        <a href="#" class="btn btn-default">Learn More</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-gift"></i> Free &amp; Open Source</h4>
                    </div>
                    <div class="panel-body">
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Itaque, optio corporis quae nulla aspernatur in alias at numquam rerum ea excepturi expedita tenetur assumenda voluptatibus eveniet incidunt dicta nostrum quod?</p>
                        <a href="#" class="btn btn-default">Learn More</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-compass"></i> Easy to Use</h4>
                    </div>
                    <div class="panel-body">
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Itaque, optio corporis quae nulla aspernatur in alias at numquam rerum ea excepturi expedita tenetur assumenda voluptatibus eveniet incidunt dicta nostrum quod?</p>
                        <a href="#" class="btn btn-default">Learn More</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.row -->


		<div class="row products">

            <div class="col-lg-12">
                <h2 class="page-header" style="color: #111934">Collections</h2>
            </div>
		<!-- Projects Row -->
        <div class="row">
            <div class="col-md-6 img-portfolio">
                <a href="portfolio-item.html">
                    <img class="img-responsive img-hover" style="box-shadow: 0 0 10px rgba(0,0,0,0.5);" src="static/images/collections/gemstone.png" alt="" >
                </a>
                <h3 style="text-align: center">
                    <a href="portfolio-item.html" style="color: #111934">Gemstone Jewelry</a>
                </h3>
            </div>
            <div class="col-md-6 img-portfolio">
                <a href="portfolio-item.html">
                    <img class="img-responsive img-hover" style="box-shadow: 0 0 10px rgba(0,0,0,0.5);" src="static/images/collections/resin.png" alt="" >
                </a>
                <h3 style="text-align: center">
                    <a href="portfolio-item.html" style="color: #111934">Resin Jewelry</a>
                </h3>
            </div>
            <div class="col-md-6 img-portfolio">
                <a href="portfolio-item.html">
                    <img class="img-responsive img-hover" style="box-shadow: 0 0 10px rgba(0,0,0,0.5);" src="static/images/collections/earrings.png" alt="">
                </a>
                <h3 style="text-align: center">
                    <a href="portfolio-item.html" style="color: #111934">Earrings</a>
                </h3>
            </div>
            <div class="col-md-6 img-portfolio">
                <a href="portfolio-item.html">
                    <img class="img-responsive img-hover" style="box-shadow: 0 0 10px rgba(0,0,0,0.5);" src="static/images/collections/bracelets.png" alt="">
                </a>
                <h3 style="text-align: center">
                    <a href="portfolio-item.html" style="color: #111934">Bracelets</a>
                </h3>
            </div>
            <div class="col-md-6 img-portfolio">
                <a href="portfolio-item.html">
                    <img class="img-responsive img-hover" style="box-shadow: 0 0 10px rgba(0,0,0,0.5);" src="static/images/collections/necklaces.png" alt="">
                </a>
                <h3 style="text-align: center">
                    <a href="portfolio-item.html" style="color: #111934">Necklaces</a>
                </h3>
            </div>
            <div class="col-md-6 img-portfolio">
                <a href="portfolio-item.html">
                    <img class="img-responsive img-hover" style="box-shadow: 0 0 10px rgba(0,0,0,0.5);" src="static/images/collections/rings.png" alt="">
                </a>
                <h3 style="text-align: center">
                    <a href="portfolio-item.html" style="color: #111934">Rings</a>
                </h3>
            </div>
        </div>
        <!-- /.row -->

		</div>
		
		<!-- Footer -->	
		 <@d.page>
		 </@d.page>
    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="static/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="static/js/bootstrap.min.js"></script>

    <!-- Script to Activate the Carousel -->
    <script>
    $('.carousel').carousel({
        interval: 5000 //changes the speed
    })
    </script>
</@c.page>
</body>

</html>
