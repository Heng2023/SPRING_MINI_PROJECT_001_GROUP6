package com.homework.spring_mini_project_001_group6.controller;



import com.homework.spring_mini_project_001_group6.model.dto.File;
import com.homework.spring_mini_project_001_group6.service.AppFileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/files")
public class AppFileController {
    private final AppFileService appFileService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {
        String fileName = appFileService.fileUpload(file);
        String fileUrl = "http://localhost:8080/api/v1/files?fileName=" + fileName;
        File fileResponse = new File(fileName,fileUrl,file.getContentType(), file.getSize());
        return ResponseEntity.ok(fileResponse);
    }

    @GetMapping
    public ResponseEntity<?> getFile(@RequestParam String fileName) throws IOException {
        try{
            Resource resource = appFileService.getFileByFileName(fileName);
            MediaType mediaType;
            if (fileName.endsWith(".pdf")){
                mediaType = MediaType.APPLICATION_PDF;
            }
            else if(
                    fileName.endsWith(".jpg") ||
                            fileName.endsWith(".jpeg") ||
                            fileName.endsWith(".png") ||
                            fileName.endsWith(".gif")
            ){
                mediaType = MediaType.IMAGE_PNG;
            }
            else {
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .contentType(mediaType).body(resource);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File do not exist !!!");
        }
    }
}
