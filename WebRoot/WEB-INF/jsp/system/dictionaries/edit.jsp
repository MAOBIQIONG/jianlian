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
		<!--/meta 作为公共模版分离出去-->
		<title>修改项目基本信息</title>
	</head>

	<body>
	    <article class="page-container">
			<form class="form form-horizontal" id="form-dict-add">  
				<input type="hidden" value="${pd.PARENT_ID }" id="PARENT_ID" name="PARENT_ID">
			    <input type="hidden" value="${pd.ZD_ID }" id="ZD_ID" name="ZD_ID">
			    <input type="hidden" value="${pd.PP_BM }" id="PP_BM" name="PP_BM">  
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>名称：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					   <input type="text" class="input-text"  name="NAME" id="NAME" placeholder="这里输入名称" data-rule="required" size="30" value="${pd.NAME }"  data-rule-required="true"/>
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">编码：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					    <input type="text" class="input-text" name="BIANMA" id="BIANMA" placeholder="这里输入编码" data-rule="required" size="30" value="${pd.BIANMA }"  data-rule-required="true" />
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">序号：</label>
					<div class="formControls col-xs-8 col-sm-9">
					    <input type="number" class="input-text" style="width:200px;" name="ORDY_BY" id="ORDY_BY" placeholder="这里输入序号"  data-rule="required" size="30" value="${pd.ORDY_BY}"  data-rule-required="true"/>
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
			 var validate = $("#form-dict-add").validate({ 
				   submitHandler: function(form){ 
					   dict_save(); //执行提交方法
	                },
	            });    
		 });
		function dict_save(){ 
				var index = parent.layer.getFrameIndex(window.name); 
				$.ajax({
	                url : "<%=basePath%>dictionaries/save.do",
	                data :$('#form-dict-add').serialize(),
	                type : 'post',
	                dataType : 'json',
	                async : false,
	                success : function(result) { 
	                   if(result.statusCode==200){
	                	    parent.$('.breadcrumb .r .Hui-iconfont').click();
		       				parent.layer.close(index);
		                }else{
		                	alert(result.msg);
		                } 
	                },
	                error : function(msg) {
	                }
	            });
		    }
		</script>
	</body>
</html>
<!-- <script type="text/javascript">
	//判断编码是否存在
	function has() {
		var ZD_ID = $("#ZD_ID").val();
		var BIANMA = $("#BIANMA").val();
		var url = "dictionaries/has.do?BIANMA=" + BIANMA + "&ZD_ID=" + ZD_ID
				+ "&tm=" + new Date().getTime();
		$.get(url, function(data) {
			if (data == "error") {
				$("#BIANMA").css("background-color", "#D16E6C");

				setTimeout("$('#BIANMA').val('此编码已存在!')", 500);

			} else {
				$("#dict_form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		});
	}
</script>
 -->