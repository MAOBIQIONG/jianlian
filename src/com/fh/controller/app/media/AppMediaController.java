package com.fh.controller.app.media;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;  

import org.python.antlr.PythonParser.pass_stmt_return;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.app.AppController;
import com.fh.entity.Page;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.Appcertificates.AppCertificatesService;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.app.collection.AppCollectionService;
import com.fh.service.app.company.AppcompanyService;
import com.fh.service.app.media.AppCommentsService;
import com.fh.service.app.media.AppMediaService;
import com.fh.service.app.media.AppRotationService;
import com.fh.util.AppUtil;
import com.fh.util.FileUpload;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

@Controller
@RequestMapping({ "/appmedia" })
public class AppMediaController extends AppController{

	@Resource(name = "appmediaService")
	private AppMediaService appmediaService;
		
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	
	@Resource(name="appRotationservice")
	private AppRotationService appRotationservice;
	
	@Resource(name="appCommentsService")
	private AppCommentsService appCommService;
	
	@Resource(name = "appCollectionService")
	private AppCollectionService appCollectionService;
	
	//媒体分页
	@RequestMapping({ "/queryPageByParam" })
	@ResponseBody
	public PageData queryPageByParam() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());  
		int PageCurrent=Integer.parseInt(pa.getString("PageCurrent"));//当前页
		int start=PageCurrent-1;
		int pageSize=2;//每页2条,自定义
		pa.put("start",start);
		pa.put("end",pageSize);
		Page page=new Page(); 
		page.setPd(pa);
		List<PageData> list=new ArrayList<PageData>();
		list=this.appmediaService.queryPageByParam(page);
		
//		PageData fenzong=this.appmediaService.queryPageByParamzong(page);
//		PageData femye=new PageData();
//		femye.put("fenz", fenzong);
//		femye.put("list",list);
		_result = AppUtil.ss(list, "01", "成功","true", null);
		System.out.println(_result);
		return _result;
	}
	
	//媒体分类获取 
	@RequestMapping({ "/queryByfenlei" })
	@ResponseBody
	public PageData queryByfenlei() throws Exception {
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData()); 
		List<PageData> list=new ArrayList<PageData>();
		list=this.appmediaService.queryByfenlei(pa); 
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result;
	}
	
	//媒体首页
	@RequestMapping({ "/queryByshouye" })
	@ResponseBody
	public PageData queryByshouye() throws Exception {
		long t1 = System.currentTimeMillis();
		
		PageData _result=new PageData();
		PageData pa =getPageData().getObject("pa");
		List<PageData> list=new ArrayList<PageData>();
		list=this.appmediaService.queryByfenlei(pa); 
		pa.put("LOCATION_NO","03");
		List<PageData> piclist=this.appRotationservice.queryByColno(pa); 
		Object curpage=pa.get("currentPage");
		int currentPage=Integer.parseInt(curpage+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数 
		int start=(currentPage-1)*pageSize;
		//pa.put("start",currentPage-1);
		pa.put("start",start); 
		pa.put("pageSize",pageSize); 
		
		List<PageData> tuijian=new ArrayList<PageData>();
		tuijian=this.appmediaService.queryByshouye(pa);
		List<PageData> zhiding=this.appmediaService.queryShouYeTopNews(pa);
		int LC=zhiding.size();
//		PageData fenzong=this.appmediaService.queryPageByParamzong(pa);
		PageData fenzong=this.appmediaService.queryByshouyezong(pa);
		PageData shou=new PageData();
		shou.put("list", list);
		shou.put("tuijian", tuijian);
		shou.put("zhiding", zhiding);
		shou.put("piclist", piclist);
		shou.put("fenzong", fenzong);
		shou.put("shouyes", LC);
		_result = AppUtil.ss(shou, "01", "成功","true", null);
		
		long t2 = System.currentTimeMillis();
		System.out.println("/------------------华丽的分割线---------------------/");
		System.out.println("媒体首页获取数据时间（毫秒）："+(t2-t1));
		System.out.println("/------------------华丽的分割线---------------------/");
		return _result;
	}
	
	//媒体分类获取 
	@RequestMapping({ "/queryByxinwen" })
	@ResponseBody
	public PageData queryByxinwen() throws Exception {
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData());

		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		/*int totalSize=currentPage*pageSize; 
		pa.put("totalSize",totalSize);*/ 
		int start=(currentPage-1)*pageSize;
		//pa.put("start",currentPage-1);
		pa.put("start",start); 
		pa.put("pageSize",pageSize); 
		List<PageData> list=new ArrayList<PageData>();
		List<PageData> zhidin=new ArrayList<PageData>();
		int xinwzongs;
		PageData xinzong=new PageData();
		 String column=pa.get("COLUMN_ID")+"";
		if("0".equals(column)){
			list=this.appmediaService.queryByshouye(pa);
			xinwzongs=list.size();
			xinzong=this.appmediaService.queryByxinwenzong(pa);
			zhidin=this.appmediaService.queryShouYeTopNews(pa);
		}else{
			list=this.appmediaService.queryByxinwen(pa);
			xinwzongs=list.size();
			xinzong=this.appmediaService.queryByxinwenzong(pa);
			zhidin=this.appmediaService.queryTopNews(pa);
		} 
		PageData querylist=new PageData();
		querylist.put("list", list);
		querylist.put("zhidin", zhidin);
		querylist.put("xinzong", xinzong);
		System.out.println(xinzong);
		querylist.put("xinwzongs", xinwzongs);
		_result = AppUtil.ss(querylist, "01", "成功","true", null);
		return _result;
	}
	
	//获取媒体首页新闻大图
	@RequestMapping({ "/queryMediaByColno" })
	@ResponseBody
	public PageData queryMediaByColno()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		pa.put("LOCATION_NO","03");
		List<PageData> list=this.appRotationservice.queryByColno(pa); 
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result; 
	}
	
	//媒体新闻详细信息接口
	@RequestMapping({ "/queryMediaById" })
	@ResponseBody
	public PageData queryMediaById()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		PageData data=this.appmediaService.queryById(pa);//媒体详细信息
		List<PageData> pinlist=appCommService.queryBypinluns(pa);
		pa.put("USER_ID", pa.get("userid"));
		pa.put("OBJECT_ID", pa.getString("MEDIA_ID"));
		pa.put("TYPE", "JL_APP_MEDIA");
		PageData shouc=this.appCollectionService.checkCollection(pa); 
		for (PageData pageData : pinlist) { 
			List<PageData> huilist=appCommService.queryByhuifus(pageData);
			for (PageData huifu : huilist) { 
				huifu.put("HDATE",huifu.get("COMMENT_DATE")+""); 
			}
			pageData.put("huilist",huilist);
			pageData.put("PDATE",pageData.get("COMMENT_DATE")+""); 
		} 
		Map maps=new HashMap();
		maps.put("data",data);
		maps.put("pinlist",pinlist);
		maps.put("shouc",shouc);
		_result = AppUtil.ss(maps, "01", "成功","true", null);
		return _result; 
	}
	
	//媒体分享
	@RequestMapping({ "/queryfenxiangById" })
	public ModelAndView queryfenxiangById()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = getPageData();
		PageData data=this.appmediaService.queryById(pd);//媒体详细信息
		mv.setViewName("app/fenxiang");
		mv.addObject("fenData", data); 
		return mv;
	}
	
	//媒体新闻新增评论接口
	@RequestMapping({ "/addComments" })
	@ResponseBody
	public PageData addComments()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		pa.put("ID",get32UUID());
		pa.put("COMMENT_DATE", new Date());
		String CONTENT=pa.getString("CONTENT");
		if ((CONTENT != null) && (!"".equals(CONTENT))) { 
			CONTENT=new String(CONTENT.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("CONTENT",CONTENT);
		}
		String answerId=pa.getString("ANSWER_ID");
		if(answerId==null||"".equals(answerId)){//发表评论
			PageData pinji=this.appCommService.quseronpin(pa);
			if(pinji!=null){
				Object hj= pinji.get("counts");
				pa.put("IDENTIFICATION",Integer.parseInt(hj+"")+1);
			}else{
				pa.put("IDENTIFICATION",1);
			} 
		}else{//进行回复，页面传值
			 
		} 
		Object obj=appCommService.save(pa);
		if(Integer.valueOf(obj.toString()) >= 1){
			PageData data=new PageData();
			data.put("COMMENT_COUNT",Integer.valueOf(obj.toString()));
			data.put("MEDIA_ID",pa.getString("MEDIA_ID"));
			Object ob=appmediaService.updateCounts(data);
			if(Integer.valueOf(ob.toString()) >= 1){
				_result = AppUtil.ss(null, "01", "成功","true",null);
			}else{
				_result = AppUtil.ss(null, "90", "失败","false",null);
			}
		}else{
			 _result = AppUtil.ss(null, "90", "失败","false",null);
		} 
		return _result; 
	}
	
	//媒体新闻新增分享接口
	@RequestMapping({ "/addShare" })
	@ResponseBody
	public PageData addShare()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		pa.put("ID",get32UUID());
		pa.put("SHARE_DATE", new Date());
		Object obj=appCommService.addShare(pa); 
		if(Integer.valueOf(obj.toString()) >= 1){
			PageData data=new PageData();
			data.put("SHARE_COUNT",Integer.valueOf(obj.toString()));
			data.put("MEDIA_ID",pa.getString("MEDIA_ID"));
			Object ob=appmediaService.updateCounts(data); 
			if(Integer.valueOf(ob.toString()) >= 1){
				_result = AppUtil.ss(null, "01", "成功","true",null);
			}else{
				_result = AppUtil.ss(null, "90", "失败","false",null);
			}
		}else{
			 _result = AppUtil.ss(null, "90", "失败","false",null);
		} 
		return _result; 
	} 
	
	//媒体新闻评论列表获取接口
	@RequestMapping({ "/queryComByMediaId" })
	@ResponseBody
	public PageData queryComByMediaId()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());   
		List<PageData> list=appCommService.queryComByMediaId(pa); 
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result; 
	} 
}
