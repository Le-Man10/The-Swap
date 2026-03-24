package com.example.The_Swap.Services;

import com.example.The_Swap.Exceptions.EmptyFileException;
import com.example.The_Swap.Exceptions.WrongFileFormatException;
import com.example.The_Swap.Interface.AiChatService;
import com.example.The_Swap.Interface.Query;
import com.example.The_Swap.Model.Group;
import com.example.The_Swap.Model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.*;

import java.io.*;

@Service
public class Swap implements Query<MultipartFile,Resource> {
    private ObjectMapper mapper = new ObjectMapper();
    private final AiChatService aiChatService;
    private static int i = 1;
    private static int x = 1;
    private static Sheet sheet1;
    private static Sheet sheetreceive;

    public Swap(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }


    @Override
    public ResponseEntity<Resource> execute(MultipartFile Input) {
        Workbook book = new HSSFWorkbook();
        sheet1 = book.createSheet("Sheet1");
        Row header = sheet1.createRow(0);
        Cell groupNo = header.createCell(0);
        groupNo.setCellValue("GroupNo");
        Cell consultantOrClientName = header.createCell(1);
        consultantOrClientName.setCellValue("Consultant/Client Name");
        Cell consultantOrClientEmail = header.createCell(2);
        consultantOrClientEmail.setCellValue("Consultant/Client Email");
        Cell studentNo = header.createCell(3);
        studentNo.setCellValue("Student#");
        Cell surname = header.createCell(4);
        surname.setCellValue("Surname");
        Cell fullNames = header.createCell(5);
        fullNames.setCellValue("Full Names");
        Cell attendance = header.createCell(6);
        attendance.setCellValue("Attendance Rate");



        String contentType = Input.getContentType();
        String fileName = Input.getOriginalFilename();
        System.out.println(fileName);
        if(Input.isEmpty()){
            throw new EmptyFileException();
        } else if (contentType.equals("application/vnd.ms-excel") || contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            try {
                     Workbook wb = WorkbookFactory.create(Input.getInputStream()) ;
                    sheetreceive = wb.getSheetAt(0);
            } catch (IOException e) {
                throw new RuntimeException("no excel sheet detected");
            }

            try {
                String jsonPreSwapList = convertExcelToJson(sheetreceive);
                System.out.println(jsonPreSwapList);
                String jsonPostSwapList = aiChatService.chat(jsonPreSwapList);
                List<Group> postSwapList = mapper.readerForListOf(Group.class).readValue(jsonPostSwapList);
                int i = 1;
                int studentCount = 0;
                for(Group group:postSwapList){
                    while(studentCount< 2){
                        int sec = i++;
                        if (i%2==1){
                            Row groupDetails = sheet1.createRow(i);
                            groupDetails.createCell(0).setCellValue(group.getGroupNo());
                            groupDetails.createCell(1).setCellValue(group.getClientName());
                            groupDetails.createCell(2).setCellValue(group.getClientEmail());
                            groupDetails.createCell(3).setCellValue(group.getPartners().getFirst().getStudentNo());
                            groupDetails.createCell(4).setCellValue(group.getPartners().getFirst().getFullName());
                            groupDetails.createCell(5).setCellValue(group.getPartners().getFirst().getSurname());
                            groupDetails.createCell(6).setCellValue(group.getPartners().getFirst().getAttendanceRate());
                        }else {
                            Row existingGroupDetails = sheet1.createRow(i);
                            existingGroupDetails.createCell(3).setCellValue(group.getPartners().getLast().getStudentNo());
                            existingGroupDetails.createCell(4).setCellValue(group.getPartners().getLast().getFullName());
                            existingGroupDetails.createCell(5).setCellValue(group.getPartners().getLast().getSurname());
                            existingGroupDetails.createCell(6).setCellValue(group.getPartners().getLast().getAttendanceRate());
                            sheet1.addMergedRegion(new CellRangeAddress(
                                    i, // First row index (inclusive)
                                    sec, // Last row index (inclusive)
                                    0, // First column index (inclusive)
                                    0  // Last column index (inclusive)
                            ));
                            sheet1.addMergedRegion(new CellRangeAddress(
                                    i, // First row index (inclusive)
                                    sec, // Last row index (inclusive)
                                    1, // First column index (inclusive)
                                    1  // Last column index (inclusive)
                            ));
                            sheet1.addMergedRegion(new CellRangeAddress(
                                    i, // First row index (inclusive)
                                    sec, // Last row index (inclusive)
                                    2, // First column index (inclusive)
                                    2  // Last column index (inclusive)
                            ));
                        }
                        i++;
                        studentCount++;
                    }
                }
                ByteArrayOutputStream fileout = new ByteArrayOutputStream();
                book.write(fileout);
                book.close();
                byte[] SwappedFbytes = fileout.toByteArray();
                Resource SwappedExcelF = new ByteArrayResource(SwappedFbytes);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Group Pairings.xls");
                headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                return ResponseEntity.ok().headers(headers).body(SwappedExcelF);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            throw new WrongFileFormatException();
        }
    }


        public String convertExcelToJson(Sheet sheet) throws Exception {

            List<Group> preSwapList = new ArrayList<>();

            DataFormatter formatter = new DataFormatter();

            // 2. STATE TRACKER: This remembers the car name for "empty" rows
            int groupNumber = 0;

            for (Row row : sheet) {
                // Get raw values from Column 0 (Car) and Column 1 (Person)
                int groupCell = Integer.parseInt(formatter.formatCellValue(row.getCell(0)));
                String clientName = formatter.formatCellValue(row.getCell(1));
                String clientEmail = formatter.formatCellValue(row.getCell(2));
                long studentNo = Long.parseLong(formatter.formatCellValue(row.getCell(3)));
                String studentSurname = formatter.formatCellValue(row.getCell(4));
                String studentFullNames = formatter.formatCellValue(row.getCell(5));
                int attendanceRate = Integer.parseInt(formatter.formatCellValue(row.getCell(6)));


                // Skip completely empty rows


                // 3. LOGIC: Update currentCarName ONLY if the cell isn't empty
                // If the cell is empty (like row 2 of a pair), it keeps the previous value
                if (groupCell != 0) {
                    groupNumber = groupCell;
                    Student studentDetails = new Student(studentNo,studentSurname,studentFullNames,attendanceRate);
                    List<Student> students = new ArrayList<>();
                    students.add(studentDetails);
                    Group pair = new Group(groupNumber,clientName,clientEmail, students);
                    preSwapList.add(pair);
                } else{
                    int groupIndex = groupNumber-1;
                    Student studentDetails = new Student(studentNo,studentSurname,studentFullNames,attendanceRate);
                    preSwapList.get(groupIndex).getPartners().add(studentDetails);
                }
            }

            // 6. Convert the list of unique Pairs into a JSON string
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(preSwapList);
        }


}
