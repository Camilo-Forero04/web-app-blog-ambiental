package dev.rayo.blogambiental.servicios;

import dev.rayo.blogambiental.entidades.Articulo;
import dev.rayo.blogambiental.entidades.Parrafo;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.repositorios.ArticuloRepositorio;
import dev.rayo.blogambiental.repositorios.ParrafoRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ParrafoServicio {

    @Autowired
    private ParrafoRepositorio parrafoRepositorio;

    @Autowired
    private ArticuloRepositorio articuloRepositorio;

    private void validar(String cuerpo)throws MiException {
        if (cuerpo.isEmpty()) {
            throw new MiException("El cuerpo del parrafo no puede estar vacio");
        }
        // no se valida la fecha porque esa se tomará automaticamente del sistema
    }

    @Transactional
    private void registrar(String cuerpo,  Long idArticulo) throws MiException {
        validar(cuerpo);
        Parrafo parrafo = new Parrafo();
        Optional<Articulo> respuesta = articuloRepositorio.findById(idArticulo);

        if (respuesta.isPresent()){
            Articulo articulo = respuesta.get();
            parrafo.setCuerpo(cuerpo);
            parrafo.setArticulo(articulo);
            parrafoRepositorio.save(parrafo);
        }
    }

    @Transactional
    private void actualizar(Long idParrafo, String cuerpo)throws MiException{
        validar(cuerpo);
        Optional<Parrafo> respuesta = parrafoRepositorio.findById(idParrafo);

        if (respuesta.isPresent()) {
            Parrafo parrafo = respuesta.get();
            parrafo.setCuerpo(cuerpo);
            parrafoRepositorio.save(parrafo);
        }
    }

    @Transactional
    private List<Parrafo> listarTodosParrafos(Long idArticulo){
        Optional<Articulo> respuesta = articuloRepositorio.findById(idArticulo);
        List<Parrafo> parrafos = null;
        if (respuesta.isPresent()){
            Articulo articulo = respuesta.get();
            parrafos = articulo.getParrafos();
        }
        return parrafos;
    }

    @Transactional
    private void eliminar(Long id){
        parrafoRepositorio.deleteById(id);
    }
}
