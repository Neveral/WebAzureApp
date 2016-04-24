<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
	<title>Search for plagiarism</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- css -->
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="css/style.css" rel="stylesheet" media="screen" >
	<link href="color/default.css" rel="stylesheet" media="screen">
	<script src="js/modernizr.custom.js"></script>
</head>
<body>
<div class="menu-area">
	<div id="dl-menu" class="dl-menuwrapper">
		<button class="dl-trigger">Open Menu</button>
		<ul class="dl-menu">
			<li>
				<a href="#intro">Home</a>
			</li>
			<li><a href="#contact">Contact</a></li>
			<li>
				<a href="#">Sub Menu</a>
				<ul class="dl-submenu">
					<li><a href="#">Sub menu</a></li>
					<li><a href="#">Sub menu</a></li>
				</ul>
			</li>
		</ul>
	</div><!-- /dl-menuwrapper -->
</div>

<!-- intro area -->
<div id="intro">
	<form name="check" method="post" action="${pageContext.request.contextPath}/controller" id="form">
		<h1>The service of checking rewrite quality</h1>
		<div class="block">
			<textarea name="text1" placeholder="Insert text, please...">${data1}</textarea>
		</div>


		<div class="block">
			<textarea name="text2" placeholder="Insert text, please...">${data2}</textarea>
		</div>
		<input type="submit" name="submit" value="Compare" onclick="document.getElementById('form').submit();">
		<h1>${same}</h1>
	</form>
</div>
<!-- Contact -->
<section id="contact" class="home-section bg-white">
	<div class="container">
		<div class="row">
			<div class="col-md-offset-2 col-md-8">
				<div class="section-heading">
					<h2>Contact us</h2>
					<p>Contact via form below and we will be get in touch with you within 24 hours. </p>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-offset-1 col-md-10">

				<form class="form-horizontal" role="form">
					<div class="form-group">
						<div class="col-md-offset-2 col-md-8">
							<input type="text" class="form-control" id="inputName" placeholder="Name">
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-offset-2 col-md-8">
							<input type="email" class="form-control" id="inputEmail" placeholder="Email">
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-offset-2 col-md-8">
							<input type="text" class="form-control" id="inputSubject" placeholder="Subject">
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-offset-2 col-md-8">
							<textarea name="message" class="form-control" rows="3" placeholder="Message"></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-offset-2 col-md-8">
							<button type="button" class="btn btn-theme btn-lg btn-block">Send message</button>
						</div>
					</div>
				</form>

			</div>


		</div>
		<div class="row mar-top30 ">
			<div class="col-md-offset-2 col-md-8">
				<h5>We're on social networks</h5>
				<ul class="social-network">
					<li><a href="#">
						<span class="fa-stack fa-2x">
							<i class="fa fa-circle fa-stack-2x"></i>
							<i class="fa fa-facebook fa-stack-1x fa-inverse"></i>
						</span></a>
					</li>
					<li><a href="#">
						<span class="fa-stack fa-2x">
							<i class="fa fa-circle fa-stack-2x"></i>
							<i class="fa fa-dribbble fa-stack-1x fa-inverse"></i>
						</span></a>
					</li>
					<li><a href="#">
						<span class="fa-stack fa-2x">
							<i class="fa fa-circle fa-stack-2x"></i>
							<i class="fa fa-twitter fa-stack-1x fa-inverse"></i>
						</span></a>
					</li>
					<li><a href="#">
						<span class="fa-stack fa-2x">
							<i class="fa fa-circle fa-stack-2x"></i>
							<i class="fa fa-pinterest fa-stack-1x fa-inverse"></i>
						</span></a>
					</li>
				</ul>
			</div>
		</div>

	</div>
</section>

<footer>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<p>Copyright &copy; 2015 By George Strelchenok. All rights reserved. By <a href="http://vk.com/neveral">Neveral</a></p>
			</div>
			<!--
                All links in the footer should remain intact.
                Licenseing information is available at: http://bootstraptaste.com/license/
                You can buy this theme without footer links online at: http://bootstraptaste.com/buy/?theme=Mamba
            -->
		</div>
	</div>
</footer>

<!-- js -->
<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.smooth-scroll.min.js"></script>
<script src="js/jquery.dlmenu.js"></script>
<script src="js/wow.min.js"></script>
<script src="js/custom.js"></script>

</body>
</html>
