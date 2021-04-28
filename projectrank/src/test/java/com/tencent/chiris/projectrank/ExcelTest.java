package com.tencent.chiris.projectrank;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelTest {

    @Test
    public void test1() throws IOException, InvalidFormatException {
        Workbook wb = WorkbookFactory.create(new File("D:\\data\\reagape.xlsx")); //拿到文件
        Sheet sheet = wb.getSheetAt(0); //读取第一张表
        int lastRowNum = sheet.getLastRowNum();//获取到总行数 -->最后一行就是总行数
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i); //拿每一行
            ArrayList<Double> datas = new ArrayList<>(6);
            short lastCellNum = row.getLastCellNum(); //拿到对应行的总列数
            for (int j = 0; j < 6; j++) {
                Cell cell = row.getCell(j);
                if (cell == null){
                    continue;
                }
                if (j == 0){
                    System.out.println(cell.getStringCellValue() + "----");
                }else {
                    System.out.println(cell.getNumericCellValue() + "----");
                }
            }
            System.out.println();
        }

    }
}
