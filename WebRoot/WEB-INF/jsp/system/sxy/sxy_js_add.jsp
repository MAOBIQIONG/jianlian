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
			<form class="form form-horizontal" id="form-project-add">
			    <input type="hidden" name="SXY_ID" id="SXY_ID" value="${pd.SXY_ID }"/>  
			    <input type="hidden" name="ID" id="ID" value="${pd.ID }"/> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>教授名称：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.PRO_NAME}" placeholder="" id="PRO_NAME" name="PRO_NAME" data-rule-required="true">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>教授职位：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.PRO_TITLE}" placeholder="" id="PRO_TITLE" name="PRO_TITLE" data-rule-required="true">
					</div>
				</div>
				<div class="row ">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>教授头像：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<span class="btn-upload form-group"> 
							<input type="file" value="${pd.PRO_IMG }" id="PRO_IMG" name="PRO_IMG" onchange="check()" style="width:150px;" />
						</span> 
					</div>
				</div>
				<div class="row ">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>教授详情图：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<span class="btn-upload form-group"> 
							<input type="file" value="${pd.PRO_DETAILS_IMG }" id="PRO_DETAILS_IMG" name="PRO_DETAILS_IMG" onchange="check()" style="width:150px;" />
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
		<!--/_footer /作为公共模版分离出去-->

		<!--请在下方写此页面业务相关的脚本-->
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/webuploader/0.1.5/webuploader.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.config.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ajaxfileupload.js"></script> 
		<script type="text/javascript" src="<%=basePath%>js/exif.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/uploadImg.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jquery.form.js"></script>
		<script type="text/javascript">
		 $(function(){ 
			 var validate = $("#form-project-add").validate({ 
				   submitHandler: function(form){ 
					   project_save(); //执行提交方法
	                },
	            });    
		 });
		
		 function project_save(){  
				var SXY_ID=$('#SXY_ID').val();
				var ID=$('#ID').val();
				var PRO_NAME=$('#PRO_NAME').val(); 
				var PRO_TITLE=$('#PRO_TITLE').val();  
				if(SXY_ID==undefined){
					SXY_ID=""; 
				}
				if(ID==undefined){
					ID=""; 
				}
				var PRO_IMG=document.getElementById("PRO_IMG").value;
				if(!PRO_IMG){
					alert("请选择要上传的教授头像图片！");
					return
				}
				
				var PRO_DETAILS_IMG=document.getElementById("PRO_DETAILS_IMG").value;
				if(!PRO_DETAILS_IMG){
					alert("请选择要上传的教授头像图片！");
					return
				}
				var url="<%=basePath%>sxypro/jssave.do?ID="+ID; 
				var map = {
					"PRO_NAME":PRO_NAME,
					"SXY_ID":SXY_ID,
					"PRO_TITLE":PRO_TITLE
			  	}; 
			  	var index = parent.layer.getFrameIndex(window.name); 
				$.ajaxFileUpload({  
					  url : url,
					  type:'post',
		             secureuri:false,  
		             data:map,
		             fileElementId:['PRO_IMG','PRO_DETAILS_IMG'],//file标签的id  
		             dataType: 'json',//返回数据的类型   
		             success: function (data, status) { 
		                 if(data.statusCode == 200){  
		                	  if(data.statusCode == 200){ 
		      					parent.$('.breadcrumb .r .Hui-iconfont').click();
		      				    parent.layer.close(index);
		                      }
		                 }
		             },  
		             error: function (data, status, e) { 
		                alert(e);  
		             }  
		        }); 
		    }  
		    
			 
		function check(){ 
			 var aa=document.getElementById("PRO_IMG").value.toLowerCase().split('.');//以“.”分隔上传文件字符串 
			 var bb=document.getElementById("PRO_DETAILS_IMG").value.toLowerCase().split('.');
			 if(aa[aa.length-1]=='gif'||aa[aa.length-1]=='jpg'||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg'){//判断图片格式
				var imagSize =  document.getElementById("PRO_IMG").files[0].size;
				if(imagSize<1024*1024){ 
				    return true;
				}else{
					alert("图片为："+imagSize/(1024*1024)+"M,大于1M,请重新上传！");
					document.getElementById("PRO_IMG").value=null;
					return false;
				} 
		    }else{
		        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
		        document.getElementById("PRO_IMG").value=null;
		        return false;
		    } 
		    
		     if(bb[bb.length-1]=='gif'||bb[bb.length-1]=='jpg'||bb[bb.length-1]=='png'||bb[bb.length-1]=='jpeg'){//判断图片格式
				var imagSize =  document.getElementById("PRO_DETAILS_IMG").files[0].size;
				if(imagSize<1024*1024){ 
				    return true;
				}else{
					alert("图片为："+imagSize/(1024*1024)+"M,大于1M,请重新上传！");
					document.getElementById("PRO_DETAILS_IMG").value=null;
					return false;
				} 
		    }else{
		        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
		        document.getElementById("PRO_DETAILS_IMG").value=null;
		        return false;
		    } 
		 }
			
		</script>
		<!--/请在上方写此页面业务相关的脚本-->
	</body> 
</html>