package dev.rayo.blogambiental.controladores;

import dev.rayo.blogambiental.entidades.Tematica;
import dev.rayo.blogambiental.entidades.Tipo;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.servicios.TematicaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// esto es para la conexión de frontend con backend
@CrossOrigin(origins="*")
// para definir la url
@RequestMapping("/tematicas")
public class TematicaControlador {

    @Autowired
    private TematicaServicio tematicaServicio;

    @RequestMapping(value = "/listar",method = RequestMethod.GET)
    public List<Tematica> getAllTematics(){
        return tematicaServicio.listarTodasTematicas();
    }

    @PostMapping("/guardar")
    public Tematica guardarTematica(@RequestBody Tematica tematicaEntrada) throws MiException {
        return tematicaServicio.crearNuevaTematica(tematicaEntrada);
    }

    @PutMapping("/modificar/{id}")
    public Tematica modificarTematica(@RequestBody Tematica tematicaEntrada,@PathVariable("id") Long id) throws MiException {
        return tematicaServicio.actualizar(id,tematicaEntrada);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTematica(@PathVariable("id") Long id) throws MiException {
        tematicaServicio.eliminar(id);

        // Return a 204 (no content) status code
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
