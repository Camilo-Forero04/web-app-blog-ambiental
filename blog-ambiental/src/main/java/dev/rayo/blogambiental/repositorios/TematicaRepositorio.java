
package dev.rayo.blogambiental.repositorios;

import dev.rayo.blogambiental.entidades.Tematica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TematicaRepositorio extends JpaRepository<Tematica,Long> {
    
}
