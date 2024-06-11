/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.rayo.blogambiental.controladores;

import dev.rayo.blogambiental.entidades.Usuario;
import dev.rayo.blogambiental.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins="*")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUsuario(@PathVariable("id") Long idUsuario){
        try{
            Usuario usuario = usuarioServicio.obtenerUsuario(idUsuario);
            return ResponseEntity.status(200).body(usuario);
        }catch(Exception ex){
            return ResponseEntity.status(404).body("No se encontró dicho usuario");
        }
    }
    
    @PostMapping("/guardar")
    public ResponseEntity<Object> registar(@RequestParam String nombre, @RequestParam String email, @RequestParam String contraseña){
        try{
            Usuario usuario = usuarioServicio.registrar(nombre, email, contraseña);
            return ResponseEntity.status(201).body(usuario);
        }catch(Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable("id") Long idUsuario,@RequestParam String nombre, @RequestParam String email, @RequestParam String contraseña){
        try{
            Usuario usuario = usuarioServicio.actualizar(idUsuario,nombre, email, contraseña);
            return ResponseEntity.status(201).body(usuario);
        }catch(Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable("id") Long idUsuario){
        try{
            usuarioServicio.eliminar(idUsuario);
            return ResponseEntity.status(200).body("Usuario Eliminado");//El codgio 204 (No content) podria ser mas apropiado, pero para indicar que la solicitud se procesó un 200 tambien esta OK
        }catch(Exception ex){
            return ResponseEntity.status(404).body("Error al eliminar");
        }
    }
}
