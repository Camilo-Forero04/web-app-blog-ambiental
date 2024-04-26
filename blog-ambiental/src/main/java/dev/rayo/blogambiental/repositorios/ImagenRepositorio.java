
package dev.rayo.blogambiental.repositorios;

import dev.rayo.blogambiental.entidades.Imagen;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen,Long> {
    @Query("SELECT i FROM Imagen i WHERE i.articulo.id = :id_articulo")
    public Optional<List<Imagen>> encontrarPorArticulo(@Param("id_articulo") Long idArticulo);
}
