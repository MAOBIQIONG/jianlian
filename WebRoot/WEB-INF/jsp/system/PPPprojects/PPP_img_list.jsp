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
		<title>项目图片展示</title>
	</head>

<body class="pos-r">  
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>项目管理 <span class="c-gray en">&gt;</span>项目图片展示
<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"  href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container"> 
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
	    <span class="l">
	        <a href="javascript:;" onclick="img_del('<%=basePath%>pppxmtp/delImg.do?PRO_ID=${pro.ID}')" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>  
		    <a id="img-add" class="btn btn-primary radius" data-title="添加图片" onclick="img_edit('添加图片','<%=basePath%>pppxmtp/toAdd.do?PRO_ID=${pro.ID}','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>添加图片</a>
		    <a id="img-add" class="btn btn-primary radius" data-title="添加封面" onclick="img_edit('添加封面','<%=basePath%>pppxmtp/toAddCover.do?PRO_ID=${pro.ID}','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>添加项目封面</a>
		</span> 
	</div>
	<div class="portfolio-content">
		<form id="img-form">
		    <ul class="cl portfolio-area">
			    <!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty xmtp}"> 
						<c:forEach items="${xmtp}" var="img"> 
						   <c:if test="${pro.USER_ID !=null && pro.USER_ID !=''}">  
						        <c:if test="${img.IMG_PATH !=null && img.IMG_PATH !=''}"> 
						        	<c:choose>
										 <c:when test="${fn:contains(img.IMG_PATH,'http://')}"> 
											<li class="item">
												<div class="portfoliobox">
													<input class="checkbox" name="ID" type="checkbox" value="${img.ID}">
													<div class="picbox"><a href="${img.IMG_PATH}" data-lightbox="gallery" ><img src="${img.IMG_PATH}"></a></div>
												</div>
											</li>
										</c:when>
										<c:otherwise>
											<li class="item">
												<div class="portfoliobox">
													<input class="checkbox" name="ID" type="checkbox" value="${img.ID}">
													<div class="picbox"><a href="uploadImg/client/${img.IMG_PATH}" data-lightbox="gallery" ><img src="uploadImg/client/${img.IMG_PATH}"></a></div>
												</div>
											</li>	
										</c:otherwise>
									</c:choose>
								</c:if> 
							</c:if>
							<c:if test="${pro.CREATE_BY !=null && pro.CREATE_BY !=''}">  
						        <c:if test="${img.IMG_PATH !=null && img.IMG_PATH !=''}"> 
						        	<c:choose>
										 <c:when test="${fn:contains(img.IMG_PATH,'http://')}"> 
											<li class="item">
												<div class="portfoliobox">
													<input class="checkbox" name="ID" type="checkbox" value="${img.ID}">
													<div class="picbox"><a href="${img.IMG_PATH}" data-lightbox="gallery" ><img src="${img.IMG_PATH}"></a></div>
												</div>
											</li> 
										</c:when>
										<c:otherwise>
											<li class="item">
												<div class="portfoliobox">
													<input class="checkbox" name="ID" type="checkbox" value="${img.ID}">
													<div class="picbox"><a href="uploadImg/server/${img.IMG_PATH}" data-lightbox="gallery" ><img src="uploadImg/server/${img.IMG_PATH}"></a></div>
												</div>
											</li>
										</c:otherwise>
									</c:choose>
								</c:if> 
							</c:if> 
						</c:forEach> 
					</c:when>
					<c:otherwise>
						<li class="item">
							 没有数据
						</li>
					</c:otherwise>
				</c:choose>	
			</ul> 
			<ul class="cl portfolio-area"> 
				 <c:if test="${pro.USER_ID !=null && pro.USER_ID !=''}"> 
				      <c:if test="${pro.COVER_PATH !=null && pro.COVER_PATH !=''}"> 
			        	<c:choose>
							 <c:when test="${fn:contains(pro.COVER_PATH,'http://')}"> 
								<li class="item">
									<div class="portfoliobox">
										<input class="checkbox" name="cover" type="checkbox" value="${pro.COVER_PATH}">
										<div class="picbox"><a href="${pro.COVER_PATH}" data-lightbox="gallery" ><img src="${pro.COVER_PATH}"></a></div>
									</div>
								</li>
							</c:when>
							<c:otherwise>
								<li class="item">
									<div class="portfoliobox">
										<input class="checkbox" name="cover" type="checkbox" value="${pro.COVER_PATH}">
										<div class="picbox"><a href="uploadImg/client/${pro.COVER_PATH}" data-lightbox="gallery" ><img src="uploadImg/client/${pro.COVER_PATH}"></a></div>
									</div>
								</li>	
							</c:otherwise>
						</c:choose>
					</c:if> 
				</c:if>   
				<c:if test="${pro.CREATE_BY !=null && pro.CREATE_BY !=''}">
					<c:if test="${pro.COVER_PATH !=null && pro.COVER_PATH !=''}"> 
			        	<c:choose>
							 <c:when test="${fn:contains(pro.COVER_PATH,'http://')}"> 
								<li class="item">
									<div class="portfoliobox">
										<input class="checkbox" name="cover" type="checkbox" value="${pro.COVER_PATH}">
										<div class="picbox"><a href="${pro.COVER_PATH}" data-lightbox="gallery" ><img src="${pro.COVER_PATH}"></a></div>
									</div>
								</li> 
							</c:when>
							<c:otherwise>
								<li class="item">
									<div class="portfoliobox">
										<input class="checkbox" name="cover" type="checkbox" value="${pro.COVER_PATH}">
										<div class="picbox"><a href="uploadImg/server/${pro.COVER_PATH}" data-lightbox="gallery" ><img src="uploadImg/server/${pro.COVER_PATH}"></a></div>
									</div>
								</li>
							</c:otherwise>
						</c:choose>
					</c:if>   
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