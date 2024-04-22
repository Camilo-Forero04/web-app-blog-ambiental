package dev.rayo.blogambiental.servicios;

import dev.rayo.blogambiental.entidades.Articulo;
import dev.rayo.blogambiental.entidades.Imagen;
import dev.rayo.blogambiental.entidades.Tipo;
import dev.rayo.blogambiental.enumeraciones.TipoArticulo;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.repositorios.ArticuloRepositorio;
import dev.rayo.blogambiental.repositorios.TipoRepositorio;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TipoServicio {

    @Autowired
    private TipoRepositorio tipoRepositorio;

    @Autowired
    private ArticuloRepositorio articuloRepositorio;

    private void validar(String nombre)throws MiException{
        if (nombre.isEmpty()) {
            throw new MiException("El nombre no puede estar vacio");
        }
    }

    @Transactional
    @PostConstruct
    private void inizializar(){
    for (TipoArticulo nombre: TipoArticulo.values()){
        Tipo tipo = new Tipo();
        tipo.setNombre(nombre.toString());
        tipoRepositorio.save(tipo);
    }
    }
    @Transactional
    private void registrar(String nombre) throws MiException {
        validar(nombre);
        Tipo tipo = new Tipo();
        tipo.setNombre(nombre);
        tipoRepositorio.save(tipo);
    }

    @Transactional
    private void actualizar(Long idTipo, String nombre)throws MiException{
        validar(nombre);
        Optional<Tipo> tipoEncontrado = tipoRepositorio.findById(idTipo);

        if (tipoEncontrado.isPresent()) {
            Tipo tipo = tipoEncontrado.get();
            tipo.setNombre(nombre);
            tipoRepositorio.save(tipo);
        }
    }

    @Transactional
    private void eliminar(Long id) {
        tipoRepositorio.deleteById(id);
    }

    @Transactional
    private Tipo assigmentArticuloToTipo(Long idTipo, Long idArticulo){
        Optional<Tipo> tipoEncontrado = tipoRepositorio.findById(idTipo);
        Optional<Articulo> articuloEncontrado = articuloRepositorio.findById(idArticulo);
        if(tipoEncontrado.isPresent() && articuloEncontrado.isPresent()){
            List<Articulo> articulos = null;
            Tipo tipo = tipoEncontrado.get(); // es el tipo (entidad) buscado por id
            Articulo articulo = articuloEncontrado.get(); // es el articulo (entidad) buscado por id
            articulos = tipo.getArticulos(); //una lista con todos articulos relacionados a tipo
            articulos.add(articulo);  // a la lista se le agrega el nuevo articulo asignado
            tipo.setArticulos(articulos); // a la entidad tipo se le envia la lista de articulos
            return tipoRepositorio.save(tipo); // se procede a guardar la entidad tipo
        }
        return null;
    }
}
