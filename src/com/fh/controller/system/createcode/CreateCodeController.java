 package com.fh.controller.system.createcode;
 
 import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.util.DelAllFile;
import com.fh.util.FileDownload;
import com.fh.util.FileZip;
import com.fh.util.Freemarker;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
 
 @Controller
 @RequestMapping({"/createCode"})
 public class CreateCodeController extends BaseController
 {
   @RequestMapping({"/proCode"})
   public void proCode(HttpServletResponse response)
     throws Exception
   {
     PageData pd = new PageData();
     pd = getPageData();
 
     String packageName = pd.getString("packageName");
     String objectName = pd.getString("objectName");
     String tabletop = pd.getString("tabletop");
     tabletop = tabletop == null ? "" : tabletop.toUpperCase();
     String zindext = pd.getString("zindex");
     int zindex = 0;
     if ((zindext != null) && (!"".equals(zindext))) {
       zindex = Integer.parseInt(zindext);
     }
     List fieldList = new ArrayList();
     for (int i = 0; i < zindex; i++) {
       fieldList.add(pd.getString("field" + i).split("-"));
     }
 
     Map root = new HashMap();
     root.put("fieldList", fieldList);
     root.put("packageName", packageName);
     root.put("objectName", objectName);
     root.put("objectNameLower", objectName.toLowerCase());
     root.put("objectNameUpper", objectName.toUpperCase());
     root.put("tabletop", tabletop);
     root.put("nowDate", new Date());
 
     DelAllFile.delFolder(PathUtil.getClasspath() + "admin/ftl");
 
     String filePath = "admin/ftl/code/";
     String ftlPath = "createCode";
 
     Freemarker.printFile("controllerTemplate.ftl", root, "controller/" + packageName + "/" + objectName.toLowerCase() + "/" + objectName + "Controller.java", filePath, ftlPath);
 
     Freemarker.printFile("serviceTemplate.ftl", root, "service/" + packageName + "/" + objectName.toLowerCase() + "/" + objectName + "Service.java", filePath, ftlPath);
 
     Freemarker.printFile("mapperMysqlTemplate.ftl", root, "mybatis_mysql/" + packageName + "/" + objectName + "Mapper.xml", filePath, ftlPath);
     Freemarker.printFile("mapperOracleTemplate.ftl", root, "mybatis_oracle/" + packageName + "/" + objectName + "Mapper.xml", filePath, ftlPath);
 
     Freemarker.printFile("mysql_SQL_Template.ftl", root, "mysql数据库脚本/" + tabletop + objectName.toUpperCase() + ".sql", filePath, ftlPath);
     Freemarker.printFile("oracle_SQL_Template.ftl", root, "oracle数据库脚本/" + tabletop + objectName.toUpperCase() + ".sql", filePath, ftlPath);
 
     Freemarker.printFile("jsp_list_Template.ftl", root, "jsp/" + packageName + "/" + objectName.toLowerCase() + "/" + objectName.toLowerCase() + "_list.jsp", filePath, ftlPath);
     Freemarker.printFile("jsp_edit_Template.ftl", root, "jsp/" + packageName + "/" + objectName.toLowerCase() + "/" + objectName.toLowerCase() + "_edit.jsp", filePath, ftlPath);
 
     Freemarker.printFile("docTemplate.ftl", root, "说明.doc", filePath, ftlPath);
 
     FileZip.zip(PathUtil.getClasspath() + "admin/ftl/code", PathUtil.getClasspath() + "admin/ftl/code.zip");
 
     FileDownload.fileDownload(response, PathUtil.getClasspath() + "admin/ftl/code.zip", "code.zip");
   }
   
   @RequestMapping({ "/toConfig" })
	public ModelAndView toConfig(String id) throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("createcode/createcode");
		return mv;
	}
 }

/* Location:           F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\
 * Qualified Name:     com.fh.controller.system.createcode.CreateCodeController
 * JD-Core Version:    0.6.2
 */