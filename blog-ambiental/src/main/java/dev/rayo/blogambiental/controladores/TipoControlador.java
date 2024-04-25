package dev.rayo.blogambiental.controladores;

import dev.rayo.blogambiental.entidades.Tipo;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.servicios.TipoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// esto es para la conexión de frontend con backend
@CrossOrigin(origins="*")
// para definir la url
@RequestMapping("/tipos")

public class TipoControlador {
    @Autowired
    private TipoServicio tipoServicio;

    @RequestMapping(value = "/listar",method = RequestMethod.GET)
    public List<Tipo> getAllTypes(){
        return tipoServicio.listarTodosServicios();
    }

    @PostMapping("guardar")
    public Tipo guardarTipo(@RequestBody Tipo tipoEntrada) throws MiException {
        return tipoServicio.registrar(tipoEntrada);
    }

    @PutMapping("modificar/{id}")
    public Tipo modificarTipo(@RequestBody Tipo tipoEntrada,@PathVariable("id") Long id) throws MiException {
        return tipoServicio.actualizar(id,tipoEntrada);
    }

    @DeleteMapping("eliminar/{id}")
    public void eliminarTipo(@PathVariable("id") Long id) throws MiException {
        tipoServicio.eliminar(id);
    }
}
