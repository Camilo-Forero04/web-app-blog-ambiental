/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.rayo.blogambiental.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private LocalDate fecha;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "articulo")
    private List<Parrafo> parrafos;

    private boolean isAprobado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "articulo")
    private List<Imagen> imagenes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "articulo")
    private List<Comentario> comentarios;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties("articulosPublicados")
    private Usuario usuario;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tematica_articulo", joinColumns = @JoinColumn(name = "id_articulo", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_tematica", referencedColumnName = "id")
    )
    @JsonIgnoreProperties("articulos")
    private List<Tematica> tematicas;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tipo_articulo",joinColumns = @JoinColumn(name = "id_articulo", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    )
    @JsonIgnoreProperties("articulos")
    private List<Tipo> tipos;
}
