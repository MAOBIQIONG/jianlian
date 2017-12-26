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
<!-- <style>
.col-xs-2,.col-xs-1{
   padding:0px;
}
</style> -->
	</head>

	<body>
		<article class="page-container">
			<form class="form form-horizontal" id="form-project-add">
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
									<option value="${gcdl.BIANMA }" <c:if test="${gcdl.BIANMA == pd.INDUSTRY}">selected</c:if>>${gcdl.NAME}</option>
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
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>项目星级：</label>
					<div class="formControls col-xs-8 col-sm-9">
					    <span class="select-box" style="width:150px;"> 
							<select class="select" name="STAR_LEVEL" id="STAR_LEVEL">
								<option value="0">--请选择--</option> 
								<option value="01" <c:if test="${pd.STAR_LEVEL=='01'}">selected</c:if>>一级</option>
								<option value="02" <c:if test="${pd.STAR_LEVEL=='02'}">selected</c:if>>二级</option>
								<option value="03" <c:if test="${pd.STAR_LEVEL=='03'}">selected</c:if>>三级</option>
								<option value="04" <c:if test="${pd.STAR_LEVEL=='04'}">selected</c:if>>四级</option>
								<option value="05" <c:if test="${pd.STAR_LEVEL=='05'}">selected</c:if>>五级</option>
							</select>
						</span> 
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>企业资质：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					    <span class="select-box" style="width:150px;"> 
							<select class="select" name="COM_QUALIFICATION" id="COM_QUALIFICATION">
								 <option value="0">--请选择--</option>
								<c:forEach items="${levelList}" var="level">
									<option value="${level.BIANMA}" <c:if test="${level.BIANMA == pd.COM_QUALIFICATION }">selected</c:if>>${level.NAME }</option>
								</c:forEach>
							</select>
						</span> 
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>审核结果：</label> 
					<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="STATUS">
						<div class="radio-box">
							<input type="radio" id="flag-1" name="STATUS"  value="3" <c:if test="${pd.STATUS == '3' }">checked="checked"</c:if>>
							<label for="flag-1">审核通过</label>
						</div>
						<div class="radio-box">
							<input type="radio" id="flag-2" name="STATUS" value="2" <c:if test="${pd.STATUS == '2' }">checked="checked"</c:if>>
							<label for="flag-2">审核不通过</label>
						</div> 
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">不通过原因：</label> 
					<div class="formControls col-xs-8 col-sm-9">
					    <span class="select-box" style="width:150px;"> 
							<select class="select" name="NO_PASS_REASON" id="NO_PASS_REASON">
							    <option value="0">--请选择--</option>
								<c:forEach items="${noPassList}" var="noPass"> 
									<option value="${noPass.BIANMA}" <c:if test="${noPass.BIANMA == pd.NO_PASS_REASON }">selected</c:if>>${noPass.NAME }</option>
								</c:forEach>
							</select>
						</span>  
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
		<!--/_footer /作为公共模版分离出去-->

		<!--请在下方写此页面业务相关的脚本-->
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/webuploader/0.1.5/webuploader.min.js"></script> 
		<script type="text/javascript">
		 $(function(){ 
			 $(".lx_select").hide();
			 $(".city-select").hide();
			 var areaId=$("#AREA_ID").find("option:selected").val(); 
			 var typeId=$("#TYPE_ID").find("option:selected").val();
			 if(areaId=="110000"){
				 $("#CITY").find("option:selected").text('北京市');
				 $("#CITY").find("option:selected").val('北京市');
			 }else if(areaId=='120000'){
				$("#CITY").find("option:selected").text('天津市');
				$("#CITY").find("option:selected").val('天津市');
			}else if(areaId=='310000'){
				$("#CITY").find("option:selected").text('上海市');
				$("#CITY").find("option:selected").val('上海市');
			}else if(areaId=='500000'){
				$("#CITY").find("option:selected").text('重庆市');
				$("#CITY").find("option:selected").val('重庆市');
			}else{  
				var options=$("#CITY").find("option[cid="+areaId+"]"); 
				options.show(); 
			} 
			var types=$("#PROJECT_TYPE_ID").find("option[lxpid="+typeId+"]"); 
			types.show(); 
			var validate = $("#form-project-add").validate({ 
			   submitHandler: function(form){ 
				   project_save(); //执行提交方法
                },
            });    
		 });
		
		 function project_save(){ 
			var index = parent.layer.getFrameIndex(window.name);
			var level=$("#PROJECT_LEVEL").find("option:selected").val(); 
			var require=$("#BID_REQUIREMENTS").find("option:selected").val(); 
			var hot = $("input[name='IS_HOT']:checked").val();
			var status = $("input[name='STATUS']:checked").val();
			if(status==null||status==""||status==undefined){
				alert("请选择审核结果！");
				return;
			}else{
				if(status=="2"){
					var noPass=$("#NO_PASS_REASON").find("option:selected").val(); 
					if(noPass=="0"){
					   alert("请选择审核不通过原因！");	
					   return;
					} 
				} 
			} 
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
		    
			function toGetType(ob){ 
				$(".lx_select").hide();
				$(".lx_selected").hide();
				$("#PROJECT_TYPE_ID").find("option:selected").text('请选择');  
				var options=$("option[lxpid="+ob.value+"]");  
				options.show(); 
			}
			
			function toGetCity(ob){
				$(".city-select").hide();
				$(".city-selected").hide(); 
				if(ob.value=='110000'){
					$("#CITY").find("option:selected").text('北京市');
					$("#CITY").find("option:selected").val('北京市');
				}else if(ob.value=='120000'){
					$("#CITY").find("option:selected").text('天津市');
					$("#CITY").find("option:selected").val('天津市');
				}else if(ob.value=='310000'){
					$("#CITY").find("option:selected").text('上海市');
					$("#CITY").find("option:selected").val('上海市');
				}else if(ob.value=='500000'){
					$("#CITY").find("option:selected").text('重庆市');
					$("#CITY").find("option:selected").val('重庆市');
				}else{ 
					$("#CITY").find("option:selected").text('请选择');  
					var options=$("option[cid="+ob.value+"]");  
					options.show(); 
				} 
			}
		</script>
		<!--/请在上方写此页面业务相关的脚本-->
	</body> 
</html>