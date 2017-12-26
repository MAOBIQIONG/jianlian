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

  <div class="cl pd-5 bg-1 bk-gray mt-20">
  <%--  <c:if test="${QX.edit == 1 }"> --%>
      <span class="l">
        <a href="javascript:void(0)" data-title="添加" onclick="role_edit('新增','<%=basePath%>role/toAdd.do?parent_id=${pd.ROLE_ID}','','550')" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe600;</i>添加</a>
		<a href="javascript:void(0)" data-title="修改组名称" onclick="role_edit('新增','<%=basePath%>role/toEdit.do?ROLE_ID=${pd.ROLE_ID}','','550')"  class="btn btn-primary"><i class="Hui-iconfont">&#xe600;</i>修改组名称</a>
		<a href="javascript:void(0)" onclick="role_del('<%=basePath%>role/delete.do?ROLE_ID=${pd.ROLE_ID}')"  class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i>删除</a> 
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
		<%--<c:forEach items="${kefuqxlist}" var="varK" varStatus="vsK">
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
<script>
//按钮权限
function roleButton(ROLE_ID,msg){
	if(msg == 'add_qx'){
		var Title = "授权新增权限";
	}else if(msg == 'del_qx'){
		Title = "授权删除权限";
	}else if(msg == 'edit_qx'){
		Title = "授权修改权限";
	}else if(msg == 'cha_qx'){
		Title = "授权查看权限";
	}
	
	$(this).dialog({id:'addButton', url:'<%=basePath%>role/button.do?ROLE_ID='+ROLE_ID+'&msg='+msg, title:Title});
}
</script>