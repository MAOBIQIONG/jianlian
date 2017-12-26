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
<title>重置密码</title>
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
       <div class="row cl" style="clear: both;">
        <label class="form-label col-xs-3"><i class="Hui-iconfont" style="text-align: right;">账户</i></label>
        <div class="formControls col-xs-8">
          <input id="USER_NAME" name="USER_NAME" type="text"  class="input-text size-L">
        </div>
      </div>
      <div class="row cl" style="clear: both;">
        <label class="form-label col-xs-3"><i class="Hui-iconfont" style="text-align: right;">密码</i></label>
        <div class="formControls col-xs-8">
          <input id="PASSWORD" name="PASSWORD" type="password"  class="input-text size-L">
        </div>
      </div>  
      <div class="row cl" style="clear: both;">
        <label class="form-label col-xs-3"><i class="Hui-iconfont" style="text-align: right;">确认密码</i></label>
        <div class="formControls col-xs-8">
          <input id="password" name="password" type="password" class="input-text size-L">
        </div>
      </div>  
      <div class="row cl" style="clear: both;margin-top:50px;">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <input type="button" onclick="save()" class="btn btn-success radius size-L" value="&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;定&nbsp;">
        </div>
      </div>
    </form> 
     <div class="row cl" id="returnDiv" style="display:none;text-align: center;font-size: 25px;padding-top: 100px;"> 
        <div class="formControls col-xs-8" >
             <div style="color:red;text-align: center;font-size: 35px;">密码修改完成！</div>
        </div>
      </div>
  </div> 

<!-- <div style="position:absolut;bottom:0px;background-color:lightblue;text-align: center;">Copyright 上海建联  </div> -->
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript">  
		 function save(){
			 var PASSWORD=$("#PASSWORD").val();
			 var password=$("#password").val();
			 var USER_NAME=$("#USER_NAME").val();
			 if(PASSWORD==password){ 
				 var url="<%=basePath%>appLogin/updatePW.do";
				 var URL=url+"?USER_NAME="+USER_NAME+"&PASSWORD="+PASSWORD;
				 $.ajax({
		                url :URL,
		                data : $('#update').serialize(),
		                type : 'post',
		                dataType : 'json',
		                async : false,
		                success : function(result) { 
		                	if(result.statusCode==200){
		                		$("#updatePw").hide();
		                		$("#returnDiv").text(result.msg); 
			                	$("#returnDiv").show(); 
		                	}else{
		                		$("#updatePw").hide();
		                		$("#returnDiv").text(result.msg); 
			                	$("#returnDiv").show(); 
		                	} 
		                },
		                error : function(msg) {
		                	
		                }
		            });
			 }else{
				alert("两次输入的密码不一致！") 
			 } 
		 } 
		</script>
</body>
</html>