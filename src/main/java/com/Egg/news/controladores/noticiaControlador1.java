
package com.Egg.news.controladores;

import com.Egg.news.MiExcepciones.MisExcepciones;
import com.Egg.news.entidades.noticia;
import com.Egg.news.servicios.NoticiaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/noticia")
public class noticiaControlador1 {

    @Autowired
    private NoticiaServicio NoticiaServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "noticia.html";
    }
     @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<noticia> noticias = NoticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
         if (noticias.isEmpty()) {
             System.out.println("no hay noticias cargadas");
         }
        return "lista_cuadro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String titulo, @RequestParam String cuerpo, ModelMap modelo) {
        try {
            NoticiaServicio.CrearNoticia(titulo, cuerpo);
            modelo.put("exito", "la noticia fue cargada correctamente");

        } catch (MisExcepciones e) {
            System.out.println("" + e.getMessage());
          
            modelo.put("error", e.getMessage());
            return "noticia.html";
        }
        return "noticia.html";

    }

   @GetMapping("/modificar/{id}")
   public String modificar(@PathVariable String id, ModelMap modelo){
       
       modelo.addAttribute("noticia", NoticiaServicio.getOne(id));
       return "modificar.html";
   }
   @PostMapping("/modificar/{id}")
   public String modificar(@PathVariable String id, String titulo,String cuerpo, ModelMap modelo){
           
       try {
          
           NoticiaServicio.modificarNoticia(id, titulo, cuerpo);
           modelo.addAttribute("exito", "la noticia fue modificada correctamente");
           return "redirect:../listar";
       } catch (Exception e) {
           modelo.put("error", e.getMessage());
       return "modificar.html";
       }
       
   }
   @GetMapping("/eliminarNoticia/{id}")
   public String eliminarNoticias(@PathVariable String id){
    NoticiaServicio.eliminarNoticia(id);
    return "redirect:/noticia/listar";
   }
   
}
