
package com.Egg.news.controladores;

import com.Egg.news.entidades.Usuario;
import com.Egg.news.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/seguridad")
public class NoticiaControlador2 {

    @Autowired
    private UsuarioServicio UsuarioServicio;

    @GetMapping("/")
    public String index() {
        return "Index.html";
    }

    @GetMapping("/registro")
    public String registrar() {

        return "registrar.html";
    }

    @PostMapping("/registrarUsuario")
    public String registro(@RequestParam String nombreUsuario, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo, MultipartFile archivo) {
        try {
            UsuarioServicio.crearUsuario(archivo, nombreUsuario, email, password, password2);
            modelo.put("exito", "el ususario se registro correctamente");

            return "registrar.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombreUsuario);
            modelo.put("email", email);
            return "registrar.html";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "usuario o contraseña inválidos");
        }

        return "login.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String Inicio(HttpSession session, ModelMap modelo) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        modelo.addAttribute("usuariosession", logueado);
        if (logueado.getRol().toString().equals("ADMIN")) {
           
            return "redirect:/admin/dashboard";
          
        }
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String Perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "usuario_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String Actualizar(MultipartFile archivo, @PathVariable String id, @RequestParam String nombreUsuario, @RequestParam String email,
             @RequestParam String password, @RequestParam String password2, ModelMap modelo) {
        try {
            UsuarioServicio.Actualizar(archivo, id, nombreUsuario, email, password, password2);
            modelo.put("exito", "el usuario fue actualizado");
            return "inicio.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombreusuario", nombreUsuario);
            modelo.put("email", email);
            return "usuario_modificar.html";
        }
    }

}
