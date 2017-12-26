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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/jquery.toast.min.css"/>
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>上海建联管理系统</title>
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="">上海建联管理系统</a>  <span class="logo navbar-slogan f-l mr-10 hidden-xs"></span> <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
			<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
				<ul class="cl"> 
					<li><a onclick="dian()"/>${user.NAME}</li>
					<li><a href="javascript:;" onclick="editPWD('修改密码','<%=basePath%>user/toEditPWD.do?USERNAME=${user.USERNAME}','','510')">修改密码</a></li>   
					<li><a id="logout" onclick="logout()" data-title="退出登录">退出登录</a></li> 
					<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
							<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
							<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
							<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
							<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
							<li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>
	</div>
</header>
  <%-- <aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
	    <dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe616; </i>媒体管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul> 
				    <li><a _href="<%=basePath%>Press" data-title="栏目管理">栏目管理</a></li>
					<li><a _href="<%=basePath%>Rotation" data-title="图片轮换管理">图片轮换管理</a></li>
					<li><a _href="<%=basePath%>Media" data-title="新闻管理">新闻管理</a></li>
				</ul>
			</dd>
		</dl> 
		<dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe616; </i>项目管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul> 
				    <li><a _href="<%=basePath%>project" data-title="项目管理">项目管理</a></li>
					<li><a _href="<%=basePath%>project/toBidList" data-title="中标管理">中标管理</a></li>
					<li><a _href="<%=basePath%>proSche" data-title="进度跟踪">进度跟踪</a></li>
				</ul>
			</dd>
		</dl>  
		<dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe616; </i>企业管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul> 
				    <li><a _href="<%=basePath%>tbcompany" data-title="企业管理">企业管理</a></li>
					<li><a _href="<%=basePath%>tbuser" data-title="会员管理">会员管理</a></li>
					<li><a _href="<%=basePath%>Certifi/querycertifi" data-title="实名认证管理">实名认证管理</a></li>
					<li><a _href="<%=basePath%>Points/querypoint" data-title="积分管理">积分管理</a></li>
					<li><a _href="<%=basePath%>pay" data-title="支付管理">支付管理</a></li> 
				</ul>
			</dd>
	   </dl>  
	   <dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe616; </i>活动管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul> 
				    <li><a _href="<%=basePath%>activity" data-title="活动管理">活动管理</a></li> 
				</ul>
			</dd>
		</dl> 
		  <dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe616; </i>城市建联管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul> 
				    <li><a _href="<%=basePath%>clan" data-title="城市建联管理">城市建联管理</a></li> 
				</ul>
			</dd>
		</dl>
		  <dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe616; </i>夜猫管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				 <ul> 
				    <li><a _href="<%=basePath%>project" data-title="夜猫管理">夜猫管理</a></li> 
				</ul> 
			</dd>
		</dl>
		<dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe616; </i>俱乐部管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul> 
				    <li><a _href="<%=basePath%>club" data-title="俱乐部管理">俱乐部管理</a></li> 
				</ul>
			</dd>
		</dl>
		<dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe616; </i>报表管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul> 
				    <li><a _href="<%=basePath%>project" data-title="俱乐部管理">俱乐部管理</a></li> 
				</ul> 
			</dd>
		</dl> 
		<dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe62e; </i>系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd> 
			        <li><a  _href="<%=basePath%>user" data-title="用户管理">用户管理</a></li>  
					<li><a  _href="<%=basePath%>role" data-title="角色管理">角色管理</a></li> 
					<li><a  _href="<%=basePath%>dictionaries" data-title="字典管理">字典管理</a></li>
					<!-- <li><a  href="javascript:void(0)" onclick="">参数管理</a></li> -->
					<li><a  _href="<%=basePath%>operLog" data-title="日志管理">日志管理</a></li> 
					<li><a _href="<%=basePath%>menu" data-title="菜单管理">菜单管理</a></li>
					<li><a  _href="<%=basePath%>contractModel" data-title="合同模板管理">合同模板管理</a></li>
					<li><a  href="javascript:void(0)" onclick="">滚动大图管理</a></li>
					<li><a  href="javascript:void(0)" onclick="">权限管理</a></li>   
				</ul>
			</dd>
		</dl> 
	</div> 
</aside>  --%>
 <aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2"> 
		<c:forEach items="${menuList}" var="menu">
			<c:if test="${menu.hasMenu}">
				<dl id="lm${menu.MENU_ID }">
					<dt><i class="Hui-iconfont">&#xe616; </i>${menu.MENU_NAME }<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
					<span id="msg${menu.MENU_ID }" style="display:none;font-size:12px;color:white;border-radius:100px;background-color:red;padding:0px 5px;"></span></dt>
					<dd>
						<ul class="submenu">
						    <c:forEach items="${menu.subMenu}" var="sub">
								<c:if test="${sub.hasMenu}">
									<c:choose>
										<c:when test="${not empty sub.MENU_URL}"> 
										    <li id="z${sub.MENU_ID }">
										        <a _href="<%=basePath%>${sub.MENU_URL }" data-title="${sub.MENU_NAME }">${sub.MENU_NAME }
										        	<span id="msg${sub.MENU_ID }" style="display:none;font-size:12px;color:white;border-radius:100px;background-color:red;padding:0px 5px;"></span>
										        </a>
										    </li>
										</c:when>
										<c:otherwise>
											<li>
												<a href="javascript:void(0);"><i class="icon-double-angle-right"></i>${sub.MENU_NAME }</a>
											</li>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach> 	 
						</ul>
					</dd>
				</dl>
			</c:if>
		</c:forEach>  
	</div> 
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span title="我的工作台" data-href="">我的工作台</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div> 
			<iframe scrolling="yes" frameborder="0"></iframe>
		</div>
	</div>
</section> 
<script type="text/javascript" src="<%=basePath%>H-ui/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>H-ui/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="<%=basePath%>H-ui/static/h-ui.admin/js/H-ui.admin.js"></script> 
<script type="text/javascript" src="<%=basePath%>js/jquery.toast.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/im/NIM_Web_NIM_v4.3.0.js"></script>
<script type="text/javascript" src="<%=basePath%>js/im/im-web.js"></script>
<script type="text/javascript"> 
//网易登录
var user = {"ID":"${user.ID}","WYTOKEN":"${user.WYTOKEN}"};
initIM(user);

function logout(){
    //网易登出
    logoutIM();
    setTimeout(function(){
       window.location.href = "<%=basePath%>logout.do";
    },50)
}

function afterReceiveMsg(msg){  
    $.toast({
	  text : "<div style='display: -webkit-flex'><div style='width: 20%;'><img style='height: 45px;width: 45px;' src='img/64.png'/></div><div style='width: 80%;'>"+msg.text+"</div></div>", 
	  textColor : '#eee',
	  showHideTransition : 'slide',
	  stack : 1,
	  position : 'bottom-right'
	});
	showLogin(); 
} 

function showLogin(){
	$.ajax({
        url : "<%=basePath%>user/msgCounts.do", 
        type : 'post',
        dataType : 'json',
        async : false,
        success : function(result) {
        	//项目模块
        	if(result.jecounts>0){
        		$("#msg3").show(); 
            	$("#msg3").html(result.jecounts); 
            	var counts=result.jecounts+result.xmdfcounts;
            	$("#msg1").show();
            	$("#msg1").html(counts);
        	}else{
        		$("#msg3").hide();
        		$("#msg1").hide();
        	}
        	if(result.xmdfcounts>0){
        		$("#msg45").show(); 
            	$("#msg45").html(result.xmdfcounts);  
        	}else{
        		$("#msg45").hide(); 
        	}
        	//会员模块
        	if(result.rhscounts>0){
        		$("#msg39").show(); 
            	$("#msg39").html(result.rhscounts); 
            	var counts=result.rhscounts+result.zjscounts;
            	$("#msg9").show();
            	$("#msg9").html(counts);
        	}else{
        		$("#msg39").hide();
        		$("#msg9").hide();
        	}
        	
        	if(result.zjscounts>0){
        		$("#msg12").show(); 
            	$("#msg12").html(result.zjscounts);  
        	}else{
        		$("#msg12").hide(); 
        	}
        	//活动模块
        	if(result.hdtjscounts>0){
        		$("#msg16").show(); 
            	$("#msg16").html(result.hdtjscounts); 
            	var counts=result.hdtjscounts;
            	$("#msg15").show();
            	$("#msg15").html(counts);
        	}else{
        		$("#msg16").hide();
        		$("#msg15").hide();
        	}
        	//城市建联
        	if(result.csjlscounts>0){
        		$("#msg18").show(); 
            	$("#msg18").html(result.csjlscounts); 
            	var counts=result.hdtjscounts+result.cshhrcounts;
            	$("#msg17").show();
            	$("#msg17").html(counts);
        	}else{
        		$("#msg17").hide();
        		$("#msg18").hide();
        	}
        	
        	if(result.cshhrcounts>0){
        		$("#msg43").show(); 
            	$("#msg43").html(result.cshhrcounts);  
        	}else{
        		$("#msg43").hide();
        	}
        	
        	//俱乐部场地预订
        	if(result.cdydcounts>0){
        		$("#msg20").show(); 
            	$("#msg20").html(result.cdydcounts); 
            	$("#msg19").show();
            	$("#msg19").html(result.cdydcounts);
        	}else{
        		$("#msg20").hide();
        		$("#msg19").hide();
        	}
        	
        	//反馈模块
        	if(result.fkcounts>0){
        		$("#msg29").show(); 
            	$("#msg29").html(result.fkcounts); 
            	$("#msg30").show();
            	$("#msg30").html(result.fkcounts);
        	}else{
        		$("#msg29").hide();
        		$("#msg30").hide();
        	}
        	//ppp模版
        	if(result.pppcycounts>0){
        		$("#msg52").show(); 
            	$("#msg52").html(result.pppcycounts); 
            	$("#msg50").show();
            	$("#msg50").html(result.pppcycounts);
        	}else{
        		$("#msg50").hide();
        		$("#msg52").hide();
        	}
        	/* if(result.club_counts>0){
        		$("#msg20").show(); 
            	$("#msg20").html(result.club_counts); 
            	$("#msg19").show(); 
            	$("#msg19").html(result.club_counts); 
        	}else{
        		$("#msg20").hide();
        		$("#msg19").hide();
        	}
        	if(result.activity_counts>0){
        		$("#msg16").show(); 
            	$("#msg16").html(result.activity_counts); 
            	$("#msg15").show(); 
            	$("#msg15").html(result.activity_counts); 
        	}else{
        		$("#msg16").hide();
        		$("#msg15").hide();
        	}
        	if(result.cert_counts>0){
        		$("#msg12").show(); 
            	$("#msg12").html(result.cert_counts); 
            	$("#msg9").show(); 
            	$("#msg9").html(result.cert_counts); 
        	}else{
        		$("#msg12").hide();
        		$("#msg9").hide();
        	}
        	if(result.procheck_counts>0){
        		$("#msg2").show();  
            	$("#msg2").html(result.procheck_counts); 
            	var counts=result.procheck_counts+result.prodis_counts;
            	$("#msg1").show();
            	$("#msg1").html(counts);
        	}else{
        		$("#msg2").hide();
        		$("#msg1").hide();
        	}
        	if(result.prodis_counts>0){
        		$("#msg3").show(); 
            	$("#msg3").html(result.prodis_counts); 
            	var counts=result.procheck_counts+result.prodis_counts;
            	$("#msg1").show();
            	$("#msg1").html(counts);
        	}else{
        		$("#msg3").hide();
        		$("#msg1").hide();
        	} */
        	 
        },
        error : function(msg) {
        }
    });
}
showLogin();
//setInterval("showLogin()","600000");//10分钟运行一次600000
/*修改用户密码*/
function editPWD(title,url,w,h){
	layer_show(title,url,w,h);
}

var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s)})();
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html> 