package com.fh.controller.app.pay;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.app.AppCommonService;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.app.pay.AppOrdersService;
import com.fh.service.app.pay.AppPayService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

@Controller
@RequestMapping(value="/appPay")
public class AppPayController extends BaseController { 
 
	@Resource(name="appPayService")
	private AppPayService appPayService;
	
	@Resource(name="appOrdersService")
	private AppOrdersService appOrdersService; 
	
	@Resource(name="appCommonService")
	private AppCommonService appCommonService;
	
	@Resource(name = "appusersService")
	private AppusersService appusersService; 
	
	@RequestMapping({ "/queryAllOrders" })
	@ResponseBody
	public PageData queryAllOrders() throws Exception {
		PageData _result=new PageData();
		PageData pa =this.appCommonService.findtokenDetail(getPageData()); 
		
//		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
//		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
//		int totalSize=currentPage*pageSize; 
//		pa.put("totalSize",totalSize);
		
		int currentPage=Integer.parseInt(pa.get("currentPage")+"");//当前页 
		int pageSize=Integer.parseInt(pa.get("pageSize")+"");//每页条数
		int start=(currentPage-1)*pageSize;
		pa.put("start",start); 
		pa.put("pageSize",pageSize);
		
		List<PageData> list=this.appOrdersService.findByUId(pa);
		int dzong=list.size();
		PageData zfzon= this.appOrdersService.findByUIdzong(pa);
		
		PageData ijn=new PageData();
		ijn.put("list", list);
		ijn.put("dzong", dzong);
		ijn.put("zfzon", zfzon);
		for (PageData pageData : list) { 
			pageData.put("DATE",pageData.get("DATE")+""); 
		} 
		_result = AppUtil.ss(ijn, "01", "成功","true",null); 
		return _result; 
	} 
	
	//绑定订单号
	@RequestMapping({ "/bindOrderNO" })
	@ResponseBody
	public PageData bindOrderNO() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());    
		Object re=appOrdersService.bindOrderNo(pa); 
		if(Integer.valueOf(re.toString()) >= 1){
			_result = AppUtil.ss(null, "01", "成功","true",null); 
		}else{
			_result = AppUtil.ss(null, "90", "失败","false",null); 
		}  
		return _result;
	}  
	
	//支付
	@RequestMapping({ "/updateOrders" })
	@ResponseBody
	public PageData updateOrders() throws Exception {
		PageData _result=new PageData();
		PageData pa = this.appCommonService.findtokenDetail(getPageData());  
		PageData  orders=appOrdersService.findById(pa);
		orders.put("STATUS","03");
		Object re=appOrdersService.edit(orders);
		pa.put("VIP_LEVEL","01");
		pa.put("IS_VIP","1");
		Object rel=appusersService.edit(pa); 
 	   if(Integer.valueOf(re.toString()) >= 1&&Integer.valueOf(rel.toString())>=1){
 			PageData data=new PageData(); 
			data.put("ID",get32UUID());
			data.put("ORDER_ID",orders.getString("ID")); 
			data.put("PRICE",orders.get("PRICE")); 
			data.put("DESCRIPTION",orders.getString("EVENT"));
			data.put("PAY_TYPE",pa.getString("channel"));
			data.put("DATE",new Date()); 
			data.put("STATUS","03");//未支付
			data.put("USER_ID",pa.getString("userid")); 
			Object ob=appPayService.save(data); 
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
	 
	@RequestMapping(value={"/success"}) 
	public ModelAndView success() throws Exception {
		ModelAndView mv = getModelAndView();
		mv.setViewName("webhook/webhook_receiver");
		return mv;
	} 
}
