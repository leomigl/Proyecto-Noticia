
package com.Egg.news.controladores;

import com.Egg.news.servicios.NoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/")
public class noticiaControlador {
    @Autowired
    private NoticiaServicio NoticiaServicio;
    
    @GetMapping("/")
    public String Inicio(){
        return "Index.html";
    }
  
    
}
