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
					<label class="form-label col-xs-4 col-sm-2">发布时间：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input onblur="showdateInput()" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate" value="${pd.PUBLISH_DATE }" placeholder="" id="PUBLISH_DATE" name="PUBLISH_DATE" data-rule-required="true">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">内容：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.CONETENT}" placeholder="" id="CONETENT" name="CONETENT" data-rule-required="true">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">城市：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.CITY}" placeholder="" id="CITY" name="CITY" data-rule-required="true">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">地址：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.ADDR}" placeholder="" id="ADDR" name="ADDR" data-rule-required="true">
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
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.config.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript">
		function user_list(){ 
			layer_show('选择会员','<%=basePath%>friends/user_list?TY=1','1000','600'); 
         } 
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
			var types=$("#INDUSTRY").find("option[lxpid="+typeId+"]"); 
			types.show(); 
			var validate = $("#form-project-add").validate({ 
			   submitHandler: function(form){ 
				   project_save(); //执行提交方法
                },
            });    
		 });
		
		 function project_save(){ 
				var index = parent.layer.getFrameIndex(window.name);  
				$.ajax({
	                url : "<%=basePath%>friends/save.do",
	                data : $('#form-project-add').serialize(),
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