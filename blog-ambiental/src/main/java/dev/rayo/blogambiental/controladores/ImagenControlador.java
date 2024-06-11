
package dev.rayo.blogambiental.controladores;

import dev.rayo.blogambiental.entidades.Articulo;
import dev.rayo.blogambiental.entidades.Imagen;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.servicios.ArticuloServicio;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imagen")
@CrossOrigin(origins="*")
public class ImagenControlador {
    
    @Autowired
    private ArticuloServicio articuloServicio;

    @GetMapping("/articulo/{id}")
    public ResponseEntity<List<String>> obtenerImagenesArticulo(@PathVariable("id") Long idArticulo) throws MiException {
        try {
            Articulo articulo = articuloServicio.getById(idArticulo);

            List<String> base64Images = new ArrayList<>();

            for (Imagen imagen : articulo.getImagenes()) {
                byte[] imageData = imagen.getContenido();
                String base64Image = Base64.encodeBase64String(imageData);
                base64Images.add(base64Image);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.status(200).headers(headers).body(base64Images);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

