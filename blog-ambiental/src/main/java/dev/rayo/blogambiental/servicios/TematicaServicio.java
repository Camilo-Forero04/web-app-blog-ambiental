
package dev.rayo.blogambiental.servicios;

import dev.rayo.blogambiental.entidades.Articulo;
import dev.rayo.blogambiental.entidades.Tematica;
import dev.rayo.blogambiental.enumeraciones.Problematica;
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
    public Tematica asignarArticuloATipo(Long tipoId, Long articuloId){
        Optional<Tematica> respuestaTematica = tematicaRepo.findById(tipoId);
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
    public void crearNuevaTematica(String nombre){
        Tematica tematica = new Tematica();
        tematica.setNombre(nombre);
        tematicaRepo.save(tematica);
    }
    
    @Transactional
    public void actualizar(Long idTematica,String nuevoNombre){
        Optional<Tematica> respuesta = tematicaRepo.findById(idTematica);
        if (respuesta.isPresent()) {
            Tematica tematica = respuesta.get();
            tematica.setNombre(nuevoNombre);
            tematicaRepo.save(tematica);
        }
    }
    
    @Transactional
    public void eliminar(Long idTematica){
        tematicaRepo.deleteById(idTematica);
    }
    
}  

