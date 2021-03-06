 package com.fh.util;
 
 import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
 
 
 public class Watermark
 {
   private static String strFWATERM = Tools.readTxtFile("admin/config/FWATERM.txt");
   private static String strIWATERM = Tools.readTxtFile("admin/config/IWATERM.txt");
 
   public static void fushValue()
   {
     strFWATERM = Tools.readTxtFile("admin/config/FWATERM.txt");
     strIWATERM = Tools.readTxtFile("admin/config/IWATERM.txt");
   }
 
   public static void setWatemark(String imagePath)
   {
     if ((strFWATERM != null) && (!"".equals(strFWATERM))) {
       String[] strFW = strFWATERM.split(",fh,");
       if ((strFW.length == 5) && 
         ("yes".equals(strFW[0]))) {
         pressText(strFW[1].toString(), imagePath, "", 1, Color.RED, Integer.parseInt(strFW[2]), Integer.parseInt(strFW[3]), Integer.parseInt(strFW[4]));
       }
 
     }
 
     if ((strIWATERM != null) && (!"".equals(strIWATERM))) {
       String[] strIW = strIWATERM.split(",fh,");
       if ((strIW.length == 4) && 
         ("yes".equals(strIW[0])))
         pressImage(PathUtil.getClasspath() + "/image/" + strIW[1], imagePath, Integer.parseInt(strIW[2]), Integer.parseInt(strIW[3]));
     }
   }
 
   public static final void pressImage(String pressImg, String targetImg, int x, int y)
   {
     try
     {
       File _file = new File(targetImg);
       Image src = ImageIO.read(_file);
       int wideth = src.getWidth(null);
       int height = src.getHeight(null);
       BufferedImage image = new BufferedImage(wideth, height, 
         1);
       Graphics g = image.createGraphics();
       g.drawImage(src, 0, 0, wideth, height, null);
 
       File _filebiao = new File(pressImg);
       Image src_biao = ImageIO.read(_filebiao);
       int wideth_biao = src_biao.getWidth(null);
       int height_biao = src_biao.getHeight(null);
 
       g.drawImage(src_biao, x, y, wideth_biao, height_biao, null);
 
       g.dispose();
       FileOutputStream out = new FileOutputStream(targetImg);
       JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
       encoder.encode(image);
       out.close();
     } catch (Exception e) {
       e.printStackTrace();
     }
   }
 
   public static void pressText(String pressText, String targetImg, String fontName, int fontStyle, Color color, int fontSize, int x, int y)
   {
     try
     {
       File _file = new File(targetImg);
       Image src = ImageIO.read(_file);
       int wideth = src.getWidth(null);
       int height = src.getHeight(null);
       BufferedImage image = new BufferedImage(wideth, height, 
         1);
       Graphics g = image.createGraphics();
       g.drawImage(src, 0, 0, wideth, height, null);
       g.setColor(color);
       g.setFont(new Font(fontName, fontStyle, fontSize));
       g.drawString(pressText, x, y);
       g.dispose();
       FileOutputStream out = new FileOutputStream(targetImg);
       JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
       encoder.encode(image);
       out.close();
     } catch (Exception e) {
       //System.out.println(e);
     }
   }
 }

/* Location:           F:\掌上幼儿园\源码\yzy_web\WEB-INF\classes\
 * Qualified Name:     com.fh.util.Watermark
 * JD-Core Version:    0.6.2
 */