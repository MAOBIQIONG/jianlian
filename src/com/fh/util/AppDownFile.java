package com.fh.util;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.app.AppController;

@Controller
@RequestMapping({ "/appdownfile" })
public class AppDownFile extends AppController{
	//图片下载
	@RequestMapping({ "/downFile" })
	@ResponseBody
	public void downFile(HttpServletResponse response) throws Exception {	 
		PageData pa = getPageData(); 
		FileDownload.fileDownload(response,pa.getString("FILE_PATH"),pa.getString("FILE_NAME"));
	}
	
	
}
