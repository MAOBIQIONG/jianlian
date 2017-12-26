package com.fh.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageUtil {
	public static String GetImageStr(String imgFile) {
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	public static String GenerateImage(String imgStr, String imgFilePath, String suffix) throws Exception {
		if (imgStr == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();

		byte[] b = decoder.decodeBuffer(imgStr);
		for (int i = 0; i < b.length; i++) {
			if (b[i] < 0) {
				int tmp39_37 = i;
				byte[] tmp39_35 = b;
				tmp39_35[tmp39_37] = ((byte) (tmp39_35[tmp39_37] + 256));
			}
		}
		String realName = UuidUtil.get32UUID() + "." + suffix;
		File file = new File(imgFilePath, realName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}

		OutputStream out = new FileOutputStream(imgFilePath + realName);
		out.write(b);
		out.flush();
		out.close();
		return realName;
	}

	/**
	 * 获得图片的宽高
	 * 
	 * @param filename
	 * @return
	 */
	public static int[] getImageInfo(String filename) {
		int[] info = new int[] { 0, 0 };
		try {
			InputStream is = new FileInputStream(filename);
			BufferedImage buff = ImageIO.read(is);
			info = new int[] { buff.getWidth(), buff.getHeight() };
			buff.flush();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return info;
		}
	}

	/**
	 * @param im
	 *            原始图像
	 * @param resizeTimes
	 *            需要缩小的倍数，缩小2倍为原来的1/2 ，这个数值越大，返回的图片越小
	 * @return 返回处理后的图像
	 */
	public BufferedImage resizeImage(BufferedImage im, float resizeTimes) {
		/* 原始图像的宽度和高度 */
		int width = im.getWidth();
		int height = im.getHeight();

		/* 调整后的图片的宽度和高度 */
		int toWidth = (int) (Float.parseFloat(String.valueOf(width)) / resizeTimes);
		int toHeight = (int) (Float.parseFloat(String.valueOf(height)) / resizeTimes);

		/* 新生成结果图片 */
		BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);

		result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0,
				null);
		return result;
	}

	/**
	 * @param im
	 *            原始图像
	 * @param resizeTimes
	 *            倍数,比如0.5就是缩小一半,0.98等等double类型
	 * @return 返回处理后的图像
	 */
	public BufferedImage zoomImage(BufferedImage im, float resizeTimes) {
		/* 原始图像的宽度和高度 */
		int width = im.getWidth();
		int height = im.getHeight();

		/* 调整后的图片的宽度和高度 */
		int toWidth = (int) (Float.parseFloat(String.valueOf(width)) * resizeTimes);
		int toHeight = (int) (Float.parseFloat(String.valueOf(height)) * resizeTimes);

		/* 新生成结果图片 */
		BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);

		result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0,
				null);
		return result;
	}

	/**
	 * @param path
	 *            要转化的图像的文件夹,就是存放图像的文件夹路径
	 * @param type
	 *            图片的后缀名组成的数组
	 * @return
	 */
	public List<BufferedImage> getImageList(String path, String[] type) throws IOException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for (String s : type) {
			map.put(s, true);
		}
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		File[] fileList = new File(path).listFiles();
		for (File f : fileList) {
			if (f.length() == 0)
				continue;
			if (map.get(getExtension(f.getName())) == null)
				continue;
			result.add(javax.imageio.ImageIO.read(f));
		}
		return result;
	}

	/**
	 * 把图片写到磁盘上
	 * 
	 * @param im
	 * @param path
	 * @param fileName
	 * @return
	 */
	public boolean writeToDisk(BufferedImage im, String path, String fileName) {
		File f = new File(path + fileName);
		String fileType = getExtension(fileName);
		if (fileType == null)
			return false;
		try {
			ImageIO.write(im, fileType, f);
			im.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean writeHighQuality(BufferedImage im, String fileFullPath) {
		try {
			/* 输出到文件流 */
			FileOutputStream newimage = new FileOutputStream(fileFullPath + System.currentTimeMillis() + ".jpg");
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
			/* 压缩质量 */
			jep.setQuality(1f, true);
			encoder.encode(im, jep);
			/* 近JPEG编码 */
			newimage.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 返回文件的文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public String getExtension(String fileName) {
		try {
			return fileName.split("\\.")[fileName.split("\\.").length - 1];
		} catch (Exception e) {
			return null;
		}
	}
	
	public static PageData readImg(HttpServletRequest request,PageData pd) throws Exception {
		List<PageData> list = readImglist(request,pd);
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}else
			return null;
	}
	
	public static List<PageData> readImglist(HttpServletRequest request,PageData pd) throws Exception {
		List<PageData> pictures = new ArrayList<PageData>();
		for (int i = 0;; i++) {
			String string = request.getParameter("FILE_" + i);
			pd.put("FILE_" + i, null);
			if (string == null)
				break;
			String picture = string.split(",")[1];
			String info = string.split(",")[0];

			String suffix = info.substring(info.indexOf("/") + 1, info.indexOf(";"));
			if (picture != null) {
				String ffile = DateUtil.getDays();
				String filePath = PathUtil.getClasspath() + "../" + Const.FILEPATHIMG + ffile + "/";

				String fileName = ImageUtil.GenerateImage(picture, filePath, suffix);
				PageData _pd = new PageData();

				if (fileName != null) {
					String PICTURE_ID = UuidUtil.get32UUID();
					_pd.put("PICTURES_ID", PICTURE_ID);
					_pd.put("PICTURE_ID", PICTURE_ID);
					_pd.put("TITLE", pd.get("TITLE"));
					_pd.put("NAME", fileName);
					_pd.put("PATH", ffile + "/" + fileName);
					_pd.put("CREATETIME", DateUtil.getTime());
					_pd.put("MASTER_ID", "1");
					_pd.put("BZ", "相册");
					
					int [] wh = getImageInfo(filePath+"/"+fileName);
					_pd.put("W", wh[0]);
					_pd.put("H", wh[1]);
					_pd.put("FAVOR", 0);
					_pd.put("PL", 0);
					
					pictures.add(_pd);
				} else {
					return new ArrayList<PageData>();
				}
			}
		}
		return pictures;
	}
}
