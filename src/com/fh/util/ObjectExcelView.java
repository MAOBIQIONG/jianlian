package com.fh.util;

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 * 导入到EXCEL 类名称：ObjectExcelView.java 类描述：
 * 
 * @author FH 作者单位： 联系方式：
 * @version 1.0
 */
public class ObjectExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ filename + ".xls");
		sheet = workbook.createSheet("sheet1");

		List<String> titles = (List<String>) model.get("titles");
		int len = titles.size();
		HSSFCellStyle headerStyle = workbook.createCellStyle(); // 标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = workbook.createFont(); // 标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short) 11);
		headerStyle.setFont(headerFont);
		short width = 20, height = 25 * 20;
		sheet.setDefaultColumnWidth(width);
		for (int i = 0; i < len; i++) { // 设置标题
			String title = titles.get(i);
			cell = getCell(sheet, 0, i);
			cell.setCellStyle(headerStyle);
			setText(cell, title);
		}
		sheet.getRow(0).setHeight(height);

		HSSFCellStyle contentStyle = workbook.createCellStyle(); // 内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		List<PageData> varList = (List<PageData>) model.get("varList");
		int varCount = varList.size();
		for (int i = 0; i < varCount; i++) {
			PageData vpd = varList.get(i);
			for (int j = 0; j < len; j++) {
				String varstr = vpd.get("var" + (j + 1)) != null ? (vpd.get("var" + (j + 1)) + "") : "";
				cell = getCell(sheet, i + 1, j);
				cell.setCellStyle(contentStyle);
				setText(cell, varstr);
			}

		}
	}

	/**
	 * 读取excel 数据
	 * 
	 * @param params
	 * @param excelFile
	 * @return
	 */
	public static List<PageData> readExcelData(InputStream is,
			List<ExcelCellSetting> settings, int[] ignore) throws Exception {
		return readWorkbook(settings, WorkbookFactory.create(is), ignore);
	}

	public static List<PageData> readExcelData(File excelFile,
			List<ExcelCellSetting> settings, int[] ignore) throws Exception {
		Workbook workbook = WorkbookFactory.create(excelFile);
		return readWorkbook(settings, workbook, ignore);
	}

	private static List<PageData> readWorkbook(List<ExcelCellSetting> settings,
			Workbook workbook, int[] ignore) throws Exception {
		List<String[]> dataList = new ArrayList<String[]>();
		Sheet sheet = workbook.getSheetAt(0);
		int columnNum = 0;
		if (sheet.getRow(0) != null) {
			columnNum = sheet.getRow(0).getLastCellNum()
					- sheet.getRow(0).getFirstCellNum();
		}
		if (columnNum > 0) {
			for (Row row : sheet) {
				if (!isIgnore(ignore, row))
					continue;
				String[] singleRow = new String[columnNum];
				int n = 0;
				for (int i = 0; i < columnNum; i++) {
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BLANK:
						singleRow[n] = "";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						singleRow[n] = Boolean.toString(cell
								.getBooleanCellValue());
						break;

					// 数值
					case Cell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
							SimpleDateFormat sdf = null;
							if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
									.getBuiltinFormat("h:mm")) {
								sdf = new SimpleDateFormat("HH:mm");
							} else {// 日期
								sdf = new SimpleDateFormat("yyyy-MM-dd");
							}
							Date date = cell.getDateCellValue();
							singleRow[n] = sdf.format(date);
						} else if (cell.getCellStyle().getDataFormat() == 58) {
							// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd");
							double value = cell.getNumericCellValue();
							Date date = org.apache.poi.ss.usermodel.DateUtil
									.getJavaDate(value);
							singleRow[n] = sdf.format(date);
						} else {
							double value = cell.getNumericCellValue();
							CellStyle style = cell.getCellStyle();
							DecimalFormat format = new DecimalFormat();
							String temp = style.getDataFormatString();
							// 单元格设置成常规
							if (temp.equals("General")) {
								format.applyPattern("#");
							}
							singleRow[n] = format.format(value);
						}
						break;
					case Cell.CELL_TYPE_STRING:
						singleRow[n] = cell.getStringCellValue().trim();
						break;
					case Cell.CELL_TYPE_ERROR:
						singleRow[n] = "";
						break;
					case Cell.CELL_TYPE_FORMULA:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						singleRow[n] = cell.getStringCellValue();
						if (singleRow[n] != null) {
							singleRow[n] = singleRow[n].replaceAll("#N/A", "")
									.trim();
						}
						break;
					default:
						singleRow[n] = "";
						break;
					}
					n++;
				}
				if ("".equals(singleRow[0])) {
					continue;
				}// 如果第一行为空，跳过
				dataList.add(singleRow);
			}
		}

		/**
		 * 根据配置转换数据格式
		 */

		List<PageData> result = new ArrayList<PageData>();
		for (int i = 0; i < dataList.size(); i++) {
			PageData pd = new PageData();
			for (int j = 0; j < dataList.get(i).length; j++) {
				ExcelCellSetting setting = ExcelCellSetting.getByCell(settings,
						j);
				if (setting != null) {
					if (setting.getConvert() != null) {
						try {
							pd.put(setting.getName(), setting.getConvert()
									.convert(dataList.get(i)[j]));
						} catch (Exception e) {
							throw new Exception("第" + (i + 1) + "行的"
									+ e.getMessage());
						}
					} else
						pd.put(setting.getName(), dataList.get(i)[j]);
				}
			}
			result.add(pd);
		}

		return result;
	}

	private static boolean isIgnore(int[] ignore, Row row) {
		for (int i = 0; i < ignore.length; i++) {
			if (row.getRowNum() == ignore[i])
				return false;
		}
		return true;
	}

}
