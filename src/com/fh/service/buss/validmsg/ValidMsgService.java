package com.fh.service.buss.validmsg;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.fh.util.message.SMUtil;

@Service("validmsgService")
public class ValidMsgService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public void save(PageData pd) throws Exception {
		this.dao.save("ValidMsgMapper.save", pd);
	}

	public void delete(PageData pd) throws Exception {
		this.dao.delete("ValidMsgMapper.delete", pd);
	}

	public void edit(PageData pd) throws Exception {
		this.dao.update("ValidMsgMapper.edit", pd);
	}

	public List<PageData> list(Page page) throws Exception {
		return (List) this.dao.findForList("ValidMsgMapper.datalistPage", page);
	}

	public List<PageData> listAll(PageData pd) throws Exception {
		return (List) this.dao.findForList("ValidMsgMapper.listAll", pd);
	}

	public PageData findById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ValidMsgMapper.findById", pd);
	}

	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		this.dao.delete("ValidMsgMapper.deleteAll", ArrayDATA_IDS);
	}

	public void validsmsSend(PageData pd) throws Exception {
		pd.put("time", 600000);
		List last = (List) this.dao.findForList("ValidMsgMapper.findLastCode", pd);
		if (last != null && !last.isEmpty()) {
			throw new Exception("该号码已发送验证码,请稍后再尝试!");
		}
		
		//10分钟发送一次验证码
		String mobile = pd.getString("MOBILE");
		String randomCode = getRandomCode(6);
		pd.put("MOBILE", mobile);
		pd.put("VALID_CODE", randomCode);
		pd.put("SEND_TIME", Long.valueOf(System.currentTimeMillis()));
		pd.put("VALIDMSG_ID", UuidUtil.get32UUID());
		this.dao.save("ValidMsgMapper.save", pd);

		SMUtil.sendSM(mobile, "【宁夏招标】您的手机验证码为:" + randomCode + ", 请在10分钟内按页面提示提交验证码。");
	}

	private static String getRandomCode(int i) {
		return String.valueOf(Math.random()).substring(2, 2 + i);
	}

	public static void main(String[] args) {
		System.out.println(getRandomCode(6));
	}

	public boolean valid(String mobile, String code) {
		PageData pd = new PageData();
		pd.put("MOBILE", mobile);
		pd.put("VALID_CODE", code);
		pd.put("time", 1000*60*10);
		PageData result = null;
		try {
			result = (PageData) this.dao.findForObject("ValidMsgMapper.findValidCode", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result != null;
	}
}

/*
 * Location: F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\ Qualified Name:
 * com.fh.service.buss.validmsg.ValidMsgService JD-Core Version: 0.6.2
 */