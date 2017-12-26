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
			<form class="form form-horizontal" id="form-add">
			    <input type="hidden" name="ID" id="ID" value="${pd.ID }"/>
			    <input type="hidden" name="USER_ID" id="USER_ID" value="${pd.USER_ID}"/> 
			    <input type="hidden" name="PORTRALT" id="PORTRALT" value="${pd.SHOP_PORTRALT}"/> 
			    <input type="hidden" name="THEME" id="THEME" value="${pd.SHOP_THEME}"/>  
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>发布人：</label>
				    <div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.REAL_NAME}" placeholder="" id="REAL_NAME" name="REAL_NAME"  onclick="user_list()">
					</div> 
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">店铺名称：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.SHOP_NAME}" placeholder="" id="SHOP_NAME" name="SHOP_NAME">
					</div> 
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">详细地址：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.ADDR}" placeholder="" id="ADDR" name="ADDR">
					</div> 
				</div> 
				<div class="row ">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>店铺头像：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<span class="btn-upload form-group"> 
							<input type="file" value="${pd.SHOP_PORTRALT }" id="SHOP_PORTRALT" name="SHOP_PORTRALT" onchange="check(SHOP_PORTRALT)" style="width:150px;" />
						</span> 
					</div>
				</div>
				<div class="row ">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>店铺主题：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<span class="btn-upload form-group"> 
							<input type="file" value="${pd.SHOP_THEME }" id="SHOP_THEME" name="SHOP_THEME" onchange="check(SHOP_THEME)" style="width:150px;" />
						</span> 
					</div>
				</div> 
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">店铺描述：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
				       <textarea class="textarea"  placeholder="说点什么..." id="SHOP_DESC" name="SHOP_DESC">${pd.SHOP_DESC}</textarea>  
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
		<script type="text/javascript" src="<%=basePath%>js/ajaxfileupload.js"></script>  
		<script type="text/javascript">  
		function user_list(){ 
			layer_show('选择会员','<%=basePath%>shop/user_list?TY=1','1000','600'); 
         } 
		
		$(function(){
			var validate = $("#form-add").validate({ 
			   submitHandler: function(form){ 
				   save(); //执行提交方法
		        },
		     });    
		 });
		
		 function check(id){ 
			var aa=document.getElementById(id).value.toLowerCase().split('.');//以“.”分隔上传文件字符串
		    if(aa[aa.length-1]=='gif'||aa[aa.length-1]=='jpg'||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg'){//判断图片格式
				var imagSize =  document.getElementById(id).files[0].size;
				if(imagSize<1024*1024){ 
				    return true;
				}else{
					alert("图片为："+imagSize/(1024*1024)+"M,大于1M,请重新上传！");
					document.getElementById(id).value=null;
					return false;
				} 
		    }else{
		        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
		        document.getElementById(id).value=null;
		        return false;
		    } 
		 }
		
		 function save(){  
			var index = parent.layer.getFrameIndex(window.name);  
			var ID=$('#ID').val();
			var USER_ID=$('#USER_ID').val();
			var SHOP_NAME=$('#SHOP_NAME').val();
			var ADDR=$('#ADDR').val();
			var SHOP_DESC=$('#SHOP_DESC').val();   
			var theme=document.getElementById("SHOP_THEME").value;
			var port=document.getElementById("SHOP_PORTRALT").value;
			if(port==null||port==""||port==undefined){
				var PORTRALT=$('#PORTRALT').val();  
				if(PORTRALT!=null&&PORTRALT!=''){ 
				}else{
					alert("请选择要上传的店铺头像！");
					return ;
				}
			}
			if(theme==null||theme==""||theme==undefined){
				var THEME=$('#THEME').val();  
				if(THEME!=null&&THEME!=''){ 
				}else{
					alert("请选择要上传的店铺主题！");
					return ;
				} 
			} 
			var map = {
				"ID":ID,
				"SHOP_NAME":SHOP_NAME,
				"USER_ID":USER_ID,
				"ADDR":ADDR,
				"SHOP_DESC":SHOP_DESC 
			};  
			var url="<%=basePath%>shop/save.do"; 
			$.ajaxFileUpload({  
				  url :url,
				  type:'post',
				  data:map,
			      secureuri:false,  
			      fileElementId:['SHOP_PORTRALT','SHOP_THEME'],//file标签的id  
			      dataType: 'json',//返回数据的类型    
			      success: function (data, status) { 
			          if(data.statusCode == 200){ 
						  parent.$('.breadcrumb .r .Hui-iconfont').click();
						  parent.layer.close(index);
			          }else{
			        	  alert("上传图片失败！");
			          } 
			      },  
			      error: function (data, status, e) { 
			         alert(e);  
			      }  
			 }); 
		}   
		 
		 function getUser(id,name){  
			$('#USER_ID').val(id);
			$('#REAL_NAME').val(name); 
         }       
		</script>
		<!--/请在上方写此页面业务相关的脚本-->
	</body> 
</html>