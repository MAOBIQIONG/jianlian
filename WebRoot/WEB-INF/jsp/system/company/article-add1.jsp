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
<input type="hidden" name="uname" id="uname" value="${data.USER_NAME}"/>
	<form class="form form-horizontal" id="form-horizontal"> 
	<input type="hidden" name="ID" id="ID" value="${data.ID }"/>
	<%-- <form class="form form-horizontal" id="form-project-add" action="<%=basePath%>project/add.do">   --%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>会员名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.REAL_NAME}" placeholder="" id="REAL_NAME" name="REAL_NAME" data-rule-required="true" >
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>会员昵称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.NICK_NAME}" placeholder="" id="NICK_NAME" name="NICK_NAME">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>会员账号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" onchange="checkuName(this)" value="${data.USER_NAME}" placeholder="" id="USER_NAME" name="USER_NAME" >
			</div>
		</div>
		<%-- <div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>会员头像：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.IMG}" placeholder="" id="IMG" name="IMG">
			</div>
		</div> --%>
		<%-- <div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>会员卡号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.CARD_NO}" placeholder="最多15位数字" id="CARD_NO" name="CARD_NO">
			</div>
		</div> --%>
		
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>联系电话：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.PHONE}" placeholder="" id="PHONE" name="PHONE" >
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>邮箱：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.EMAIL}" placeholder="" id="EMAIL" name="EMAIL" >
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>性别：</label> 
			<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="SEX">
				<div class="radio-box">
					<input name="SEX" type="radio" id="flag-1" value="1" <c:if test="${data.SEX == '1' }">checked="checked"</c:if>>
					<label for="flag-1">男</label>
				</div>
				<div class="radio-box">
					<input type="radio" id="flag-2" name="SEX" value="2" <c:if test="${data.SEX == '2' }">checked="checked"</c:if>>
					<label for="flag-2">女</label>
				</div> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>职位：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.POSITION}" placeholder="" id="POSITION" name="POSITION">
			</div>
		</div>
		<%-- <div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>所属公司：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.COMPANY_ID}" placeholder="" id="COMPANY_ID" name="COMPANY_ID">
			</div>
		</div> --%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>是否会员：</label> 
			<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="isvp">
				<div class="radio-box">
					<input name="IS_VIP" type="radio" id="IS_VIP1" value="1" <c:if test="${data.IS_VIP == '1' }">checked="checked"</c:if>>
					<label for="IS_VIP1">是</label>
				</div>
				<div class="radio-box">
					<input type="radio" id="IS_VIP2" name="IS_VIP" value="0" <c:if test="${data.IS_VIP == '0' }">checked="checked"</c:if>>
					<label for="IS_VIP2">不是</label>
				</div> 
				<div class="radio-box">
					<input type="radio" id="IS_VIP3" name="IS_VIP" value="2" <c:if test="${data.IS_VIP == '2' }">checked="checked"</c:if>>
					<label for="IS_VIP3">升级中</label>
				</div> 
				<div class="radio-box">
					<input type="radio" id="IS_VIP4" name="IS_VIP" value="3" <c:if test="${data.IS_VIP == '3' }">checked="checked"</c:if>>
					<label for="IS_VIP4">内部用户</label>
				</div> 
			</div>
		</div>     
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>会员等级：</label>
			<div class="formControls col-xs-8 col-sm-9">
			    <span class="select-box" style="width:150px;"> 
					<select class="select" name="VIP_LEVEL" id="VIP_LEVEL">
						<option value="">--请选择会员等级--</option>
						<c:forEach items="${levelList}" var="level"> 
							<option value="${level.BIANMA}" <c:if test="${level.BIANMA == data.VIP_LEVEL }">selected</c:if>>${level.NAME }</option>
						</c:forEach>
					</select>
				</span> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>服务类型：</label> 
			<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="isvp">
				<div class="radio-box">
					<input name="SERVICE_TYPE" type="radio" id="SERVICE_TYPE1" value="00" <c:if test="${data.SERVICE_TYPE == '00' }">checked="checked"</c:if>>
					<label for="SERVICE_TYPE1">无</label>
				</div>
				<div class="radio-box">
					<input type="radio" id="SERVICE_TYPE2" name="SERVICE_TYPE" value="10" <c:if test="${data.SERVICE_TYPE == '10' }">checked="checked"</c:if>>
					<label for="SERVICE_TYPE2">项目经理</label>
				</div> 
				<div class="radio-box">
					<input type="radio" id="SERVICE_TYPE3" name="SERVICE_TYPE" value="20" <c:if test="${data.SERVICE_TYPE == '20' }">checked="checked"</c:if>>
					<label for="SERVICE_TYPE3">会员经理</label>
				</div> 
				<div class="radio-box">
					<input type="radio" id="SERVICE_TYPE4" name="SERVICE_TYPE" value="30" <c:if test="${data.SERVICE_TYPE == '30' }">checked="checked"</c:if>>
					<label for="SERVICE_TYPE4">城市合伙人经理</label>
				</div> 
			</div>
		</div>   
		<%-- <div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>入会日期：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate" value="${data.UPGRADE_DATE}" id="UPGRADE_DATE" name="UPGRADE_DATE">
		
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>会员到期时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate" value="${data.DUE_DATE}" id="DUE_DATE" name="DUE_DATE">
		
			</div>
		</div> --%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>用户状态：</label>
			<div class="formControls col-xs-8 col-sm-9">
			     
				<div class="radio-box">
					<input name="STATUS" type="radio" id=""STATUS"1" value="01" <c:if test="${data.STATUS == '01' }">checked="checked"</c:if>>
					<label for="STATUS1">启用</label>
				</div>
				<div class="radio-box">
					<input type="radio" id=""STATUS"2" name=""STATUS"" value="02" <c:if test="${data.IS_VIP == '02' }">checked="checked"</c:if>>
					<label for="STATUS2">禁用</label>
				</div> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>总积分：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.TOTAL_POINTS}" placeholder="" id="TOTAL_POINTS" name="TOTAL_POINTS">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>会员简介：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea rows="5" maxlength="300" cols="135" id="SUMMARY" placeholder="最多可输入300字" name="SUMMARY">${data.SUMMARY }</textarea>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>他人评价：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<textarea rows="5" cols="135" id="ASSESS" name="ASSESS">${data.ASSESS }</textarea>
			</div>
		</div>
		<%-- <div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>助理：</label>
			<div class="formControls col-xs-8 col-sm-9">
			    <span class="select-box" style="width:150px;"> 
					<select class="select" name="MANAGER_ID" id="MANAGER_ID">
						<option value="01" <c:if test="${'01' == data.MANAGER_ID }">selected</c:if>>张三</option>
						<option value="02" <c:if test="${'02' == data.MANAGER_ID }">selected</c:if>>李四</option>
					</select>
				</span> 
			</div>
		</div> --%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>是否显示标识：</label> 
			<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="isflag">
				<div class="radio-box">
					<input name="FLAG" type="radio" id="flag-1" value="1" <c:if test="${data.FLAG == '1' }">checked="checked"</c:if>>
					<label for="flag-1">是</label>
				</div>
				<div class="radio-box">
					<input type="radio" id="flag-2" name="FLAG" value="0" <c:if test="${data.FLAG == '0' }">checked="checked"</c:if>>
					<label for="flag-2">否</label>
				</div> 
			</div>
		</div>     
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">身份标识：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${data.SHOW_NAME }" placeholder="" id="SHOW_NAME" name="SHOW_NAME">
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
				$.ajax({
	                url : "<%=basePath%>tbuser/adduser.do",
	                data :$('#form-horizontal').serialize(),
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
  		 if(USER_NAME != $("#uname").val()){
 			  alert("会员账号已存在");
         	  $(obj).val('');
 		  }
  	  }
    },
    error : function(msg) {
    }
});
}
</script>
</body>

</html>
