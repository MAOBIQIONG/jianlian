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
		<title>添加新闻</title>
	</head>

	<body class="pos-r"> 
	<div>
<article class="page-container">
	<form class="form form-horizontal" id="form-horizontal"> 
	<input type="hidden" name="ID" id="ID" value="${mediaData.ID }"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${mediaData.TITLE}" placeholder="" id="TITLE" name="TITLE" data-rule-required="true">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">发布时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text Wdate" value="${mediaData.PUBLISH_DATE }" placeholder="" id="PUBLISH_DATE" name="PUBLISH_DATE" data-rule-required="true">
			</div>
		</div>
		 <div class="row ">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>选择图片：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<span class="btn-upload form-group"> 
					<input type="file" value="${mediaData.IMGS }" id="IMGS" name="IMGS" onchange="check()" style="width:150px;" />
				</span> 
			</div>
		</div>
		<div class="row ">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>添加视频：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<span class="btn-upload form-group"> 
					<input type="file" value="${mediaData.VIDEOS }" id="VIDEOS" name="VIDEOS" onchange="checkVideo()" style="width:150px;" />
				</span> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>类型：</label> 
			<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="media-type">
				<div class="radio-box">
					<input name="TYPE_ID" type="radio"  class="TYPE_ID"  value="1"  <c:if test="${mediaData.TYPE_ID == '1' }">checked="checked"</c:if>>
					<label for="flag-1">视频</label>
				</div>
				<div class="radio-box">
					<input type="radio" class="TYPE_ID" name="TYPE_ID" value="2"  <c:if test="${mediaData.TYPE_ID == '2' }">checked="checked"</c:if>>
					<label for="flag-2">文字</label>
				</div> 
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>广告链接：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${mediaData.LINK_URL }" placeholder="http://" id="LINK_URL" name="LINK_URL" data-rule-url="true">
			</div>
		</div>
	<%-- 	<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>来源：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${mediaData.RESOURCE }" placeholder="" id="RESOURCE" name="RESOURCE" data-rule-required="true">
			</div>
		</div>  --%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>栏目编号：</label>
			<div class="formControls col-xs-8 col-sm-9">
		        <span class="select-box" style="width:150px;"> 
					<select class="select" name="COLUMN_ID" id="COLUMN_ID"> 
						<c:forEach items="${lanlist}" var="media">
							<option value="${media.ID }" <c:if test="${media.ID == mediaData.COLUMN_ID }">selected</c:if>>${media.TITLE_CATEGORY_NAME }</option>
						</c:forEach>
					</select>
				</span> 
			</div>
		</div> 
		<!-- <div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>是否推荐：</label> 
			<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="recommend">
				<div class="radio-box">
					<input name="IS_RECOMMEND" type="radio" class="IS_RECOMMEND" value="1"  <c:if test="${mediaData.IS_RECOMMEND == '1' }">checked="checked"</c:if>>
					<label for="flag-1">推荐</label>
				</div>
				<div class="radio-box">
					<input type="radio"  class="IS_RECOMMEND" name="IS_RECOMMEND" value="2"  <c:if test="${mediaData.IS_RECOMMEND == '2' }">checked="checked"</c:if>>
					<label for="flag-2">不推荐</label>
				</div> 
			</div>
		</div> -->
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>是否置顶：</label> 
			<div class="formControls col-xs-8 col-sm-9 skin-minimal" id="is-top">
				<div class="radio-box">
					<input name="IS_TOP" type="radio" class="IS_TOP" value="1"  <c:if test="${mediaData.IS_TOP == '1' }">checked="checked"</c:if>>
					<label for="flag-1">置顶</label>
				</div>
				<div class="radio-box">
					<input type="radio" class="IS_TOP" name="IS_TOP" value="2"  <c:if test="${mediaData.IS_TOP == '2' }">checked="checked"</c:if>>
					<label for="flag-2">不置顶</label>
				</div> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>来源：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${mediaData.RESOURCE }" placeholder="" id="RESOURCE" name="RESOURCE" data-rule-required="true">
			</div>
		</div> 
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">媒体内容：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<script  id="editor" type="text/plain"  placeholder="" id="CONETENT" name="CONETENT" style="width:100%;height:400px;" data-rule-required="true">
					 ${mediaData.CONETENT}
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
</div>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/bootstrap-modal/2.2.4/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/bootstrap-modal/2.2.4/bootstrap-modal.js"></script>

<script type="text/javascript" src="<%=basePath%>H-ui/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery.validation/1.14.0/messages_zh.min.js"></script> 
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath%>H-ui/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/webuploader/0.1.5/webuploader.min.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.config.js"></script>
<script type="text/javascript" src="<%=basePath%>H-ui/lib/ueditor/1.4.3/ueditor.all.js"></script>
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

function check(){ 
	 var aa=document.getElementById("IMGS").value.toLowerCase().split('.');//以“.”分隔上传文件字符串 
	 if(aa[aa.length-1]=='gif'||aa[aa.length-1]=='jpg'||aa[aa.length-1]=='png'||aa[aa.length-1]=='jpeg'){//判断图片格式
		var imagSize =  document.getElementById("IMGS").files[0].size;
		if(imagSize<1024*1024){ 
		    return true;
		}else{
			alert("图片为："+imagSize/(1024*1024)+"M,大于1M,请重新上传！");
			document.getElementById("IMGS").value=null;
			return false;
		} 
    }else{
        alert('请选择格式为*.jpg、*.gif、*.png、*.jpeg 的图片');//jpg和jpeg格式是一样的只是系统Windows认jpg，Mac OS认jpeg，//二者区别自行百度
        document.getElementById("IMGS").value=null;
        return false;
    } 
 }
 
function checkVideo(){ 
	 var aa=document.getElementById("VIDEOS").value.toLowerCase().split('.');//以“.”分隔上传文件字符串 
	 if(aa[aa.length-1]=='mp4'){//判断视频格式
		var imagSize =  document.getElementById("VIDEOS").files[0].size;
		if(imagSize<1024*1024*30){ 
		    return true;
		}else{
			alert("视频为："+imagSize/(1024*1024)+"M,大于30M,请重新上传！");
			document.getElementById("VIDEOS").value=null;
			return false;
		} 
   }else{
       alert('请选择格式为*.mp4视频');
       document.getElementById("VIDEOS").value=null;
       return false;
   } 
}

function companyAdd(){
      var index = parent.layer.getFrameIndex(window.name);  
			var editor = UE.getEditor('editor');
			var content=editor.getContent();//editor.getPlainTxt();
			var ID=$('#ID').val();
			var TITLE=$('#TITLE').val();
			var PUBLISH_DATE=$('#PUBLISH_DATE').val(); 
			var TYPE_ID = $('#media-type input[name="TYPE_ID"]:checked ').val(); 
			if( !TYPE_ID ){
				alert("请选择类型!");
				return;
			}
			var LINK_URL=$('#LINK_URL').val();
			var RESOURCE=$('#RESOURCE').val(); 
			var COLUMN_ID = $('#COLUMN_ID').val();  
			var IS_RECOMMEND = $('#recommend input[name="IS_RECOMMEND"]:checked ').val(); 
			var IS_TOP = $('#is-top input[name="IS_TOP"]:checked ').val(); 
			if( !IS_TOP ){
				alert("请确认是否置顶!");
				return;
			}
			if(ID==undefined){
				ID="";
			} 
			var url="<%=basePath%>Media/addmediaImgsAndVedios.do?ID="+ID;
		    //var URL=url+"?CONETENT=&ID="+ID+"&TITLE="+TITLE+"&PUBLISH_DATE="+PUBLISH_DATE+"&TYPE_ID="+TYPE_ID+"&LINK_URL="+LINK_URL+"&RESOURCE="+RESOURCE+"&COLUMN_ID="+COLUMN_ID+"&IS_RECOMMEND="+IS_RECOMMEND+"&IS_TOP="+IS_TOP; 
			var map = {
					"CONETENT":content,
					"ID":ID,
					"TITLE":TITLE,
					"PUBLISH_DATE":PUBLISH_DATE,
					"TYPE_ID":TYPE_ID,
					"LINK_URL":LINK_URL,
					"RESOURCE":RESOURCE,
					"COLUMN_ID":COLUMN_ID,
					"IS_RECOMMEND":IS_RECOMMEND,
					"IS_TOP":IS_TOP 
			};
		    $.ajaxFileUpload({  
				  url : url,
				  type:'post',
	             secureuri:false,  
	             fileElementId:['VIDEOS','IMGS'],//file标签的id  
	             dataType: 'json',//返回数据的类型   
	             success: function (data, status) { 
	                 if(data.statusCode == 200){ 
	                	  var mediaId = data.ID;
	                	  if( mediaId != null && mediaId != undefined && mediaId != ""   ){
	                		  map.ID =mediaId;
	                	  }
	                	  updMedia(map);
	                 }
	             },  
	             error: function (data, status, e) { 
	                alert(e);  
	             }  
	        });  
   }
		    
	function updMedia(map){
		var url="<%=basePath%>Media/addmedia.do";
		$.ajax( {
			type : "POST",
			url : url,
			data:map, 
			success : function(data) {
				if(data.statusCode == 200){ 
					parent.$('.breadcrumb .r .Hui-iconfont').click();
				    parent.layer.close(index);
               }
			}
		});	
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
</body>

</html>
