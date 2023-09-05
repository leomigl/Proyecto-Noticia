
package com.Egg.news.servicios;

import com.Egg.news.Enumeraciones.Rol;
import com.Egg.news.MiExcepciones.MisExcepciones;
import com.Egg.news.entidades.Imagen;
import com.Egg.news.entidades.Usuario;
import com.Egg.news.repositorios.UsuarioRepositorio;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenservicio;

    @Transactional
    public void crearUsuario(MultipartFile archivo, String nombreUsuario, String email, String password, String password2) throws MisExcepciones, IOException {
        validar(nombreUsuario, email, password, password2);
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setPassword2(password2);
        usuario.setEmail(email);
        Date fechaAlta = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/mm/aa");
        usuario.setFechaAlta(fechaAlta);
        usuario.setActivo(true);
        usuario.setRol(Rol.USER);
        Imagen imagen = imagenservicio.guardar(archivo);
        usuario.setImagen(imagen);
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void Actualizar(MultipartFile archivo, String id, String nombreUsuario, String email, String password, String password2) throws MisExcepciones {
        System.out.println("aqui estamos");
        validar(nombreUsuario, email, password, password2);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setEmail(email);
            Date fechaAlta = new Date();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/mm/aa");
            usuario.setFechaAlta(fechaAlta);
            usuario.setActivo(true);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setPassword2(password2);
            usuario.setRol(Rol.USER);
            String idImagen=null;
            if (usuario.getImagen() != null) {
                idImagen=usuario.getImagen().getId();
            }
            Imagen imagen=imagenservicio.actualizar(archivo, idImagen);
            System.out.println("hola"+imagen);
            usuario.setImagen(imagen);
            usuarioRepositorio.save(usuario);
        }
    }

    public void validar(String nombreUsuario, String email, String password, String password2) throws MisExcepciones {
        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            throw new MisExcepciones("el usuario no puede ser nulo o estar vacio");
        }
        if (email == null || email.isEmpty()) {
            throw new MisExcepciones("el email no puede ser nulo o estar vacio");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MisExcepciones("la contraseña no puede ser nula o estar vacia, y debe tener al menos 5 caracteres");
        }
        if (!password.equalsIgnoreCase(password2)) {
            throw new MisExcepciones("las contraseñas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
    @Transactional
    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }
    @Transactional
    public void eliminarUsuario(String id){
        usuarioRepositorio.deleteById(id);
        
    }
    public List<Usuario> ListarUsuarios(){
        List<Usuario> usuarios=usuarioRepositorio.findAll();
        return usuarios ;
    }
    @Transactional
    public void cambiarRol(String id){
        Optional<Usuario> respuesta=usuarioRepositorio.findById(id);
         if (respuesta.isPresent()) {
            Usuario usuario=respuesta.get();
             if (usuario.getRol().equals(Rol.ADMIN)) {
                 usuario.setRol(Rol.USER);
                 usuarioRepositorio.save(usuario);
             }else if(usuario.getRol().equals(Rol.USER)){
                 usuario.setRol(Rol.USER);
                 usuarioRepositorio.save(usuario);
             }
        }
    }
}
