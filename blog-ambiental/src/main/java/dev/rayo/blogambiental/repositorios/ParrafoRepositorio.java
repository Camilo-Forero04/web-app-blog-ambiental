/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dev.rayo.blogambiental.repositorios;


import dev.rayo.blogambiental.entidades.Parrafo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ParrafoRepositorio extends JpaRepository<Parrafo,Long>  {
    
    @Query("SELECT p FROM Parrafo p WHERE p.articulo.id = :id_articulo")
    public Optional<List<Parrafo>> encontrarPorArticulo(@Param("id_articulo")Long idArticulo);
    
}
