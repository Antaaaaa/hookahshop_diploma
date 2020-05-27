package com.example.hookahshop_diploma.util;

import com.example.hookahshop_diploma.model.ShopOrder;
import com.example.hookahshop_diploma.model.ShopProduct;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelGenerator {
    @SneakyThrows
    public ByteArrayInputStream getExcelOrdersList(List<ShopOrder> shopOrderList){
        String columns[] = {"id", "Ім'я", "Прізвище", "Телефон", "Email","Адреса", "Статус доставки", "Статус замовлення" , "Дата", "Загальна сума"};
        try(
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
        ){
            Sheet sheet = workbook.createSheet("Замовлення");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.AQUA.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }
            int rowId = 1;
            for (ShopOrder order : shopOrderList){
                Row row = sheet.createRow(rowId++);
                row.createCell(0).setCellValue(order.getId());
                row.createCell(1).setCellValue(order.getName());
                row.createCell(2).setCellValue(order.getSurname());
                row.createCell(3).setCellValue(order.getPhone());
                row.createCell(4).setCellValue(order.getEmail());
                row.createCell(5).setCellValue(order.getAddress().getAddress());
                row.createCell(6).setCellValue(order.getIsPaid() ? "Оплачено" : "Оплата при отриманні");
                row.createCell(7).setCellValue(order.getStatus().getStatusName());
                row.createCell(8).setCellValue(order.getDate());
                row.createCell(9).setCellValue(order.getSummary());
            }
            Row row = sheet.createRow(rowId);
            row.createCell(8).setCellValue("Разом");
            row.createCell(9).setCellFormula(String.format("SUM(%s:%s)", "J2", "J"+rowId));

            CellStyle yellowStyle = workbook.createCellStyle();
            yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            row.getCell(9).setCellStyle(yellowStyle);

            autoSizeColumns(workbook);
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    @SneakyThrows
    public ByteArrayInputStream getProductExcel(List<ShopProduct> shopProductList){
        String columns[] = {"id", "Товар", "Наявна кількість", "Ціна"};
        try(    Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();){
            Sheet sheet = workbook.createSheet("Товари");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.AQUA.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++){
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }
            int rowId = 1;
            for (ShopProduct product : shopProductList){
                Row row = sheet.createRow(rowId++);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getAmount());
                if (product.getAmount() == 0) {
                    CellStyle redStyle = workbook.createCellStyle();
                    Font redFont = workbook.createFont();
                    redFont.setColor(IndexedColors.WHITE.getIndex());
                    redStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                    redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    redStyle.setFont(redFont);
                    row.getCell(2).setCellStyle(redStyle);
                }
                row.createCell(3).setCellValue(product.getPrice());
            }
            autoSizeColumns(workbook);
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private static void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(sheet.getFirstRowNum());
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                }
            }
        }
    }
}
