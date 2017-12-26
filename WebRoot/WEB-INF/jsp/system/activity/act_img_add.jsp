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
		<title>上传活动图片</title>
	</head>
	<body class="pos-r">  
	    <article class="page-container">
		    <form class="form form-horizontal" id="form-horizontal">  
		        <input type="hidden" name="ACT_ID" id="ACT_ID" value="${ACT_ID}"/>  
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">活动封面图片：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					    <span class="btn-upload form-group"> 
							<input type="file" id="ACT_IMG" name="ACT_IMG" onchange="check()" style="width:150px;" />
						</span> 
					</div> 
				</div>  
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">活动海报图片：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					    <span class="btn-upload form-group"> 
							<input type="file" id="YY_IMG" name="YY_IMG" onchange="check()" style="width:150px;" />
						</span> 
					</div> 
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">活动详情图片：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					    <span class="btn-upload form-group"> 
							<input type="file" id="DETAILS_IMG" name="DETAILS_IMG" onchange="check()" style="width:150px;" />
						</span> 
					</div> 
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2">活动按钮图片：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
					    <span class="btn-upload form-group"> 
							<input type="file" id="BTN_IMG" name="BTN_IMG" onchange="check()" style="width:150px;" />
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
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/layer/2.1/layer.js"></script>  
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
		<script type="text/javascript" src="<%=basePath%>js/ajaxfileupload.js"></script> 
		<script type="text/javascript">
		$(function(){
			var validate = $("#form-horizontal").validate({ 
			   submitHandler: function(form){ 
				   honor_save(); //执行提交方法
		        },
		     });    
		 });
		
		 function check(){ 
		 	//验证封面图片
			var aa=document.getElementById("ACT_IMG").value.toLowerCase().split('.');//以“.”分隔上传文件字符串
		   	var bb=document.getElementById("DETAILS_IMG").value.toLowerCase().split('.');//以“.”分隔上传文件字符串
		    var cc=document.getElementById("YY_IMG").value.toLowerCase().split('.');//以“.”分隔上传文件字符串
		   	var dd=document.getElementById("BTN_IMG").value.toLowerCase().split('.');//以“.”分隔上传文件字符串
		    if(aa[aa.length-1]=='gif'||aa[aa.length-1]=='jpg'||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg'){//判断图片格式
				var imagSize =  document.getElementById("ACT_IMG").files[0].size;
				if(imagSize<930*410){ 
				    return true;
				}else{
					alert("图片为："+imagSize/(930*410)+",请重新上传！");
					document.getElementById("ACT_IMG").value=null;
					return false;
				} 
		    }else if( aa[aa.length-1]=='' ){
		        return false;
		    }else{
		        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
		        document.getElementById("ACT_IMG").value=null;
		        return false;
		    }  
		    
		    //验证活动详情图片
		    
		    if(bb[bb.length-1]=='gif'||bb[bb.length-1]=='jpg'||bb[bb.length-1]=='png'||bb[bb.length-1]=='jpeg'){//判断图片格式
				var imagSize =  document.getElementById("DETAILS_IMG").files[0].size;
				if(imagSize<1400*5208){ 
				    return true;
				}else{
					alert("图片为："+imagSize/(1400*5208)+"M,大于1M,请重新上传！");
					document.getElementById("DETAILS_IMG").value=null;
					return false;
				} 
		    }else if( bb[bb.length-1]=='' ){
		        return false;
		    }else{
		        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
		        document.getElementById("DETAILS_IMG").value=null;
		        return false;
		    } 
		    //验证活动海报图片
		   
		    if(cc[cc.length-1]=='gif'||cc[cc.length-1]=='jpg'||cc[cc.length-1]=='png'||cc[cc.length-1]=='jpeg'){//判断图片格式
				var imagSize =  document.getElementById("YY_IMG").files[0].size;
				if(imagSize<1400*2208){ 
				    return true;
				}else{
					alert("图片为："+imagSize/(1400*2208)+"M,大于1M,请重新上传！");
					document.getElementById("YY_IMG").value=null;
					return false;
				} 
		    }else if( cc[cc.length-1]=='' ){
		        return false;
		    }else{
		        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
		        document.getElementById("YY_IMG").value=null;
		        return false;
		    }
		    //验证活动按钮图片
		    
		    if(dd[dd.length-1]=='gif'||dd[dd.length-1]=='jpg'||dd[dd.length-1]=='png'||dd[dd.length-1]=='jpeg'){//判断图片格式
				var imagSize =  document.getElementById("BTN_IMG").files[0].size;
				if(imagSize<120*35){ 
				    return true;
				}else{
					alert("图片为："+imagSize/(120*35)+"M,大于1M,请重新上传！");
					document.getElementById("BTN_IMG").value=null;
					return false;
				} 
		    }else if( dd[dd.length-1]=='' ){
		        return false;
		    }else{
		        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
		        document.getElementById("BTN_IMG").value=null;
		        return false;
		    }
		 }
		
		 function honor_save(){  
			var index = parent.layer.getFrameIndex(window.name);  
			var ACT_ID=document.getElementById("ACT_ID").value;
			var ACT_IMG=document.getElementById("ACT_IMG").value;
			var DETAILS_IMG=document.getElementById("DETAILS_IMG").value;
			var YY_IMG=document.getElementById("YY_IMG").value;
			var BTN_IMG=document.getElementById("BTN_IMG").value;
			if(ACT_IMG==null&&ACT_IMG==""&&ACT_IMG==undefined&&DETAILS_IMG==null&&DETAILS_IMG==""&&DETAILS_IMG==undefined&&YY_IMG==null&&YY_IMG==""&&YY_IMG==undefined&&BTN_IMG==null&&BTN_IMG==""&&BTN_IMG==undefined){
				alert("请选择要上传的图片！");
				return
			}else{
				var url="<%=basePath%>acttp/uploadImgs.do?ACT_ID="+ACT_ID; 
				$.ajaxFileUpload({  
					  url :url,
					  type:'post',
				      secureuri:false,  
				      fileElementId:['ACT_IMG','DETAILS_IMG','YY_IMG','BTN_IMG'],//file标签的id  
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
		}  
		</script>
	</body> 
</html>
