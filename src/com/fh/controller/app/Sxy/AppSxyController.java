package com.fh.controller.app.Sxy;



import java.util.List;

import javax.annotation.Resource;  

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.app.AppController;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.Sxy.AppSxyService;
import com.fh.util.PageData;
@Controller
@RequestMapping({ "/appSxy" })
public class AppSxyController extends AppController{

	@Resource(name = "appsxyService")
	private AppSxyService appsxyService;
	
	//媒体分享
	@RequestMapping({ "/queryfenxiangById" })
	public ModelAndView queryfenxiangById()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = getPageData();
		PageData data=this.appsxyService.queryById(pd);//媒体详细信息
		List<PageData> jsdata=(List<PageData>) this.appsxyService.queryByjs(pd);//查询教授信息列表
		mv.setViewName("app/Sxydedas");
		mv.addObject("fenData", data); 
		mv.addObject("jsdata", jsdata); 
		mv.addObject("apppicPath", "http://jianlian.shanghai-cu.com/jianlian/uploadImg/img/"); 
		return mv;
	}
	

}
