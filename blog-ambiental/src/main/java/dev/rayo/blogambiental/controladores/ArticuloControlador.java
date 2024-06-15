package dev.rayo.blogambiental.controladores;

import dev.rayo.blogambiental.dto.ArticuloDTO;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.servicios.ArticuloServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/articulo")
public class ArticuloControlador {

    @Autowired
    private ArticuloServicio articuloServicio;

    @GetMapping("/listar")
    public ResponseEntity<List<ArticuloDTO>> listaArticulos() {
        try {
            List<ArticuloDTO> articulos = articuloServicio.listarArticulosRelevantes();
            return ResponseEntity.status(200).body(articulos);
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<ArticuloDTO> obtenerArticulo(@PathVariable("id") Long idArticulo) {
        try {
            ArticuloDTO articulo = articuloServicio.obtenerArticuloPorId(idArticulo);
            return ResponseEntity.status(200).body(articulo);
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(null);
        }
    }


    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<ArticuloDTO>> listaArticulosUsuario(@PathVariable("id") Long idUsuario) {
        try {
            List<ArticuloDTO> articulos = articuloServicio.listarArticulosDeUsuario(idUsuario);
            return ResponseEntity.status(200).body(articulos);
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/guardar/{id}")
    public ResponseEntity<Object> articulo(
            @PathVariable("id") Long idUsuario,
            @RequestParam String titulo,
            @RequestParam(required = false) List<MultipartFile> archivos,
            @RequestParam List<String> parrafos,
            @RequestParam List<Long> tematicas,
            @RequestParam List<Long> tipos
    ) {
        try {
            ArticuloDTO articuloDTO = articuloServicio.registrarArticulo(idUsuario, titulo, archivos, parrafos, tematicas, tipos);
            return ResponseEntity.status(HttpStatus.CREATED).body(articuloDTO);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<Object> actualizar(
            @PathVariable("id") Long idArticulo,
            @RequestParam String titulo,
            @RequestParam(required = false) List<MultipartFile> archivos,
            @RequestParam List<String> parrafos,
            @RequestParam List<Long> tematicas,
            @RequestParam List<Long> tipos
    ) {
        try {
            ArticuloDTO articuloDTO = articuloServicio.actualizarArticulo(idArticulo, titulo, archivos, parrafos, tematicas, tipos);
            return ResponseEntity.status(HttpStatus.CREATED).body(articuloDTO);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Long articuloId) {
        try {
            articuloServicio.eliminarArticulo(articuloId);
            return ResponseEntity.status(200).body("Articulo eliminado");
        } catch (Exception ex) {
            return ResponseEntity.status(404).body("Error al eliminar");
        }
    }
}
