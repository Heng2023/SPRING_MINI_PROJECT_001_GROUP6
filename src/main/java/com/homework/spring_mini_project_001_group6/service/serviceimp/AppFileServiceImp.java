package com.homework.spring_mini_project_001_group6.service.serviceimp;

import com.homework.spring_mini_project_001_group6.service.AppFileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppFileServiceImp implements AppFileService {
    private final Path path = Paths.get("src/main/resources/images");
    @Override
    public String fileUpload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if (
                file.getOriginalFilename().toLowerCase().contains(".png") ||
                        file.getOriginalFilename().toLowerCase().contains(".jpg") ||
                        file.getOriginalFilename().toLowerCase().contains(".jpeg") ||
                        file.getOriginalFilename().toLowerCase().contains(".gif") ||
                        file.getOriginalFilename().toLowerCase().contains(".pdf") ||
                        file.getOriginalFilename().toLowerCase().contains(".docx") ||
                        file.getOriginalFilename().toLowerCase().contains(".exe") ||
                        file.getOriginalFilename().toLowerCase().contains(".pptx")
        ){
            fileName = UUID.randomUUID()+ "." + StringUtils.getFilenameExtension(fileName);
            if (!Files.exists(path)){
                Files.createDirectories(path);}
            Files.copy(file.getInputStream(), path.resolve(fileName));
            return fileName;
        }else {return "Upload Failed";}
    }

    @Override
    public Resource getFileByFileName(String fileName) throws IOException {
        Path path = Paths.get("src/main/resources/images/" + fileName);
        return new ByteArrayResource(Files.readAllBytes(path));
    }

}