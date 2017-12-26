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
		<title>查看封面图片</title>
	</head>

	<body class="pos-r"> 
		<article class="page-container"> 
			<div class="portfolio-content">
				<ul class="cl portfolio-area">
				      <c:if test="${kanData.COVER_PATH !=null && kanData.COVER_PATH !=''}"> 
			        	<c:choose>
							 <c:when test="${fn:contains(kanData.COVER_PATH,'http://')}"> 
								<li class="item">
									<div class="portfoliobox">
										<input class="checkbox" name="cover" type="checkbox" value="${kanData.COVER_PATH}">
										<div class="picbox"><a href="${kanData.COVER_PATH}" data-lightbox="gallery" ><img src="${kanData.COVER_PATH}"></a></div>
									</div>
								</li>
							</c:when>
							<c:otherwise>
								<li class="item">
									<div class="portfoliobox">
										<input class="checkbox" name="cover" type="checkbox" value="${kanData.COVER_PATH}">
										<div class="picbox"><a href="uploadImg/client/${kanData.COVER_PATH}" data-lightbox="gallery" ><img src="uploadImg/client/${kanData.COVER_PATH}"></a></div>
									</div>
								</li>	
							</c:otherwise>
						</c:choose>
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
