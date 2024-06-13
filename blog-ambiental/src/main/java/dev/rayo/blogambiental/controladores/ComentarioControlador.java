package dev.rayo.blogambiental.controladores;

import dev.rayo.blogambiental.entidades.Comentario;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.servicios.ComentarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/comentarios")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/listar/{idArticulo}")
    public ResponseEntity<List<Comentario>> getAllComentarios(@PathVariable("idArticulo") Long idArticulo) {
        List<Comentario> comentarios = comentarioServicio.listarTodosComentarios(idArticulo);
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

    @PostMapping("/guardar/{idArticulo}")
    public ResponseEntity<Comentario> guardarComentario(@PathVariable("idArticulo") Long idArticulo, @RequestBody String cuerpo) throws MiException {
        Comentario comentario = comentarioServicio.registrar(cuerpo, idArticulo);
        return new ResponseEntity<>(comentario, HttpStatus.CREATED);
    }

    @PutMapping("/modificar/{idComentario}")
    public ResponseEntity<Comentario> modificarComentario(@PathVariable("idComentario") Long id, @RequestBody String cuerpo) throws MiException {
        Comentario comentario = comentarioServicio.actualizar(id, cuerpo);
        return new ResponseEntity<>(comentario, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable("id") Long id) throws MiException {
        comentarioServicio.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
