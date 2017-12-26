<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>建联app后台管理系统</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="key" content="">
<link rel="icon" href="yzy.ico" type="image/x-icon" />
<script type="text/javascript" src="static/js/jquery-last.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript" src="static/js/jquery.validate.js"></script>
<script type="text/javascript" src="static/js/messages_zh.min.js"></script>
<script type="text/javascript" src="static/js/manager/util.js"></script>
<script type="text/javascript" src="static/js/additional-methods.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<script type="text/javascript" src="static/plugins/block/jquery.block.ui.js"></script>
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<!--[if IE 7]>
  <link rel="stylesheet" href="css/font-awesome-ie7.min.css" />
<![endif]-->
<!-- page specific plugin styles -->
<!-- ace styles -->
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<script src="static/js/jquery.cookie.js"></script>
<style type="text/css">
#login-box,#forgot-box,#signup-box{background: #7f8c8d;}
</style>
</head>
<body class="login-layout" style="background:lightblue;">
	<div class="container-fluid" id="main-container">
		<div id="main-content">
			<div class="row-fluid">
				<div class="span12">
					<div class="login-container">
						<div class="row-fluid" style="margin-top:28px;">
							<div class="center">
								<h1>
<%--									<img src="static/front/images/logo_w.png" style="width:160px;">--%>
								</h1>
								<h4 class="white" style="text-align:center;">&copy;建联管理后台</h4>
							</div>
						</div>
						<div class="space-6"></div>
						<div class="row-fluid">
							<div class="position-relative">
								<div id="login-box" class="visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header lighter bigger">
												<i class="icon-user green"></i> 登录
											</h4>

											<div class="space-6"></div>

											<form action="" method="post" name="loginForm" id="loginForm" >
												<fieldset>
													<label> <span
														class="block input-icon input-icon-right"> <input
															type="text" class="span12"
															data-rule-required="true"
															data-rule-minlength="5"
															data-rule-maxlength="11" value="admin404"
															 placeholder="用户名/手机号" name="loginname" id="loginname"/> <i
															class="icon-user"></i>
													</span>
													</label>
													<label> <span
														class="block input-icon input-icon-right"> <input name="password" id="password"
															type="password" class="span12"
															data-rule-required="true" value="123456"
															placeholder="登录密码" />
															<i class="icon-lock"></i>
													</span>
													</label>
													<div class="space"></div>
													<div class="row-fluid">
														<label class="span8" for="saveid"> 
														
														</label>
														<button id="btn_login"
															class="span4 btn btn-small btn-primary">
															<i class="icon-key"></i>登录
														</button>
													</div>

												</fieldset>
											</form>
										</div>
										<!--/widget-main-->
										<div class="toolbar clearfix">
											<div>
											</div>
											<div>
											</div>
										</div>
									</div>
									<!--/widget-body-->
								</div>
								<!--/login-box-->


								<div id="forgot-box" class="widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger">
												<i class="icon-key"></i> 忘记密码
											</h4>
											<form action="" method="post" id="resetForm" name="resetForm">
												<fieldset>
													<label> <span
														class="block input-icon input-icon-right"> <input
															data-rule-required="6"
															data-rule-minlength="11"
															data-rule-maxlength="11"
															data-rule-mobileCN="true"
															maxlength="11"
															id="loginname"
															name="loginname"
															type="text" class="span12" placeholder="手机号码" /> <i
															class="icon-phone"></i>
													</span>
													</label>
													
													<label> <span
														class="block input-icon input-icon-right"> <input
															data-rule-required="true"
															data-rule-minlength="6"
															data-rule-maxlength="6"
															maxlength="6"
															name="msgCode" id="msgCode"
															type="text" class="span12" placeholder="短信验证码" /> <i
															class="icon-lock"></i>
													</span>
													</label>
													
													<label> <span
														class="block input-icon input-icon-right"> <input  name="password" id="password"
															data-rule-required="true"
															data-rule-minlength="6"
															data-rule-maxlength="20"
															type="password" class="span12" placeholder="新密码 (6-12位)" /> <i
															class="icon-lock"></i>
													</span>
													</label>
													
														
													<label> <span
														class="block input-icon input-icon-right"> <input name="repassword" id="repassword"
															data-rule-required="true"
															data-rule-minlength="6"
															data-rule-maxlength="20"
															data-rule-equalTo="#resetForm #password"
															type="password" class="span12" placeholder="确认密码 (6-12位)" /> <i
															class="icon-retweet"></i>
													</span>
													</label>
													
													<div class="row-fluid">
														<a onclick="sendMsg();" type="button"
															class="span6 btn btn-small btn-danger">
															<i class="icon-lightbulb"></i> 发送验证码
														</a>
														<button type="submit"
															class=" span6 btn btn-small btn-primary">
															<i class="icon-key"></i> 确定
														</button>
													</div>
												</fieldset>
											</form>
										</div>
										<!--/widget-main-->
										<div class="toolbar center">
											<a href="#" onclick="show_box('login-box'); return false;"
												class="back-to-login-link">返回登录 <i
												class="icon-arrow-right"></i></a>
										</div>
									</div>
									<!--/widget-body-->
								</div>
								<!--/forgot-box-->



								<div id="signup-box" class="widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header green lighter bigger">
												<i class="icon-group blue"></i> 注册
											</h4>
											<form id="registeForm" onsubmit="return false;">
												<fieldset>
													<label> <span
														class="block input-icon input-icon-right"> <input
															type="text" maxlength="11" name="USERNAME" 
															autocomplete="off"
															data-rule-mobileCN="true"
															data-rule-required="true"
															data-rule-minlength="11"
															data-rule-maxlength="11"
															id="loginname" class="span12" placeholder="您的手机号" /> <i
															class="icon-phone"></i>
													</span>
													</label> <label> <span
														class="block input-icon input-icon-right"> <input
															type="text" class="span12"
															autocomplete="off"
															data-rule-required="true"
															data-rule-minlength="2"
															data-rule-maxlength="6"
															 maxlength="6" name="NAME" placeholder="您的姓名" /> <i
															class="icon-user"></i>
													</span>
													</label>
													 <label> <span
														class="block input-icon input-icon-right"> <input
															type="text" class="span12"
															autocomplete="off"
															data-rule-required="true"
															data-rule-idCard="true"
															data-rule-minlength="18"
															data-rule-maxlength="18"
															 maxlength="18" name="SFZH" placeholder="您的身份证号" /> <i
															class="icon-picture"></i>
													</span>
													</label> 
													<label> <span
														class="block input-icon input-icon-right"> <input
															type="password" class="span12" maxlength="12" name="PASSWORD" id="password"
															autocomplete="off"
															data-rule-required="true"
															data-rule-minlength="6"
															data-rule-maxlength="12"
															placeholder="密码 (6-12位)" />
															<i class="icon-lock"></i>
													</span>
													</label> <label> <span
														class="block input-icon input-icon-right"> <input
															type="password" class="span12" maxlength="12" id="repassword"
															autocomplete="off"
															data-rule-required="true"
															data-rule-equalTo="#registeForm #password"
															data-rule-minlength="6"
															data-rule-maxlength="12"
															placeholder="确认密码 (6-12位)" /> <i class="icon-retweet"></i>
													</span>
													</label> 
													
													 <label> <span
														class="block input-icon input-icon-right"> <input
															autocomplete="off"
															type="text" class="span12"
															data-rule-email="true"
															name="EMAIL" placeholder="您的常用邮箱" /> <i
															class="icon-envelope"></i>
													</span>
													</label> 
													
													<label> 
													<input name="gg" id="gg" data-rule-requiredCheck="true" type="checkbox"/>
													<span class="lbl"> 我已阅读并接受   <a href="javascript:window.open('login_yhxy');">《幼智园用户协议》</a></span>
													</label>

													<div class="space-24"></div>

													<div class="row-fluid">
														<button type="reset" class="span6 btn btn-small">
															<i class="icon-refresh"></i> 重置
														</button>
														<button type="submit" id="btn-registe"
															class="span6 btn btn-small btn-success">
															注册 <i class="icon-arrow-right icon-on-right"></i>
														</button>
													</div>

												</fieldset>
											</form>
										</div>

										<div class="toolbar center">
											<a href="#" onclick="show_box('login-box'); return false;"
												class="back-to-login-link"><i class="icon-arrow-left"></i>
												返回登录</a>
										</div>
									</div> 
								</div> 
							</div> 
						</div>
						
					</div>
				</div> 
			</div>
			<!--/row-->
		</div>
		
	</div>
	<!--/.fluid-container-->
	<!-- inline scripts related to this page -->

	<script type="text/javascript">
		function show_box(id) {
			$('.widget-box.visible').removeClass('visible');
			$('#' + id).addClass('visible');
		}
	</script>
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="static/js/manager/login.js"></script>
</body>
</html>