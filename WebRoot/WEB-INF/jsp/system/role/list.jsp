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
		<title>权限管理</title>
		<style type="text/css">
			/*链接*/
			.btn-link{color:#0e90d2;cursor:pointer;border-color:transparent;background-color:aliceblue;}
			.a-link:hover{text-decoration:none;}
		</style>
	</head>
	
	<body class="pos-r" style=""> 
		<div>
			<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>角色管理 <span class="c-gray en">&gt;</span>角色管理
				<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
			</nav>  
			<%-- <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span><span class="c-gray en">&gt;</span>权限管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"  href="javascript:void(0)" onclick="roleguanli('<%=basePath%>role.do')" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav> --%>
			<div class="bjui-pageContent">
			    <form action="ajaxDone1.html" id="j_form_form" class="pageForm" data-toggle="validate">
			        <div style="margin:15px auto 0; width:100%;">
			            <fieldset>
			                <legend>角色</legend>  
			                <div class="cl pd-5"> 
							   <%--  <c:if test="${QX.add == 1 }">  --%>
							      <span class="l"> 
							        <a class="btn btn-primary radius" data-title="新增" onclick="role_edit('新增','<%=basePath%>role/toAdd.do?parent_id=0','','550')" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i>新增</a>
								  </span>
							<%--  </c:if>  --%>
							</div> 
							<div id="tab_demo" class="HuiTab">     
							  <div class="tabBar cl"  style="border-bottom:1px solid lightgrey;">  
							  <ul class="nav nav-tabs" role="tablist" >
			                    <c:choose>
									<c:when test="${not empty roleList}">
										<c:forEach items="${roleList}" var="role" varStatus="vs">
											<li class="btn btn-link" style="float: left;margin-right:2px;">
											     <a href="javascript:void(0)" onclick="roleList('<%=basePath%>role/listZ.do?ROLE_ID=${role.ROLE_ID }')" >${role.ROLE_NAME}</a>   
											</li>
										</c:forEach>
									</c:when>
									<c:otherwise>
										
									</c:otherwise>
								</c:choose>
			                </ul> </div> </div>
			                <div class="roleList">
			               <div class="cl pd-5 bg-1 bk-gray mt-20"> 
							<%--   <c:if test="${QX.edit == 1 }">  --%>
							      <span class="l">
							        <a href="javascript:void(0)" data-title="添加" onclick="role_edit('新增','<%=basePath%>role/toAdd.do?parent_id=${pd.ROLE_ID}','','550')" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe600;</i>添加</a>
									<a href="javascript:void(0)" data-title="修改组名称" onclick="role_edit('新增','<%=basePath%>role/toEdit.do?ROLE_ID=${pd.ROLE_ID}','','550')"  class="btn btn-primary"><i class="Hui-iconfont">&#xe600;</i>修改组名称</a>
									<a href="javascript:void(0)" data-title="删除" onclick="role_del('<%=basePath%>role/delete.do?ROLE_ID=${pd.ROLE_ID}')"  class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i>删除</a> 
									<a href="javascript:void(0)" data-title="组菜单权限" onclick="role_edit('组菜单权限','<%=basePath%>role/auth.do?ROLE_ID=${pd.ROLE_ID}','','550')"  class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>组菜单权限</a> 
								  </span> 
								<%-- </c:if> --%>
							</div>     
						    <div class="mt-20">  
							   <table class="table table-border table-bordered table-bg table-hover table-sort">
									<thead>
										<tr class="text-c"> 
										    <th width="200px;">序号</th>
											<th width="200px;">角色</th>
											<th width="200px;">增</th>
											<th width="200px;">删</th>
											<th width="200px;">改</th>
											<th width="200px;">查</th> 
											<th width="200px;">操作</th> 
										</tr>
									</thead>
									<tbody>
								<c:choose>
								<c:when test="${not empty roleList_z}">
									<c:forEach items="${roleList_z}" var="var" varStatus="vs">
									<%-- <c:forEach items="${kefuqxlist}" var="varK" varStatus="vsK">
										<c:if test="${var.QX_ID == varK.GL_ID }">
											<c:set value="${varK.FX_QX }" var="fx_qx"></c:set>
											<c:set value="${varK.FW_QX }" var="fw_qx"></c:set>
											<c:set value="${varK.QX1 }" var="qx1"></c:set>
											<c:set value="${varK.QX2 }" var="qx2"></c:set>
										</c:if>
									</c:forEach>
									<c:forEach items="${gysqxlist}" var="varG" varStatus="vsG">
										<c:if test="${var.QX_ID == varG.U_ID }">
											<c:set value="${varG.C1 }" var="c1"></c:set>
											<c:set value="${varG.C2 }" var="c2"></c:set>
											<c:set value="${varG.Q1 }" var="q1"></c:set>
											<c:set value="${varG.Q2 }" var="q2"></c:set>
										</c:if>
									</c:forEach> --%>
										<tr> 
											<td>${vs.index+1}</td>
											<td>${var.ROLE_NAME }</td>
											<td style="width:30px;"><a onclick="role_edit('授权新增权限','<%=basePath%>role/button.do?msg=add_qx&ROLE_ID=${var.ROLE_ID }','','550')" class="btn btn-orange" title="分配新增权限">分配新增权限</a></td>
											<td style="width:30px;"><a onclick="role_edit('授权删除权限','<%=basePath%>role/button.do?msg=del_qx&ROLE_ID=${var.ROLE_ID }','','550')" class="btn btn-orange" title="分配删除权限">分配删除权限</a></td>
											<td style="width:30px;"><a onclick="role_edit('授权修改权限','<%=basePath%>role/button.do?msg=edit_qx&ROLE_ID=${var.ROLE_ID }','','550')" class="btn btn-orange" title="分配修改权限">分配修改权限</a></td>
											<td style="width:30px;"><a onclick="role_edit('授权查看权限','<%=basePath%>role/button.do?msg=cha_qx&ROLE_ID=${var.ROLE_ID }','','550')" class="btn btn-orange" title="分配查看权限">分配查看权限</a></td>
											<td><a onclick="role_edit('修改','<%=basePath%>role/toEdit.do?ROLE_ID=${var.ROLE_ID }','','550')"
												href="javascript:void(0)"
												class="btn btn-green" data-toggle="dialog" data-id="role-edit" id="edit"
												data-reload-warn="本页已有打开的内容，确定将刷新本页内容，是否继续？" data-title="编辑-招标公告">编辑</a>
												<a href="javascript:void(0)" onclick="role_del('<%=basePath%>role/delete.do?ROLE_ID=${var.ROLE_ID}')"
												class="btn btn-red" data-toggle="doajax" 
												data-confirm-msg="确定要删除该行信息吗？">删除</a></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="30" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
									</tbody>
							    </table> 
							</div>
							</div> 
			            </fieldset>
			        </div>
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
        <script type="text/javascript"> 
         /*编辑*/
		 function role_edit(title,url,w,h){ 
			layer_show(title,url,w,h);
         } 
         
		 function roleList(url){  
		   $.ajax({  
		         type: "POST",
		         url:url, 
		         async: false,
		         error: function(request) {
		             alert("Connection error");
		         },
		         success: function(data) {
		             $(".roleList").html(data); 
		         }
		     });
		}
		
		function role_del(url){
				 layer.confirm('确认要删除该数据吗？',function(index){ 
					//此处请求后台程序，下方是成功后的前台处理…… 
					$.ajax({
		                url : url, 
		                type : 'post',
		                dataType : 'json',
		                async : false,
		                success : function(data) { 
		                	if(data.statusCode==200){ 
			                }else{
			                    alert(data.result);
			                } 
		                    $(".layui-layer-setwin .layui-layer-close1").click(); 
	                		window.location.reload();  
		                },
		                error : function(msg) {
		                }
		            });
				 }); 
			}
		</script> 
	</body>		
</html>