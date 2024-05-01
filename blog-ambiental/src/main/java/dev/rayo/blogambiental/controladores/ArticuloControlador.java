
package dev.rayo.blogambiental.controladores;

import dev.rayo.blogambiental.entidades.Articulo;

import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.servicios.ArticuloServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/articulo")
@CrossOrigin("*")
public class ArticuloControlador {
    
    @Autowired
    private ArticuloServicio articuloServicio;
    
    @GetMapping
    public ResponseEntity<List<Articulo>> listaArticulos(){
        try{
            List<Articulo> articulos = articuloServicio.listarArticulosRelevantes();
            return ResponseEntity.status(200).body(articulos);
        }catch(Exception ex){
            return ResponseEntity.status(404).body(null);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<List<Articulo>> listaArticulosUsuario(@PathVariable("id") Long idUsuario){
        try{
            List<Articulo> articulos = articuloServicio.listarArticulosDeUsuario(idUsuario);
            return ResponseEntity.status(200).body(articulos);
        }catch(Exception ex){
            return ResponseEntity.status(404).body(null);
        }
    }
    
    @PostMapping("/agregar/{id}")
    public ResponseEntity<Object> articulo(
            @PathVariable("id") Long idUsuario,
            @RequestParam String titulo,
            @RequestParam List<MultipartFile> archivos,
            @RequestParam List<String> parrafos,
            @RequestParam List<Long> tematicasId,
            @RequestParam List<Long> tiposId
    ){
        try{
            System.out.println(tematicasId);
            System.out.println(tiposId);
            Articulo articulo = articuloServicio.registrarArticulo(idUsuario , titulo, archivos, parrafos, tematicasId, tiposId);
            return ResponseEntity.status(201).body(articulo);
        }catch(Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
//    @PutMapping("/actualizar/{id}")
//    public ResponseEntity<Object> actualizar(
//            @PathVariable("id") Long idArticulo,
//            @RequestParam String titulo,
//            @RequestParam List<MultipartFile> archivos,
//            @RequestParam List<String> parrafos,
//            @RequestParam List<Tematica> tematicas,
//            @RequestParam List<Tipo> tipos
//    ){
//        try{
//            Articulo articulo = articuloServicio.actualizarArticulo(idArticulo, titulo, archivos, parrafos, tematicas, tipos);
//            return ResponseEntity.status(201).body(articulo);
//        }catch(Exception ex){
//            return ResponseEntity.status(404).body(ex.getMessage());
//        }
//    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Long articuloId){
        try{
            articuloServicio.eliminarArticulo(articuloId);
            return ResponseEntity.status(200).body("Articulo eliminado");
        }catch(Exception ex){
            return ResponseEntity.status(404).body("Error al eliminar");   
        }
    }
}
