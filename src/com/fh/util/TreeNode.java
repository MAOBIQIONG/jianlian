 package com.fh.util;
 
 import java.util.List;
 
 public class TreeNode
 {
   private String name;
   private String parentMenu;
   private Integer MENU_ORDER;
   private String MENU_URL;
   private String MENU_TYPE;
   private String checked;
   private String target;
   private String id;
   private String MENU_ICON;
   private List<TreeNode> chilren;
   private List<TreeNode> nodes;
   private String iconSkin;
   private String font;
   private String open = "false";
   public String getOpen() {
	return open;
}

public void setOpen(String open) {
	this.open = open;
}

String PARENT_ID;
   String click;
   Boolean isParent = Boolean.valueOf(false);
   String pId;
 
   public String getIconSkin()
   {
     return this.iconSkin;
   }
 
   public void setIconSkin(String iconSkin) {
     this.iconSkin = iconSkin;
   }
 
   public String getFont()
   {
     return this.font;
   }
 
   public void setFont(String font) {
     this.font = font;
   }
 
   public List<TreeNode> getNodes() {
     return this.chilren;
   }
 
   public Boolean getIsParent()
   {
     return this.isParent;
   }
 
   public void setIsParent(Boolean isParent) {
     this.isParent = isParent;
   }
 
   public String getClick() {
     return this.click;
   }
 
   public void setClick(String click) {
     this.click = click;
   }
 
   public String getpId() {
     return this.pId;
   }
 
   public void setpId(String pId) {
     this.pId = pId;
   }
 
   public String getName()
   {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String getParentMenu() {
     return this.parentMenu;
   }
 
   public void setParentMenu(String parentMenu) {
     this.parentMenu = parentMenu;
   }
 
   public Integer getMENU_ORDER() {
     return this.MENU_ORDER;
   }
 
   public void setMENU_ORDER(Integer mENU_ORDER) {
     this.MENU_ORDER = mENU_ORDER;
   }
 
   public String getMENU_URL() {
     return this.MENU_URL;
   }
 
   public void setMENU_URL(String mENU_URL) {
     this.MENU_URL = mENU_URL;
   }
 
   public String getMENU_TYPE() {
     return this.MENU_TYPE;
   }
 
   public void setMENU_TYPE(String mENU_TYPE) {
     this.MENU_TYPE = mENU_TYPE;
   }
 
   public String getChecked() {
     return this.checked;
   }
 
   public void setChecked(String checked) {
     this.checked = checked;
   }
 
   public String getTarget() {
     return this.target;
   }
 
   public void setTarget(String target) {
     this.target = target;
   }
 
   public String getId() {
     return this.id;
   }
 
   public void setId(String id) {
     this.id = id;
   }
 
   public String getMENU_ICON() {
     return this.MENU_ICON;
   }
 
   public void setMENU_ICON(String mENU_ICON) {
     this.MENU_ICON = mENU_ICON;
   }
 
   public List<TreeNode> getChilren()
   {
     return this.chilren;
   }
 
   public void setChilren(List<TreeNode> chilren) {
     this.chilren = chilren;
   }
 
   public String getPARENT_ID() {
     return this.PARENT_ID;
   }
 
   public void setPARENT_ID(String pARENT_ID) {
     this.PARENT_ID = pARENT_ID;
   }
 }

/* Location:           F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\
 * Qualified Name:     com.fh.util.TreeNode
 * JD-Core Version:    0.6.2
 */