<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<title>活动图片展示</title>
	</head>

<body class="pos-r">  
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>活动管理 <span class="c-gray en">&gt;</span>活动图片展示
<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"  href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container"> 
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
	    <span class="l">
	       <%--  <a href="javascript:;" onclick="img_del('<%=basePath%>acttp/delImg.do?ACT_ID=${act.ID}')" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>   --%>
	        <a id="img-add" class="btn btn-primary radius" data-title="添加活动图片" onclick="img_edit('添加活动图片','<%=basePath%>acttp/toAdd.do?ACT_ID=${act.ID}','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>添加活动图片</a>
		    <%-- <a id="img-add" class="btn btn-primary radius" data-title="添加活动海报图片" onclick="img_edit('添加活动海报图片','<%=basePath%>xmtp/toAdd.do?PRO_ID=${pro.ID}','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>添加活动海报图片</a>
		    <a id="img-add" class="btn btn-primary radius" data-title="添加活动详情图片" onclick="img_edit('添加活动详情图片','<%=basePath%>xmtp/toAddCover.do?PRO_ID=${pro.ID}','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>添加活动详情图片</a>
		    <a id="img-add" class="btn btn-primary radius" data-title="添加活动按钮图片" onclick="img_edit('添加活动按钮图片','<%=basePath%>xmtp/toAddCover.do?PRO_ID=${pro.ID}','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>添加活动按钮图片</a> --%>
		</span> 
	</div>
	<div class="portfolio-content">
		<form id="img-form">
		    <ul class="cl portfolio-area">
		    <c:if test="${not empty act}"> 
		   		<c:if test="${act.ACT_IMG !=null && act.ACT_IMG !=''}"> 
		        	<c:choose>
						 <c:when test="${fn:contains(act.ACT_IMG,'http://')}"> 
							<li class="item">
								<div class="portfoliobox">
								<div>封面</div>
									<input class="checkbox" name="ID" type="checkbox" value="${act.ID}">
									<div class="picbox"><a href="${act.ACT_IMG}" data-lightbox="gallery" ><img src="${act.ACT_IMG}"></a></div>
								</div>
							</li> 
						</c:when>
						<c:otherwise>
							<li class="item">
								<div class="portfoliobox">
									<div>封面</div>
									<input class="checkbox" name="ID" type="checkbox" value="${act.ID}">
									<div class="picbox"><a href="uploadImg/server/${act.ACT_IMG}" data-lightbox="gallery" ><img src="uploadImg/server/${act.ACT_IMG}"></a></div>
								</div>
							</li>
						</c:otherwise>
					</c:choose>
				</c:if> 
				<c:if test="${act.DETAILS_IMG !=null && act.DETAILS_IMG !=''}"> 
		        	<c:choose>
						 <c:when test="${fn:contains(act.DETAILS_IMG,'http://')}"> 
							<li class="item">
								<div class="portfoliobox">
								<div>活动详情图片</div>
									<input class="checkbox" name="ID" type="checkbox" value="${act.ID}">
									<div class="picbox"><a href="${act.DETAILS_IMG}" data-lightbox="gallery" ><img src="${act.DETAILS_IMG}"></a></div>
								</div>
							</li> 
						</c:when>
						<c:otherwise>
							<li class="item">
								<div class="portfoliobox">
								<div>活动详情图片</div>
									<input class="checkbox" name="ID" type="checkbox" value="${act.ID}">
									<div class="picbox"><a href="uploadImg/server/${act.DETAILS_IMG}" data-lightbox="gallery" ><img src="uploadImg/server/${act.DETAILS_IMG}"></a></div>
								</div>
							</li>
						</c:otherwise>
					</c:choose>
				</c:if> 
				<c:if test="${act.YY_IMG !=null && act.YY_IMG !=''}"> 
		        	<c:choose>
						 <c:when test="${fn:contains(act.YY_IMG,'http://')}"> 
							<li class="item">
								<div class="portfoliobox">
								<div>活动海报图</div>
									<input class="checkbox" name="ID" type="checkbox" value="${act.ID}">
									<div class="picbox"><a href="${act.YY_IMG}" data-lightbox="gallery" ><img src="${act.YY_IMG}"></a></div>
								</div>
							</li> 
						</c:when>
						<c:otherwise>
							<li class="item">
								<div class="portfoliobox">
								<div>活动海报图</div>
									<input class="checkbox" name="ID" type="checkbox" value="${act.ID}">
									<div class="picbox"><a href="uploadImg/server/${act.YY_IMG}" data-lightbox="gallery" ><img src="uploadImg/server/${act.YY_IMG}"></a></div>
								</div>
							</li>
						</c:otherwise>
					</c:choose>
				</c:if> 
				<c:if test="${act.BTN_IMG !=null && act.BTN_IMG !=''}"> 
		        	<c:choose>
						 <c:when test="${fn:contains(act.BTN_IMG,'http://')}"> 
							<li class="item">
								<div class="portfoliobox">
								<div>按钮图片</div>
									<input class="checkbox" name="ID" type="checkbox" value="${act.ID}">
									<div class="picbox"><a href="${act.BTN_IMG}" data-lightbox="gallery" ><img src="${act.BTN_IMG}"></a></div>
								</div>
							</li> 
						</c:when>
						<c:otherwise>
							<li class="item">
								<div class="portfoliobox">
								<div>按钮图片</div>
									<input class="checkbox" name="ID" type="checkbox" value="${act.ID}">
									<div class="picbox"><a href="uploadImg/server/${act.BTN_IMG}" data-lightbox="gallery" ><img src="uploadImg/server/${act.BTN_IMG}"></a></div>
								</div>
							</li>
						</c:otherwise>
					</c:choose>
				</c:if> 
				</c:if>
				<c:if test="${empty act}"> 
					<li class="item">
						 暂无数据！
					</li> 
				</c:if> 
			</ul> 
			
		</form>  
	</div>
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
<script type="text/javascript" src="<%=basePath%>lib/lightbox2/2.8.1/js/lightbox-plus-jquery.min.js"></script> 
<script type="text/javascript">
$(function(){
	var len=$("input[name='ID']").length;
	if(len<6){
		$("#img-add").show();
	}else{
		$("#img-add").hide();
	} 
	$.Huihover(".portfolio-area li");
});

function img_edit(title,url,w,h){  
	layer_show(title,url,w,h);
}

function img_del(url){ 
	var len=$("input[name='ID']:checked").length;
	if(len==0){
	    alert("请选择需要删除的图片！");
	}else{
		$.ajax({  
		      type: "POST",
		      url:url,
		      data:$('#img-form').serialize(), 
		      async: false,
		      success : function(result) { 
		       		parent.$('.breadcrumb .r .Hui-iconfont').click();
		       		window.location.reload();   
		      },
		      error : function(msg) {
		      }
		 });
	}
}

</script>
</body>
</html> 