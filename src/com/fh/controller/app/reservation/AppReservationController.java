package com.fh.controller.app.reservation;
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
import com.fh.service.app.AppDicService; 
import com.fh.service.app.media.AppRotationService; 
import com.fh.service.app.reservation.AppReservationService; 
import com.fh.util.AppUtil; 
import com.fh.util.DateUtil;
import com.fh.util.PageData; 

@Controller
@RequestMapping({ "/appReservation" })
public class AppReservationController extends AppController{

	@Resource(name = "appReservationService")
	private AppReservationService appReservationService;
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;  
	
	@Resource(name = "appDicService")
	private AppDicService appDicService; 
	
	@Resource(name="appRotationservice")
	private AppRotationService appRotationservice;
	
	//俱乐部首页获取接口
	@RequestMapping({ "/indexInfo" })
	@ResponseBody
	public PageData indexInfo() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
	 	pa.put("LOCATION_NO","05");
		List<PageData> piclist=this.appRotationservice.queryByColno(pa); //查询俱乐部首页轮换大图
		pa.put("LOCATION_NO","06");
		List<PageData> advList=this.appRotationservice.queryByColno(pa); //查询俱乐部广告大图
		List<PageData> clubServiceList=this.appDicService.queryByPBM("clubService");//俱乐部设施服务
		List<PageData> clubrentList=this.appDicService.queryByPBM("clubrent");//俱乐部租用分类
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("piclist",piclist); 
		map.put("advList",advList);
		map.put("clubServiceList",clubServiceList);
		map.put("clubrentList",clubrentList);
		_result = AppUtil.ss(map, "01", "成功","true", null); 
		return _result;
	}
	
	//咨询提交接口
	@RequestMapping({ "/findReservation" })
	@ResponseBody
	public PageData findReservation() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());
		String id=this.get32UUID();
		pa.put("ID", id);
		pa.put("CREATE_DATE", new Date());
		pa.put("MODIFY_DATE",new Date());
		pa.put("MODIFY_BY",pa.getString("userid"));
		pa.put("STATUS","0");//未审核
		String LINK_MAND=pa.getString("LINK_MAN");
		String ESCRIPTION=pa.getString("DESCRIPTION");
		
	    if ((LINK_MAND != null) && (!"".equals(LINK_MAND))) { 
	    	LINK_MAND=new String(LINK_MAND.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("LINK_MAN",LINK_MAND);
		} 
       if ((ESCRIPTION != null) && (!"".equals(ESCRIPTION))) { 
    	   ESCRIPTION=new String(ESCRIPTION.getBytes("ISO-8859-1"),"UTF-8").trim();
			pa.put("DESCRIPTION",ESCRIPTION);
		} 
		Object obj=this.appReservationService.saveReservation(pa);
		if (Integer.valueOf(obj.toString()) >= 1) {
			_result = AppUtil.ss(null, "01", "成功","true", null);
		} else {
			_result = AppUtil.ss(null, "02", "失败", "false", null);
		}
		return _result;
	} 
	
	
	//俱乐部信息获取接口(设施服务、租用分类)俱乐部获活动类型
	@RequestMapping({ "/findClubInfo" })
	@ResponseBody
	public PageData findClubInfo() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());  
		List<PageData> clubServiceList=this.appDicService.queryByPBM("clubService");//俱乐部设施服务
		List<PageData> clubrentList=this.appDicService.queryByPBM("clubrent");//俱乐部租用分类
		List<PageData> clubacttypeList=this.appDicService.queryByPBM("clubactivitytype");//俱乐部租用分类 
		Map<String,Object> map=new HashMap<String,Object>();
		if(pa.getString("START_DATE")!=null&&!"".equals(pa.getString("START_DATE"))){
			
		}else{
			pa.put("START_DATE",DateUtil.getYearAndMonth(new Date())); 
			map.put("today",DateUtil.getDay(new Date()));
		} 
		List<PageData> dates=this.appReservationService.findByYearAndMonth(pa);
		List<String> dateList=new ArrayList<String>();
		for(PageData startdate:dates){
			if(startdate.getString("START_DATE").contains(",")){
				String[] strs=startdate.getString("START_DATE").split(","); 
				for(int i=0;i<strs.length;i++){
					dateList.add(strs[i]); 
				} 
			}else{
				dateList.add(startdate.getString("START_DATE"));
			}
		}  
		map.put("clubServiceList",clubServiceList);
		map.put("clubrentList",clubrentList);
		map.put("actTypeList",clubacttypeList);
		map.put("dateList",dateList); 
		_result = AppUtil.ss(map, "01", "成功","true", null); 
		return _result;
	}
	 
	
	//获取俱乐部轮换大图
	@RequestMapping({ "/queryClubByColno" })
	@ResponseBody
	public PageData queryClubByColno()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		pa.put("LOCATION_NO","05");
		List list=this.appRotationservice.queryByColno(pa); 
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result; 
	}
	
	//广告获取接口
	@RequestMapping({ "/queryAdvertByColno" })
	@ResponseBody
	public PageData queryAdvertByColno()throws Exception{
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData()); 
		PageData pd=new PageData();
		pd.put("LOCATION_NO","06");
		List list=this.appRotationservice.queryByColno(pd); 
		_result = AppUtil.ss(list, "01", "成功","true", null);
		return _result; 
	}
	
}
