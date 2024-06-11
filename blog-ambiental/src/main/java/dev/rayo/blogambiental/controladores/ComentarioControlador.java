package dev.rayo.blogambiental.controladores;

import dev.rayo.blogambiental.entidades.Comentario;
import dev.rayo.blogambiental.entidades.Parrafo;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.servicios.ComentarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// esto es para la conexión de frontend con backend
@CrossOrigin(origins="*")
// para definir la url
@RequestMapping("/comentarios")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @RequestMapping(value = "/listar/{id_Articulo}",method = RequestMethod.GET)
    public List<Comentario> getAllComentarios(@PathVariable("id_Articulo") Long id_Articulo){
        return comentarioServicio.listarTodosComentarios(id_Articulo);
    }

    @PostMapping("/guardar/{id_Articulo}")
    public Comentario guardarComentario(@PathVariable ("id_Articulo") Long idArticulo,@RequestBody String cuerpo) throws MiException {
        return comentarioServicio.registrar(cuerpo,idArticulo);
    }

    @PutMapping("/modificar/{id_Comentario}")
    public Comentario modificarParrafo(@RequestBody String cuerpo,@PathVariable("id_Comentario") Long id) throws MiException {
        return comentarioServicio.actualizar(id,cuerpo);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarComentario(@PathVariable("id") Long id) throws MiException {
        comentarioServicio.eliminar(id);


    }
}
