<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
	<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>CrazyTeam &mdash; Let go</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Free HTML5 Template by FREEHTML5.CO" />
	<meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />
	<meta name="author" content="FREEHTML5.CO" />

  	<!-- Facebook and Twitter integration -->
	<meta property="og:title" content=""/>
	<meta property="og:image" content=""/>
	<meta property="og:url" content=""/>
	<meta property="og:site_name" content=""/>
	<meta property="og:description" content=""/>
	<meta name="twitter:title" content="" />
	<meta name="twitter:image" content="" />
	<meta name="twitter:url" content="" />
	<meta name="twitter:card" content="" />

  	<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
  	<link rel="shortcut icon" href="favicon.ico">

  	<!-- Google Webfont -->
	<style type="text/css">
	@font-face {
  font-family: 'Lato';
  font-style: normal;
  font-weight: 300;
  src: local('Lato Light'), local('Lato-Light'), url(http://www.lobinary.com/views/team/home/woff2/EsvMC5un3kjyUhB9ZEPPwg.woff2) format('woff2');
}
@font-face {
  font-family: 'Lato';
  font-style: normal;
  font-weight: 400;
  src: local('Lato Regular'), local('Lato-Regular'), url(http://www.lobinary.com/views/team/home/woff2/1YwB1sO8YE1Lyjf12WNiUA.woff2) format('woff2');
}
@font-face {
  font-family: 'Lato';
  font-style: normal;
  font-weight: 700;
  src: local('Lato Bold'), local('Lato-Bold'), url(http://www.lobinary.com/views/team/home/woff2/H2DMvhDLycM56KNuAtbJYA.woff2) format('woff2');
}</style>
	<!-- Themify Icons -->
	<link rel="stylesheet" href="css/themify-icons.css">
	<!-- Bootstrap -->
	<link rel="stylesheet" href="css/bootstrap.css">
	<!-- Owl Carousel -->
	<link rel="stylesheet" href="css/owl.carousel.min.css">
	<link rel="stylesheet" href="css/owl.theme.default.min.css">
	<!-- Magnific Popup -->
	<link rel="stylesheet" href="css/magnific-popup.css">
	<!-- Superfish -->
	<link rel="stylesheet" href="css/superfish.css">
	<!-- Easy Responsive Tabs -->
	<link rel="stylesheet" href="css/easy-responsive-tabs.css">
	<!-- Animate.css -->
	<link rel="stylesheet" href="css/animate.css">
	<!-- Theme Style -->
	<link rel="stylesheet" href="css/style.css">

	<!-- Modernizr JS -->
	<script src="js/modernizr-2.6.2.min.js"></script>
	<!-- FOR IE9 below -->
	<!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->

	</head>
	<body>

		<!-- START #fh5co-header -->
		<header id="fh5co-header-section" role="header" class="" >
			<div class="container">

				

				<!-- <div id="fh5co-menu-logo"> -->
					<!-- START #fh5co-logo -->
					<h1 id="fh5co-logo" class="pull-left"><a href="index.jsp">
					
					<!-- <img src="images/logo.png" alt="Slant Free HTML5 Template"> -->
					Crazy Team
					
					</a></h1>
					
					<!-- START #fh5co-menu-wrap -->
					<nav id="fh5co-menu-wrap" role="navigation">
						
						
						<ul class="sf-menu" id="fh5co-primary-menu">
							<li class="active">
								<a href="index.jsp">主页</a>
							</li>
							<li>
								<a href="#" class="fh5co-sub-ddown">功能</a>
								 <ul class="fh5co-sub-menu">
								 	<li><a href="#">啥也没有</a></li>
								 	<li><a href="#">你也不来开发</a></li>
									<li>
										<a href="#" class="fh5co-sub-ddown">你要是不来开发</a>
										<ul class="fh5co-sub-menu">
											<li><a href="#" target="_blank">那</a></li>
											<li><a href="#" target="_blank">就</a></li>
											<li><a href="#" target="_blank">啥</a></li>
											<li><a href="#" target="_blank">也</a></li>
											<li><a href="#" target="_blank">没</a></li>
											<li><a href="#" target="_blank">有</a></li>
										</ul>
									</li>
									<li><a href="#">快来一起开发吧</a></li> 
								</ul>
							</li>
							<li><a href="index.jsp">论坛？没有</a></li>
							<li class="fh5co-special"><a href="contact.html">Contact US</a></li>
						</ul>
					</nav>
				<!-- </div> -->

			</div>
		</header>
		
		
		<div id="fh5co-hero">
			<a href="#fh5co-main" class="smoothscroll fh5co-arrow to-animate hero-animate-4"><i class="ti-angle-down"></i></a>
			<!-- End fh5co-arrow -->
			<div class="container">
				<div class="col-md-8 col-md-offset-2">
					<div class="fh5co-hero-wrap">
						<div class="fh5co-hero-intro">
							<h1 class="to-animate hero-animate-1">
							<% 
								String isSuccessObj = request.getParameter("isSuccess");
							if(isSuccessObj==null){
								%>
									欢迎光临疯子团队主页面
									<h2 class="to-animate hero-animate-2">加入QQ群(202254695) <a href="http://shang.qq.com/wpa/qunwpa?idkey=6291ca6acea7e551d6ba2a87e58d5f7555e49f12af92d203805489bcbde51db8" target="_blank" title="CrazyTeam"><font color="blue" ><b>点击加入CrazyTeam</b></font></a></h2>
								<%
							}else{
								if(isSuccessObj.equals("true")){
									%>
									恭喜您!<br>您已经成为疯子团队的一员<br>赶快加入我们吧
									<h2 class="to-animate hero-animate-2">加入QQ群(202254695) <a href="http://shang.qq.com/wpa/qunwpa?idkey=6291ca6acea7e551d6ba2a87e58d5f7555e49f12af92d203805489bcbde51db8" target="_blank" title="CrazyTeam"><font color="blue" ><b>点击加入CrazyTeam</b></font></a></h2>
								<%
								}else{
									%>
									诶哟，服务器可能坏了<br>~~~~(>_<)~~~~<br>快来，助我们一臂之力吧. 
									<h2 class="to-animate hero-animate-2">加入QQ群(202254695) <a href="http://shang.qq.com/wpa/qunwpa?idkey=6291ca6acea7e551d6ba2a87e58d5f7555e49f12af92d203805489bcbde51db8" target="_blank" title="CrazyTeam"><font color="blue" ><b>点击加入CrazyTeam</b></font></a></h2>
								<%
								}
							}
							%></h1>
							<p class="to-animate hero-animate-3"><a href="http://shang.qq.com/wpa/qunwpa?idkey=6291ca6acea7e551d6ba2a87e58d5f7555e49f12af92d203805489bcbde51db8" class="btn btn-outline btn-lg">Get Started</a></p>
						</div>
					</div>
				</div>
			</div>		
		</div>
		
		<div id="fh5co-main">
	
			<div class="container">
				<div class="row" id="fh5co-features">
					
					<div class="col-md-4 col-sm-6 text-center fh5co-feature feature-box">
						<div class="fh5co-feature-icon">
							<i class="ti-mobile"></i>
						</div>
						<h3 class="heading">Mobile</h3>
						<p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Iste sunt porro delectus cum officia magnam.</p>
					</div>
					<div class="col-md-4 col-sm-6 text-center fh5co-feature feature-box"> 
						<div class="fh5co-feature-icon">
							<i class="ti-lock"></i>
						</div>
						<h3 class="heading">Lock</h3>
						<p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Iste sunt porro delectus cum officia magnam. </p>
					</div>

					<div class="clearfix visible-sm-block"></div>

					<div class="col-md-4 col-sm-6 text-center fh5co-feature feature-box"> 
						<div class="fh5co-feature-icon">
							<i class="ti-video-camera"></i>
						</div>
						<h3 class="heading">Video</h3>
						<p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Iste sunt porro delectus cum officia magnam.</p>
					</div>

					<div class="clearfix visible-md-block visible-lg-block"></div>

					<div class="col-md-4 col-sm-6 text-center fh5co-feature feature-box">
						<div class="fh5co-feature-icon">
							<i class="ti-shopping-cart"></i>
						</div>
						<h3 class="heading">Shop</h3>
						<p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Iste sunt porro delectus cum officia magnam. </p>
					</div>

					<div class="clearfix visible-sm-block"></div>

					<div class="col-md-4 col-sm-6 text-center fh5co-feature feature-box"> 
						<div class="fh5co-feature-icon">
							<i class="ti-palette"></i>
						</div>
						<h3 class="heading">Pallete</h3>
						<p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Iste sunt porro delectus cum officia magnam.</p>
					</div>
					<div class="col-md-4 col-sm-6 text-center fh5co-feature feature-box"> 
						<div class="fh5co-feature-icon">
							<i class="ti-truck"></i>
						</div>
						<h3 class="heading">Deliver</h3>
						<p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Iste sunt porro delectus cum officia magnam.</p>
					</div>
				</div>
				<!-- END row -->

				<div class="fh5co-spacer fh5co-spacer-md"></div>
				<!-- End Spacer -->

				<div class="row" id="fh5co-works">
					<div class="col-md-8 col-md-offset-2 text-center fh5co-section-heading work-box">
						<h2 class="fh5co-lead">Awesome Projects</h2>
						<p class="fh5co-sub">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Velit est facilis maiores, perspiciatis accusamus asperiores sint consequuntur debitis. Ut, dolores sit amet consectetur adipisicing elit.</p>
						<div class="fh5co-spacer fh5co-spacer-sm"></div>
					</div>
					<div class="col-md-4 col-sm-6 col-xs-6 col-xxs-12 text-center fh5co-work-item work-box">
						<figure><a href="#"><img class="img-responsive" src="images/work_1.jpg" alt="Free HTML5 Template"></a></figure>
						<p class="fh5co-category">Web Design, Identity, Packaging </p>
						<h3 class="heading">Work no. 1</h3>
					</div>
					<div class="col-md-4 col-sm-6 col-xs-6 col-xxs-12 text-center fh5co-work-item work-box"> 
						<figure><a href="#"><img class="img-responsive" src="images/work_2.jpg" alt="Free HTML5 Template"></a></figure>
						<p class="fh5co-category">Web Design, Identity, Packaging </p>
						<h3 class="heading">Work no. 2</h3>
					</div>

					<div class="clearfix visible-sm-block visible-xs-block"></div>

					<div class="col-md-4 col-sm-6 col-xs-6 col-xxs-12 text-center fh5co-work-item work-box"> 
						<figure><a href="#"><img class="img-responsive" src="images/work_3.jpg" alt="Free HTML5 Template"></a></figure>
						<p class="fh5co-category">Web Design, Identity, Packaging </p>
						<h3 class="heading">Work no. 3</h3>
					</div>

					<div class="clearfix visible-md-block visible-lg-block"></div>

					<div class="col-md-4 col-sm-6 col-xs-6 col-xxs-12 text-center fh5co-work-item work-box">
						<figure><a href="#"><img class="img-responsive" src="images/work_4.jpg" alt="Free HTML5 Template"></a></figure>
						<p class="fh5co-category">Web Design, Identity, Packaging </p>
						<h3 class="heading">Work no. 4</h3>
					</div>

					<div class="clearfix visible-sm-block visible-xs-block"></div>

					<div class="col-md-4 col-sm-6 col-xs-6 col-xxs-12 text-center fh5co-work-item work-box"> 
						<figure><a href="#"><img class="img-responsive" src="images/work_5.jpg" alt="Free HTML5 Template"></a></figure>
						<p class="fh5co-category">Web Design, Identity, Packaging </p>
						<h3 class="heading">Work no. 5</h3>
					</div>
					<div class="col-md-4 col-sm-6 col-xs-6 col-xxs-12 text-center fh5co-work-item work-box"> 
						<figure><a href="#"><img class="img-responsive" src="images/work_6.jpg" alt="Free HTML5 Template"></a></figure>
						<p class="fh5co-category">Web Design, Identity, Packaging </p>
						<h3 class="heading">Work no. 6</h3>
					</div>

					<div class="col-md-4 col-md-offset-4 text-center work-box">
						<p><a href="#" class="btn btn-outline btn-md">View Portfolio</a></p>
					</div>
				</div>
				<!-- END row -->
				
				<div class="fh5co-spacer fh5co-spacer-md"></div>
				<div class="row">
					<!-- Start Slider Testimonial -->
	            <h2 class="fh5co-uppercase-heading-sm text-center animate-box">Customer Says...</h2>
	            <div class="fh5co-spacer fh5co-spacer-xs"></div>
	            <div class="owl-carousel-fullwidth animate-box">
	            <div class="item">
	              <p class="text-center quote">&ldquo;Design must be functional and functionality must be translated into visual aesthetics, without any reliance on gimmicks that have to be explained. &rdquo; <cite class="author">&mdash; Ferdinand A. Porsche</cite></p>
	            </div>
	            <div class="item">
	              <p class="text-center quote">&ldquo;Creativity is just connecting things. When you ask creative people how they did something, they feel a little guilty because they didn’t really do it, they just saw something. It seemed obvious to them after a while. &rdquo;<cite class="author">&mdash; Steve Jobs</cite></p>
	            </div>
	            <div class="item">
	              <p class="text-center quote">&ldquo;I think design would be better if designers were much more skeptical about its applications. If you believe in the potency of your craft, where you choose to dole it out is not something to take lightly. &rdquo;<cite class="author">&mdash; Frank Chimero</cite></p>
	            </div>
	          </div>
	           <!-- End Slider Testimonial -->
				</div>
				<!-- END row -->
				<div class="fh5co-spacer fh5co-spacer-md"></div>

			</div>
			<!-- END container -->

		
		</div>
		<!-- END fhtco-main -->


		<footer role="contentinfo" id="fh5co-footer">
			<a href="#" class="fh5co-arrow fh5co-gotop footer-box"><i class="ti-angle-up"></i></a>
			<div class="container">
				<div class="row">
					<div class="col-md-4 col-sm-6 footer-box">
						<h3 class="fh5co-footer-heading">About us</h3>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima delectus dolorem fugit totam, commodi ad.</p>
						<p><a href="#" class="btn btn-outline btn-sm">I'm a button</a></p>

					</div>
					<div class="col-md-4 col-sm-6 footer-box">
						<h3 class="fh5co-footer-heading">Links</h3>
						<ul class="fh5co-footer-links">
							<li><a href="#">Terms &amp; Conditions</a></li>
							<li><a href="#">Our Careers</a></li>
							<li><a href="#">Support &amp; FAQ's</a></li>
							<li><a href="#">Sign up</a></li>
							<li><a href="#">Log in</a></li>
						</ul>
					</div>
					<div class="col-md-4 col-sm-12 footer-box">
						<h3 class="fh5co-footer-heading">Get in touch</h3>
						<ul class="fh5co-social-icons">
							
							<li><a href="#"><i class="ti-google"></i></a></li>
							<li><a href="#"><i class="ti-twitter-alt"></i></a></li>
							<li><a href="#"><i class="ti-facebook"></i></a></li>	
							<li><a href="#"><i class="ti-instagram"></i></a></li>
							<li><a href="#"><i class="ti-dribbble"></i></a></li>
						</ul>
					</div>
					<div class="col-md-12 footer-box">
						<div class="fh5co-copyright">
						<p>&copy; 2016 All Rights Reserved. <br><a href="http://www.lobinary.com/" target="_blank" title="Crazy Team">Crazy Team</a> </p>
						</div>
					</div>
					
				</div>
				<!-- END row -->
				<div class="fh5co-spacer fh5co-spacer-md"></div>
			</div>
		</footer>
			
			
		<!-- jQuery -->
		<script src="js/jquery-1.10.2.min.js"></script>
		<!-- jQuery Easing -->
		<script src="js/jquery.easing.1.3.js"></script>
		<!-- Bootstrap -->
		<script src="js/bootstrap.js"></script>
		<!-- Owl carousel -->
		<script src="js/owl.carousel.min.js"></script>
		<!-- Magnific Popup -->
		<script src="js/jquery.magnific-popup.min.js"></script>
		<!-- Superfish -->
		<script src="js/hoverIntent.js"></script>
		<script src="js/superfish.js"></script>
		<!-- Easy Responsive Tabs -->
		<script src="js/easyResponsiveTabs.js"></script>
		<!-- FastClick for Mobile/Tablets -->
		<script src="js/fastclick.js"></script>
		<!-- Parallax -->
		<script src="js/jquery.parallax-scroll.min.js"></script>
		<!-- Waypoints -->
		<script src="js/jquery.waypoints.min.js"></script>
		<!-- Main JS -->
		<script src="js/main.js"></script>

	</body>
</html>
