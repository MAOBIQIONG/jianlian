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
		<title>荣誉，需求，资源</title>
	</head>

	<body class="pos-r"> 
		<article class="page-container">
			<form class="form form-horizontal" id="form-cert-edit" > 
			<input type="hidden" name="ID" id="ID" value="${data.ID }"/>   
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>会员名称：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" readonly="readonly" class="input-text" value="${data.REAL_NAME}" placeholder="" id="REAL_NAME" name="REAL_NAME" data-rule-required="true">
				</div>
			</div>
			<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">荣誉：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<table id="ry" style="margin-top: 4px;">
					<tr id="rut" <c:if test="${empty ru}">style="display: none;"</c:if>>
			 		   <td><b>荣誉名称</b></td>
			 		   <td><b>获得时间</b></td>
			 		</tr>
		 			<tr id="wru" <c:if test="${not empty ru}">style="display: none;"</c:if>>
			 		   <td colspan="3">暂无荣誉信息！</td>
			 		</tr>
			 		<c:if test="${not empty ru}">
			 			<c:forEach items="${ru }" var="r">
			 			<tr>
				 		   <td><input type="text" class="input-text" value="${r.HONOR }" placeholder="请输入荣誉名称" id="HONOR" name="HONOR" data-rule-required="true" ></td>
				 		   <td><input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="input-text Wdate" value="${r.GET_DATE }" placeholder="请输入荣誉获得时间" id="GET_DATE" name="GET_DATE" data-rule-required="true" ></td>
				 		   <td style="width: 30px;text-align: center;">
					 	  	 <button class="btn radius" type="button" onclick="delRy(this)">-</button>
					 	   </td>
				 		</tr>
			 			</c:forEach>
			 		</c:if>
			 		
			 	</table>
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"></label>
			<div class="formControls col-xs-8 col-sm-9">
				<button class="btn btn-success radius" style="width: 150px;" type="button" onclick="addRy()">+新增荣誉信息</button>
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">资源：</label>
			<div class="formControls col-xs-8 col-sm-9">
			 	<table id="zyy" style="margin-top: 4px;">
					<tr id="zyt" <c:if test="${empty zy}">style="display: none;"</c:if>>
			 		   <td><b>资源名称</b></td>
			 		</tr>
		 			<tr id="wzy" <c:if test="${not empty zy}">style="display: none;"</c:if>>
			 		   <td colspan="2">暂无资源信息！</td>
			 		</tr>
			 		<c:if test="${not empty zy}">
			 			<c:forEach items="${zy }" var="z">
			 			<tr>
				 		   <td><input type="text" class="input-text" value="${z.RESOURCE }" placeholder="请输入资源名称" id="RESOURCE" name="RESOURCE" data-rule-required="true" ></td>
				 		   <td style="width: 30px;text-align: center;">
					 	  	 <button class="btn radius" type="button" onclick="delZy(this)">-</button>
					 	   </td>
				 		</tr>
			 			</c:forEach>
			 		</c:if>
			 		
			 	</table>
				
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"></label>
			<div class="formControls col-xs-8 col-sm-9">
				<button class="btn btn-success radius" style="width: 150px;" type="button" onclick="addZy()">+新增资源信息</button>
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">需求：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<table id="xq" style="margin-top: 4px;">
					<tr id="xqt" <c:if test="${empty xq}">style="display: none;"</c:if>>
			 		   <td><b>需求名称</b></td>
			 		</tr>
		 			<tr id="wxq" <c:if test="${not empty xq}">style="display: none;"</c:if>>
			 		   <td colspan="2">暂无需求信息！</td>
			 		</tr>
			 		<c:if test="${not empty xq}">
			 			<c:forEach items="${xq }" var="x">
			 			<tr>
				 		   <td><input type="text" class="input-text" value="${x.NEED_NAME }" placeholder="请输入需求名称" id="NEED_NAME" name="NEED_NAME" data-rule-required="true" ></td>
				 		   <td style="width: 30px;text-align: center;">
					 	  	 <button class="btn radius" type="button" onclick="delXq(this)">-</button>
					 	   </td>
				 		</tr>
			 			</c:forEach>
			 		</c:if>
			 		
			 	</table>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"></label>
			<div class="formControls col-xs-8 col-sm-9">
				<button class="btn btn-success radius" style="width: 150px;" type="button" onclick="addXq()">+新增需求信息</button>
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
	function addRy(){
		$("#wru").hide();
		$("#rut").show();
		var tr='<tr>';
		   tr+='<td><input type="text" class="input-text" value="" placeholder="请输入荣誉名称" id="HONOR" name="HONOR" data-rule-required="true" ></td>';
		   tr+='<td><input type="text" onClick="WdatePicker({dateFmt:&#39;yyyy-MM-dd HH:mm:ss&#39;})" class="input-text Wdate" value="" placeholder="请输入荣誉获得时间" id="GET_DATE" name="GET_DATE" data-rule-required="true" ></td>';
		   tr+='<td style="width: 30px;text-align: center;">';
		   tr+='<button class="btn radius" type="button" onclick="delRy(this)">-</button>';
		   tr+='</td>';
		   
		   tr+='</tr>';
		$("#ry").append(tr);
	}
	function delRy(obj){

		$(obj).parent().parent().remove();
		if($("#ry").find("tr").length == 2){
			$("#wru").show();
			$("#rut").hide();
		}
	}
	
	function addZy(){
		$("#wzy").hide();
		$("#zyt").show();
		var tr='<tr>';
		   tr+='<td><input type="text" class="input-text" value="" placeholder="请输入资源名称" id="RESOURCE" name="RESOURCE" data-rule-required="true" ></td>';
		   tr+='<td style="width: 30px;text-align: center;">';
		   tr+='<button class="btn radius" type="button" onclick="delZy(this)">-</button>';
		   tr+='</td>';
		   
		   tr+='</tr>';
		$("#zyy").append(tr);
	}
	function delZy(obj){
		$(obj).parent().parent().remove();
		if($("#zyy").find("tr").length == 2){
			$("#wzy").show();
			$("#zyt").hide();
		}
	}
	
	function addXq(){
		$("#wxq").hide();
		$("#xqt").show();
		var tr='<tr>';
		   tr+='<td><input type="text" class="input-text" value="" placeholder="请输入需求名称" id="NEED_NAME" name="NEED_NAME" data-rule-required="true" ></td>';
		   tr+='<td style="width: 30px;text-align: center;">';
		   tr+='<button class="btn radius" type="button" onclick="delXq(this)">-</button>';
		   tr+='</td>';
		   
		   tr+='</tr>';
		$("#xq").append(tr);
	}
	function delXq(obj){
		$(obj).parent().parent().remove();
		if($("#xq").find("tr").length == 2){
			$("#wxq").show();
			$("#xqt").hide();
		}
	}
	
	$(function(){
		 var validate = $("#form-cert-edit").validate({ 
			   submitHandler: function(form){ 
				   companyAdd(); //执行提交方法
	           },
	       });    
	});

function companyAdd(){ 
				var index = parent.layer.getFrameIndex(window.name); 
				$.ajax({
	                url : "<%=basePath%>tbuser/adduserxx.do",
	                data :$('#form-cert-edit').serialize(),
	                type : 'post',
	                dataType : 'json',
	                async : false,
	                success : function(result) {
	                   if(result.status == 1){
							parent.$('.breadcrumb .r .Hui-iconfont').click();
		       				parent.layer.close(index);
		                }
	                },
	                error : function(msg) {
	                }
	            });
		    }
    </script>
   </body> 
</html>
