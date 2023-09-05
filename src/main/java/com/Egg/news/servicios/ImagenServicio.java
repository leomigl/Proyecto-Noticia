/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Egg.news.servicios;

import com.Egg.news.MiExcepciones.MisExcepciones;
import com.Egg.news.entidades.Imagen;
import com.Egg.news.repositorios.ImagenRepositorio;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    @Transactional
    public Imagen guardar(MultipartFile archivo) throws MisExcepciones {

        validacionArchivo(archivo);
        try {

            Imagen imagen = new Imagen();
            imagen.setMime(archivo.getContentType());
            imagen.setNombre(archivo.getName());
            imagen.setContenido(archivo.getBytes());
            return imagenRepositorio.save(imagen);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;

        }

    }

    @Transactional
    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MisExcepciones {
        System.out.println(idImagen);
        try {
            if (idImagen == null || idImagen.isEmpty()) {
                throw new Exception("ID de imagen invalido");
            }
            Imagen imagen = new Imagen();
            Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
            if (!respuesta.isPresent()) {
                throw new Exception("La imagen que quiere actualizar no existe");
            }
            imagen = respuesta.get();
            imagen.setMime(archivo.getContentType());
            imagen.setNombre(archivo.getName());
            imagen.setContenido(archivo.getBytes());
            return imagenRepositorio.save(imagen);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    public void validacionArchivo(MultipartFile archivo) throws MisExcepciones {
        if (archivo == null || archivo.isEmpty()) {
            throw new MisExcepciones("error el archivo es nulo o vacio");
        }
    }
}
