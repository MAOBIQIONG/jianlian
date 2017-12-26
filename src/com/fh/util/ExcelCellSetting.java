 package com.fh.util;
 
 import java.util.ArrayList;
import java.util.List;
 
 public class ExcelCellSetting
 {
   private String name;
   private int cell;
   private IExcelCellDataConvert convert;
 
   public static List<ExcelCellSetting> getList(String name, int cell, IExcelCellDataConvert iRevert)
   {
     List<ExcelCellSetting> list = new ArrayList<ExcelCellSetting>();
     return getList(name, cell, iRevert, list);
   }
 
   public static List<ExcelCellSetting> getList(String name, int cell, IExcelCellDataConvert iRevert, List<ExcelCellSetting> list)
   {
     list.add(new ExcelCellSetting(name, cell, iRevert));
     return list;
   }
 
   public static ExcelCellSetting getByCell(List<ExcelCellSetting> cellSettings, int cell) {
     for (ExcelCellSetting excelCellSetting : cellSettings) {
       if (excelCellSetting.getCell() == cell)
         return excelCellSetting;
     }
     return null;
   }
 
   public ExcelCellSetting()
   {
   }
 
   public ExcelCellSetting(String name, int cell, IExcelCellDataConvert convert)
   {
     this.name = name;
     this.cell = cell;
     this.convert = convert;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public int getCell() {
     return this.cell;
   }
 
   public void setCell(int cell) {
     this.cell = cell;
   }
 
   public IExcelCellDataConvert getConvert() {
     return this.convert;
   }
 
   public void setConvert(IExcelCellDataConvert convert) {
     this.convert = convert;
   }
 }
