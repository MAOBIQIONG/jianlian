<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'industries.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	body { 
			font-family:Arial, Helvetica, sans-serif; 
			font-size:12px; 
			margin:0; 
			} 
	#main { 
			height:1800px; 
			padding-top:90px; 
			text-align:center; 
			} 
	#fullbg { 
			background-color:gray; 
			left:0; 
			opacity:0.5; 
			position:absolute; 
			top:0; 
			z-index:3; 
			filter:alpha(opacity=50); 
			-moz-opacity:0.5; 
			-khtml-opacity:0.5; 
			} 
	#dialog { 
			background-color:#fff; 
			border:5px solid rgba(0,0,0, 0.4); 
			height:400px; 
			left:50%; 
			margin:-200px 0 0 -200px; 
			padding:1px; 
			position:fixed !important; /* 浮动对话框 */ 
			position:absolute; 
			top:50%; 
			width:400px; 
			z-index:5; 
			border-radius:5px; 
			display:none; 
			} 
	#dialog p { 
			margin:0 0 12px; 
			height:24px; 
			line-height:24px; 
			background:#CCCCCC; 
			} 
	#dialog p.close { 
			text-align:right; 
			padding-right:10px; 
			} 
	#dialog p.close a { 
			color:#fff; 
			text-decoration:none; 
			} 
	
	</style>
  </head>
  
  <body>
	 	<div id="main"><a href="javascript:showBg();">点击这里查看效果</a> 
			<div id="fullbg"></div> 
				<div id="dialog"> 
					<p class="close"><a href="#" onclick="closeBg();">关闭</a></p> 
					<div>正在加载，请稍后....</div> 
				</div> 
		</div> 
	<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript"> 
	//显示灰色 jQuery 遮罩层 
	function showBg() { 
		var bh = $("body").height(); 
		var bw = $("body").width(); 
		$("#fullbg").css({ 
		height:bh, 
		width:bw, 
		display:"block" 
		}); 
		$("#dialog").show(); 
	} 
	//关闭灰色 jQuery 遮罩 
	function closeBg() { 
		$("#fullbg,#dialog").hide(); 
		} 
	</script>
  </body>
</html>