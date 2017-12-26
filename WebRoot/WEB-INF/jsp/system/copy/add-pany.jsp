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
		<title>添加公司</title>
	</head>

	<body class="pos-r"> 
	<div>
<article class="page-container">
	<form class="form form-horizontal" id="form-horizontal"> 
	<input type="hidden" name="COMPANY_ID" id="COMPANY_ID" value="${pageData.ID }"/>
	<input type="hidden" name="ID" id="ID" value=""/>
	<%-- <form class="form form-horizontal" id="form-project-add" action="<%=basePath%>project/add.do">   --%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>公司名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" onchange="checkCompanyName(this)" value="${pageData.COMPANY_NAME}" placeholder="" id="COMPANY_NAME" name="COMPANY_NAME" data-rule-required="true">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>外文名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${pageData.ENGLISH_NAME}" placeholder="" id="ENGLISH_NAME" name="ENGLISH_NAME">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>创始人：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${pageData.FOUNDER}" placeholder="" id="FOUNDER" name="FOUNDER">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>员工人数：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${pageData.STAFFS_NUMBER}" placeholder="" id="STAFFS_NUMBER" name="STAFFS_NUMBER">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">公司logo：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
			    <span class="btn-upload form-group"> 
					<input type="file" id="LOGO_IMG" name="LOGO_IMG" value="${pageData.LOGO}" onchange="check1()" style="width:150px;" />
				</span> 
			</div> 
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">营业执照：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
			    <span class="btn-upload form-group"> 
					<input type="file" id="BUSINESS_IMG" name="BUSINESS_IMG" value="${pageData.BUSINESS_LICENSE}" onchange="check2()" style="width:150px;" />
				</span> 
			</div> 
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">公司封面：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
			    <span class="btn-upload form-group"> 
					<input type="file" id="COVER_IMG" name="COVER_IMG" value="${pageData.COVER_PATH}" onchange="check3()" style="width:150px;" />
				</span> 
			</div> 
		</div> 
		<input type="hidden" id="LOGO" name="LOGO" value="${pageData.LOGO}" >
		<input type="hidden" id="BUSINESS_LICENSE" name="BUSINESS_LICENSE" value="${pageData.BUSINESS_LICENSE}" >
		<input type="hidden" id="COVER_PATH" name="COVER_PATH" value="${pageData.COVER_PATH}" >
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">成立时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="input-text Wdate" value="${pageData.FOUND_TIME }" placeholder="" id="FOUND_TIME" name="FOUND_TIME">
			</div>
		</div> 
	
		<%-- <div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>所属行业：</label>
			<div class="formControls col-xs-8 col-sm-9">
			    <span class="select-box" style="width:150px;"> 
					<select class="select" name="CATEGORY_ID" id="CATEGORY_ID">
						<c:forEach items="${typeList}" var="hylx">
							<option value="${hylx.ID}" <c:if test="${hylx.ID == pageData.CATEGORY_ID}">selected</c:if>>${hylx.NAME }</option>
						</c:forEach>
					</select>
				</span> 
			</div>
		</div>  --%>
		
		<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>所属行业：</label>
					<div class="formControls col-xs-1" style="width:155px;">
					   <span class="select-box" style="width:150px;"> 
							<select class="select" name="TYPE_ID" id="TYPE_ID"  onchange="toGetType(this)">
								<option value="0" selected>请选择</option>
								<c:forEach items="${typeList}" var="hylx">
									<option value="${hylx.ID}" <c:if test="${hylx.ID == pageData.TYPE_ID}">selected</c:if>>${hylx.NAME }</option>
								</c:forEach>
							</select> 
						</span> 
					</div>
					 <div class="formControls col-xs-1" style="width:155px;">
					    <span class="select-box" style="width:150px;"> 
							<select class="select" name="CATEGORY_ID" id="CATEGORY_ID">
								<option class="lx_selected" value="0" selected>请选择</option>
								<c:forEach items="${lxList}" var="lx">
									<option class="lx_select" lxpid="${lx.PID}" value="${lx.ID}" <c:if test="${lx.ID == pageData.CATEGORY_ID }">selected</c:if>>${lx.NAME }</option>
								</c:forEach>
							</select> 
						</span> 
					</div> 
				</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">公司地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${pageData.ADDR }" placeholder="" id="ADDR" name="ADDR">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">官网地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${pageData.INTERNET_PATH }" placeholder="" id="INTERNET_PATH" name="INTERNET_PATH">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">经营范围：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
		       <textarea class="textarea"  placeholder=""  id="BUSINESS_SCOPE" name="BUSINESS_SCOPE">${pageData.BUSINESS_SCOPE }</textarea> 
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">公司性质：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
		       <textarea class="textarea"  placeholder=""  id="COMPANY_PROPERTY" name="COMPANY_PROPERTY">${pageData.COMPANY_PROPERTY }</textarea> 
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">公司简介：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
		       <textarea class="textarea"  placeholder=""  id="DESCRIPTION" name="DESCRIPTION">${pageData.DESCRIPTION }</textarea> 
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">发展历程：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<textarea class="textarea"  placeholder=""  id="DEVELOPE_HISTORY" name="DEVELOPE_HISTORY">${pageData.DEVELOPE_HISTORY }</textarea> 
		       <!--<script id="editor" type="text/plain" placeholder="" class="CONETENT" name="DEVELOPE_HISTORY" style="width:100%;height:300px;" data-rule-required="true">
					 ${pageData.DEVELOPE_HISTORY}
				</script>   -->
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">主要业务：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
			<textarea class="textarea"  placeholder=""  id="PRIMARY_SERVICE" name="PRIMARY_SERVICE">${pageData.PRIMARY_SERVICE }</textarea> 
		       <!--<script id="editor1" type="text/plain" placeholder="" class="CONETENT" name="PRIMARY_SERVICE" style="width:100%;height:300px;" data-rule-required="true">
					 ${pageData.PRIMARY_SERVICE}
				</script>   -->
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">企业文化：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
			<textarea class="textarea"  placeholder=""  id="BUSINESS_SULTURE" name="BUSINESS_SULTURE">${pageData.BUSINESS_SULTURE }</textarea> 
		       <!--<script id="editor2" type="text/plain" placeholder="" class="CONETENT" name="BUSINESS_SULTURE" style="width:100%;height:300px;" data-rule-required="true">
					 ${pageData.BUSINESS_SULTURE}
				</script>   -->
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2"> 
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
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
		
		<script type="text/javascript" src="<%=basePath%>js/ajaxfileupload.js"></script> 
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
            url : "<%=basePath%>copy/addcompany.do",
            data :$('#form-horizontal').serialize(),
            type : 'post',
            dataType : 'json',
            async : false,
            success : function(result) {
              if(result.statusCode == 200){
					parent.$('.breadcrumb .r .Hui-iconfont').click();
    				parent.layer.close(index);
              }
            },
            error : function(msg) {
            }
        });
  }
  
  function checkCompanyName(obj){
	  var COMPANY_NAME=$(obj).val();
	  $.ajax({
          url : "<%=basePath%>copy/findGsdByname.do",
          data :{"COMPANY_NAME":COMPANY_NAME},
          type : 'post',
          dataType : 'json',
          async : false,
          success : function(result) {
        	  if(result.comList != null && result.comList != ''){
        		  if(result.comList.length > 1){
        			  alert("公司名称已存在");
            		  $(obj).val('');
        		  }else if(result.comList.length == 1){
        			  var id = $("#COMPANY_ID").val();
        			  if(id == null || id==''){
        				  alert("公司名称已存在");
                		  $(obj).val('');
        			  }
        		  }
        		  
        	  }
          },
          error : function(msg) {
          }
      });
  }
  
  function toGetType(ob){ 
		$(".lx_select").hide();
		$(".lx_selected").hide();
		$("#CATEGORY_ID").find("option:selected").text('请选择');  
		var options=$("option[lxpid="+ob.value+"]");  
		options.show(); 
	}

 
var editor = UE.getEditor('editor');
var editor1 = UE.getEditor('editor1');
var editor2 = UE.getEditor('editor2');
 
function check1(){ 
	var aa=document.getElementById("LOGO_IMG").value.toLowerCase().split('.');//以“.”分隔上传文件字符串
    if(aa[aa.length-1]=='gif'||aa[aa.length-1]=='jpg'||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg'){//判断图片格式
		var imagSize =  document.getElementById("LOGO_IMG").files[0].size;
		if(imagSize<1024*1024){ 
			var url="<%=basePath%>copy/addLogo.do"; 
			$.ajaxFileUpload({  
				  url :url,
				  type:'post',
			      secureuri:false,  
			      fileElementId:'LOGO_IMG',//file标签的id  
			      dataType: 'json',//返回数据的类型    
			      success: function (data, status) { 
			          $("#LOGO").val(data.fileName);
			      },  
			      error: function (data, status, e) { 
			         alert(e);  
			      }  
			 });  
		}else{
			alert("图片为："+imagSize/(1024*1024)+"M,大于1M,请重新上传！");
			document.getElementById("LOGO_IMG").value=null;
		} 
    }else{
        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
        document.getElementById("LOGO_IMG").value=null;
    } 
 }
 
 
function check2(){ 
	var aa=document.getElementById("BUSINESS_IMG").value.toLowerCase().split('.');//以“.”分隔上传文件字符串
    if(aa[aa.length-1]=='gif'||aa[aa.length-1]=='jpg'||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg'){//判断图片格式
		var imagSize =  document.getElementById("BUSINESS_IMG").files[0].size;
		if(imagSize<1024*1024){ 
			var url="<%=basePath%>copy/addBUSINESS.do"; 
			$.ajaxFileUpload({  
				  url :url,
				  type:'post',
			      secureuri:false,  
			      fileElementId:'BUSINESS_IMG',//file标签的id  
			      dataType: 'json',//返回数据的类型    
			      success: function (data, status) { 
			          $("#BUSINESS_LICENSE").val(data.fileName);
			      },  
			      error: function (data, status, e) { 
			         alert(e);  
			      }  
			 });  
		}else{
			alert("图片为："+imagSize/(1024*1024)+"M,大于1M,请重新上传！");
			document.getElementById("BUSINESS_IMG").value=null;
		}
    }else{
        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
        document.getElementById("BUSINESS_IMG").value=null;
    } 
 }
 
function check3(){ 
	var aa=document.getElementById("COVER_IMG").value.toLowerCase().split('.');//以“.”分隔上传文件字符串
    if(aa[aa.length-1]=='gif'||aa[aa.length-1]=='jpg'||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg'){//判断图片格式
		var imagSize =  document.getElementById("COVER_IMG").files[0].size;
		if(imagSize<1024*1024){ 
			var url="<%=basePath%>copy/addCOVER.do"; 
			$.ajaxFileUpload({  
				  url :url,
				  type:'post',
			      secureuri:false,  
			      fileElementId:'COVER_IMG',//file标签的id  
			      dataType: 'json',//返回数据的类型    
			      success: function (data, status) { 
			          $("#COVER_PATH").val(data.fileName);
			      },  
			      error: function (data, status, e) { 
			         alert(e);  
			      }  
			 });  
		}else{
			alert("图片为："+imagSize/(1024*1024)+"M,大于1M,请重新上传！");
			document.getElementById("COVER_IMG").value=null;
		} 
    }else{
        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
        document.getElementById("COVER_IMG").value=null;
    } 
 }

</script>
</body>

</html>
