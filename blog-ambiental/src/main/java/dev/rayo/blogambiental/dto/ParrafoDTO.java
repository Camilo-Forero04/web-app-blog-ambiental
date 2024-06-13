package dev.rayo.blogambiental.dto;

public class ParrafoDTO {
    private Long id;
    private String cuerpo;
    private Long articuloId;

    public ParrafoDTO(Long id, String cuerpo, Long articuloId) {
        this.id = id;
        this.cuerpo = cuerpo;
        this.articuloId = articuloId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
}
