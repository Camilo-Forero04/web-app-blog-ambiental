package dev.rayo.blogambiental.servicios;

import dev.rayo.blogambiental.entidades.*;
import dev.rayo.blogambiental.dto.*;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.repositorios.ArticuloRepositorio;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tomcat.util.codec.binary.Base64;

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

    // Método para convertir un Articulo a ArticuloDTO
    public ArticuloDTO convertirADTO(Articulo articulo) {
        List<ParrafoDTO> parrafosDTO = articulo.getParrafos().stream().map(this::convertirParrafoADTO).collect(Collectors.toList());
        List<TematicaDTO> tematicasDTO = articulo.getTematicas().stream().map(this::convertirTematicaADTO).collect(Collectors.toList());
        List<TipoDTO> tiposDTO = articulo.getTipos().stream().map(this::convertirTipoADTO).collect(Collectors.toList());
        List<ImagenDTO> imagenesDTO = articulo.getImagenes().stream().map(this::convertirImagenADTO).collect(Collectors.toList());

        return new ArticuloDTO(
                articulo.getId(),
                articulo.getTitulo(),
                articulo.getFecha(),
                articulo.isAprobado(),
                articulo.getUsuario().getId(),
                parrafosDTO,
                tematicasDTO,
                tiposDTO,
                imagenesDTO
        );
    }

    // Métodos para convertir entidades a DTOs
    private ParrafoDTO convertirParrafoADTO(Parrafo parrafo) {
        return new ParrafoDTO(parrafo.getId(), parrafo.getCuerpo(), parrafo.getArticulo().getId());
    }

    private TematicaDTO convertirTematicaADTO(Tematica tematica) {
        return new TematicaDTO(tematica.getId(), tematica.getNombre());
    }

    private TipoDTO convertirTipoADTO(Tipo tipo) {
        return new TipoDTO(tipo.getId(), tipo.getNombre());
    }

    private ImagenDTO convertirImagenADTO(Imagen imagen) {
        String base64Content = Base64.encodeBase64String(imagen.getContenido());
        return new ImagenDTO(imagen.getId(), imagen.getMime(), imagen.getName(), base64Content, imagen.getArticulo().getId());
    }

    @Transactional
    public ArticuloDTO registrarArticulo(
            Long idUsuario, String titulo, List<MultipartFile> archivos,
            List<String> parrafos, List<Long> tematicasId,
            List<Long> tiposId) throws Exception {

        Articulo articulo = new Articulo();
        articulo.setTitulo(titulo);
        articulo.setFecha(LocalDate.now());
        articulo.setAprobado(false);

        Usuario usuario = usuarioServicio.obtenerUsuario(idUsuario);
        articulo.setUsuario(usuario);

        articulo = articuloRepo.save(articulo);
        if (articulo.getId() == null) {
            throw new IllegalStateException("El ID del artículo no fue generado después de guardar.");
        }

        List<Parrafo> parrafosSet = agregarParrafos(parrafos, articulo);
        List<Tematica> tematicasSet = agregarTematicas(tematicasId, articulo);
        List<Tipo> tiposSet = agregarTipos(tiposId, articulo);
        agregarImagenes(archivos, articulo);

        articulo.setParrafos(parrafosSet);
        articulo.setTematicas(tematicasSet);
        articulo.setTipos(tiposSet);

        // Guardar el artículo después de añadir todos los elementos relacionados
        articuloRepo.save(articulo);

        // Convertir a DTO antes de devolver
        return convertirADTO(articulo);
    }

    @Transactional
    public ArticuloDTO actualizarArticulo(
            Long idArticulo, String titulo, List<MultipartFile> archivos,
            List<String> parrafos, List<Long> tematicas,
            List<Long> tipos) throws MiException {

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

            if (archivos != null && !archivos.isEmpty()) {
                List<Imagen> imagenes = actualizarImagenes(archivos, articulo);
                articulo.setImagenes(imagenes);
            }

            articulo = articuloRepo.save(articulo);

            // Convertir a DTO antes de devolver
            return convertirADTO(articulo);
        }
        return null;
    }

    // Otros métodos protegidos y privados
    @Transactional
    protected void agregarUsuario(Usuario usuario, Articulo articulo) throws MiException {
        usuarioServicio.añadirArticulo(usuario, articulo);
    }

    @Transactional
    protected List<Parrafo> agregarParrafos(List<String> parrafos, Articulo articulo) throws MiException {
        List<Parrafo> parrafosReturn = new ArrayList<>();
        for (String p : parrafos) {
            parrafosReturn.add(parrafoServicio.registrar(p, articulo.getId()));
        }
        return parrafosReturn;
    }

    @Transactional
    protected List<Parrafo> actualizarParrafos(List<String> parrafos, Articulo articulo) throws MiException {
        List<Parrafo> parrafosReturn = new ArrayList<>();
        List<Parrafo> parrafosAntiguos = parrafoServicio.obtenerParrafos(articulo.getId());

        // actualizar los parrafos ya existentes
        for (int i = 0; i < parrafosAntiguos.size(); i++) {
            if (i >= parrafos.size()) {
                // eliminar parrafos que sobran
                for (int j = i; j < parrafosAntiguos.size(); j++) {
                    parrafoServicio.eliminar(parrafosAntiguos.get(j).getId());
                }
                break;
            }
            parrafosReturn.add(parrafoServicio.actualizar(parrafosAntiguos.get(i).getId(), parrafos.get(i)));
        }

        // añadir parrafos en caso de que sea necesario
        if (parrafos.size() > parrafosAntiguos.size()) {
            int agregarDesde = parrafosAntiguos.size();
            for (int i = agregarDesde; i < parrafos.size(); i++) {
                parrafosReturn.add(parrafoServicio.registrar(parrafos.get(i), articulo.getId()));
            }
        }

        return parrafosReturn;
    }

    @Transactional
    protected List<Imagen> actualizarImagenes(List<MultipartFile> imagenes, Articulo articulo) throws MiException {
        List<Imagen> imagenReturn = new ArrayList<>();
        List<Imagen> imagenesAntiguas = imagenServicio.obtenerImagenes(articulo.getId());
        // actualizar las imagenes ya existentes
        for (int i = 0; i < imagenesAntiguas.size(); i++) {
            if (i >= imagenes.size()) {
                // eliminar imagenes que sobran
                for (int j = i; j < imagenesAntiguas.size(); j++) {
                    imagenServicio.eliminar(imagenesAntiguas.get(j).getId());
                }
                break;
            }
            imagenReturn.add(imagenServicio.actualizar(imagenes.get(i), imagenesAntiguas.get(i).getId()));
        }
        // añadir imagenes en caso de que sea necesario
        if (imagenes.size() > imagenesAntiguas.size()) {
            int agregarDesde = imagenesAntiguas.size();
            for (int i = agregarDesde; i < imagenes.size(); i++) {
                imagenReturn.add(imagenServicio.guardar(imagenes.get(i), articulo.getId()));
            }
        }
        return imagenReturn;
    }

    @Transactional
    protected void agregarImagenes(List<MultipartFile> imagenes, Articulo articulo) throws MiException {
        for (MultipartFile i : imagenes) {
            imagenServicio.guardar(i, articulo.getId());
        }
    }

    @Transactional
    protected List<Tematica> agregarTematicas(List<Long> tematicasId, Articulo articulo) throws MiException {
        List<Tematica> tematicasReturn = new ArrayList<>();
        for (Long t : tematicasId) {
            tematicasReturn.add(tematicaServicio.buscarTematica(t));
        }
        return tematicasReturn;
    }

    @Transactional
    protected List<Tipo> agregarTipos(List<Long> tiposId, Articulo articulo) throws MiException {
        List<Tipo> tiposReturn = new ArrayList<>();
        for (Long tp : tiposId) {
            tiposReturn.add(tipoServicio.buscarTipo(tp));
        }
        return tiposReturn;
    }

    @Transactional
    public void eliminarArticulo(Long id){
        articuloRepo.deleteById(id);
    }

    // Método agregado para obtener un artículo por su ID
    public ArticuloDTO obtenerArticuloPorId(Long idArticulo) throws MiException {
        Optional<Articulo> respuesta = articuloRepo.findById(idArticulo);
        if (respuesta.isPresent()) {
            return convertirADTO(respuesta.get());
        } else {
            throw new MiException("Artículo no encontrado");
        }
    }

    public Articulo getById(Long idArticulo) throws MiException {
        try {
            Optional<Articulo> respuesta = articuloRepo.findById(idArticulo);
            Articulo articulo = respuesta.get();
            return articulo;
        } catch (Exception ex) {
            throw new MiException("No existe el articulo");
        }
    }

    public List<ArticuloDTO> listarArticulosRelevantes() {
        Optional<List<Articulo>> respuesta = articuloRepo.listar5Articulos();
        if (respuesta.isPresent()) {
            return respuesta.get().stream().map(this::convertirADTO).collect(Collectors.toList());
        }
        return null;
    }

    public List<ArticuloDTO> listarArticulosDeUsuario(Long usuarioId) {
        Optional<List<Articulo>> respuesta = articuloRepo.buscarPorUsuario(usuarioId);
        if (respuesta.isPresent()) {
            return respuesta.get().stream().map(this::convertirADTO).collect(Collectors.toList());
        }
        return null;
    }
}
