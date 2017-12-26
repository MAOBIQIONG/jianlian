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
		<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]--> 
<!-- <style>
.col-xs-2,.col-xs-1{
   padding:0px;
}
</style> -->
	</head> 


	<body>
		<article class="page-container">
			<form class="form form-horizontal" id="form-horizontal">
			    <input type="hidden" name="ID" id="ID" value="${data.ID }"/>  
			    <div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>入驻类别：</label> 
					<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="belong">
						<div class="radio-box">
							<input name="SETTLED_TYPE" id="person" type="radio"  class="SETTLED_TYPE"  value="1"  <c:if test="${data.SETTLED_TYPE == '1' }">checked="checked"</c:if>>
							<label for="flag-1">创业者</label>
						</div>
						<div class="radio-box">
							<input type="radio" id="company" class="SETTLED_TYPE" name="SETTLED_TYPE" value="2"  <c:if test="${data.SETTLED_TYPE == '2' }">checked="checked"</c:if>>
							<label for="flag-2">企业</label>
						</div>  
					</div>
				</div>  
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>联系人：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.YQ_NAME}" placeholder="" id="YQ_NAME" name="YQ_NAME" data-rule-required="true">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>联系方式：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.PHONE}" placeholder="" id="PHONE" name="PHONE" data-rule-required="true">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><span id="data-name">企业全称：</span></label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.ENTERPRISE_NAME}" placeholder="" id="ENTERPRISE_NAME" name="ENTERPRISE_NAME" data-rule-required="true">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>入驻诉求：  </label>
					<div class="formControls col-xs-8 col-sm-9"> 
				       <textarea class="textarea"  placeholder=""  id="REQUIREMENT" name="REQUIREMENT" data-rule-required="true">${data.REQUIREMENT}</textarea> 
					</div>
				</div> 
				<div class="row cl data-com">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>企业介绍：  </label>
					<div class="formControls col-xs-8 col-sm-9"> 
				       <textarea class="textarea"  placeholder=""  id="ENTERPRISE_INTRODUCE" name="ENTERPRISE_INTRODUCE" data-rule-required="true">${data.ENTERPRISE_INTRODUCE}</textarea> 
					</div>
				</div> 
				<div class="row cl data-com">
					<label class="form-label col-xs-4 col-sm-2">企业网址：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.ENTERPRISE_URL }" placeholder="http://" id="ENTERPRISE_URL" name="ENTERPRISE_URL" data-rule-url="true">
					</div> 
				</div> 
				<div class="row cl data-nature" style="display:none;">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>创业性质：</label> 
					<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="belong">
						<div class="radio-box">
							<input name="NATURE" type="radio"  class="NATURE"  value="01"  <c:if test="${data.NATURE == '01' }">checked="checked"</c:if>>
							<label for="flag-1">个人创业</label>
						</div>
						<div class="radio-box">
							<input type="radio" class="NATURE" name="NATURE" value="02"  <c:if test="${data.NATURE == '02' }">checked="checked"</c:if>>
							<label for="flag-2">团体创业 </label>
						</div>  
					</div>
				</div>  
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>审核结果：</label> 
					<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="STATUS">
						<div class="radio-box">
							<input type="radio" id="flag-1" name="STATUS"  value="03" <c:if test="${data.STATUS == '03' }">checked="checked"</c:if>>
							<label for="flag-1">审核通过</label>
						</div>
						<div class="radio-box">
							<input type="radio" id="flag-2" name="STATUS" value="02" <c:if test="${data.STATUS == '02' }">checked="checked"</c:if>>
							<label for="flag-2">审核不通过</label>
						</div> 
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">不通过原因：</label>  
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.NOPASS_REASON}" placeholder="" id="NOPASS_REASON" name="NOPASS_REASON">
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
		<!--/_footer /作为公共模版分离出去-->

		<!--请在下方写此页面业务相关的脚本-->
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/webuploader/0.1.5/webuploader.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.config.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript">
		$(function(){ 
			var value = $('#belong input[name="SETTLED_TYPE"]:checked ').val(); 
	      	if(value=='1'){
	      		$("#data-name").text("项目全称：");
	     		$(".data-nature").show();  
	     		$(".data-com").hide(); 
	      	}
	      	if(value=='2'){
	      		$("#data-name").text("企业全称：");
				$(".data-com").show(); 
				$(".data-nature").hide();  
	      	} 
			$("#company").click(function(){ 
				$("#data-name").text("企业全称：");
				$(".data-com").show(); 
				$(".data-nature").hide();  
     		}); 
     	   $("#person").click(function(){ 
     		  $("#data-name").text("项目全称：");
     		  $(".data-nature").show();  
     		  $(".data-com").hide(); 
     		  
 	   		});   
			 
			var validate = $("#form-horizontal").validate({ 
			   submitHandler: function(form){ 
				   save(); //执行提交方法
		        },
		     });    
		 });
		
		 function save(){  
				var STATUS = $('#STATUS input[name="STATUS"]:checked ').val(); 
				if(STATUS==null||STATUS==''||STATUS==undefined){
					alert("请选择审核结果!");
					return;
				}
				var reason=$('#NOPASS_REASON').val();
				if(STATUS=='02'&&(reason==null||reason==''||reason==undefined)){
					alert("请填写不通过的原因!");
					return;
				}
				$.ajax({
	                url : "<%=basePath%>settle/updateStatus.do",
	                data : $('#form-horizontal').serialize(),
	                type : 'post',
	                dataType : 'json',
	                async : false,
	                success : function(result) {
	                   if(result.statusCode =='200'){
							parent.$('.breadcrumb .r .Hui-iconfont').click();
		       				parent.layer.close(index);
		                }
	                },
	                error : function(msg) {
	                }
	            });
		    }  
		</script>
		<!--/请在上方写此页面业务相关的脚本-->
	</body> 
</html>