
package dev.rayo.blogambiental.repositorios;

import dev.rayo.blogambiental.entidades.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipoRepositorio extends JpaRepository<Tipo,Long> {
    
}
