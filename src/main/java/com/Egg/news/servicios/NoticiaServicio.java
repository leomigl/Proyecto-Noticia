
package com.Egg.news.servicios;

import com.Egg.news.MiExcepciones.MisExcepciones;
import com.Egg.news.entidades.noticia;
import com.Egg.news.repositorios.NoticiaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NoticiaServicio {

    @Autowired
    NoticiaRepositorio NoticiaRepositorio;

    @Transactional
    public void CrearNoticia(String titulo, String cuerpo) throws MisExcepciones {
        Validacion(titulo, cuerpo);
        noticia not = new noticia();

        not.setCuerpo(cuerpo);
        not.setTitulo(titulo);
        NoticiaRepositorio.save(not);

    }

    public List<noticia> listarNoticias() {
        List<noticia> noticia = new ArrayList();
        noticia=NoticiaRepositorio.findAll();
        return noticia;

    }

    public void Validacion(String titulo, String cuerpo) throws MisExcepciones {
        if (titulo.isEmpty() || titulo == null) {
            throw new MisExcepciones("el nombre no puede estar vacio o ser nulo");

        }
        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MisExcepciones("el nombre no puede estar vacio o ser nulo");
        }
    }
    @Transactional
    public void modificarNoticia(String id,String titulo, String cuerpo) throws MisExcepciones {
        Validacion(titulo, cuerpo);
        if (id==null) {
            throw new MisExcepciones("id no puede ser null");
        }
        Optional<noticia> respuesta = NoticiaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            noticia not = respuesta.get();
            not.setTitulo(titulo);
            not.setCuerpo(cuerpo);
            NoticiaRepositorio.save(not);
        }
    }

    public noticia getOne(String id) {
        return NoticiaRepositorio.getOne(id);
    }
    @Transactional
    public void eliminarNoticia(String id){
        NoticiaRepositorio.deleteById(id);
        
    }
   
   
}
