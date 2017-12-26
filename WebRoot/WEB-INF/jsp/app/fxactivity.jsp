<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>上海建联</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		.conetent{
			margin-top:55px;  
		}
		.conetent img{
	    	max-width: 100%; 
	    }
	    .ewm{
		width: 100%;
		}
		.ewdiv{
			width:300px;
			height: 300px;
			margin: 0px auto; 
		}
		.divimg{
			width: 100%;
		}
		
	</style> 
  </head>
  
  <body>
  	<div id="ptll" style="position: fixed;top: 0px;right:0;width: 100%;display: none;">
  		<div style="display: -webkit-flex;background-color: #000000"> 
	  		<div style="width: 20%;height: 50px;text-align: center;padding-top: 1%">
	  			<img style="width: 45px;height: 45px;" src="http://jianlian.shanghai-cu.com/jianlian/qk/images/64.png" />
	  		</div> 
	  		<div style="width: 50%;color: #ffffff">
	  		    <div>上海建联</div>
	  		    <div>联合铸就未来</div>
	  		</div>
	  		<div style="width: 30%;background-color: red;color: #ffffff;text-align: center;padding-top: 4%">
			<a style="text-decoration: none;color: #ffffff" href="jianlian://abc">立即打开<a>
	  		</div>
  		</div>
  	</div>
  	<div id="wxll" style="width: 100%;display: none;height: 100px">
  		<img style="width: 100%;height: 100%" src="http://jianlian.shanghai-cu.com/jianlian/qk/images/fxshjl.png"/>
  	</div>
    <div class="conetent"> 
    	<c:choose>
			<c:when test="${fn:contains(fenData.YY_IMG,'http://')}"> 
			 	<img src="${fenData.YY_IMG}" />
			</c:when>
			<c:otherwise>
				<img src="${apppicPath}${fenData.YY_IMG}">	
			</c:otherwise>
		</c:choose>
    </div>
    <div class="ewm">
		<div class="ewdiv">
			<img class="divimg" src="http://jianlian.shanghai-cu.com/jianlian/qk/images/ewm.png"/>
		</div>
		<div class="ewdiv">
			<img class="divimg" src="http://jianlian.shanghai-cu.com/jianlian/qk/images/4.png"/>
		</div>
	</div>
  </body>
  <script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script> 
   <script type="text/javascript">
	var browser={
	    versions:function(){
	        var u = navigator.userAgent, app = navigator.appVersion;
	        return {
	            trident: u.indexOf('Trident') > -1, //IE内核
	            presto: u.indexOf('Presto') > -1, //opera内核
	            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
	            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐内核
	            mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
	            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
	            android: u.indexOf('Android') > -1 || u.indexOf('Adr') > -1, //android终端
	            iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
	            iPad: u.indexOf('iPad') > -1, //是否iPad
	            webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
	            weixin: u.indexOf('MicroMessenger') > -1, //是否微信 （2015-01-22新增）
	            qq: u.match(/\sQQ/i) == " qq" //是否QQ
	        };
	    }(),
	    language:(navigator.browserLanguage || navigator.language).toLowerCase()
	} 
			isWeiXin();
			function isWeiXin() { 
				//判斷是不是移動端
				if (browser.versions.mobile) {
					//判斷是不是ios
					if(browser.versions.ios){
						//判斷是不是微信
						if(browser.versions.weixin){
							$("#wxll").css('display','block'); 
						}else{
							$("#ptll").css('display','block'); 
						}
					}else{
						//判斷是不是微信
						if(browser.versions.weixin){
							$("#wxll").css('display','block'); 
						}else{
							$("#ptll").css('display','block'); 
						}
					}
				} else {
					return false;
				}
			}
		</script>
</html>
