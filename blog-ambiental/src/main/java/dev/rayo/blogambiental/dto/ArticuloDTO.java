package dev.rayo.blogambiental.dto;

import java.time.LocalDate;
import java.util.List;

public class ArticuloDTO {
    private Long id;
    private String titulo;
    private LocalDate fecha;
    private boolean isAprobado;
    private Long usuarioId;
    private List<ParrafoDTO> parrafosDTO;
    private List<TematicaDTO> tematicasDTO;
    private List<TipoDTO> tiposDTO;
    private List<ImagenDTO> imagenesDTO;

    public ArticuloDTO() {}

    public ArticuloDTO(Long id, String titulo, LocalDate fecha, boolean isAprobado, Long usuarioId,
                       List<ParrafoDTO> parrafosDTO, List<TematicaDTO> tematicasDTO,
                       List<TipoDTO> tiposDTO, List<ImagenDTO> imagenesDTO) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        this.isAprobado = isAprobado;
        this.usuarioId = usuarioId;
        this.parrafosDTO = parrafosDTO;
        this.tematicasDTO = tematicasDTO;
        this.tiposDTO = tiposDTO;
        this.imagenesDTO = imagenesDTO;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isAprobado() {
        return isAprobado;
    }

    public void setAprobado(boolean aprobado) {
        isAprobado = aprobado;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<ParrafoDTO> getParrafosDTO() {
        return parrafosDTO;
    }

    public void setParrafosDTO(List<ParrafoDTO> parrafosDTO) {
        this.parrafosDTO = parrafosDTO;
    }

    public List<TematicaDTO> getTematicasDTO() {
        return tematicasDTO;
    }

    public void setTematicasDTO(List<TematicaDTO> tematicasDTO) {
        this.tematicasDTO = tematicasDTO;
    }

    public List<TipoDTO> getTiposDTO() {
        return tiposDTO;
    }

    public void setTiposDTO(List<TipoDTO> tiposDTO) {
        this.tiposDTO = tiposDTO;
    }

    public List<ImagenDTO> getImagenesDTO() {
        return imagenesDTO;
    }

    public void setImagenesDTO(List<ImagenDTO> imagenesDTO) {
        this.imagenesDTO = imagenesDTO;
    }
}
