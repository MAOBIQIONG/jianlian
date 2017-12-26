<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
 <base href="<%=basePath%>">   
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="<%=basePath%>H-ui/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<%-- <link href="<%=basePath%>H-ui/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" /> --%>
<link href="<%=basePath%>H-ui/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>H-ui/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
<title>失效页面</title>
<style>
    .loginBox {
	    position: absolute;
	    width: 80%;
	    height: 50%;
	   /*  background-color:red; */
	     /* background: url(../images/admin-loginform-bg.png) no-repeat; */
	    left: 10%;
	    top: 20%; 
	    padding-top: 38px;
	}
	
    .col-xs-3{
	    min-height: 1px;
	    position: relative; 
	    box-sizing: border-box;
	    padding-left:0px;
	    padding-right:15px;
	    -webkit-transition: all .3s ease-in;
	    -moz-transition: all .3s ease-in;
	    -o-transition: all .3s ease-in;
	    transition: all .3s ease-in;
	}
	
	.col-xs-8 {
	    min-height: 1px;
	    position: relative;
	    box-sizing: border-box;
	    padding-left: 0px;
	    padding-right: 0px;
	    -webkit-transition: all .3s ease-in;
	    -moz-transition: all .3s ease-in;
	    -o-transition: all .3s ease-in;
	    transition: all .3s ease-in;
	} 
	 
	.form-horizontal .form-label {
	    text-align: right;
	}
 
</style>
</head>
<body> 
<div class="header" style="background-color:lightblue;text-align: center;font-size: 35px;">上海建联</div>
  <div class="loginBox">
    <form class="form form-horizontal"  id="updatePw" method="post" style="clear: both;" >
        <div style="font-size:20px;">链接已失效！</div>
    </form>  
  </div> 

<!-- <div style="position:absolut;bottom:0px;background-color:lightblue;text-align: center;">Copyright 上海建联  </div> -->
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script> 
		 
</body>
</html>