/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dev.rayo.blogambiental.repositorios;


import dev.rayo.blogambiental.entidades.Parrafo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParrafoRepositorio extends JpaRepository<Parrafo,Long>  {
    
}
