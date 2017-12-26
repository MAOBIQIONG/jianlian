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
		<title>添加活动</title>
	</head>

<body class="pos-r">  
<article class="page-container">
	<form class="form form-horizontal" id="form-horizontal"> 
	<input type="hidden" name="ID" id="ID" value="${activityData.ID }"/>  
	<input type="hidden" name="STATUS" id="STATUS" value="${activityData.STATUS }"/>  
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>活动名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text valib" value="${activityData.ACT_NAME}" placeholder="" id="ACT_NAME" name="ACT_NAME" data-rule-required="true">
			</div>
		</div>  
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>活动类型：</label>
			<div class="formControls col-xs-8 col-sm-9">
			    <span class="select-box" style="width:150px;"> 
					<select class="select" name="ACT_TYPE" id="ACT_TYPE">
						<c:forEach items="${hdlxlist}" var="hdlx">
							<option value="${hdlx.BIANMA}" <c:if test="${hdlx.BIANMA == activityData.ACT_TYPE}">selected</c:if>>${hdlx.NAME }</option>
						</c:forEach>
					</select>
				</span> 
			</div>
		</div>  
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>开始时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text valib Wdate" value="${activityData.START_DATE }" placeholder="" id="START_DATE" name="START_DATE" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" data-rule-required="true">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>结束时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text valib Wdate" value="${activityData.END_DATE }" placeholder="" id="END_DATE" name="END_DATE" data-rule-required="true"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:m'})">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>截止时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text valib Wdate" value="${activityData.DUE_DATE }" placeholder="" id="DUE_DATE" name="DUE_DATE" data-rule-required="true"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:m'})">
			</div>
		</div>
		<%-- <div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>活动城市：</label> 
			<div class="formControls col-xs-8 col-sm-9">
			    <span class="select-box" style="width:150px;"> 
					<select class="select" name="ACT_CITY" id="ACT_CITY">
						<c:forEach items="${citiyList}" var="city">
							<option value="${city.name}" <c:if test="${city.name == activityData.ACT_CITY}">selected</c:if>>${city.name}</option>
						</c:forEach>
					</select>
				</span> 
			</div>
		</div> --%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>活动城市：</label>
			  <div class="formControls col-xs-1" style="width:155px;"> 
				 <span class="select-box" style="width:150px;"> 
					<select class="select"  name="AREA_ID" id="AREA_ID"  onchange="toGetCity(this)">
						<option value="0" selected>请选择</option>
						<c:forEach items="${areaList}" var="area">
							<option value="${area.areacode}" <c:if test="${area.areacode == activityData.PARENTID }">selected</c:if>>${area.areaname}</option>
						</c:forEach>
					</select> 
				 </span> 
			 </div>   
			 <div class="formControls col-xs-1" style="width:155px;">
			   <span class="select-box" style="width:150px;">  
				    <select class="select" name="ACT_CITY" id="ACT_CITY">
						<option class="city-selected" value="0" selected>请选择</option>
						<c:forEach items="${cityList}" var="city">
							<option class="city-select" cid="${city.parentid}" value="${city.areaname}" <c:if test="${city.areaname == activityData.ACT_CITY}">selected</c:if>>${city.areaname }</option>
						</c:forEach>
					</select>  
				</span> 
			</div>  
		</div>  
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>具体地点：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text valib" value="${activityData.ACT_ADDR }" placeholder="" id="ACT_ADDR" name="ACT_ADDR" data-rule-required="true">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">活动链接：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text valib" value="${activityData.LINK_URL}" placeholder="" id="LINK_URL" name="LINK_URL">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">限制人数：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="number" style="width:150px;" class="input-text valib" value="${activityData.LIMIT_COUNT }" placeholder="人数" id="LIMIT_COUNT" name="LIMIT_COUNT">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>是否免费：</label> 
			<div class="formControls col-xs-8 col-sm-9 skin-minimal">
				<div class="radio-box">
					<input id="isFree" name="IS_FREE" type="radio" value="1"  <c:if test="${activityData.IS_FREE == '1' }">checked="checked"</c:if>>
					<label for="flag-1">免费</label>
				</div>
				<div class="radio-box">
					<input id="noFree" type="radio"  name="IS_FREE" value="2"  <c:if test="${activityData.IS_FREE == '2' }">checked="checked"</c:if>>
					<label for="flag-2">不免费</label>
				</div> 
			</div>
		</div>
		<div id="xian" style="display:none;">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">原价：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text valib" value="${activityData.PRICE }" placeholder="" id="PRICE" name="PRICE" data-rule-required="true">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">会员价：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text valib" value="${activityData.VIP_PRICE }" placeholder="" id="VIP_PRICE" name="VIP_PRICE" data-rule-required="true">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">副会长价格：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text valib" value="${activityData.VICE_PRE_PRICE }" placeholder="" id="VICE_PRE_PRICE" name="VICE_PRE_PRICE" data-rule-required="true">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">常务副会长价格：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text valib" value="${activityData.EXE_VICE_PRE_PRICE }" placeholder="" id="EXE_VICE_PRE_PRICE" name="EXE_VICE_PRE_PRICE" data-rule-required="true">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">会长价格：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text valib" value="${activityData.PRESIDENT_PRICE }" placeholder="" id="PRESIDENT_PRICE" name="PRESIDENT_PRICE" data-rule-required="true">
				</div>
			</div>  
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>费用描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
			    <input type="text" class="input-text" value="${activityData.FEE_DESCRIPTION}" placeholder="" id="FEE_DESCRIPTION" name="FEE_DESCRIPTION" data-rule-required="true">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">活动内容：</label> 
			<div class="formControls col-xs-8 col-sm-9"> 
		       <textarea class="textarea"  placeholder=""  id="ACT_CONTENT" name="ACT_CONTENT" >${activityData.ACT_CONTENT }</textarea> 
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>主办方：</label>
			<div class="formControls col-xs-8 col-sm-9">
			    <input type="text" class="input-text" value="${activityData.SPONSOR}" placeholder="" id="SPONSOR" name="SPONSOR" data-rule-required="true">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>负责人电话：</label>
			<div class="formControls col-xs-8 col-sm-9">
			    <input type="text" class="input-text" value="${activityData.HEADPHONE}" placeholder="" id="HEADPHONE" name="HEADPHONE" data-rule-required="true">
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2"> 
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
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
        $(function(){
        	var value=$("input[name='IS_FREE']:checked").val();
        	if(value=='2'){
        		 $("#xian").show();
        	}else{
        		 $("#xian").hide();
        	} 
     	   $("#noFree").click(function(){ 
     		    $("#xian").show();
     		});
     	   
     	   $("#isFree").click(function(){ 
     		   $("#xian").hide();
 	   		}); 
     	   
 		}); 
         
        $(function(){  
			 $(".city-select").hide();
			 var areaId=$("#AREA_ID").find("option:selected").val();  
			 if(areaId=="110000"){
				 $("#ACT_CITY").find("option:selected").text('北京市');
				 $("#ACT_CITY").find("option:selected").val('北京市');
			 }else if(areaId=='120000'){
				$("#ACT_CITY").find("option:selected").text('天津市');
				$("#ACT_CITY").find("option:selected").val('天津市');
			}else if(areaId=='310000'){
				$("#ACT_CITY").find("option:selected").text('上海市');
				$("#ACT_CITY").find("option:selected").val('上海市');
			}else if(areaId=='500000'){
				$("#ACT_CITY").find("option:selected").text('重庆市');
				$("#ACT_CITY").find("option:selected").val('重庆市');
			}else{  
				var options=$("#ACT_CITY").find("option[cid="+areaId+"]"); 
				options.show(); 
			}  
			 var validate = $("#form-horizontal").validate({ 
				   submitHandler: function(form){ 
					   companyAdd(); //执行提交方法
	                },
	            });    
		 }); 
        
			function companyAdd(){  
				var city=$("#ACT_CITY").find("option:selected").val(); 
				if(city=="0"){
				   alert("请选择活动所在城市！");	
				   return;
				} 
				//alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value); 
				var index = parent.layer.getFrameIndex(window.name); 
				  $.ajax({
	                url : "<%=basePath%>activity/addactivity.do",
	                data :$('#form-horizontal').serialize(),
	                type : 'post',
	                dataType : 'json',
	                async : false,
	                success : function(result) {
							parent.$('.breadcrumb .r .Hui-iconfont').click();
		       				parent.layer.close(index);
	                },
	                error : function(msg) {
	                }
	            }); 
		    }
		  
			function toGetCity(ob){
				$(".city-select").hide();
				$(".city-selected").hide(); 
				if(ob.value=='110000'){
					$("#ACT_CITY").find("option:selected").text('北京市');
					$("#ACT_CITY").find("option:selected").val('北京市');
				}else if(ob.value=='120000'){
					$("#ACT_CITY").find("option:selected").text('天津市');
					$("#ACT_CITY").find("option:selected").val('天津市');
				}else if(ob.value=='310000'){
					$("#ACT_CITY").find("option:selected").text('上海市');
					$("#ACT_CITY").find("option:selected").val('上海市');
				}else if(ob.value=='500000'){
					$("#ACT_CITY").find("option:selected").text('重庆市');
					$("#ACT_CITY").find("option:selected").val('重庆市');
				}else{ 
					$("#ACT_CITY").find("option:selected").text('请选择');  
					var options=$("option[cid="+ob.value+"]");  
					options.show(); 
				} 
			}
		    
</script>
</body>

</html>
