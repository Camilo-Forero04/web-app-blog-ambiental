/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dev.rayo.blogambiental.repositorios;

import dev.rayo.blogambiental.entidades.Articulo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepositorio extends JpaRepository<Articulo,Long>  {
    @Query("SELECT a FROM articulo a WHERE a.id_usuario = : id_usuario")
    public Optional<List<Articulo>> buscarPorUsuario(@Param("id_usuario")Long id_usuario);
    @Query("SELECT a FROM articulo a LIMIT 5")
    public Optional<List<Articulo>> listarArticulosRelevantes();
}
