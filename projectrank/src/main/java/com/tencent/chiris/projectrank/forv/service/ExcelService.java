package com.tencent.chiris.projectrank.forv.service;

import com.tencent.chiris.projectrank.forv.model.OldData;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelService {

    private static List<OldData> dataList = new ArrayList<>();

    public void readFile(String filePath) throws IOException, InvalidFormatException {
        Workbook wb = WorkbookFactory.create(new File(filePath)); //拿到文件
        Sheet sheet = wb.getSheetAt(0); //读取第一张表
        int lastRowNum = sheet.getLastRowNum();//获取到总行数 -->最后一行就是总行数
        for (int i = 1; i <= lastRowNum; i++) {
            OldData data = new OldData();
            Row row = sheet.getRow(i); //拿每一行
            for (int j = 0; j < 6; j++) {
                Cell cell = row.getCell(j);
                if (j == 0){
                    data.setCompanyName(cell.getStringCellValue());
                }else {
                    if (cell == null){
                        data.getData().add(null);
                    }else {
                        data.getData().add(cell.getNumericCellValue());
                    }
                }
            }
            System.out.println(data);
            dataList.add(data);
        }
    }

    public void writeFile() throws IOException {
        //在内存中创建一个Excel文件
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        //给Excel文件创建一张 Sheet1表
        Sheet sheet = workbook.createSheet("vv");//table --》下面的Sheet1名
        int rowNum = 2, col = 0, year = 2015;
        for (OldData oldData : dataList) {
            List<Double> list = oldData.getData();
            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(rowNum);
                // 写公司名
                Cell cell1 = row.createCell(col);
                cell1.setCellValue(oldData.getCompanyName());
                col ++;

                //写年份
                Cell cell2 = row.createCell(col);
                cell2.setCellValue("v" + (year + i));
                col++;

                // 写数字
                if (list.get(i) != null){
                    Cell cell3 = row.createCell(col);
                    cell3.setCellValue(list.get(i));
                }
                rowNum++;
                col = 0;

            }
        }
        //文件输出流--》把内存中的excel文件写到磁盘中
        FileOutputStream fos = new FileOutputStream("hello.xlsx");
        workbook.write(fos);
        fos.close();

    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        ExcelService service = new ExcelService();
        System.out.println("start reading file...");
        service.readFile("D:\\data\\reagape.xlsx");
        System.out.println("start write file...");
        service.writeFile();
        System.out.println("completed");
    }
}
