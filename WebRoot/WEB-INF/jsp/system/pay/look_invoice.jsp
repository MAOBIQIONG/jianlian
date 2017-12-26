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
		<title>发票信息查看</title>
	</head>

	<body>
		<article class="page-container">
			<form class="form form-horizontal" id="form-horizontal">
			    <input type="hidden" name="ID" id="ID" value="${data.ORDER_ID}"/>   
			    <div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>发票类型：</label> 
					<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="belong">
						<div class="radio-box">
							<input name="TYPE" id="type-1" type="radio"  class="TYPE"  value="00"  <c:if test="${data.TYPE == '00' }">checked="checked"</c:if>>
							<label for="flag-1">纸质发票</label>
						</div>
						<div class="radio-box">
							<input type="radio" id="type-2" class="TYPE" name="TYPE" value="01"  <c:if test="${data.TYPE == '01' }">checked="checked"</c:if>>
							<label for="flag-2">电子发票</label>
						</div>  
					</div>
				</div> 
				<div class="row cl ">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><span id="data-name">金额：</span></label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.PRICE}" placeholder="" id="PRICE" name="PRICE" data-rule-required="true">
					</div>
				</div> 
				<div class="row cl ">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><span id="data-name">用户：</span></label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.EVENT}" placeholder="" id="EVENT" name="EVENT" data-rule-required="true">
					</div>
				</div> 
				<div class="row cl ">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><span id="data-name">税号：</span></label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.TAX_NO}" placeholder="" id="TAX_NO" name="TAX_NO" data-rule-required="true">
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><span id="data-name">公司名称：</span></label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.COMPANY_NAME}" placeholder="" id="COMPANY_NAME" name="COMPANY_NAME" data-rule-required="true">
					</div>
				</div>
				<div class="row cl elec">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span><span id="data-name">邮箱：</span></label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.EMAIL}" placeholder="" id="EMAIL" name="EMAIL" data-rule-required="true">
					</div>
				</div>
				
				<div class="row cl paper">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>姓名：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.NAME}" placeholder="" id="NAME" name="NAME" data-rule-required="true">
					</div>
				</div>
				<div class="row cl paper">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>联系方式：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.PHONE}" placeholder="" id="PHONE" name="PHONE" data-rule-required="true">
					</div>
				</div> 
				<div class="row cl paper" style="display:none;">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>是否快递：</label> 
					<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="express">
						<div class="radio-box">
							<input name="IS_EXPRESS" id="is-1" type="radio"  class="IS_EXPRESS"  value="00"  <c:if test="${data.IS_EXPRESS == '00' }">checked="checked"</c:if>>
							<label for="flag-1">快递</label>
						</div>
						<div class="radio-box">
							<input type="radio" id="is-2" class="IS_EXPRESS" name="IS_EXPRESS" value="01"  <c:if test="${data.IS_EXPRESS == '01' }">checked="checked"</c:if>>
							<label for="flag-2">自取 </label>
						</div>  
					</div>
				</div>  
				<div class="row cl isPress paper">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>地址：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${data.ADDR}" placeholder="" id="ADDR" name="ADDR" data-rule-required="true">
					</div>
				</div>   
				<div class="row cl">
					<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
						<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;发票已处理&nbsp;&nbsp;"> 
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
			var value = $('#belong input[name="TYPE"]:checked ').val(); 
	      	if(value=='00'){ 
	     		$(".paper").show();  
	     		$(".elec").hide(); 
	      	}
	      	if(value=='01'){ 
				$(".elec").show(); 
				$(".paper").hide();  
	      	}
	      	var exp = $('#express input[name="IS_EXPRESS"]:checked ').val();  
	      	if(exp=='00'){ 
	      		$(".isPress").show(); 
	      	}
	      	if(exp=='01'){ 
	      		$(".isPress").hide(); 
	      	}
			$("#type-1").click(function(){  
				 $(".paper").show();  
		     	 $(".elec").hide(); 
     		}); 
     	   	$("#type-2").click(function(){  
     		  $(".elec").show(); 
			  $(".paper").hide();  
 	   	   });    
     	    $("#is-1").click(function(){  
     	    	$(".isPress").show(); 
   			}); 
   	   		$("#is-2").click(function(){  
   	   			$(".isPress").hide();  
	   	   });    
			 
			var validate = $("#form-horizontal").validate({ 
			   submitHandler: function(form){ 
				   save(); //执行提交方法
		        },
		     });    
		 }); 
		
		 function save(){ 
			 var index = parent.layer.getFrameIndex(window.name);  
			 var ID=$("#ID").val();
			 $.ajax({  
		         type: "POST",
		         url:'<%=basePath%>pay/saveOrders.do',
		         data: {
						ID: ID,
						STATUS:"08"
				 },
		         async: false,
		         success : function(result) {  
        	    	 parent.$('.breadcrumb .r .Hui-iconfont').click();
					 parent.layer.close(index); 
	           	 },
	             error : function(msg) {
	             }
		    });
		}  
		</script>
		<!--/请在上方写此页面业务相关的脚本-->
	</body> 
</html>