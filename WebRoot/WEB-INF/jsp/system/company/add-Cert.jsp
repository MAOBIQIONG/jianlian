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
		<title>添加证件</title>
	</head>

	<body class="pos-r"> 
		<article class="page-container">
			<form class="form form-horizontal" id="form-cert-edit" > 
			<input type="hidden" name="ID" id="ID" value="${CertData.ID }"/>   
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>会员：</label>
				<div class="formControls col-xs-8 col-sm-9"> 
				    <span class="select-box" style="width:150px;"> 
						<select class="select" name="USER_ID" id="USER_ID"> 
							<c:forEach items="${userList}" var="user">
								<option value="${user.ID }" <c:if test="${user.ID ==CertData.USER_ID}">selected</c:if>>${user.REAL_NAME }</option>
							</c:forEach>
						</select>
					</span> 
				</div>
			</div>  
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>证件名称：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" value="${CertData.CERTIFICATE_NAME}" placeholder="" id="CERTIFICATE_NAME" name="CERTIFICATE_NAME" data-rule-required="true">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>证件类型：</label>
				<div class="formControls col-xs-8 col-sm-9"> 
				    <span class="select-box" style="width:150px;"> 
						<select class="select" name="CERTIFICATE_TYPE" id="CERTIFICATE_TYPE">
							<c:forEach items="${typeList}" var="type">
								<option value="${type.BIANMA}" <c:if test="${type.BIANMA == CertData.CERTIFICATE_TYPE}">selected</c:if>>${type.NAME }</option>
							</c:forEach>
						</select>
					</span> 
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>选择图片：</label>
				<div class="formControls col-xs-8 col-sm-9"> 
					<span class="btn-upload form-group"> 
						<input type="file" value="${CertData.IMG_PATH }" id="IMG_PATH" name="IMG_PATH" style="width:150px;" />
					</span> 
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
	<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui/js/H-ui.js"></script>
	<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui.admin/js/H-ui.admin.js"></script>
	<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/validate-methods.js"></script>
	<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/messages_zh.min.js"></script>

       <!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript" src="<%=basePath%>H-ui/lib/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>H-ui/lib/webuploader/0.1.5/webuploader.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.config.js"></script>
	<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script> 
    <script type="text/javascript" src="<%=basePath%>js/ajaxfileupload.js"></script> 
	<script type="text/javascript"> 
	$(function(){
		 var validate = $("#form-cert-edit").validate({ 
			   submitHandler: function(form){ 
				   companyAdd(); //执行提交方法
	           },
	       });    
	});

		 function companyAdd(){ 
				var index = parent.layer.getFrameIndex(window.name);  
				var USER_ID=$('#USER_ID').val();
				var CERTIFICATE_NAME=$('#CERTIFICATE_NAME').val();
				var CERTIFICATE_TYPE=$('#CERTIFICATE_TYPE').val();
				var Id=$('#ID').val();
				if(Id==undefined){
				    Id="";
				} 
				var url="<%=basePath%>Certifi/addcertifi.do";
				var URL=url+"?USER_ID="+USER_ID+"&CERTIFICATE_NAME="+CERTIFICATE_NAME+"&CERTIFICATE_TYPE="+CERTIFICATE_TYPE+"&ID="+Id;
				$.ajaxFileUpload({  
					  url :URL,
					  type:'post',
		              secureuri:false,  
		              fileElementId:'IMG_PATH',//file标签的id  
		              dataType: 'json',//返回数据的类型   
		             // data:$('#form-model-edit').serialize(),//一同上传的数据  
		              success: function (data, status) { 
		                  if(data.statusCode == 200){ 
							  parent.$('.breadcrumb .r .Hui-iconfont').click();
		       				  parent.layer.close(index);
			              } 
		              },  
		              error: function (data, status, e) { 
		                 alert(e);  
		              }  
		         });  
		    } 
    </script>
   </body> 
</html>
