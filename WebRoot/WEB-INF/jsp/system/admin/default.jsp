<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<!-- 下拉框 -->
<link rel="stylesheet" href="static/css/chosen.css" />

<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<link rel="stylesheet" href="static/css/datepicker.css" />
<!-- 日期框 -->
<script type="text/javascript" src="static/js/jquery-last.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</head>
<body style="overflow: hidden;">

	<div class="container-fluid" id="main-container">

		<div id="page-content" class="clearfix">

			<div id="page-content" class="clearfix">
				<div class="row-fluid">
					<div class="widget-box transparent">
						<div class="widget-header">
							<div class="widget-toolbar no-border">
								<ul class="nav nav-tabs" id="myTab2">
										<li class="in active"><a data-toggle="tab"
											href="#main" onclick="changeTab(1)"><i
												class="icon-user"></i> 工作台</a></li>
								</ul>

							</div>
						</div>
						<div class="widget-body">
							<div class="widget-main">
								<div class="row-fluid">
									<iframe id="mainFrame" src="" frameborder="no" width="100%;"></iframe>
									<script type="text/javascript">
										$("#mainFrame").height($(window).height());
									</script>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 引入 -->
	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript">
		$(top.hangge());

		$(function() {
			changeTab(1)
		});
	</script>
</body>
</html>
