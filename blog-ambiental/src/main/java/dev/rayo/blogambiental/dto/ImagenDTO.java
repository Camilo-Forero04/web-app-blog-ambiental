package dev.rayo.blogambiental.dto;

public class ImagenDTO {
    private Long id;
    private String mime;
    private String name;
    private byte[] contenido;
    private Long articuloId;

    public ImagenDTO(Long id, String mime, String name, byte[] contenido, Long articuloId) {
        this.id = id;
        this.mime = mime;
        this.name = name;
        this.contenido = contenido;
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
}
