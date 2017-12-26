 package com.fh.util;
 
 import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;
 
 public class TwoDimensionCodeImage
   implements QRCodeImage
 {
   BufferedImage bufImg;
 
   public TwoDimensionCodeImage(BufferedImage bufImg)
   {
     this.bufImg = bufImg;
   }
 
   public int getHeight()
   {
     return this.bufImg.getHeight();
   }
 
   public int getPixel(int x, int y)
   {
     return this.bufImg.getRGB(x, y);
   }
 
   public int getWidth()
   {
     return this.bufImg.getWidth();
   }
 }

/* Location:           F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\
 * Qualified Name:     com.fh.util.TwoDimensionCodeImage
 * JD-Core Version:    0.6.2
 */