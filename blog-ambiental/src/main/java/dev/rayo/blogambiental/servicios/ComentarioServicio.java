package dev.rayo.blogambiental.servicios;

import dev.rayo.blogambiental.entidades.Articulo;
import dev.rayo.blogambiental.entidades.Comentario;
import dev.rayo.blogambiental.entidades.Usuario;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.repositorios.ArticuloRepositorio;
import dev.rayo.blogambiental.repositorios.ComentarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioServicio {
    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    @Autowired
    private ArticuloRepositorio articuloRepositorio;

    public void validar(String cuerpo)throws MiException {
        if (cuerpo.isEmpty()) {
            throw new MiException("El cuerpo del comentario no puede estar vacio");
        }
        // no se valida la fecha porque esa se tomará automaticamente del sistema
    }

    @Transactional
    public Comentario registrar(String cuerpo,  Long idArticulo) throws MiException {
        validar(cuerpo);
        Comentario comentario = new Comentario();
        Optional<Articulo> respuesta = articuloRepositorio.findById(idArticulo);

        if (respuesta.isPresent()){
            Articulo articulo = respuesta.get();
            comentario.setCuerpo(cuerpo);
            comentario.setArticulo(articulo);
            return comentarioRepositorio.save(comentario);
        }
        return null;
    }
    @Transactional
    public void actualizar(Long idComentario, String cuerpo)throws MiException{
        validar(cuerpo);
        Optional<Comentario> respuesta = comentarioRepositorio.findById(idComentario);

        if (respuesta.isPresent()) {
            Comentario comentario = respuesta.get();
            comentario.setCuerpo(cuerpo);
            comentarioRepositorio.save(comentario);
        }
    }

    @Transactional
    public List<Comentario> listarTodosComentarios(Long idArticulo){
        Optional<Articulo> respuesta = articuloRepositorio.findById(idArticulo);
        List<Comentario> comentarios = null;
        if (respuesta.isPresent()){
            Articulo articulo = respuesta.get();
            comentarios = articulo.getComentarios();
        }
        return comentarios;
    }

    @Transactional
    public void eliminar(Long id){
        comentarioRepositorio.deleteById(id);
    }
}
