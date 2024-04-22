/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.rayo.blogambiental.servicios;

import dev.rayo.blogambiental.entidades.Usuario;
import dev.rayo.blogambiental.excepciones.MiException;
import dev.rayo.blogambiental.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    @Autowired 
    private UsuarioRepositorio usuarioRepo;
    @Transactional
    private void registrar(String nombre, String email, String contraseña) throws MiException{
        validar(nombre,email,contraseña);
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContraseña(contraseña);
        usuarioRepo.save(usuario);
    }
    
    @Transactional
    private void actualizar(Long idUsuario, String nombre, String email, String contraseña)throws MiException{
        validar(nombre,email,contraseña);
        Optional<Usuario> respuesta = usuarioRepo.findById(idUsuario);
        
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setContraseña(contraseña);
            usuarioRepo.save(usuario);
        }
    }
    private void validar(String nombre, String email, String contraseña)throws MiException{
        if (nombre.isEmpty()) {
            throw new MiException("El nombre no puede estar vacio");
        }
        if (email.isEmpty()) {
            throw new MiException("El email no puede estar vacio");
        }
        if (contraseña.length() < 8) {
            throw new MiException("La contraseña debe ser mayor a 8");
        }
    }
    @Transactional
    private void eliminar(Long id){
        usuarioRepo.deleteById(id);
    }
    
    public Usuario obtenerUsuario(Long id){
        Optional<Usuario> respuesta = usuarioRepo.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        }
        return null;
    }
}
