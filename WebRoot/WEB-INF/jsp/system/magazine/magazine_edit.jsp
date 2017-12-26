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
<link rel="stylesheet" href="<%=basePath%>H-ui/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
		<title>编辑期刊</title>
	</head>

<body class="pos-r">  
<article class="page-container">
	<form class="form form-horizontal" id="form-horizontal"> 
		<input type="hidden" name="ID" id="ID" value="${pd.ID }"/>   
		<input type="hidden" name="QK_COVER" id="QK_COVER" value="${pd.COVER_PATH}"/>   
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>期刊名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text valib" value="${pd.TITLE}" placeholder="" id="TITLE" name="TITLE" data-rule-required="true">
			</div>
		</div>  
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>期刊版本：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text valib" value="${pd.VERSION}" placeholder="" id="VERSION" name="VERSION" data-rule-required="true">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">期刊封面：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
			    <span class="btn-upload form-group"> 
					<input type="file" id="COVER_PATH" name="COVER_PATH" onchange="check()" style="width:150px;" />
				</span> 
			</div> 
		</div>  
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">期刊封面：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
			    <span class="btn-upload form-group"> 
					<input type="text" class="input-text valib"  id="QK_PATH" name="QK_PATH" value="${pd.QK_PATH}" data-rule-required="true"/>
				</span> 
			</div> 
		</div>  
		 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">期刊描述：</label> 
			<div class="formControls col-xs-8 col-sm-9"> 
		       <textarea class="textarea"  placeholder=""  id="ABOUT" name="ABOUT" data-rule-required="true">${pd.ABOUT }</textarea> 
			</div>
		</div> 
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2"> 
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article>   
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/bootstrap-modal/2.2.4/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/bootstrap-modal/2.2.4/bootstrap-modal.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/messages_zh.min.js"></script>
<!--请在下方写此页面业务相关的脚本-->
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/webuploader/0.1.5/webuploader.min.js"></script> 
		<script type="text/javascript" src="<%=basePath%>js/ajaxfileupload.js"></script> 
		<script type="text/javascript">
		$(function(){
			var validate = $("#form-horizontal").validate({ 
			   submitHandler: function(form){ 
				   honor_save(); //执行提交方法
		        },
		     });    
		 });
		
		 function check(){ 
			var aa=document.getElementById("COVER_PATH").value.toLowerCase().split('.');//以“.”分隔上传文件字符串
		    if(aa[aa.length-1]=='gif'||aa[aa.length-1]=='jpg'||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg'){//判断图片格式
				var imagSize =  document.getElementById("COVER_PATH").files[0].size;
				if(imagSize<1024*1024){ 
				    return true;
				}else{
					alert("图片为："+imagSize/(1024*1024)+"M,大于1M,请重新上传！");
					document.getElementById("COVER_PATH").value=null;
					return false;
				} 
		    }else{
		        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
		        document.getElementById("COVER_PATH").value=null;
		        return false;
		    }  
		 }
		
		 function honor_save(){  
			var index = parent.layer.getFrameIndex(window.name);  
			var ID=$('#ID').val(); 
			var TITLE=$('#TITLE').val(); 
			var VERSION=$('#VERSION').val(); 
			var ABOUT=$('#ABOUT').val();
			var QK_PATH=$('#QK_PATH').val(); 
			var QK_COVER=$('#QK_COVER').val(); 
			var cover=document.getElementById("COVER_PATH").value; 
			if((cover==null||cover==""||cover==undefined)&&(QK_COVER==null||QK_COVER==""||QK_COVER==undefined)){
				alert("请选择要上传的封面！");
			}else{
				var param="?ID="+ID+"&TITLE="+TITLE+"&VERSION="+VERSION+"&ABOUT="+ABOUT+"&QK_PATH="+QK_PATH;
				var url="<%=basePath%>magazine/edit.do"+param; 
				$.ajaxFileUpload({  
					  url :url,
					  type:'post',
				      secureuri:false,  
				      fileElementId:'COVER_PATH',//file标签的id  
				      dataType: 'json',//返回数据的类型    
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
		}   
	</script>
</body>

</html>
