package dev.rayo.blogambiental.controladores;

import dev.rayo.blogambiental.entidades.Parrafo;
import dev.rayo.blogambiental.entidades.Tematica;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.servicios.ParrafoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// esto es para la conexión de frontend con backend
@CrossOrigin(origins="*")
// para definir la url
@RequestMapping("/parrafos")
public class ParrafoControlador {
    @Autowired
    private ParrafoServicio parrafoServicio;

    @RequestMapping(value = "/listar/{id_Articulo}",method = RequestMethod.GET)
    public List<Parrafo> getAllParrafo(@PathVariable("id_Articulo") Long id_Articulo){
        return parrafoServicio.listarTodosParrafos(id_Articulo);
    }

    @PostMapping("/guardar/{id_Articulo}")
    public Parrafo guardarParrafo(@PathVariable ("id_Articulo") Long idArticulo,@RequestBody String cuerpo) throws MiException {
        System.out.println("el cuerpo registrado es: "+cuerpo);
        return parrafoServicio.registrar(cuerpo,idArticulo);
    }

//    @PostMapping("guardar/{id_Articulo}")
//    public Parrafo guardarParrafo(@PathVariable ("id_Articulo") Long idArticulo,@RequestBody Parrafo parrafoEntrante) throws MiException {
//        return parrafoServicio.registrar(parrafoEntrante,idArticulo);
//    }

    @PutMapping("/modificar/{id_Parrafo}")
    public Parrafo modificarParrafo(@RequestBody String cuerpo,@PathVariable("id_Parrafo") Long id) throws MiException {
        return parrafoServicio.actualizar(id,cuerpo);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarParrafo(@PathVariable("id") Long id) throws MiException {
        parrafoServicio.eliminar(id);

        // Return a 204 (no content) status code
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
