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
		<title>新增用户信息</title>
	</head>

	<body>
	    <article class="page-container">
			<form class="form form-horizontal" id="form-user-edit">
			    <input type="hidden" name="ID" id="user_id" value="${pd.ID }"/>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>请选择角色：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<span class="select-box" style="width:150px;"> 
							<select class="select" name="ROLE_ID" id="role_id" data-placeholder="请选择职位" style="vertical-align:top;">
								<option value=""></option>
								<c:forEach items="${roleList}" var="role">
									<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
								</c:forEach>
							</select>
						</span> 
					</div> 	
				</div>  
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>账号：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					    <input type="text" class="input-text" name="USERNAME" id="loginname" data-rule="required;remote[<%=basePath%>user/hasU.do];length[5~18]" value="${pd.USERNAME }" placeholder="这里输入用户名" data-rule-required="true"/>
					</div>
				</div>
				<%-- <div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>密码：</label>
					<div class="formControls col-xs-8 col-sm-9">
					    <input type="password" class="input-text" value="${pd.PASSWORD}" name="PASSWORD" id="password" placeholder="输入密码" title="密码" data-rule="required;length[6~18]"/> 
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>确认密码：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					   <input type="password" class="input-text" value="${pd.PASSWORD}" name="chkpwd" id="chkpwd" data-rule="match(PASSWORD)" placeholder="确认密码" title="确认密码" />
					</div>
				</div>   --%>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>姓名：</label>
					<div class="formControls col-xs-8 col-sm-9">
					   <input type="text" class="input-text" name="NAME" id="name"  value="${pd.NAME }"  maxlength="32" placeholder="姓名"  data-rule-required="true"/>
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>性别：</label> 
					<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="status">
						<div class="radio-box">
							<input name="SEX" type="radio" id="flag-1" value="1" <c:if test="${pd.SEX==1}">checked</c:if>>
							<label for="flag-1">男</label>
						</div>
						<div class="radio-box">
							<input type="radio" id="flag-2" name="SEX" value="0" <c:if test="${pd.SEX==0}">checked</c:if>>
							<label for="flag-2">女</label>
						</div> 
					</div>
				</div> 	  
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>手机号：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					  <input type="number" style="width:150px;" class="input-text" name="PHONE" id="PHONE"  value="${pd.PHONE }" data-rule="mobile" placeholder="手机号" title="手机号" data-rule-required="true"/>
					</div>
				</div>  
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">邮箱：</label>
					<div class="formControls col-xs-8 col-sm-9">
					   <input type="email" class="input-text" name="EMAIL" id="EMAIL"  value="${pd.EMAIL }" data-rule="email" placeholder="邮箱" title="邮箱"/>
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>职位：</label>
					<div class="formControls col-xs-8 col-sm-9">
					   <input type="text" class="input-text" name="POSITION" id="POSITION"  value="${pd.POSITION }"  placeholder="职位" data-rule-required="true"/>
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">年龄：</label>
					<div class="formControls col-xs-8 col-sm-9">
					   <input type="number" style="width:150px;" class="input-text" name="AGE" id="AGE"  value="${pd.AGE }" placeholder="年龄" />
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">微信：</label>
					<div class="formControls col-xs-8 col-sm-9">
					   <input type="text" class="input-text" name="WEIXIN" id="WEIXIN"  value="${pd.WEIXIN }" />
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">身份证号：</label>
					<div class="formControls col-xs-8 col-sm-9">
					   <input type="text" class="input-text" name="IDNO" id="IDNO"  value="${pd.IDNO }" data-rule="IDNO" placeholder="身份证号"/>
					</div>
				</div>  
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">住址：</label>
					<div class="formControls col-xs-8 col-sm-9">
					   <input type="text" class="input-text" name="ADDR" id="ADDR"  value="${pd.ADDR }"  placeholder="住址"/>
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">员工编号：</label>
					<div class="formControls col-xs-8 col-sm-9">
					   <input type="text" class="input-text" name="JOBNO" id="JOBNO"  value="${pd.JOBNO }"  placeholder="员工编号"/>
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>是否为项目经理：</label> 
					<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="status">
						<div class="radio-box">
							<input name="IS_MANAGER" type="radio" id="flag-1" value="1" <c:if test="${pd.IS_MANAGER==1}">checked</c:if>>
							<label for="flag-1">是</label>
						</div>
						<div class="radio-box">
							<input type="radio" id="flag-2" name="IS_MANAGER" value="0" <c:if test="${pd.IS_MANAGER==0}">checked</c:if>>
							<label for="flag-2">否</label>
						</div> 
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
			//手机号码验证
			 jQuery.validator.addMethod("isMobile", function(value, element) {
			 var length = value.length;
			 var mobile = /^(13[0-9]|14[5|7]|15[0-9]|176|177|18[0-9])\d{8}$/;
			 return this.optional(element) || (length == 11 && mobile.test(value)); 
			 }, "请正确填写您的手机号码");
			
			 var validate = $("#form-user-edit").validate({ 
				   submitHandler: function(form){ 
					   user_save(); //执行提交方法
	                }, 
	                rules:{
	                	PHONE:{
	                        required:true,
	                        isMobile:true
	                    }
	                },
	                messages:{
	                	PHONE:{
	                        required:"手机号不能为空!"
	                    }
	                }
	                          
	            });    
		 });
		 
		 function user_save(){  
			 //当用户名重复了，无法提交
				var index = parent.layer.getFrameIndex(window.name); 
				if( $("#AGE").val() == "" || $("#AGE").val() == null){
				    $("#AGE").val("0");
				}
				$.ajax({
	                url : "<%=basePath%>user/${msg }.do",
	                data :$('#form-user-edit').serialize(),
	                type : 'post',
	                dataType : 'json',
	                async : false,
	                success : function(result) { 
	                   if(result.statusCode == 200){ 
							parent.$('.breadcrumb .r .Hui-iconfont').click();
		       				parent.layer.close(index);
		                 }else if(result.statusCode == 300){
		                	 alert("用户账号已存在！");
		                 } 
	                },
	                error : function(msg) {
	                }
	            });
		    }
		</script> 
	</body> 
</html>  