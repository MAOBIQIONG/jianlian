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
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>商学院名称：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="${pd.TITLE}" placeholder="" id="TITLE" name="TITLE" data-rule-required="true">
					</div>
				</div>
				<div class="row ">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>选择封面图片：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
						<span class="btn-upload form-group"> 
							<input type="file" value="${pd.COVER_PATH }" id="COVER_PATH" name="COVER_PATH" onchange="check()" style="width:150px;" />
						</span> 
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>内容：</label>
					<div class="formControls col-xs-8 col-sm-9"> 
				       <script id="editor" type="text/plain" placeholder="" class="CONETENT" id="CONETENT" name="CONETENT" style="width:100%;height:400px;" data-rule-required="true">
					 		${pd.CONETENT}
						</script>  
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
		<script type="text/javascript" src="<%=basePath%>js/exif.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/uploadImg.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jquery.form.js"></script>
		<script type="text/javascript">
		 $(function(){ 
			 var validate = $("#form-project-add").validate({ 
				   submitHandler: function(form){ 
					   project_save(); //执行提交方法
	                },
	            });    
		 });
		
		 function project_save(){ 
				var editor = UE.getEditor('editor');
				var content=editor.getContent();
				var ID=$('#ID').val();
				var TITLE=$('#TITLE').val(); 
				if(ID==undefined){
					ID=""; 
				}
				var COVER_PATH=document.getElementById("COVER_PATH").value;
				if(!COVER_PATH){
					alert("请选择要上传的封面图片！");
					return
				}
				var url="<%=basePath%>sxypro/save.do?ID="+ID; 
				var map = {
					"CONETENT":content,
					"ID":ID,
					"TITLE":TITLE
			  	}; 
			  	var index = parent.layer.getFrameIndex(window.name); 
				$.ajaxFileUpload({  
					  url : url,
					  type:'post',
		             secureuri:false,  
		             data:map,
		             fileElementId:['COVER_PATH'],//file标签的id  
		             dataType: 'json',//返回数据的类型   
		             success: function (data, status) { 
		                 if(data.statusCode == 200){  
		                	  if(data.statusCode == 200){ 
		      					parent.$('.breadcrumb .r .Hui-iconfont').click();
		      				    parent.layer.close(index);
		                      }
		                 }
		             },  
		             error: function (data, status, e) { 
		                alert(e);  
		             }  
		        }); 
		    }  
		    
			 
		function check(){ 
			 var aa=document.getElementById("COVER_PATH").value.toLowerCase().split('.');//以“.”分隔上传文件字符串 
			 if(aa[aa.length-1]=='gif'||aa[aa.length-1]=='jpg'||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg'){//判断图片格式
				var imagSize =  document.getElementById("COVER_PATH").files[0].size;
				if(imagSize<1024*1024){ 
				    return true;
				}else{
					alert("图片为："+imagSize/(1024*1024)+"M,大于1M,请重新上传！");
					document.getElementById("COVER_PATH").value=null;
					return false;
				} 
		    }else{
		        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
		        document.getElementById("COVER_PATH").value=null;
		        return false;
		    } 
		 }
			
			
			
			
			$(function(){
				$('.skin-minimal input').iCheck({
					checkboxClass: 'icheckbox-blue',
					radioClass: 'iradio-blue',
					increaseArea: '20%'
				});
				
				
				$list = $("#fileList"),
				$btn = $("#btn-star"),
				state = "pending",
				uploader;
			
				var uploader = WebUploader.create({
					auto: true,
					swf: 'lib/webuploader/0.1.5/Uploader.swf',
				
					// 文件接收服务端。
					server: 'fileupload.php',
				
					// 选择文件的按钮。可选。
					// 内部根据当前运行是创建，可能是input元素，也可能是flash.
					pick: '#filePicker',
				
					// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
					resize: false,
					// 只允许选择图片文件。
					accept: {
						title: 'Images',
						extensions: 'gif,jpg,jpeg,bmp,png',
						mimeTypes: 'image/*'
					}
				});
				uploader.on( 'fileQueued', function( file ) {
					var $li = $(
						'<div id="' + file.id + '" class="item">' +
							'<div class="pic-box"><img></div>'+
							'<div class="info">' + file.name + '</div>' +
							'<p class="state">等待上传...</p>'+
						'</div>'
					),
					$img = $li.find('img');
					$list.append( $li );
				
					// 创建缩略图
					// 如果为非图片文件，可以不用调用此方法。
					// thumbnailWidth x thumbnailHeight 为 100 x 100
					uploader.makeThumb( file, function( error, src ) {
						if ( error ) {
							$img.replaceWith('<span>不能预览</span>');
							return;
						}
				
						$img.attr( 'src', src );
					}, thumbnailWidth, thumbnailHeight );
				});
				// 文件上传过程中创建进度条实时显示。
				uploader.on( 'uploadProgress', function( file, percentage ) {
					var $li = $( '#'+file.id ),
						$percent = $li.find('.progress-box .sr-only');
				
					// 避免重复创建
					if ( !$percent.length ) {
						$percent = $('<div class="progress-box"><span class="progress-bar radius"><span class="sr-only" style="width:0%"></span></span></div>').appendTo( $li ).find('.sr-only');
					}
					$li.find(".state").text("上传中");
					$percent.css( 'width', percentage * 100 + '%' );
				});
				
				// 文件上传成功，给item添加成功class, 用样式标记上传成功。
				uploader.on( 'uploadSuccess', function( file ) {
					$( '#'+file.id ).addClass('upload-state-success').find(".state").text("已上传");
				});
				
				// 文件上传失败，显示上传出错。
				uploader.on( 'uploadError', function( file ) {
					$( '#'+file.id ).addClass('upload-state-error').find(".state").text("上传出错");
				});
				
				// 完成上传完了，成功或者失败，先删除进度条。
				uploader.on( 'uploadComplete', function( file ) {
					$( '#'+file.id ).find('.progress-box').fadeOut();
				});
				uploader.on('all', function (type) {
			        if (type === 'startUpload') {
			            state = 'uploading';
			        } else if (type === 'stopUpload') {
			            state = 'paused';
			        } else if (type === 'uploadFinished') {
			            state = 'done';
			        }
			
			        if (state === 'uploading') {
			            $btn.text('暂停上传');
			        } else {
			            $btn.text('开始上传');
			        }
			    });
			
			    $btn.on('click', function () {
			        if (state === 'uploading') {
			            uploader.stop();
			        } else {
			            uploader.upload();
			        }
			    });
				
				var ue = UE.getEditor('editor');
				
			});
		</script>
		<!--/请在上方写此页面业务相关的脚本-->
	</body> 
</html>