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
		<title>会员管理</title>
	</head>

	<body class="pos-r"> 
	<div>
<article class="page-container">
	<form class="form form-horizontal" id="form-horizontal">  
	<input type="hidden" value="888888" id="PASSWORD" name="PASSWORD"> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>会员名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.REAL_NAME}" placeholder="" id="REAL_NAME" name="REAL_NAME" data-rule-required="true" >
			</div>
		</div>   
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>会员账号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" onchange="checkuName(this)" value="${data.USER_NAME }" placeholder="" id="USER_NAME" name="USER_NAME" data-rule-required="true">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">联系电话：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.PHONE }" placeholder="" id="PHONE" name="PHONE">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">所属公司：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.COMPANY_NAME }" placeholder="" id="COMPANY_NAME" name="COMPANY_NAME">
			</div>
		</div>   
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">职位：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.POSITION }" placeholder="" id="POSITION" name="POSITION">
			</div>
		</div>   
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>会员等级：</label>
			<div class="formControls col-xs-8 col-sm-9">
			    <span class="select-box" style="width:150px;"> 
					<select class="select" name="VIP_LEVEL" id="VIP_LEVEL">
						<c:forEach items="${levelList}" var="level"> 
							<option value="${level.BIANMA}" <c:if test="${level.BIANMA == data.VIP_LEVEL }">selected</c:if>>${level.NAME }</option>
						</c:forEach>
					</select>
				</span> 
			</div>
		</div>   
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2"> 
				<button class="btn btn-primary radius" type="submit"><i class="Hui-iconfont">&#xe632;</i>提交</button>
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
<script type="text/javascript">

$(function(){
 var validate = $("#form-horizontal").validate({ 
	   submitHandler: function(form){ 
		   companyAdd(); //执行提交方法
          },
      });    
});

function companyAdd(){ 
	var index = parent.layer.getFrameIndex(window.name); 
	var ds=$("#VIP_LEVEL").val(); 
	$.ajax({
            url : "<%=basePath%>tbuser/saveUserInfo.do",
            data :$('#form-horizontal').serialize(),
            type : 'post',
            dataType : 'json',
            async : false,
            success : function(result) {
               if(result.statusCode ==200){
					parent.$('.breadcrumb .r .Hui-iconfont').click();
    				parent.layer.close(index);
             }
            },
            error : function(msg) {
            }
        });  
    }
    
function checkuName(obj){
	  var USER_NAME=$(obj).val();
	  $.ajax({
        url : "<%=basePath%>tbuser/findU.do",
        data :{"USER_NAME":USER_NAME},
        type : 'post',
        dataType : 'json',
        async : false,
        success : function(result) {
      	  if(result.user != null && result.user != ''){
      		  alert("会员账号已存在");
          	  $(obj).val('');
      		  
      	  }
        },
        error : function(msg) {
        }
    });
}
</script>
</body>

</html>
