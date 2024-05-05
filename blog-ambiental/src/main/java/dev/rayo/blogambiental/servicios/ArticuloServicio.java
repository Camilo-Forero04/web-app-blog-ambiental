
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
import java.util.ArrayList;
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
    public Articulo registrarArticulo(
            Long idUsuario, String titulo, List<MultipartFile> archivos,
            List<String> parrafos, List<Long> tematicasId,
            List<Long> tiposId
    ) throws MiException {
        //articulo para obtener un id
        Articulo articulo1 = new Articulo();
        
        Articulo articulo = articuloRepo.save(articulo1);
        
        articulo.setAprobado(false);
        articulo.setFecha(LocalDate.now());
        Usuario usuario = usuarioServicio.obtenerUsuario(idUsuario);
        articulo.setUsuario(usuario);
        articulo.setTitulo(titulo);
        
        List<Parrafo> parrafosSet = agregarParrafos(parrafos, articulo);
        List<Tematica> tematicasSet = agregarTematicas(tematicasId, articulo);
        List<Tipo> tiposSet = agregarTipos(tiposId, articulo);
        
        articulo.setParrafos(parrafosSet);
        articulo.setTematicas(tematicasSet);
        articulo.setTipos(tiposSet);
        
        agregarImagenes(archivos, articulo);
        List<Imagen> imagenes = imagenServicio.obtenerImagenes(articulo.getId());
        articulo.setImagenes(imagenes);
        agregarUsuario(usuario, articulo);
        return articuloRepo.save(articulo);
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
        Optional<List<Articulo>>  respuesta = articuloRepo.listar5Articulos();
        if (respuesta.isPresent()) {
            return respuesta.get();
        }
        return null;
    }
    
    @Transactional
    public Articulo actualizarArticulo(
            Long idArticulo, String titulo, List<MultipartFile> archivos,
            List<String> parrafos, List<Long> tematicas,
            List<Long> tipos
    ) throws MiException {
        
        Optional<Articulo> respuesta = articuloRepo.findById(idArticulo);
        
        if (respuesta.isPresent()) {
            
            Articulo articulo = respuesta.get();
            
            articulo.setAprobado(false);
            articulo.setFecha(LocalDate.now());
            
            articulo.setTitulo(titulo);
   
            List<Parrafo> parrafosSet = actualizarParrafos(parrafos, articulo);
            
            articulo.setParrafos(parrafosSet);
            
            List<Tematica> tematicasSet = agregarTematicas(tematicas, articulo);
            
            articulo.setTematicas(tematicasSet);
            
            List<Tipo> tiposSet = agregarTipos(tipos, articulo);
            
            articulo.setTipos(tiposSet);

            List<Imagen> imagenes = actualizarImagenes(archivos, articulo);

            articulo.setImagenes(imagenes);
            
            return articuloRepo.save(articulo);
        }
        return null;
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
    private List<Parrafo> agregarParrafos(List<String> parrafos, Articulo articulo) throws MiException{
        List<Parrafo> parrafosReturn = new ArrayList<>();
        for (String p : parrafos) {
            parrafosReturn.add(parrafoServicio.registrar(p,articulo.getId()));
        }
        return parrafosReturn;
    }
    
    @Transactional
    private List<Parrafo> actualizarParrafos(List<String> parrafos, Articulo articulo) throws MiException {
        List<Parrafo> parrafosReturn = new ArrayList<>();
        List<Parrafo> parrafosAntiguos = parrafoServicio.obtenerParrafos(articulo.getId());
        
        //actualizar los parrafos ya existentes
        for (int i = 0; i < parrafosAntiguos.size(); i++) {
            if (i >= parrafos.size()) {
                //eliminar parrafos que sobran
                for (int j = i; j < parrafosAntiguos.size(); j++) {
                    parrafoServicio.eliminar(parrafosAntiguos.get(j).getId());
                }
                break;
            }
            parrafosReturn.add(parrafoServicio.actualizar(parrafosAntiguos.get(i).getId(), parrafos.get(i)));
        }
        
        //añadir parrafos en caso de que sea necesario
        if (parrafos.size() > parrafosAntiguos.size()) {
            int agregarDesde = parrafosAntiguos.size();
            for (int i = agregarDesde; i < parrafos.size(); i++) {
                parrafosReturn.add(parrafoServicio.registrar(parrafos.get(i), articulo.getId()));
            }
        }
        
        return parrafosReturn;
    }
    
    @Transactional
    private List<Imagen> actualizarImagenes(List<MultipartFile> imagenes, Articulo articulo) throws MiException {
        List<Imagen> imagenReturn = new ArrayList<>();
        List<Imagen> imagenesAntiguas = imagenServicio.obtenerImagenes(articulo.getId());
        //actualizar las imagenes ya existentes
        for (int i = 0; i < imagenesAntiguas.size(); i++) {
            if (i >= imagenes.size()) {
                //eliminar imagenes que sobran
                for (int j = i; j < imagenesAntiguas.size(); j++) {
                    imagenServicio.eliminar(imagenesAntiguas.get(j).getId());
                }
                break;
            }
            imagenReturn.add(imagenServicio.actualizar(imagenes.get(i), imagenesAntiguas.get(i).getId()));
        }
        //añadir imagenes en caso de que sea necesario
        if (imagenes.size() > imagenesAntiguas.size()) {
            int agregarDesde = imagenesAntiguas.size();
            for (int i = agregarDesde; i < imagenes.size(); i++) {
                imagenReturn.add(imagenServicio.guardar(imagenes.get(i),articulo.getId()));
            }
        }
        return imagenReturn;
    }
    
    @Transactional
    private void agregarImagenes(List<MultipartFile> imagenes, Articulo articulo) throws MiException{
        for (MultipartFile i : imagenes) {
            imagenServicio.guardar(i, articulo.getId());
        }
    }
    
    @Transactional
    private List<Tematica> agregarTematicas(List<Long> tematicasId, Articulo articulo) throws MiException{
        List<Tematica> tematicasReturn = new ArrayList<>();
        for (Long t : tematicasId) {
            tematicasReturn.add(tematicaServicio.asignarArticuloATematica(t, articulo.getId()));
        }
        return tematicasReturn;
    }
    
    @Transactional
    private List<Tipo> agregarTipos(List<Long> tiposId, Articulo articulo) throws MiException{
        List<Tipo> tiposReturn = new ArrayList<>();
        for (Long tp : tiposId) {
            tiposReturn.add(tipoServicio.assigmentArticuloToTipo(tp, articulo.getId()));
        }
        return tiposReturn;
    } 

    public Articulo getById(Long idArticulo) throws MiException{
 
        try{
            Optional<Articulo> respuesta = articuloRepo.findById(idArticulo);
            Articulo articulo = respuesta.get();
            return articulo;
            
        }catch(Exception ex){
            throw new MiException("No existe el articulo");
        }
    }
    
}
