package com.petapp.backend.serviceImpl;

import com.petapp.backend.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path uploadLocation = Paths.get("uploads");

    public FileStorageServiceImpl() {
        try {
            Files.createDirectories(uploadLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de subidas", e);
        }
    }

    @Override
    public String saveFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path destinationPath = this.uploadLocation.resolve(fileName);
            Files.copy(file.getInputStream(), destinationPath);

            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }
}