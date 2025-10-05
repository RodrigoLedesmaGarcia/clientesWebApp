package com.spring.www.ms_clientes.controllers;

import com.spring.www.ms_clientes.entity.Cliente;
import com.spring.www.ms_clientes.services.ClienteServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/clientes/fotografia")
public class ClienteFotoController {

    private final ClienteServiceImpl service;

    public ClienteFotoController(ClienteServiceImpl service) {
        this.service = service;
    }


    @PostMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> subirFoto(@PathVariable Long id,
                                            @RequestParam("file") MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Archivo vacío");
        }

        Cliente c = service.buscarClientePorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        String ct = file.getContentType();

        if (ct == null || ct.isBlank()) {
            String name = file.getOriginalFilename();
            if (name != null && name.contains(".")) {
                String ext = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
                if (ext.equals("jpg") || ext.equals("jpeg")) ct = "image/jpeg";
                else if (ext.equals("png")) ct = "image/png";
                else if (ext.equals("gif")) ct = "image/gif";
                else ct = "application/octet-stream";
            } else {
                ct = "application/octet-stream";
            }
        }

        if (!ct.startsWith("image/")) {
            return ResponseEntity.badRequest().body("El archivo no parece una imagen");
        }

        c.setFotoNombre(file.getOriginalFilename());
        c.setFotoTipo(ct);
        c.setFotoTamano(file.getSize());
        c.setFotoDatos(file.getBytes());

        service.crear(c);
        return ResponseEntity.ok("Foto subida correctamente");
    }


    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> obtenerFoto(@PathVariable Long id) {
        Cliente c = service.buscarClientePorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        byte[] data = c.getFotoDatos();
        if (data == null || data.length == 0) {
            return ResponseEntity.notFound().build();
        }

        String ct = c.getFotoTipo();

        MediaType mediaType;
        if (ct != null && !ct.isBlank()) {
            try {
                mediaType = MediaType.parseMediaType(ct);
            } catch (Exception ex) {
                mediaType = MediaTypeFactory.getMediaType(c.getFotoNombre())
                        .orElse(MediaType.APPLICATION_OCTET_STREAM);
            }
        } else {
            mediaType = MediaTypeFactory.getMediaType(c.getFotoNombre())
                    .orElse(MediaType.APPLICATION_OCTET_STREAM);
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + (c.getFotoNombre() == null ? "foto" : c.getFotoNombre()) + "\"")
                .body(data);
    }
} // fin del controller
