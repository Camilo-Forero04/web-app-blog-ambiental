/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.rayo.blogambiental.controladores;

import dev.rayo.blogambiental.entidades.Articulo;
import dev.rayo.blogambiental.entidades.Imagen;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.servicios.ArticuloServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    private ArticuloServicio articuloServicio;
    
    @GetMapping("/articulo/{id}")
    public ResponseEntity<List<byte[]>> obtenerImagenesArticulo(@PathVariable Long idArticulo) throws MiException {
        
        Articulo articulo = articuloServicio.getById(idArticulo);
        List<byte[]> imagenesBytes=null;
        
        for (Imagen i : articulo.getImagenes()) {
             imagenesBytes.add(i.getContenido()); 
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagenesBytes, headers, HttpStatus.OK);
    }
}

