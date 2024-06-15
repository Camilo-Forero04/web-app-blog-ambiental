package dev.rayo.blogambiental.dto;

public class ImagenDTO {
    private Long id;
    private String mime;
    private String name;
    private String contenido; // Cambiar byte[] a String
    private Long articuloId;

    public ImagenDTO(Long id, String mime, String name, String contenido, Long articuloId) {
        this.id = id;
        this.mime = mime;
        this.name = name;
        this.contenido = contenido; // Cambiar byte[] a String
        this.articuloId = articuloId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Long articuloId) {
        this.articuloId = articuloId;
    }
}
