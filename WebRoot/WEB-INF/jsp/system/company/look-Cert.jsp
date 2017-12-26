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
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/zoom/zoom.css" />
		<title>添加证件</title>
		 <style>
    /* SIMPLE DEMO STYLES */
    body {
      font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
      font-size: 12px;
      line-height: 1.6;
    }
    .item {
      margin: 50px;
      max-width: 700px;
    }
    .item img {
      width: 100%;
    }
    .portfolio-area li.hover {
         z-index: 999;
    } 
    @media (min-width: 750px) {
      body {
        font-size: 16px;
        line-height: 1.6;
      }
      .item {
        margin: 100px auto;
      }
    }
  </style>
	</head>

	<body class="pos-r"> 
		<article class="page-container"> 
			<div class="portfolio-content">
				<ul class="cl portfolio-area">
					<c:if test="${CertData.is_extra==0}">  
						<li class="item">
						<%-- 	<div class="portfoliobox"> 
								<div class="picbox"><a href="/jianlian/uploadImg/client/${CertData.IMG_PATH}" data-lightbox="gallery"><img src="/jianlian/uploadImg/client/${CertData.IMG_PATH}"></a></div>
								<div class="textbox">${CertData.CERTIFICATE_NAME}</div>
							</div>
						  --%>
							<img data-action="zoom" src="/jianlian/uploadImg/client/${CertData.IMG_PATH}">
							${CertData.CERTIFICATE_NAME} 
						</li>
					</c:if>
					<c:if test="${CertData.is_extra==1}">  
						<c:forEach items="${img_path}" var="img">  
							<li class="item">
								<%-- <div class="portfoliobox"> 
									<div class="picbox"><a href="/jianlian/uploadImg/client/${img}" data-lightbox="gallery"><img src="/jianlian/uploadImg/client/${img}"></a></div>
									<div class="textbox">${CertData.CERTIFICATE_NAME}</div>
								</div> --%>
								<img data-action="zoom" src="/jianlian/uploadImg/client/${img}">
							    ${CertData.CERTIFICATE_NAME} 
							</li>
						</c:forEach>  
					</c:if>
				</ul>
			</div>
	    </article> 
	    <script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script> 
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/layer/2.1/layer.js"></script> 
		<script type="text/javascript" src="<%=basePath%>H-ui/lib/lightbox2/2.8.1/js/lightbox-plus-jquery.min.js"></script> 
		<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui/js/H-ui.js"></script> 
		<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui.admin/js/H-ui.admin.js"></script> 
		<script src="http://www.jq22.com/jquery/bootstrap-3.3.4.js"></script>
		<script src="<%=basePath%>js/zoom/zoom.js"></script> 
		<script type="text/javascript">
		$(function(){
			$.Huihover(".portfolio-area li");
		});
</script> 
   </body> 
</html>
