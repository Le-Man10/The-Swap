package com.example.The_Swap.Services;

import com.example.The_Swap.Exceptions.EmptyFileException;
import com.example.The_Swap.Exceptions.WrongFileFormatException;
import com.example.The_Swap.Interface.Query;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class Swap implements Query<MultipartFile,Resource> {

    @Override
    public ResponseEntity<Resource> execute(MultipartFile Input) {
        Workbook book = new HSSFWorkbook();
        Sheet sheet1 = book.createSheet("Sheet1");
        Row header = sheet1.createRow(0);
        Cell GroupNo = header.createCell(0);
        GroupNo.setCellValue("GroupNo");
        Cell ConsultantOrClientName = header.createCell(1);
        ConsultantOrClientName.setCellValue("Consultant/Client Name");
        Cell ConsultantOrClientEmail = header.createCell(2);
        ConsultantOrClientEmail.setCellValue("Consultant/Client Email");
        Cell StudentNo = header.createCell(3);
        StudentNo.setCellValue("Student#");
        Cell Surname = header.createCell(4);
        Surname.setCellValue("Surname");
        Cell FullNames = header.createCell(5);
        FullNames.setCellValue("FullNames");
        int i = 1;
        int x = 1;


        String contentType = Input.getContentType();
        String fileName = Input.getOriginalFilename();
        if(Input.isEmpty()){
            throw new EmptyFileException();
        } else if (contentType.equals("application/vnd.ms-excel") || contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            try {
                Sheet sheetreceive;
                try (Workbook wb = WorkbookFactory.create(new File(fileName))) {
                    sheetreceive = wb.getSheetAt(0);
                }
                for(Row row : sheetreceive ){
                    int Sec = i+1;
                    Cell cellperc = row.getCell(6);
                    Row row2 = sheetreceive.getRow(row.getRowNum()+x);
                    Cell cellperc2 = row2.getCell(6);
                    Cell ClientName = row.getCell(1);
                    Cell Clientemail = row.getCell(2);
                    Cell RowStudentNo = row.getCell(3);
                    Cell RowSurname = row.getCell(4);
                    Cell RowFullname = row.getCell(5);
                    double diffpercentage = cellperc2.getNumericCellValue()-cellperc.getNumericCellValue();
                    if(diffpercentage!=0) {
                        while (!(-5 <= diffpercentage && diffpercentage <= 5) && row2.getRowNum()!=sheetreceive.getLastRowNum()) {
                            x++;
                        }

                    }
                    Cell RowStudentNo2 = row2.getCell(3);
                    Cell RowSurname2 = row2.getCell(4);
                    Cell RowFullname2 = row2.getCell(5);
                    Row SortingRow = sheet1.createRow(i);
                    Cell SwappedGrpNo = SortingRow.createCell(0);
                    SwappedGrpNo.setCellValue(1);
                    Cell SwappedConsultantName = SortingRow.createCell(1);
                    SwappedConsultantName.setCellValue(ClientName.getStringCellValue());
                    Cell SwappedConsaltantEmail = SortingRow.createCell(2);
                    SwappedConsaltantEmail.setCellValue(Clientemail.getStringCellValue());
                    sheet1.addMergedRegion(new CellRangeAddress(
                            i, // First row index (inclusive)
                            Sec, // Last row index (inclusive)
                            0, // First column index (inclusive)
                            0  // Last column index (inclusive)
                    ));
                    sheet1.addMergedRegion(new CellRangeAddress(
                            i, // First row index (inclusive)
                            Sec, // Last row index (inclusive)
                            1, // First column index (inclusive)
                            1  // Last column index (inclusive)
                    ));
                    sheet1.addMergedRegion(new CellRangeAddress(
                            i, // First row index (inclusive)
                            Sec, // Last row index (inclusive)
                            2, // First column index (inclusive)
                            2  // Last column index (inclusive)
                    ));
                    Cell SwappedStudentNo = SortingRow.createCell(3);
                    SwappedStudentNo.setCellValue(RowStudentNo.getNumericCellValue());
                    Cell SwappedStudentSurname = SortingRow.createCell(4);
                    SwappedStudentSurname.setCellValue(RowSurname.getStringCellValue());
                    Cell SwappedStudentFN = SortingRow.createCell(5);
                    SwappedStudentFN.setCellValue(RowFullname.getStringCellValue());

                    Row SortingRow2 = sheet1.createRow(Sec);
                    Cell SwappedStudentNo2 = SortingRow2.createCell(3);
                    SwappedStudentNo2.setCellValue(RowStudentNo2.getNumericCellValue());
                    Cell SwappedStudentSurname2 = SortingRow2.createCell(4);
                    SwappedStudentSurname2.setCellValue(RowSurname2.getStringCellValue());
                    Cell SwappedStudentFN2 = SortingRow2.createCell(5);
                    SwappedStudentFN2.setCellValue(RowFullname2.getStringCellValue());
                    sheetreceive.removeRow(row);
                    sheetreceive.removeRow(row2);
                    i = Sec + 1;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                ByteArrayOutputStream fileout = new ByteArrayOutputStream();
                book.write(fileout);
                book.close();
                byte[] SwappedFbytes = fileout.toByteArray();
                Resource SwappedExcelF = new ByteArrayResource(SwappedFbytes);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Group Pairings.xls");
                headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

                return ResponseEntity.ok().headers(headers).body(SwappedExcelF);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            throw new WrongFileFormatException();
        }
    }
}
