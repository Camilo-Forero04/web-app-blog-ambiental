
package dev.rayo.blogambiental.servicios;

import dev.rayo.blogambiental.entidades.Articulo;
import dev.rayo.blogambiental.entidades.Tematica;
import dev.rayo.blogambiental.entidades.Tipo;
import dev.rayo.blogambiental.enumeraciones.Problematica;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.repositorios.ArticuloRepositorio;
import dev.rayo.blogambiental.repositorios.TematicaRepositorio;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TematicaServicio {
    
    @Autowired
    private TematicaRepositorio tematicaRepo;
    
    @Autowired
    private ArticuloRepositorio articuloRepo;

    private void validar(String nombre)throws MiException {
        if (nombre.isEmpty()) {
            throw new MiException("El nombre no puede estar vacio");
        }
    }

    @Transactional
    @PostConstruct
    private void iniciarlizar() {
        List<Tematica> tematicas = tematicaRepo.findAll();
        if (tematicas.isEmpty()) {
            for (Problematica nombre : Problematica.values()) {
                Tematica tematica = new Tematica();
                tematica.setNombre(nombre.toString());
                tematicaRepo.save(tematica);
            }
        }
    }
    
    @Transactional
    public Tematica asignarArticuloATematica(Long tematicaId, Long articuloId){
        Optional<Tematica> respuestaTematica = tematicaRepo.findById(tematicaId);
        Optional<Articulo> respuestaArticulo = articuloRepo.findById(articuloId);
        if (respuestaArticulo.isPresent() && respuestaTematica.isPresent()) {
            Tematica tematica = respuestaTematica.get();
            Articulo articulo = respuestaArticulo.get();
            tematica.getArticulos().add(articulo);
            return tematicaRepo.save(tematica);
        }
        return null;
    }
    
    @Transactional 
    public Tematica crearNuevaTematica(Tematica tematicaEntrada){
        String nombre = tematicaEntrada.getNombre();
        Tematica tematica = new Tematica();
        tematica.setNombre(nombre);
        tematicaRepo.save(tematica);
        return tematica;
    }
    
    @Transactional
    public Tematica actualizar(Long idTematica,Tematica tematicaEntrada) throws MiException {
        String nuevoNombre = tematicaEntrada.getNombre();
        validar(nuevoNombre);
        Optional<Tematica> respuesta = tematicaRepo.findById(idTematica);

        if (respuesta.isPresent()) {
            Tematica tematica = respuesta.get();
            tematica.setNombre(nuevoNombre);
            tematicaRepo.save(tematica);
            return tematica;
        }else {
            return  null;
        }
    }
    
    @Transactional
    public void eliminar(Long idTematica){
        tematicaRepo.deleteById(idTematica);
    }

    @Transactional
    public List<Tematica> listarTodasTematicas(){
        List<Tematica> tematicas = null;
        tematicas = tematicaRepo.findAll();
        return tematicas;
    }
    
}  

