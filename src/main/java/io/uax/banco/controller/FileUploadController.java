package io.uax.banco.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class FileUploadController {


    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            // Define la ruta donde se guardarán los archivos subidos
            Path destinationPath = Paths.get("src/main/resources/" + file.getOriginalFilename());

            // Guarda el archivo en la ruta especificada
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // Guarda el nombre del archivo en la sesión
            session.setAttribute("uploadedFileName", file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Redirige al usuario a /importCustomers después de subir el archivo
        return "redirect:/importCustomers";
    }
}
