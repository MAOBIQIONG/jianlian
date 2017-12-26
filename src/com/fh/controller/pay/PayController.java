package com.fh.controller.pay;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.fh.service.pay.OrdersService;
import com.fh.service.pay.PayService;
import com.fh.service.system.user.TbUserService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.SystemLog;
import com.fh.util.noticeConfig;
import com.fh.util.noticePushutil;
import com.fh.util.excelexception.Exceldworup;
import com.ping.ChargeUtil;
import com.pingplusplus.model.Charge;

@Controller
@RequestMapping(value="/pay")
public class PayController extends BaseController {
	
	String menuUrl = "pay/list.do"; //菜单地址(权限用)
	@Resource(name="payService")
	private PayService payService;
	
	@Resource(name="ordersService")
	private OrdersService ordersService;
	
	@Resource(name = "tbuserService")
	private TbUserService tbuserService;
	
	public static String noticeText5 = noticeConfig.getString("noticeText5");
	public static String noticeText6 = noticeConfig.getString("noticeText6");
	
	@RequestMapping
	public ModelAndView list() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		mv.setViewName("system/pay/pay_list");
		return mv;
	}
	
	@RequestMapping({ "/queryPageList" })
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
		
		List<PageData> data = this.ordersService.list(page);
		Object	total = this.ordersService.findCount(page).get("counts");//查询记录的总行数 
		JSONObject getObj = new JSONObject();
		getObj.put("sEcho", initEcho);// 不知道这个值有什么用
		getObj.put("iTotalRecords", total);//实际的行数
		getObj.put("iTotalDisplayRecords", total);//显示的行数,这个要和上面写的一样
		
		for(int i=0;i<data.size();i++){
		Object date=data.get(i).get("DATE"); 
		   if(date==null||"".equals(date)){
			   data.get(i).put("DATE","");
		   }else{
			   String DATE =date+"";
			   data.get(i).put("DATE",DATE);
		   }
		}  
		getObj.put("aaData", data);//要以JSON格式返回
		return getObj.toString();
	}
	
	@RequestMapping({ "/toAdd" })
	public ModelAndView toAdd() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		List userList = this.tbuserService.querytbuser(null); 
		mv.addObject("userList", userList);  
		mv.setViewName("system/pay/orders_edit"); 
		mv.addObject("pd",pd); 
		return mv;
	}
	
	@RequestMapping({ "/updates" })
	@SystemLog(methods="确认支付成功",type="修改")
	@ResponseBody
	public String updates() throws Exception {
		PageData pd = new PageData();
		pd = getPageData();
		pd.put("DATE",new Date()); 
		Object ob=this.ordersService.edit(pd);
		JSONObject getObj = new JSONObject();
		if(Integer.parseInt(ob.toString())>=1){
			
			PageData order=this.ordersService.findById(pd);
			PageData data=new PageData(); 
			data.put("ID",get32UUID());
			data.put("ORDER_ID",order.getString("ID")); 
			data.put("PRICE",order.get("PRICE")); 
			data.put("DESCRIPTION",order.getString("EVENT"));
			data.put("PAY_TYPE",order.getString("PAY_TYPE"));
			data.put("DATE",new Date()); 
			data.put("STATUS",pd.getString("STATUS"));//支付成功
			data.put("USER_ID",order.getString("USER_ID")); 
			Object re=payService.save(data); 
			if(Integer.parseInt(re.toString())>=1){
				String type=order.getString("TYPE");
				String notice1="";
				if("01".equals(type)){
					//获取notice.properties noticeText5内容。会费
				    String vv3=new String(noticeText5.getBytes("ISO-8859-1"),"UTF-8"); 
				    notice1=vv3;
				}else{
					//获取notice.properties noticeText6内容。活动
				    String vv3=new String(noticeText6.getBytes("ISO-8859-1"),"UTF-8"); 
				    notice1=vv3;
				}
				noticePushutil notutil=new noticePushutil();
    	        notutil.toNotice(notice1);
				getObj.put("statusCode","200");
			}else{
				getObj.put("statusCode","400");
			} 
		}else{
			getObj.put("statusCode","400");
		} 
		return getObj.toString();
	}
	
	@RequestMapping({ "/saveOrders" })
	@SystemLog(methods="支付管理",type="新增")
	@ResponseBody
	public String saveOrders() throws Exception {
		PageData pd = new PageData();
		pd = getPageData();
		pd.put("DATE",new Date());
		String ID=pd.getString("ID");
		if(ID!=null&&!"".equals(ID)){
			this.ordersService.edit(pd);
		}else{
			pd.put("ID",get32UUID());
			this.ordersService.save(pd);
		}
		JSONObject getObj = new JSONObject();
		getObj.put("statusCode",200);
		return getObj.toString();
	}
	
	@RequestMapping({ "/toPay" })
	public ModelAndView toPay() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		List userList = this.tbuserService.querytbuser(null);
		PageData data=this.ordersService.findById(pd);
		mv.addObject("userList", userList);  
		mv.setViewName("system/pay/pay"); 
		mv.addObject("pd",data); 
		return mv;
	}
	
	@RequestMapping({ "/doPay" })
	@ResponseBody
	public String doPay() throws Exception {
		PageData pd = new PageData();
		pd = getPageData();
		pd.put("DATE",new Date());
		String ID=pd.getString("ID");
		ChargeUtil chargeUtil=ChargeUtil.getInstance();
		Charge charge=chargeUtil.createCharge(pd); 
		JSONObject getObj = new JSONObject();
		getObj.put("statusCode",200);
		getObj.put("charge",charge);
		return getObj.toString();
	} 
	 
	
	@RequestMapping({ "/toedit" })
	public ModelAndView toedit() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();
		//PageData customer = customerService.findById(pd);
		mv.setViewName("system/pay/edit_pay"); 
		//mv.addObject("data", customer);
		return mv;
	}
	
	@RequestMapping({ "/toSee" })
	public ModelAndView toSee() throws Exception {
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData(); 
		PageData data=this.ordersService.queryByOid(pd);
		mv.addObject("data", data);
		mv.setViewName("system/pay/look_invoice");  
		return mv;
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
	
	
	@RequestMapping(value = "exportfeedback")
    @ResponseBody
    public String exportFeedBack(HttpServletResponse response) throws Exception{
    	JSONObject getObj = new JSONObject();
        String fileName = "支付信息"+System.currentTimeMillis()+".xls"; //文件名 
        String sheetName = "支付信息";//sheet名 
        String []title = new String[]{"订单号","会员","手机号","卡号","所属建联","所属行业","类型","事件","状态","金额（元）","日期","支付方式"};//标题
        PageData pd=null;
        List<PageData> list = this.ordersService.doexlelist(pd);//内容list
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String [][]values = new String[list.size()][];
        for(int i=0;i<list.size();i++){
            values[i] = new String[title.length];
            //将对象内容转换成string
            PageData obj = list.get(i);  
            values[i][0] = obj.getString("ID")+"";
            values[i][1] = obj.getString("REAL_NAME");
            values[i][2] = obj.getString("PHONE");
            values[i][3] = obj.getString("CARD_NO");
            values[i][4] = obj.getString("CLAN_NAME");
            values[i][5] = obj.getString("CATE_NAME");
            values[i][6] = obj.getString("OTYPE");
            values[i][7] = obj.getString("EVENT");
            values[i][8] = obj.getString("STATUS_NAME");
            values[i][9] = obj.get("PRICE").toString();
            values[i][10] = sdf.format(DateFormat.getDateInstance().parse(obj.getString("DATE")));  
            values[i][11] = obj.getString("PAY_NAME");  

              
        }
        
        HSSFWorkbook wb = Exceldworup.getHSSFWorkbook(sheetName, title, values, null);
        
        //将文件存到指定位置  
        try {  
             this.setResponseHeader(response, fileName);  
             OutputStream os = response.getOutputStream();  
             wb.write(os);  
             os.flush();  
             os.close();  
        } catch (Exception e) {  
             e.printStackTrace();  
        }  
        return getObj.toString();
    }
    
     public void setResponseHeader(HttpServletResponse response, String fileName) {  
         try {  
              try {  
                   fileName = new String(fileName.getBytes(),"ISO8859-1");  
              } catch (UnsupportedEncodingException e) {  
                   // TODO Auto-generated catch block  
                   e.printStackTrace();  
              }  
              response.setContentType("application/octet-stream;charset=ISO8859-1");  
              response.setHeader("Content-Disposition", "attachment;filename="+ fileName);  
              response.addHeader("Pargam", "no-cache");  
              response.addHeader("Cache-Control", "no-cache");  
         } catch (Exception ex) {  
              ex.printStackTrace();  
         }  
    } 
}
