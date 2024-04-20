
package dev.rayo.blogambiental.repositorios;

import dev.rayo.blogambiental.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen,Long> {
    
}
