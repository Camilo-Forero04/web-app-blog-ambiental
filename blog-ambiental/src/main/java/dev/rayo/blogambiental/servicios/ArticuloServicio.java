
package dev.rayo.blogambiental.servicios;

import dev.rayo.blogambiental.entidades.Articulo;
import dev.rayo.blogambiental.entidades.Parrafo;
import dev.rayo.blogambiental.entidades.Usuario;
import dev.rayo.blogambiental.repositorios.ArticuloRepositorio;
import dev.rayo.blogambiental.repositorios.UsuarioRepositorio;
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
    
    @Transactional
    private void registrarArticulo(Long idUsuario, List<MultipartFile> archivo, String titulo, List<Parrafo> parrafos){
        Articulo articulo = new Articulo();
        articulo.setFecha(LocalDate.now());
        articulo.setParrafos(parrafos);
        articuloRepo.save(articulo);
        usuarioServicio.obtenerUsuario(idUsuario);  
    }
    
    @Transactional
    private void aprobarArticulo(Long idArticulo){
        Optional<Articulo> respuesta = articuloRepo.findById(idArticulo);
        if (respuesta.isPresent()) {
            Articulo articulo = respuesta.get();
            articulo.setAprobado(true);
            articuloRepo.save(articulo);
        }
    }
}
