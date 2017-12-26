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
					<label class="form-label col-xs-4 col-sm-2">项目名称：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.PROJECT_NAME}" placeholder="" id="PROJECT_NAME" name="PROJECT_NAME" data-rule-required="true">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">项目发布时间：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
		                <input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate" value="${pd.RELEASE_TIME}" id="RELEASE_TIME" name="RELEASE_TIME" data-rule-required="true">
		           </div>
				</div>
				<div class="row cl" >
					<label class="form-label col-xs-4 col-sm-2">所属行业：</label>
					<div class="formControls col-xs-8 col-sm-9">
				        <span class="select-box" style="width:150px;"> 
							<select class="select" name="INDUSTRY" id="INDUSTRY"> 
							<option value="0" selected>请选择</option>
								<c:forEach items="${cateList}" var="gcdl">
									<option value="${gcdl.BIANMA }" <c:if test="${gcdl.BIANMA == pd.INDUSTRY }">selected</c:if>>${gcdl.NAME}</option>
								</c:forEach>
							</select>
						</span> 
					</div> 
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">项目总投资：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.TOTAL_INVESTMENT}" placeholder="" id="TOTAL_INVESTMENT" name="TOTAL_INVESTMENT" data-rule-required="true">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">联系电话：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.PHONE}" placeholder="" id="PHONE" name="PHONE" >
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">项目概况：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
				       <textarea class="textarea" id="CONTENT" name="CONTENT">${pd.CONTENT}</textarea>  
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">合作范围：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.RANGE}" placeholder="" id="RANGE" name="RANGE" >
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">合作期限：</label>
					<div class="formControls col-xs-8 col-sm-5">
						<input type="text" class="input-text" value="${pd.TERM}" placeholder="" id="TERM" name="TERM" >
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">项目运作方式：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.MODE}" placeholder="" id="MODE" name="MODE" >
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">采购社会资本方式：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.PURCHASE}" placeholder="" id="PURCHASE" name="PURCHASE" >
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">所在城市：</label>
					 <div class="formControls col-xs-1" style="width:155px;"> 
						 <span class="select-box" style="width:150px;"> 
							<select class="select"  name="AREA_ID" id="AREA_ID"  onchange="toGetCity(this)">
								<option value="0" selected>请选择</option>
								<c:forEach items="${areaList}" var="area">
									<option value="${area.areacode}" <c:if test="${area.areacode == pd.PARENTID }">selected</c:if>>${area.areaname}</option>
								</c:forEach>
							</select> 
						 </span> 
					 </div>   
					 <div class="formControls col-xs-1" style="width:155px;">
					   <span class="select-box" style="width:150px;">  
						    <select class="select" name="CITY" id="CITY">
								<option class="city-selected" value="0" selected>请选择</option>
								<c:forEach items="${cityList}" var="city">
									<option class="city-select" cid="${city.parentid}" value="${city.areacode}" <c:if test="${city.areacode == pd.CITY }">selected</c:if>>${city.areaname }</option>
								</c:forEach>
							</select>  
						</span> 
					</div>  
				</div>  
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">详细地址：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.ADDR}" placeholder="" id="ADDR" name="ADDR">
					</div> 
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">起始时间：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
		                <input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate" value="${pd.START_DATE}" id="START_DATE" name="START_DATE" data-rule-required="true">
		           </div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">截止时间：</label>
					<div class="formControls col-xs-8 col-sm-9">  
				         <input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate" value="${pd.DUE_DATE}"  id="DUE_DATE" name="DUE_DATE" data-rule-required="true">
		           </div>
		        </div> 
		         <div class="row cl" >
					<label class="form-label col-xs-4 col-sm-2">PPP阶段：</label>
					<div class="formControls col-xs-8 col-sm-9">
				        <span class="select-box" style="width:150px;"> 
							<select class="select" name="STAGE" id="STAGE"> 
							<option value="0" selected>请选择</option>
								<c:forEach items="${jdList}" var="gcdls">
									<option value="${gcdls.BIANMA }" <c:if test="${gcdls.BIANMA == pd.STAGE }">selected</c:if>>${gcdls.NAME}</option>
								</c:forEach>
							</select>
						</span> 
					</div> 
				</div>
				<div class="row cl" >
					<label class="form-label col-xs-4 col-sm-2">项目示范级别/批次：</label>
					<div class="formControls col-xs-8 col-sm-9">
				        <span class="select-box" style="width:150px;"> 
							<select class="select" name="LEVEL" id="LEVEL"> 
							<option value="0" selected>请选择</option>
								<c:forEach items="${gcflList}" var="gcdlevel">
									<option value="${gcdlevel.BIANMA }" <c:if test="${gcdlevel.BIANMA == pd.LEVEL}">selected</c:if>>${gcdlevel.NAME}</option>
								</c:forEach>
							</select>
						</span> 
					</div> 
				</div>   
				<div class="row cl" >
					<label style="color:red;" class="form-label col-xs-4 col-sm-2">项目中标信息</label> 
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>接单人：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${user.REAL_NAME}" placeholder="" readonly>
						<input type="hidden" value="${user.ID}" placeholder="" id="BID_WINNER_ID" name="BID_WINNER_ID">
					</div> 
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>交易时间：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" value="${pd.BID_DATE}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate" id="BID_DATE" name="BID_DATE"  data-rule-required="true">
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
	                url : "<%=basePath%>PPPproject/save.do",
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
