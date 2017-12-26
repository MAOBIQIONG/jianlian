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
<title>建联商城管理系统</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="key" content="">
<link rel="icon" href="yzy.ico" type="image/x-icon" />
<script type="text/javascript" src="<%=basePath%>static/js/jquery-last.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/manager/util.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/additional-methods.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/bootbox.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/plugins/block/jquery.block.ui.js"></script>
<link rel="stylesheet" href="<%=basePath%>static/css/bootstrap.min.css"/>
<link rel="stylesheet" href="<%=basePath%>static/css/bootstrap-responsive.min.css"/>
<link rel="stylesheet" href="<%=basePath%>static/css/font-awesome.min.css"/>
<!--[if IE 7]>
  <link rel="stylesheet" href="css/font-awesome-ie7.min.css" />
<![endif]-->
<!-- page specific plugin styles -->
<!-- ace styles -->
<link rel="stylesheet" href="<%=basePath%>static/css/ace.min.css" />
<link rel="stylesheet" href="<%=basePath%>static/css/ace-responsive.min.css" />
<script src="<%=basePath%>static/js/jquery.cookie.js"></script>
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
						<div class="row-fluid" style="margin-top:58px;">
							<div class="center"> 
								<h2 class="white" style="text-align:center;">&copy;建联商城管理后台</h2>
							</div>
						</div>
						<div class="space-6" ></div>  
						<div class="row-fluid">
							<div class="position-relative">
								<div id="login-box" class="visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header lighter bigger">
												<i class="icon-user green">账号登录</i> 
											</h4> 
											<div class="space-6"></div> 
											<form action="" method="post" name="loginForm" id="loginForm" >
												<fieldset>
													<div id="zhlogin">
														<label>  <span class="block input-icon input-icon-right"> 
															<input type="text" class="span12" data-rule-required="true" value="" placeholder="用户名" name="USERNAME" id="USERNAME"/> 
															<i class="icon-user"></i></span> 
														</label>
														<label> <span class="block input-icon input-icon-right"> 
															<input name="password" id="password" type="password" class="span12" data-rule-required="true" value="" placeholder="登录密码" />
															<i class="icon-lock"></i></span>
														</label>
													</div>
													<div id="sjlogin" style="display: none;">
														<label> <span class="block input-icon input-icon-right"> 
															<input name="PHONE" id="PHONE" type="text" class="span12" onkeyup="this.value=this.value.replace(/[^\d]/g,'')"  data-rule-required="true" value="" placeholder="请输入手机号"  maxlength="11"/>
															<i class="icon-lock"></i></span>
														</label>
														<label> <span class="block input-icon input-icon-right"> 
															<input name="yzm" id="yzm" type="yzm" class="span7" data-rule-required="true" value="" placeholder="请获取验证码" />
															<input id="verifyCode" type="hidden"/>
															<button type="button" class="btn btn-success radius yanbtn" flag="0" id="btn"  onclick="hqyzm(this)" >获取验证码</button></span>
														</label>
													</div>
													<button id="shouji" type="button">使用手机号登录</button>
													<button id="zhdl" type="button" style="display: none;">使用账号登录</button>
													<div class="space"></div>
													<div class="row-fluid">
														<label class="span8" for="saveid"></label>
														<button id="btn_login" onclick="toLogin()" class="span4 btn btn-small btn-primary">
															<i class="icon-key"></i>登录
														</button>
													</div> 
												</fieldset>
											</form>
										</div> 
									</div> 
								</div> 
							</div> 
						</div> 
					</div>
				</div> 
			</div> 
		</div> 
	</div> 
	 <script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script> 
	<!-- <script type="text/javascript" src="../static/js/jquery.tips.js"></script>   -->
	<!-- <script type="text/javascript" src="../static/js/manager/shper_login.js"></script> -->
	  <script type="text/javascript" src="<%=basePath%>H-ui/lib/layer/2.1/layer.js"></script> 
	<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui/js/H-ui.js"></script> 
	<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui.admin/js/H-ui.admin.js"></script>
	<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/validate-methods.js"></script>
	<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/messages_zh.min.js"></script> 
	<script type="text/javascript">  
	function toLogin(){
		var validate = $("#loginForm").validate({ 
		 	 submitHandler: function(form){  
				user_save(); //执行提交方法
            }        
        });    
	} 
		$(function(){
			$("#shouji").click(function(){
			  $("#zhlogin").hide();
			   $("#sjlogin").show();
			   $("#shouji").hide();
			   $("#zhdl").show();
			   $(".green").html("手机号登录");
			   $("#USERNAME").val("");
			   $("#password").val("");
			});
			
			$("#zhdl").click(function(){
			  $("#sjlogin").hide();
			   $("#zhlogin").show();
			   $("#zhdl").hide();
			   $("#shouji").show();
			   $(".green").html("账号登录");
			    $("#PHONE").val("");
			   $("#yzm").val("");
			});
			
			
			
			
		});
		function hqyzm(){
			if( $(".yanbtn").attr("flag") == "1" ){
	    		return false;
	    	}
			var mobile = $("#PHONE").val();//手机号码 
			if(!mobile){
				alert("请填写手机号");
				result
			}
			 var re=/^1[34578]\d{9}$/;  
			if (re.test(mobile)) {
			var template = "3064129";//短信模板ID 

			$.ajax({
				url : "<%=basePath%>appSend/sendmessage",
               data :{"mobile":mobile,"template":template},
               type : 'post',
               dataType : 'json',
               async : false,
               success : function(result) {
               		changeNum(60);
            	    var res=result.code; 
            	   if("200"==res){
            	   	$("#verifyCode").attr("phone",mobile);
					$("#verifyCode").val(result.obj);
            		  changeNum(60);
            	   }else{
					alert("获取验证码失败！");
				}
            	    
               },
               error : function(msg) {
            	  changeNum(60);
				alert("获取验证码失败！");
					
               }
           });
           } else {
				alert("手机格式错误！");
				return false;
			}
			 $(".yanbtn").attr("flag","1");//已提交
			 return true;
		}
		
		//倒计时
	    function changeNum(timer){
	        timer --;
	        if( timer <= 0 ){
	        	$(".yanbtn").attr("flag","0");
	            $(".yanbtn").text("获取验证码"); 
	            return;
	        }
	        $(".yanbtn").text("重新获取"+"("+timer+")");
	        
	        setTimeout(function(){
	            changeNum(timer);
	        },1000)             
	    }
		/* function settime(val) {
			if (countdown == 0) {
				val.removeAttribute("disabled");
				val.value = "获取验证码";
				countdown = 60;
			} else {
				val.setAttribute("disabled", true);
				val.value = "重新发送(" + countdown + ")";
				countdown--;
			}
			setTimeout(function() {
				settime(val)
			}, 1000);
		} */

		function user_save() {
			var USERNAME = document.getElementById("USERNAME").value;  
			if(USERNAME!=null&&USERNAME!=""&&USERNAME!=undefined){
			$.ajax({
				url : "<%=basePath%>appShoper/tologin",
               data :$("#loginForm").serialize(),
               type : 'post',
               dataType : 'json',
               async : false,
               success : function(result) {
            	   var res=result.responseText; 
            	   if("success"==res){
            		   window.location.href = "<%=basePath%>appShoper/menu"; 
            	   }if("02"==res){
            	   		alert("账号或密码错误");
            	   }if("03"==res){
            	   		alert("用户名或卡号或手机号重复，请联系管理员！");
            	   }if("04"==res){
            	   		alert("账号或密码错误");
            	   }
            	   $("#btn_login").attr("disabled",false);
               },
               error : function(msg) {
            	   var res=msg.responseText;
            	   if("success"==res){
            		   window.location.href = "<%=basePath%>appShoper/menu"; 
            	   }if("02"==res){
            	   		alert("账号或密码错误");
            	   }if("03"==res){
            	   		alert("用户名或卡号或手机号重复，请联系管理员！");
            	   }if("04"==res){
            	   		alert("账号或密码错误");
            	   }
            	   $("#btn_login").attr("disabled",false);
               }
           }); 
           }else{
           var yzm = $("#yzm").val();
           var phone = $("#PHONE").val();
           var verifyCode =$("#verifyCode").val(); 
            if(yzm != verifyCode){
				alert("验证码错误!");
				 return;
				}
           if(phone == $("#verifyCode").attr("phone") ){
	           $.ajax({
					url : "<%=basePath%>appShoper/toPhonelogin",
	               data :$("#loginForm").serialize(),
	               type : 'post',
	               dataType : 'json',
	               async : false,
	               success : function(result) { 
	            	   var res=result.responseText; 
	            	   if("success"==res){
	            		   window.location.href = "<%=basePath%>appShoper/menu"; 
	            	   }if("error"==res){
	            	   		alert("验证码错误!");
	            	   		return;
	            	   }
	            	   $("#btn_login").attr("disabled",false);
	            	    $("#verifyCode").removeAttr("phone");
						$("#verifyCode").val("");
						$("#yzm").val("");
	               },
	               error : function(msg) {
	            	   var res=msg.responseText; 
	            	   if("success"==res){
	            		   window.location.href = "<%=basePath%>appShoper/menu"; 
	            	   }
	            	   $("#btn_login").attr("disabled",false);
	            	   $("#verifyCode").removeAttr("phone");
						$("#verifyCode").val("");
						$("#yzm").val("");
	               }
	           }); 
	           }else{
	           	alert("手机号码不一致");
	           }
	       
	         
           }
	   } 
	   
	</script>  
</body>
</html>