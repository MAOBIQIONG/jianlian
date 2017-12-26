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
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="<%=basePath%>H-ui/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>H-ui/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>H-ui/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>H-ui/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
<title>重置密码</title>
</head>
<body> 
<div class="header"style="background-color:lightblue;text-align: center;font-size: 35px;">上海建联</div>
<div>
  <div id="loginform" class="loginBox"> 
       <div style="font-size:35px;text-align: center;">${msg }上海</div> 
  </div>
</div>
<div class="footer" style="background-color:lightblue;">Copyright 上海建联</div> 
</html>