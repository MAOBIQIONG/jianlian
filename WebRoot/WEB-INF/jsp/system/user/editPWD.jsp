<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="/favicon.ico" >
<LINK rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
        <link rel="stylesheet" type="text/css" href="<%=basePath%>H-ui/static/h-ui/css/H-ui.min.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>H-ui/static/h-ui.admin/css/H-ui.admin.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>H-ui/lib/Hui-iconfont/1.0.7/iconfont.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>H-ui/lib/icheck/icheck.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>H-ui/static/h-ui.admin/skin/default/skin.css" id="skin" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>H-ui/static/h-ui.admin/css/style.css" />
		<title>修改密码</title>
	</head>

	<body>
	    <article class="page-container">
			<form class="form form-horizontal" id="form-user-edit">
			    <input type="hidden" name="USERNAME" id="USERNAME" value="${pd.USERNAME }"/> 
				<%-- <div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>账号：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					    <input type="text" class="input-text" name="USERNAME" id="loginname" data-rule="required;remote[<%=basePath%>user/hasU.do];length[5~18]" value="${pd.USERNAME }" placeholder="这里输入用户名" data-rule-required="true"/>
					</div>
				</div>  --%>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>原密码：</label>
					<div class="formControls col-xs-8 col-sm-9">
					   <input type="password" class="input-text" name="PASSWORD" id="PASSWORD"  maxlength="32"  minlength="6" data-rule-required="true"/>
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>新密码：</label>
					<div class="formControls col-xs-8 col-sm-9">
					   <input type="password" class="input-text" name="pwd" id="pwd" maxlength="32" minlength="6" data-rule-required="true"/>
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>确认密码：</label>
					<div class="formControls col-xs-8 col-sm-9">
					   <input type="password" class="input-text" name="pwd2" id="pwd2"  maxlength="32"  minlength="6" data-rule-required="true"/>
					</div>
		
				</div> 
				<div class="row cl">
					<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
						<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"> 
					</div>
					
					
				</div>
			</form>
		</article> 
        <!--_footer 作为公共模版分离出去-->
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/layer/2.1/layer.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/icheck/jquery.icheck.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/validate-methods.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/messages_zh.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui/js/H-ui.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui.admin/js/H-ui.admin.js"></script>
		<!--/_footer /作为公共模版分离出去-->

		<!--请在下方写此页面业务相关的脚本-->
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/webuploader/0.1.5/webuploader.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.config.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript"> 
		 $(function(){  
			 var validate = $("#form-user-edit").validate({ 
		   	 	submitHandler: function(form){ 
			     	user_save(); //执行提交方法
             	} 
             });
		 }); 
		 
		 function user_save(){   
				var index = parent.layer.getFrameIndex(window.name); 
				var pwd=$("#pwd").val();
				var pwd2=$("#pwd2").val();
				if(pwd==pwd2){
					  $.ajax({
		                url : "<%=basePath%>user/editPWD.do",
		                data :$('#form-user-edit').serialize(),
		                type : 'post',
		                dataType : 'json',
		                async : false,
		                success : function(result) { 
		                   if(result.statusCode == 200){ 
								alert(result.msg);  
								window.location.href="<%=basePath%>logout.do";
			                 }else{
			                    alert(result.msg);
			                 }   
		                },
		                error : function(msg) {
		                }
		            });   
				}else{
				   alert("两次输入的密码不一致！");
				} 
		    } 
		</script> 
	</body> 
</html>  