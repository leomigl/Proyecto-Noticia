
package com.Egg.news.controladores;

import com.Egg.news.entidades.Usuario;
import com.Egg.news.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/dashboard")
    public String panelAdministrativo(){
        return "inicio.html";
    }
    @GetMapping("/usuarios")
    public String listar(ModelMap modelo){
        List<Usuario> usuarios = (List<Usuario>) usuarioServicio.ListarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuarioLista.html";
    }
   @GetMapping("/modificarRol/{id}")
   public String cambiarRol(@PathVariable String id){
       usuarioServicio.cambiarRol(id);
       return "redirect:/admin/usuarios";
   }
}
