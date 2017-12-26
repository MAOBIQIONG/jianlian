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
		<title>审核入会信息</title>
	</head>

<body class="pos-r">  
<article class="page-container">
	<form class="form form-horizontal" id="form-horizontal"> 
	<input type="hidden" name="ID" id="ID" value="${pad.ID }"/>   
	<input type="hidden" name="USER_ID" id="USER_ID" value="${pad.USER_ID }"/>   
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>真实姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" readonly="readonly" class="input-text" value="${pad.REAL_NAME}" placeholder="" id="REAL_NAME" name="REAL_NAME" data-rule-required="true">
			</div>
		</div>  
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>手机号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" readonly="readonly" class="input-text" value="${pad.PHONE }" placeholder="" id="PHONE" name="PHONE" data-rule-required="true">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>公司名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" readonly="readonly" readonly="readonly" class="input-text" value="${pad.COMPANY_NAME }" placeholder="" id="COMPANY_NAME" name="COMPANY_NAME" data-rule-required="true">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>职务：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" readonly="readonly" class="input-text" value="${pad.POSITION }" placeholder="" id="POSITION" name="POSITION" data-rule-required="true">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>当前权益：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" readonly="readonly" class="input-text" value="${pad.LEVELNAME }" placeholder="" id="LEVELNAME" name="LEVELNAME" data-rule-required="true">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>需升级权益：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" readonly="readonly" class="input-text" value="${pad.SJLEVELNAME }" placeholder="" id="SJLEVELNAME" name="SJLEVELNAME" data-rule-required="true">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>金额：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" readonly="readonly" class="input-text" value="${pad.PRICE_TOPAY }" placeholder="" id="PRICE_TOPAY" name="PRICE_TOPAY" data-rule-required="true">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>审核结果：</label> 
			<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="STATUS">
				<div class="radio-box">
					<input type="radio" id="flag-1" name="STATUS"  value="03" <c:if test="${pad.STATUS == '03' }">checked="checked"</c:if>>
					<label for="flag-1">审核通过</label>
				</div>
				<div class="radio-box">
					<input type="radio" id="flag-2" name="STATUS" value="02" <c:if test="${pad.STATUS == '02' }">checked="checked"</c:if>>
					<label for="flag-2">审核不通过</label>
				</div> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">不通过原因：</label>  
			<div class="formControls col-xs-8 col-sm-9">
		        <span class="select-box" style="width:150px;"> 
					<select class="select" name="REASON_BIANMA" id="REASON_BIANMA" onchange="selected(this)"> 
							<option value="">--请选择--</option>
						<c:forEach items="${reason}" var="rea">
							<option value="${rea.BIANMA }" <c:if test="${rea.BIANMA== pad.REASON_BIANMA }">selected</c:if>>${rea.NAME }</option>
						</c:forEach>
					</select>
				</span> 
			</div> 
		</div>
		<div class="row cl res" style="dispaly:none;">
			<label class="form-label col-xs-4 col-sm-2" style="color: red;"><span class="c-red">*</span>描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea rows="5" cols="136" id="NOPASS_REASON" name="NOPASS_REASON" placeholder="请输入描述！"></textarea>
			</div>
		</div>  
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2"> 
				<input class="btn btn-success radius" id="tgl" onclick="toSelect()" type="button" value="&nbsp;提交&nbsp;"> 
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
<script type="text/javascript" src="<%=basePath%>H-ui/lib/webuploader/0.1.5/webuploader.min.js"></script>
<script type="text/javascript">

function selected(){
	var bm=$("#REASON_BIANMA").find("option:selected").val();  
	if(bm=='133'){
		$(".res").show();
	}else{
		$(".res").hide();
	}
}

function toSelect(){ 
	var STATUS = $('#STATUS input[name="STATUS"]:checked ').val(); 
	if(STATUS=='02'){
		jj();
	}else if(STATUS=='03'){
		tg();
	}else{
		alert("请选择审核结果！");
	}
}

//审核通过
function tg(){  
	$("#NOPASS_REASON").val(""); 
	var ID=$("#ID").val(); 
	var USER_ID=$("#USER_ID").val(); 
	var PHONE=$("#PHONE").val(); 
	var PRICE_TOPAY=$("#PRICE_TOPAY").val(); 
	
	$("#tgl").attr("disabled","disabled");
	$("#jjl").attr("disabled","disabled");
	var index = parent.layer.getFrameIndex(window.name); 
	  $.ajax({
        url : "<%=basePath%>umbs/shtg.do",
        data :{"ID":ID,"USER_ID":USER_ID,"PHONE":PHONE,"PRICE_TOPAY":PRICE_TOPAY},
        type : 'post',
        dataType : 'json',
        async : false,
        success : function(result) { 
        	if(result.status == '1'){
        		parent.$('.breadcrumb .r .Hui-iconfont').click();
   				parent.layer.close(index);
        	}else{
        		alert("操作失败！");	
        	}
				
        },
        error : function(msg) {
        }
    }); 
}	
		       
      	//审核拒绝
	function jj(){  
		var NOPASS_REASON=$("#NOPASS_REASON").val(); 
		var bm=$("#REASON_BIANMA").find("option:selected").val();  
		var reason=$("#REASON_BIANMA").find("option:selected").text(); 
		if(bm == null || bm == '' || bm == undefined ){
			alert("请选择拒绝原因！");	
			return;
		}
		var ID=$("#ID").val(); 
		var USER_ID=$("#USER_ID").val(); 
		var PHONE=$("#PHONE").val(); 
		if(bm=='133'&&(NOPASS_REASON == null || NOPASS_REASON == '' || NOPASS_REASON == undefined) ){
		   alert("请详细描述一下审核不通过的原因！");	
		   return;
		}else{
			reason=NOPASS_REASON;
		}  
		$("#tgl").attr("disabled","disabled");
		$("#jjl").attr("disabled","disabled");
		var index = parent.layer.getFrameIndex(window.name); 
		  $.ajax({
               url : "<%=basePath%>umbs/shjj.do",
               data :{"ID":ID,"REASON_BIANMA":bm,"reason":reason,"NOPASS_REASON":NOPASS_REASON,"USER_ID":USER_ID,"PHONE":PHONE},
               type : 'post',
               dataType : 'json',
               async : false,
               success : function(result) {
               	if(result.status == '1'){
               		parent.$('.breadcrumb .r .Hui-iconfont').click();
       				parent.layer.close(index);
               	}else{
               		alert("操作失败！");	
               	}
					
               },
               error : function(msg) {
               }
           }); 
    } 	
</script>
</body>

</html>
