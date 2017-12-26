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
		<title>查看视频</title>
	</head>

	<body class="pos-r"> 
		<article class="page-container"> 
			<div class="portfolio-content">
				<ul class="cl portfolio-area">
				 <c:if test="${kanData.VIDEOS !=null && kanData.VIDEOS !=''}">  
					<li class="item">
						<div> 
							<div> 
								<iframe height=498 width=510 src='/jianlian/uploadVideo/server/${kanData.VIDEOS}'></iframe>
							</div>
							<div class="textbox">${kanData.TITLE} </div>
						</div>
					</li>
				</c:if>
				 <c:if test="${kanData.IMGS !=null && kanData.IMGS !=''}">  
					<li class="item">
						<div> 
							<div> 
								<iframe height=498 width=510 src='/jianlian/uploadImg/server/${kanData.IMGS}'></iframe>
							</div>
							<div class="textbox">${kanData.TITLE} </div>
						</div>
					</li>
				</c:if>
				 <c:if test="${(kanData.VIDEOS ==null || kanData.VIDEOS=='') && (kanData.IMGS ==null || kanData.IMGS =='')}">  
					<li class="item">
						<div> 
							 没有上传图片和文字！
						</div>
					</li>
				</c:if>
				</ul>
			</div>
	    </article> 
	    <script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script> 
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/layer/2.1/layer.js"></script> 
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/lightbox2/2.8.1/js/lightbox-plus-jquery.min.js"></script> 
		<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui/js/H-ui.js"></script> 
		<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui.admin/js/H-ui.admin.js"></script> 
		<script type="text/javascript">
		$(function(){
			$.Huihover(".portfolio-area li");
		});
</script> 
   </body> 
</html>