
package dev.rayo.blogambiental.servicios;

import dev.rayo.blogambiental.entidades.Articulo;
import dev.rayo.blogambiental.entidades.Comentario;
import dev.rayo.blogambiental.entidades.Imagen;
import dev.rayo.blogambiental.entidades.Parrafo;
import dev.rayo.blogambiental.entidades.Tematica;
import dev.rayo.blogambiental.entidades.Tipo;
import dev.rayo.blogambiental.entidades.Usuario;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.repositorios.ArticuloRepositorio;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArticuloServicio {
    
    @Autowired
    private ArticuloRepositorio articuloRepo;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private ParrafoServicio parrafoServicio;
    @Autowired
    private TematicaServicio tematicaServicio;
    @Autowired
    private TipoServicio tipoServicio;
    @Autowired
    private ComentarioServicio comentarioServicio;
    
    @Transactional
    public void registrarArticulo(
            Long idUsuario, String titulo, List<MultipartFile> archivos,
            List<Parrafo> parrafos, List<Tematica> tematicas,
            List<Tipo> tipos
    ) throws MiException {
        
        Articulo articulo = new Articulo();
        articulo.setAprobado(false);
        articulo.setFecha(LocalDate.now());
        Usuario usuario = usuarioServicio.obtenerUsuario(idUsuario);
        articulo.setUsuario(usuario);
        articulo.setTitulo(titulo);
        articulo.setParrafos(parrafos);
        articulo.setTematicas(tematicas);
        articulo.setTipos(tipos);
        agregarParrafos(parrafos, articulo);
        agregarImagenes(archivos, articulo);
        agregarTematicas(tematicas, articulo);
        agregarTipos(tipos, articulo);
        List<Imagen> imagenes = imagenServicio.obtenerImagenes(articulo.getId());
        articulo.setImagenes(imagenes);
        agregarUsuario(usuario, articulo);
        articuloRepo.save(articulo);
    }
    
    @Transactional
    public void aprobarArticulo(Long idArticulo){
        Optional<Articulo> respuesta = articuloRepo.findById(idArticulo);
        if (respuesta.isPresent()) {
            Articulo articulo = respuesta.get();
            articulo.setAprobado(true);
            articuloRepo.save(articulo);
        }
    }
    
    @Transactional
    public void agregarComentario(Long idArticulo, String cuerpo) throws MiException{
        Optional<Articulo> respuesta = articuloRepo.findById(idArticulo);
        if (respuesta.isPresent()) {
            Articulo articulo = respuesta.get();
            Comentario comentario = comentarioServicio.registrar(cuerpo, idArticulo);
            articulo.getComentarios().add(comentario);
            articuloRepo.save(articulo);
        }
    }
    
    public List<Articulo> listarArticulosDeUsuario(Long usuarioId){
        Optional<List<Articulo>>  respuesta = articuloRepo.buscarPorUsuario(usuarioId);
        if (respuesta.isPresent()) {
            return respuesta.get();
        }
        return null;
    }
    
    public List<Articulo> listarArticulosRelevantes(){
        Optional<List<Articulo>>  respuesta = articuloRepo.listarArticulosRelevantes();
        if (respuesta.isPresent()) {
            return respuesta.get();
        }
        return null;
    }
    
    @Transactional
    public void actualizarArticulo(
            Long idUsuario, Long idArticulo, String titulo, List<MultipartFile> archivos,
            List<Parrafo> parrafos, List<Tematica> tematicas,
            List<Tipo> tipos, List<Comentario> comentarios
    ) throws MiException {
        
        Optional<Articulo> respuesta = articuloRepo.findById(idArticulo);
        
        if (respuesta.isPresent()) {
            
            Articulo articulo = respuesta.get();
            
            articulo.setAprobado(false);
            articulo.setFecha(LocalDate.now());
            
            Usuario usuario = usuarioServicio.obtenerUsuario(idUsuario);
            
            articulo.setUsuario(usuario);
            articulo.setTitulo(titulo);
            articulo.setParrafos(parrafos);
            articulo.setTematicas(tematicas);
            articulo.setTipos(tipos);
            articulo.setComentarios(comentarios);
            
            agregarParrafos(parrafos, articulo);
            agregarImagenes(archivos, articulo);
            agregarTematicas(tematicas, articulo);
            agregarTipos(tipos, articulo);
            
            List<Imagen> imagenes = imagenServicio.obtenerImagenes(articulo.getId());
            articulo.setImagenes(imagenes);
            agregarUsuario(usuario, articulo);
            
            articuloRepo.save(articulo);
        }
    }
    
    @Transactional
    public void eliminarArticulo(Long id){
        articuloRepo.deleteById(id);
    }
    
    @Transactional
    private void agregarUsuario(Usuario usuario, Articulo articulo) throws MiException{
        usuarioServicio.añadirArticulo(usuario, articulo);
    }
    
    @Transactional
    private void agregarParrafos(List<Parrafo> parrafos, Articulo articulo) throws MiException{
        for (Parrafo p : parrafos) {
            parrafoServicio.registrar(p.getCuerpo(),articulo.getId());
        } 
    }
    
    @Transactional
    private void agregarImagenes(List<MultipartFile> imagenes, Articulo articulo) throws MiException{
        for (MultipartFile i : imagenes) {
            imagenServicio.guardar(i, articulo.getId());
        }
    }
    
    @Transactional
    private void agregarTematicas(List<Tematica> tematicas, Articulo articulo) throws MiException{
        for (Tematica t : tematicas) {
            tematicaServicio.asignarArticuloATipo(t.getId(), articulo.getId());
        }
    }
    
    @Transactional
    private void agregarTipos(List<Tipo> tipos, Articulo articulo) throws MiException{
        for (Tipo tp : tipos) {
            tipoServicio.assigmentArticuloToTipo(tp.getId(), articulo.getId());
        }
    } 
    
}
