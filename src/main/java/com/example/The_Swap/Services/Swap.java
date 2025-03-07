package com.example.The_Swap.Services;

import com.example.The_Swap.Exceptions.EmptyFileException;
import com.example.The_Swap.Exceptions.WrongFileFormatException;
import com.example.The_Swap.Interface.Query;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Swap implements Query<MultipartFile,Resource> {

    @Override
    public ResponseEntity<Resource> execute(MultipartFile Input) {
        String contentType = Input.getContentType();
        String fileName = Input.getOriginalFilename();
        if(Input.isEmpty()){
            throw new EmptyFileException();
        } else if (contentType.equals("application/vnd.ms-excel") || contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            for()
        }else {
            throw new WrongFileFormatException();
        }
        return null;
    }
}
