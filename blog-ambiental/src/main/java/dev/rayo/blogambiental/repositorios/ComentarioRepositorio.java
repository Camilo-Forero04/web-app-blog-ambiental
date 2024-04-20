
package dev.rayo.blogambiental.repositorios;

import dev.rayo.blogambiental.entidades.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario,Long> {
    
}
