package dev.rayo.blogambiental.servicios;


import dev.rayo.blogambiental.entidades.Imagen;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.repositorios.ImagenRepositorio;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {
    
    @Autowired
    private ImagenRepositorio imagenRepo;
    
    @Transactional
    private Imagen guardar(MultipartFile archivo) throws MiException{
        Imagen imagen = new Imagen();
        try{
            if (archivo != null) {
                imagen.setMime(archivo.getContentType());
                imagen.setName(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepo.save(imagen);
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    @Transactional
    public Imagen actualizar(MultipartFile archivo, Long idImagen)throws MiException{
        
        if (archivo != null) {
            try{
                Imagen imagen = new Imagen();
                if (idImagen!=null) {
                    Optional<Imagen> respuesta = imagenRepo.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen=respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setName(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
                return imagenRepo.save(imagen);
                
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }
    
    @Transactional
    public void Eliminar(Long idImagen) {
        imagenRepo.deleteById(idImagen);
    }   
}
