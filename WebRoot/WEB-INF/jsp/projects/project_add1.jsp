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
			<form class="form form-horizontal" id="form-project-add"> 
			<input type="hidden" name="STATUS" id="STATUS" value="5"/>
			<input type="hidden" name="ID" id="ID" value="${pd.ID }"/> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目名称：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.PROJECT_NAME}" placeholder="" id="PROJECT_NAME" name="PROJECT_NAME" data-rule-required="true">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>发布人：</label>
					<c:if test="${pd.USER_ID==null or pd.USER_ID ==''}"> 
						<div class="formControls col-xs-8 col-sm-9">
					        <span class="select-box" style="width:150px;"> 
								<select class="select" name="CREATE_BY" id="CREATE_BY"> 
									<c:forEach items="${userList}" var="user">
										<option value="${user.ID }" <c:if test="${user.ID == pd.CREATE_BY }">selected</c:if>>${user.NAME }</option>
									</c:forEach>
								</select>
							</span> 
						</div>
				    </c:if>
					<c:if test="${pd.USER_ID!=null and pd.USER_ID !=''}"> 
						<div class="formControls col-xs-8 col-sm-9">
					        <span class="select-box" style="width:150px;"> 
								<select class="select" name="USER_ID" id="USER_ID">  
									<option value="${pd.USER_ID}">${pd.REAL_NAME}</option>
								</select>
							</span> 
						</div>
					</c:if>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>所在城市：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.CITY}" placeholder="" id="CITY" name="CITY" >
					</div> 
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>详细地址：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.ADDR}" placeholder="" id="ADDR" name="ADDR" >
					</div> 
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目内容：</label>
					<%-- <div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.PROJECT_CONTENT}" placeholder="" id="PROJECT_CONTENT" name="PROJECT_CONTENT" data-rule-required="true">
					</div> --%>
					<div class="formControls col-xs-8 col-sm-9"> 
				       <textarea class="textarea"  placeholder=""  id="PROJECT_CONTENT" name="PROJECT_CONTENT" data-rule-required="true">${pd.PROJECT_CONTENT}</textarea> 
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目类型：</label>
					<div class="formControls col-xs-8 col-sm-9">
					    <span class="select-box" style="width:150px;"> 
							<select class="select" name="PROJECT_TYPE_ID" id="PROJECT_TYPE_ID">
								<c:forEach items="${typeList}" var="type">
									<option value="${type.BIANMA}" <c:if test="${type.BIANMA == pd.PROJECT_TYPE_ID }">selected</c:if>>${type.NAME }</option>
								</c:forEach>
							</select>
						</span> 
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>发布时间：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
		                <input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate" value="${pd.RELEASE_DATE}" id="RELEASE_DATE" name="RELEASE_DATE" data-rule-required="true">
		           </div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>截止时间：</label>
					<div class="formControls col-xs-8 col-sm-9">  
				         <input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate" value="${pd.DUE_DATE}"  id="DUE_DATE" name="DUE_DATE" data-rule-required="true">
		           </div>
		        </div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>企业资质：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					    <span class="select-box" style="width:150px;"> 
							<select class="select" name="PROJECT_LEVEL" id="PROJECT_LEVEL">
								<c:forEach items="${levelList}" var="level">
									<option value="${level.BIANMA}" <c:if test="${level.BIANMA == pd.PROJECT_LEVEL }">selected</c:if>>${level.NAME }</option>
								</c:forEach>
							</select>
						</span> 
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>招标要求：</label>
					<div class="formControls col-xs-8 col-sm-9">
					    <span class="select-box" style="width:150px;"> 
							<select class="select" name="BID_REQUIREMENTS" id="BID_REQUIREMENTS">
								<c:forEach items="${userLevelList}" var="userLevel"> 
									<option value="${userLevel.BIANMA}" <c:if test="${userLevel.BIANMA == pd.BID_REQUIREMENTS }">selected</c:if>>${userLevel.NAME }</option>
								</c:forEach>
							</select>
						</span> 
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目估价：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="number" class="input-text" value="${pd.START_PRICE}" placeholder="" id="START_PRICE" name="START_PRICE" data-rule-required="true" style="width:150px;">
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>是否热门：</label> 
					<div class="formControls col-xs-8 col-sm-9 skin-minimal">
						<div class="radio-box">
							<input name="IS_HOT" type="radio" id="flag-1" value="1"  <c:if test="${pd.IS_HOT == '1' }">checked="checked"</c:if>>
							<label for="flag-1">是</label>
						</div>
						<div class="radio-box">
							<input type="radio" id="flag-2" name="IS_HOT" value="0"  <c:if test="${pd.IS_HOT == '0' }">checked="checked"</c:if>>
							<label for="flag-2">否</label>
						</div> 
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">建筑层数：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="number" class="input-text" value="${pd.BUILD_LAYERS}" placeholder="" id="BUILD_LAYERS" name="BUILD_LAYERS" style="width:150px;">
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">建筑面积：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="number" class="input-text" value="${pd.BUILD_AREA}" placeholder="" id="BUILD_AREA" name="BUILD_AREA" style="width:150px;">
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">占地面积：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="number" class="input-text" value="${pd.FLOOR_AREA}" placeholder="" id="FLOOR_AREA" name="FLOOR_AREA" style="width:150px;">
					</div>
				</div>    
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">可用到的材料：</label>
					<%-- <div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.AVAILABLE_MATERIALS}" placeholder="" id="AVAILABLE_MATERIALS" name="AVAILABLE_MATERIALS">
					</div> --%>
					<div class="formControls col-xs-8 col-sm-9"> 
					    <textarea class="textarea"  placeholder=""  id="AVAILABLE_MATERIALS" name="AVAILABLE_MATERIALS">${pd.AVAILABLE_MATERIALS}</textarea> 
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">项目来源：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.RESOURCE}" placeholder="" id="RESOURCE" name="RESOURCE">
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">备注：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
				       <textarea class="textarea"  placeholder="说点什么..." id="DESCRIPTION" name="DESCRIPTION">${pd.DESCRIPTION}</textarea>  
			          <%--  <input type="text" class="input-text"  placeholder="说点什么..."  value="${pd.DESCRIPTION}" id="DESCRIPTION" name="DESCRIPTION"> --%>
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>接单人：</label>
					<div class="formControls col-xs-8 col-sm-9">
					    <span class="select-box" style="width:150px;"> 
							<select class="select" name="BID_WINNER_ID" id="BID_WINNER_ID"> 
								<c:forEach items="${bidderList}" var="bid">
									<option value="${bid.ID }" <c:if test="${bid.ID == pd.BID_WINNER_ID }">selected</c:if>>${bid.REAL_NAME }</option>
								</c:forEach>
							</select>
						</span> 
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>交易时间：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" value="${pd.BID_DATE}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate" id="BID_DATE" name="BID_DATE"  data-rule-required="true">
					</div> 
		        </div> 
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>交易金额：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.BID_PRICE}" id="BID_PRICE" name="BID_PRICE" data-rule-required="true">
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
			 var validate = $("#form-project-add").validate({ 
				   submitHandler: function(form){ 
					   project_save(); //执行提交方法
	                },
	            });    
		 });
		 
		function project_save(){ 
				var index = parent.layer.getFrameIndex(window.name); 
				$.ajax({
	                url : "<%=basePath%>project/save.do",
	                data :$('#form-project-add').serialize(),
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
		<!--/请在上方写此页面业务相关的脚本-->
	</body> 
</html>
