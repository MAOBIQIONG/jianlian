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
		<title>添加部落</title>
	</head>

	<body class="pos-r"> 
	<div>
<article class="page-container">
	<form class="form form-horizontal" id="form-horizontal"> 
	<input type="hidden" name="ID" id="ID" value="${clanData.ID }"/> 
	<input type="hidden" value="1"  id="TYPE" name="TYPE">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${clanData.NAME}" placeholder="" id="NAME" name="NAME" data-rule-required="true">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>所属省市：</label>
			<div class="formControls col-xs-8 col-sm-9">
		        <span class="select-box" style="width:150px;"> 
					<select class="select" name="AREA_CODE" id="AREA_CODE"> 
						<c:forEach items="${area}" var="area">
							<option value="${area.areacode }" <c:if test="${area.areacode == clanData.AREA_CODE }">selected</c:if>>${area.areaname }</option>
						</c:forEach>
					</select>
				</span> 
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">简介：</label>
			<%-- <div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${clanData.INTRODUCE }" placeholder="" id="INTRODUCE" name="INTRODUCE" data-rule-required="true">
			</div> --%>
			<div class="formControls col-xs-8 col-sm-9"> 
		       <textarea class="textarea"  placeholder=""  id="INTRODUCE" name="INTRODUCE" data-rule-required="true">${clanData.INTRODUCE}</textarea> 
			</div>
		</div>  
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">选择图片：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<span class="btn-upload form-group"> 
					<input type="file" value="${clanData.IMG_PATH }" id="IMG_PATH" name="IMG_PATH" onchange="check()"  style="width:150px;" />
				</span> 
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">区号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${clanData.ZONE_CODE}" placeholder="" id="ZONE_CODE" name="ZONE_CODE">
			</div>
		</div> 
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2"> 
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	</form>
</article> 
</div>
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
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.config.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ajaxfileupload.js"></script> 
        <script type="text/javascript">
       $(function(){
			 var validate = $("#form-horizontal").validate({ 
				   submitHandler: function(form){ 
					   clanAdd(); //执行提交方法
	                },
	            });    
		 });
       
       function check(){ 
  		 var aa=document.getElementById("IMG_PATH").value.toLowerCase().split('.');//以“.”分隔上传文件字符串 
  		 if(aa[aa.length-1]=='gif'||aa[aa.length-1]=='jpg'||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg'){//判断图片格式
  			var imagSize =  document.getElementById("IMG_PATH").files[0].size;
  			if(imagSize<1024*1024){ 
  			    return true;
  			}else{
  				alert("图片为："+imagSize/(1024*1024)+"M,大于1M,请重新上传！");
  				document.getElementById("IMG_PATH").value=null;
  				return false;
  			} 
  	    }else{
  	        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
  	        document.getElementById("IMG_PATH").value=null;
  	        return false;
  	    } 
  	 }

       function clanAdd(){ 
			var index = parent.layer.getFrameIndex(window.name);  
			var img=document.getElementById("IMG_PATH").value; 
			var ID=$('#ID').val();
			var NAME=$('#NAME').val();
			var TYPE=$('#TYPE').val();
			var INTRODUCE=$('#INTRODUCE').val();
			var AREA_CODE=$('#AREA_CODE').val(); 
			var ZONE_CODE=$('#ZONE_CODE').val();
			if(ID==undefined){
				ID="";
			} 
			var url="<%=basePath%>clan/addClan.do";
			var URL=url+"?ID="+ID+"&AREA_CODE="+AREA_CODE+"&NAME="+NAME+"&TYPE="+TYPE+"&INTRODUCE="+INTRODUCE+"&ZONE_CODE="+ZONE_CODE;
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
