package com.fh.controller.app.Appcertificates;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.app.AppController;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.Appcertificates.AppCertificatesService;
import com.fh.service.app.company.AppcompanyService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

@Controller
@RequestMapping({ "/appcertificates" })
public class AppCertificatesController extends AppController{

	@Resource(name = "appcertificatesService")
	private AppCertificatesService appcertificatesService;
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	
	@Resource(name="appcompanyService")
	private AppcompanyService appcompanyService;
	
	//我的证件列表获取
	@RequestMapping({ "/querymyCert" })
	@ResponseBody
	public PageData querymyCert() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		List<PageData> list=new ArrayList<PageData>();
		list=this.appcertificatesService.findByUserId(pa);
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result;
	}
	
	//证件类型
	@RequestMapping({ "/queryByPBM" })
	@ResponseBody
	public PageData queryByPBM() throws Exception {
	PageData _result=new PageData();
	List<PageData> list=this.appcertificatesService.queryByPBM("certificatetype"); 
	_result = AppUtil.ss(list, "01", "成功","true", null);
	return _result;
	}
	//所属公司名称
	@RequestMapping({ "/queryBycomname" })
	@ResponseBody
	public PageData queryBycomname() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		PageData computer=appcompanyService.queryByUId(pa);
		PageData sanzhi=this.appcertificatesService.queryBysanzhen(pa);
		PageData min=this.appcertificatesService.queryByminpian(pa);
		PageData comlogo=this.appcertificatesService.queryBymlogo(pa);
		List<PageData> qita1=new ArrayList<PageData>();
		qita1=this.appcertificatesService.queryBymqita1(pa);
		Map<String,Object> suo=new HashMap<String,Object>();
		suo.put("computer",computer);
		suo.put("sanzhi",sanzhi);
		suo.put("min",min);
		suo.put("comlogo",comlogo);
		suo.put("qita1",qita1);
		_result = AppUtil.ss(suo, "01", "成功","true", null);
		return _result;
	}
	
	//我的证件删除
	@RequestMapping({ "/delCertByid" })
	@ResponseBody
	public PageData delCertByid()throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		
		Object obj=	this.appcertificatesService.delCertByid(pa);
		if(Integer.valueOf(obj.toString()) >= 1){
			_result = AppUtil.ss(null, "01", "成功","true",null);
	    }else{
		     _result = AppUtil.ss(null, "92", "非法请求","false",null);
	    } 
		return _result;
	}
	
	//我的证件新增
	@RequestMapping({"/addconcern"})
	@ResponseBody
	public PageData addconcern()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		
		pa.put("STATUS", "01");
		pa.put("DATE",new Date());
		Object obj="";
		PageData computer=appcompanyService.queryByUId(pa);
		pa.put("COMPANY_ID", computer.getString("ID")); 
		String sanid=pa.getString("sanid");
		String minid=pa.getString("minid");
		String logoid=pa.getString("logoid");
		String qiid=pa.getString("qiid");
		String sanimg=pa.getString("THREE_PATH");
		String minimg=pa.getString("MIN_PATH");
		String logoimg=pa.getString("LOGO_PATH");
		String qitaimg=pa.getString("QITA_PATH");
		if(sanimg !=null && !"".equals(sanimg)){
			pa.put("IMG_PATH", sanimg);
			pa.put("CERTIFICATE_NAME", "三证");
			pa.put("CERTIFICATE_TYPE", "1");
			if(sanid!=null&&!"".equals(sanid)&&!"undefined".equals(sanid)){//修改
				pa.put("ID",sanid);
				obj=this.appcertificatesService.edit(pa);
			}else{//新增
				pa.put("ID", this.get32UUID()); 
				obj=this.appcertificatesService.addconcern(pa);
			}
		}
		if(minimg !=null && !"".equals(minimg)){ 
			pa.put("IMG_PATH", minimg);
			pa.put("CERTIFICATE_NAME", "名片");
			pa.put("CERTIFICATE_TYPE", "2");
			if(minid!=null&&!"".equals(minid)&&!"undefined".equals(minid)){//修改
				pa.put("ID",minid);
				obj=this.appcertificatesService.edit(pa);
			}else{//新增
				pa.put("ID", this.get32UUID()); 
				obj=this.appcertificatesService.addconcern(pa);
			} 
		}
		if(logoimg !=null && !"".equals(logoimg)){ 
			pa.put("IMG_PATH", logoimg);
			pa.put("CERTIFICATE_NAME", "logo");
			pa.put("CERTIFICATE_TYPE", "3");
			if(logoid!=null&&!"".equals(logoid)&&!"undefined".equals(logoid)){//修改
				pa.put("ID",logoid);
				obj=this.appcertificatesService.edit(pa);
			}else{//新增
				pa.put("ID", this.get32UUID()); 
				obj=this.appcertificatesService.addconcern(pa);
			} 
		}
		if(qitaimg !=null && !"".equals(qitaimg)){ 
			pa.put("IMG_PATH", qitaimg);
			pa.put("CERTIFICATE_NAME", "其他");
			pa.put("CERTIFICATE_TYPE", "4");
			if(qiid!=null&&!"".equals(qiid)&&!"undefined".equals(qiid)){//修改
				pa.put("ID",qiid);
				obj=this.appcertificatesService.edit(pa);
			}else{//新增
				pa.put("ID", this.get32UUID()); 
				obj=this.appcertificatesService.addconcern(pa);
			} 
		}  
		if(Integer.valueOf(obj.toString())>=1)
		{
			_result = AppUtil.ss(null, "01", "成功","true",null);
		}else{
			 _result = AppUtil.ss(null, "92", "非法请求","false",null);
		}
		return _result;
	}
	
}
