package com.fh.service.shoper;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport; 
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;



@Service("productsService")
public class ProductsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;  
	
	public PageData findCount(Page page) throws Exception{
	     return (PageData)this.dao.findForObject("ProductsMapper.findCount", page);
	} 
	
	public List<PageData> listPageByParam(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProductsMapper.listPageByParam",page);
	} 
	
	public PageData queryById(PageData pd) throws Exception {
		return (PageData) this.dao.findForObject("ProductsMapper.queryById", pd);
	} 
	/*
	* 通过id获取数据
	*/
	public List<PageData> findById(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProductsMapper.findById", pd);
	}
	/**
	 * 获取产品滚动图
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findImages(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProductsMapper.findImages",pd);
	} 
	public PageData queryMaxOrderby(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProductsMapper.queryMaxOrderby", pd);
	}
	
	/**
	 * 获取产品介绍图
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> finddescImages(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProductsMapper.finddescImages",pd);
	} 
	
	public Object delete(String[] ids) throws Exception {
		return this.dao.update("ProductsMapper.del", ids);
	}
	
	 
	/*
	* 新增
	*/
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Object save(PageData pd)throws Exception{
		Object obj = "";
		int totalnum = 0;
		double max_price = 0;
		double min_price = 0;
		double price = 0;
		try {
			//新增产品属性信息
			String[] sxn = pd.getString("sxn").split(",");
			String[] sxz = pd.getString("sxz").split(",");
			PageData prop = new PageData();
			prop.put("PRODUCT_ID", pd.get("ID"));
			for (int i = 1; i < sxn.length; i++) {
				prop.put("ID", UuidUtil.get32UUID());
				prop.put("PROP_NAME", sxn[i]);
				prop.put("PROP_VALUE", sxz[i]);
				prop.put("ORDERBY", i);
				obj=dao.save("ProductsMapper.saveProp", prop);
			}
			//新增产品规格信息
			String[] ggn = pd.getString("ggn").split(",");
			String[] ggz = pd.getString("ggz").split(",");
			PageData spec = new PageData();
			spec.put("PROD_ID", pd.get("ID"));
			for (int i = 1; i < ggn.length; i++) {
				spec.put("ID", UuidUtil.get32UUID());
				spec.put("PROD_SPEC_NAME", ggn[i]);
				spec.put("PROD_SPEC_VALUE", ggz[i].replace("#", ","));
				spec.put("ORDERBY", i);
				obj=dao.save("ProductsMapper.saveSpec", spec);
			}
			
			//新增产品sku信息
			String[] sku_num = pd.getString("sku_num").split(",");
			String[] sku_n = pd.getString("sku_n").split(",");
			String[] sku_price = pd.getString("sku_price").split(",");
			String[] yh_price = pd.getString("yh_price").split(",");
			String[] sc_price = pd.getString("sc_price").split(",");
			
			PageData sku = new PageData();
			sku.put("PROD_ID", pd.get("ID"));
			for (int i = 1; i < sku_n.length; i++) {
				if(i == 1){
					max_price = Double.valueOf(yh_price[i]);
					min_price = Double.valueOf(yh_price[i]);
					price = Double.valueOf(sc_price[i]);
				}else{
					if(max_price < Double.valueOf(yh_price[i])){
						max_price = Double.valueOf(yh_price[i]);
					}
					if(min_price > Double.valueOf(yh_price[i])){
						min_price = Double.valueOf(yh_price[i]);
					}
					if(price > Double.valueOf(sc_price[i])){
						price = Double.valueOf(yh_price[i]);
					}
				}
				sku.put("ID", UuidUtil.get32UUID());
				sku.put("SKU_N", sku_n[i].replace("#", ","));
				sku.put("PRICE", sku_price[i]);
				sku.put("STOCK_N", sku_num[i]);
				sku.put("MARKET_PRICE", sc_price[i]);
				sku.put("SALES_PRICE", yh_price[i]);
				sku.put("SKU_INFO", sku_n[i].replace("#", ","));
				totalnum += Integer.valueOf(sku_num[i]);
				obj=dao.save("ProductsMapper.saveSku", sku);
			}
			//新增产品信息
			pd.put("TOTALNUM", totalnum);
			pd.put("PRICE", price);
			pd.put("MAX_PRICE", max_price);
			pd.put("MIN_PRICE", min_price);
			pd.put("SALES_PRICE", yh_price[1]);
			obj = dao.save("ProductsMapper.save", pd);
			PageData ps=new PageData();
			ps.put("ID",UuidUtil.get32UUID());
			ps.put("SHOP_ID",pd.getString("SHOP_ID"));
			ps.put("PROD_ID",pd.getString("ID"));
			ps.put("USER_ID",pd.getString("USER_ID"));
			obj = dao.save("ProductsMapper.saveProShop", ps);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return obj;
	}
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Object edit(PageData pd) throws Exception {
		Object obj = "";
		int totalnum = 0;
		double max_price = 0;
		double min_price = 0;
		double price = 0;
		try {
			
			
			//新增产品属性信息
			String[] sxn = pd.getString("sxn").split(",");
			String[] sxz = pd.getString("sxz").split(",");
			PageData prop = new PageData();
			prop.put("PRODUCT_ID", pd.get("ID"));
			//删除原有数据重新录入
			obj = dao.delete("ProductsMapper.deleteProp", prop);
			for (int i = 1; i < sxn.length; i++) {
				prop.put("ID", UuidUtil.get32UUID());
				prop.put("PROP_NAME", sxn[i]);
				prop.put("PROP_VALUE", sxz[i]);
				prop.put("ORDERBY", i);
				obj=dao.save("ProductsMapper.saveProp", prop);
			}
			//新增产品规格信息
			String[] ggn = pd.getString("ggn").split(",");
			String[] ggz = pd.getString("ggz").split(",");
			PageData spec = new PageData();
			spec.put("PROD_ID", pd.get("ID"));
			//删除原有数据重新录入
			obj = dao.delete("ProductsMapper.deleteSpec", spec);
			for (int i = 1; i < ggn.length; i++) {
				spec.put("ID", UuidUtil.get32UUID());
				spec.put("PROD_SPEC_NAME", ggn[i]);
				spec.put("PROD_SPEC_VALUE", ggz[i].replace("#", ","));
				spec.put("ORDERBY", i);
				obj=dao.save("ProductsMapper.saveSpec", spec);
			}
			
			//新增产品sku信息
			String[] sku_num = pd.getString("sku_num").split(",");
			String[] sku_n = pd.getString("sku_n").split(",");
			String[] sku_price = pd.getString("sku_price").split(",");
			String[] yh_price = pd.getString("yh_price").split(",");
			String[] sc_price = pd.getString("sc_price").split(",");
			
			PageData sku = new PageData();
			sku.put("PROD_ID", pd.get("ID"));
			//删除原有数据重新录入
			obj = dao.delete("ProductsMapper.deleteSku", sku);
			for (int i = 1; i < sku_n.length; i++) {
				if(i == 1){
					max_price = Double.valueOf(yh_price[i]);
					min_price = Double.valueOf(yh_price[i]);
					price = Double.valueOf(sc_price[i]);
				}else{
					if(max_price < Double.valueOf(yh_price[i])){
						max_price = Double.valueOf(yh_price[i]);
					}
					if(min_price > Double.valueOf(yh_price[i])){
						min_price = Double.valueOf(yh_price[i]);
					}
					if(price > Double.valueOf(sc_price[i])){
						price = Double.valueOf(yh_price[i]);
					}
				}
				sku.put("ID", UuidUtil.get32UUID());
				sku.put("SKU_N", sku_n[i].replace("#", ","));
				sku.put("PRICE", sku_price[i]);
				sku.put("STOCK_N", sku_num[i]);
				sku.put("MARKET_PRICE", sc_price[i]);
				sku.put("SALES_PRICE", yh_price[i]);
				sku.put("SKU_INFO", sku_n[i].replace("#", ","));
				totalnum += Integer.valueOf(sku_num[i]);
				obj=dao.save("ProductsMapper.saveSku", sku);
			}
			//新增产品信息
			pd.put("TOTALNUM", totalnum);
			pd.put("PRICE", price);
			pd.put("MAX_PRICE", max_price);
			pd.put("MIN_PRICE", min_price);
			pd.put("SALES_PRICE", yh_price[1]);
			obj = dao.update("ProductsMapper.edit", pd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return obj;
	}  
	
	public Object updateStatus(PageData pd) throws Exception {
		return this.dao.update("ProductsMapper.updateStatus", pd);
	}  
	
	public Object delete(PageData pd) throws Exception {
		return this.dao.delete("ProductsMapper.delete", pd);
	} 
	public Object saveImg(PageData pd) throws Exception {
		return this.dao.save("ProductsMapper.saveImg", pd);
	} 
	
	
}

