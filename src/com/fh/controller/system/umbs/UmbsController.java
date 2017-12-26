package com.fh.controller.system.umbs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.app.Apppersonal.AppusersService;
import com.fh.service.pay.OrdersService;
import com.fh.service.pay.PayService;
import com.fh.service.system.dictionaries.DictionariesService;
import com.fh.service.system.umbs.UmbsService;
import com.fh.service.system.user.TbUserService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.PushUtil;
import com.fh.util.SystemLog;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.ping.ChargeUtil;
import com.pingplusplus.model.Charge;

@Controller
@RequestMapping(value="/umbs")
public class UmbsController extends BaseController {
	
	String menuUrl = "umbs/list.do"; //菜单地址(权限用)

	@Resource(name = "umbsService")
	private UmbsService umbsService;
	@Resource(name = "appusersService")
	private AppusersService appusersService;
	
	@Resource(name = "dictionariesService")
	private DictionariesService dicService; 
	
	@RequestMapping
	public ModelAndView list() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		PageData pds=null;
		this.umbsService.updatedelhd(pds);
		mv.setViewName("system/umbs/umbs-list");
		return mv;
	}
	
	@RequestMapping({ "/queryList" })
	@ResponseBody
	public String queryPageList(Page page) throws Exception {
		PageData pd = new PageData();
		pd = getPageData();
		
		String sEcho = "0";// 记录操作的次数  每次加1  
		String iDisplayStart = "0";// 起始  
		String iDisplayLength = "10";// size  
		String mDataProp = "mDataProp_";
		String sortName = "";//排序字段
		String iSortCol_0 = "";//排序索引
		String sSortDir_0 = "";//排序方式
		//获取jquery datatable当前配置参数  
		JSONArray jsonArray = JSONArray.fromObject(pd.getString("aoData"));  
		for (int i = 0; i < jsonArray.size(); i++)  
		{  
		    try  
		    {  
		        JSONObject jsonObject = (JSONObject)jsonArray.get(i);  
		        if (jsonObject.get("name").equals("sEcho")){ 
		            sEcho = jsonObject.get("value").toString();  
		        }
		        else if (jsonObject.get("name").equals("iDisplayStart")){
		            iDisplayStart = jsonObject.get("value").toString(); 
		        }
		        else if (jsonObject.get("name").equals("iDisplayLength")){ 
		            iDisplayLength = jsonObject.get("value").toString(); 
		        }
		        else if (jsonObject.get("name").equals("sSortDir_0")){
		        	sSortDir_0 = jsonObject.get("value").toString(); 
		        }
		        else if (jsonObject.get("name").equals("iSortCol_0")){
		        	iSortCol_0 = jsonObject.get("value").toString();
		        	mDataProp = mDataProp+""+iSortCol_0;
		        	for (int j = 0; j < jsonArray.size(); j++)  {
		        		JSONObject jsonObject1 = (JSONObject)jsonArray.get(j);  
		        		if(jsonObject1.get("name").equals(mDataProp)){
		        			sortName = jsonObject1.get("value").toString(); 
		        		}
		        	}
		        }
		        
		    }  
		    catch (Exception e)  
		    {  
		        break;  
		    }  
		}  

		page.setPd(pd);
		page.setOrderDirection(sSortDir_0);
		page.setOrderField(sortName);
		page.setPageSize(Integer.valueOf(iDisplayLength));
		page.setPageCurrent(Integer.valueOf(iDisplayStart));
		int initEcho = Integer.parseInt(sEcho) + 1;  
		
		List<PageData> data = this.umbsService.listByParam(page);
		Object	total = this.umbsService.findCount(page).get("ZS");//查询记录的总行数 
		JSONObject getObj = new JSONObject();
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	}
	
	@RequestMapping({ "/toEdit" })
	public ModelAndView toAdd() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		PageData pad = this.umbsService.findByid(pd); 
		List<PageData> reason=this.dicService.queryByPBM("upgrade_check");//升级入会审核结果
		mv.addObject("pad", pad);  
		mv.addObject("reason", reason); 
		mv.setViewName("system/umbs/edit-umbs"); 
		return mv;
	}
	
	@RequestMapping({ "/toSee" })
	public ModelAndView toSee() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		PageData pad = this.umbsService.findByid(pd); 
		List<PageData> reason=this.dicService.queryByPBM("upgrade_check");//升级入会审核结果 
		mv.addObject("reason", reason); 
		mv.addObject("pad", pad);  
		mv.setViewName("system/umbs/see-umbs"); 
		return mv;
	}
	
	/**
	 * 审核拒绝
	 */
	@RequestMapping({ "/shjj" })
	@SystemLog(methods="升级入会管理",type="审核拒绝")
	@ResponseBody
	public String shjj() throws Exception {
		PageData pd = new PageData();
		pd = getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();

		User user = (User) session.getAttribute("sessionUser");
		pd.put("AUDIT_BY", user.getID());
		
		String content="尊敬的用户，您的升级入会请求已被驳回，拒绝原因为："+pd.getString("reason")+",会费将在三天之内退给您，如有疑问请致电客服.";
		pd.put("CONTENT", content);
		Integer count=(Integer)this.umbsService.shjj(pd);

		JSONObject getObj = new JSONObject();
		getObj.put("status", "0");
		if(count > 0){
			//推送消息
	 		PageData pagedata = new PageData();
	 		pagedata.put("ID", pd.getString("USER_ID"));
			PageData ur = this.appusersService.queryById(pagedata);
	 		if(ur != null ){
	 			//推送审核消息
//				NotificationTemplate template = null;
//				if( ur.getString("PLATFORM") == "1" ){
//					template = PushUtil.notificationTemplateDemo(); 
//					template.setTitle("上海建联");
//					template.setText(content);
//				}
	 			String jsonStr = "{'type':'notice','title':'上海建联','content':'"+content+"'}";//透传内容
				TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
				PushUtil pushApp=new PushUtil();
				String alias = pd.getString("USER_ID");
				pushApp.pushToSingle(tmTemplate,alias); 
	 		}
			getObj.put("status", "1");
		}
		return getObj.toString();
	}
	
	/**
	 * 审核通过
	 */
	@RequestMapping({ "/shtg" })
	@SystemLog(methods="升级入会管理",type="审核通过")
	@ResponseBody
	public String shtg() throws Exception {
		PageData pd = new PageData();
		pd = getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();

		User user = (User) session.getAttribute("sessionUser");
		pd.put("AUDIT_BY", user.getID());
		
		//String content="尊敬的用户，您的升级入会请求已审核通过，订单有效时间为24小时，请及时付款！";
		String content="尊敬的用户，您的升级入会请求已审核通过！";
		pd.put("CONTENT", content);
		Object count=this.umbsService.shtg(pd);

		JSONObject getObj = new JSONObject();
		getObj.put("status", "0");
		if(Integer.valueOf(count.toString()) > 0){
			//推送消息
	 		PageData pagedata = new PageData();
	 		pagedata.put("ID", pd.getString("USER_ID"));
			PageData ur = this.appusersService.queryById(pagedata);
	 		if(ur != null ){
	 			//推送审核消息
//				NotificationTemplate template = null;
//				if( ur.getString("PLATFORM") == "1" ){
//					template = PushUtil.notificationTemplateDemo(); 
//					template.setTitle("上海建联");
//					content=content+"您的会员卡号为："+ur.getString("CARD_NO");
//					template.setText(content);
//				}
	 			String jsonStr = "{'type':'notice','title':'上海建联','content':'"+content+"'}";//透传内容
				TransmissionTemplate tmTemplate = PushUtil.transmissionTemplateDemo("上海建联",content,jsonStr);
				PushUtil pushApp=new PushUtil();
				String alias = pd.getString("USER_ID");
				pushApp.pushToSingle(tmTemplate,alias); 
	 		}
			getObj.put("status", "1");
		}
		return getObj.toString();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping({ "/delete" })
	@ResponseBody
	public String delete() throws Exception {
		PageData pd = new PageData();
		pd = getPageData();
		
		JSONObject getObj = new JSONObject();
		getObj.put("status", "1");//
		return getObj.toString();
	}
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	} 
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
