package controllers;

import java.util.List;
import javax.inject.*;
import java.util.ArrayList;

import play.*;
import play.mvc.*;
import views.html.*;
import static play.libs.Json.*;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.*;

import static play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.Play;

import java.io.*;
import java.io.File;
import java.io.IOException;

import services.*;
import models.*;

public class UsuarioController extends Controller {
        @Inject FormFactory formFactory;

   public Result crearUsuarioFormulario() {
      return ok(crearUsuarioFormulario.render(formFactory.form(Usuario.class),""));
   }

   @Transactional
    public Result crearUsuario() {

        Form<Usuario> user = formFactory.form(Usuario.class).bindFromRequest();

        if(user.hasErrors()){
            return badRequest(crearUsuarioFormulario.render(user, "Los datos del formulario contienen errores"));
        }
        
            Usuario usuario = user.get();
            Logger.debug("Usuario nuevo: " + usuario.toString());
            if(UsuariosService.crearUsuario(usuario)){
                flash("crearUsuario", "El usuario se ha creado correctamente");
                return ok(crearUsuarioFormulario.render(user, "usuario creado"));   
            }
            else{
                return ok(crearUsuarioFormulario.render(user, "Usuario no creado, elige otro Login"));                   
            }     
            
    
   }

    @Transactional(readOnly = true)
    public Result listaUsuarios() {
        // Obtenemos el mensaje flash guardado en la petición por el controller crearUsuario
        String mensaje = flash("crearUsuario");
        List<Usuario> usuarios = UsuariosService.findAllUsuarios();
        return ok(listarUsuarios.render(usuarios, mensaje));
    }

    @Transactional
    public Result detalleUsuario(Integer id) {
        Usuario user = UsuariosService.findUsuario(id);
        
        return ok(detalleUsuario.render(user));
    }

    @Transactional
    public Result editarUsuario(Integer id) {        
        Usuario user = UsuariosService.findUsuario(id);
        Form<Usuario> userForm = formFactory.form(Usuario.class);
        userForm = userForm.fill(user);

        return ok(editarUsuario.render(userForm,""));
    }

    @Transactional
    public Result escribirUsuarioModificado() {

        Form<Usuario> user = formFactory.form(Usuario.class).bindFromRequest();

        if(user.hasErrors()){
            return badRequest(editarUsuario.render(user, "Los datos del formulario contienen errores"));
        }
        
            Usuario usuario = user.get();
            Logger.debug("Usuario modificado: " + usuario.toString());
            usuario = UsuariosService.modificaUsuario(usuario);
            flash("modificar", "El usuario se ha modificado correctamente");
            return badRequest(editarUsuario.render(user, "Usuario Modificado"));        
   }

    @Transactional
    public Result borraUsuario(Integer id) {
        boolean termina = UsuariosService.deleteUsuario(id);
        if(termina){
            return ok();
        }else{
            return badRequest();
        }
    }

    @Transactional
    public Result paginaInicio() {
        Form<Usuario> user = formFactory.form(Usuario.class).bindFromRequest();  
        Logger.debug("Llamada página de inicio");  
        return ok(paginaInicioLR.render(user, ""));
    }

     @Transactional
    public Result registrarUsuario() {

        Form<Usuario> user = formFactory.form(Usuario.class).bindFromRequest();

        if(user.hasErrors()){
            return badRequest(paginaInicioLR.render(user, "Error en los campos"));
        }

        Usuario usuario = user.get();

        boolean existe = UsuariosService.existeUsuarioConPass(usuario);//TIENE LOGIN Y NO PASS

        if(existe){//Si existe mensaje de error
            return badRequest(paginaInicioLR.render(user, "El usuario ya existe"));        
        }else{//Si no existe se registra o actualiza
            try{//esta el login solo falta el pass
                Usuario sUsuario = UsuariosService.existeLogin(usuario);
                usuario.id=sUsuario.id;
                usuario.nombre = sUsuario.nombre;
                usuario.apellidos = sUsuario.apellidos;
                usuario.eMail = sUsuario.eMail;
                usuario.fechaNacimiento = sUsuario.fechaNacimiento;
                usuario = UsuariosService.modificaUsuario(usuario);
                return ok(paginaInicioLR.render(user, "Actualizada la Contraseña"));

            }catch(Exception e){//no existen referencias al usuario
                UsuariosService.crearUsuario(usuario);//se ha comprobado ya si existe o no, no hace falta controlar aqui la no repeticion del login
                return ok(paginaInicioLR.render(user, "El usuario no existía y ha sido creado"));
            }
        }  
   }
    @Transactional
    public Result entrarLogin() {

        Form<Usuario> user = formFactory.form(Usuario.class).bindFromRequest();  

        try{
            Usuario usuario = user.get();

            boolean entra = UsuariosService.loginUsuario(usuario);

            if(entra){
                //Recupero el usuario 
                Usuario userRecu = UsuariosService.existeLogin(usuario);
                String mensajes = mensajesDash(userRecu.id);
                String proyectos = proyectosDash(userRecu.id);
                List<Notificacion> notificaciones = NotificacionService.findAll(userRecu.id);



                return ok(DashBoard.render(userRecu,mensajes, proyectos,"Bienvenido "+userRecu.login, notificaciones));
            }
            else{
                return badRequest(paginaInicioLR.render(user, "Login incorrecto"));  
            }
        }
        catch(Exception e){
            return badRequest(paginaInicioLR.render(user, "Datos incorrectos, rellenar los campos"));  
        }

        
        
    }

    @Transactional
    public Result Buscar(Integer id, String busqueda, Integer caso) {

        try{
            Usuario user = UsuariosService.findUsuario(id);

            List<Usuario> usuarios = new ArrayList<Usuario>();
            List<Tarea> tareas = new ArrayList<Tarea>();
            int cantidadU = 0;
            int cantidadT = 0;            

            if(caso==0){
                usuarios = UsuariosService.busquedaUsuario(busqueda);
                tareas = TareasService.busquedaTarea(busqueda);
                cantidadU = UsuariosService.CantidadUsuariosBusqueda(busqueda);
                cantidadT = TareasService.CantidadTareasBusqueda(busqueda);
            }
            else if(caso==1){
                usuarios = UsuariosService.busquedaUsuario(busqueda);
                tareas = new ArrayList<Tarea>();
                cantidadU = UsuariosService.CantidadUsuariosBusqueda(busqueda);
                cantidadT = 0;            

            }
            else if(caso==2){
                usuarios = new ArrayList<Usuario>();
                tareas = TareasService.busquedaTarea(busqueda);
                cantidadU = 0;
                cantidadT = TareasService.CantidadTareasBusqueda(busqueda);
            }else{
                usuarios = new ArrayList<Usuario>();
                tareas = new ArrayList<Tarea>();
                cantidadU = 0;
                cantidadT = 0;
            }



        return ok(Buscar.render(usuarios, tareas , user, cantidadU, cantidadT));
        }
        catch(Exception e){
            return badRequest("No puedes acceder a este recurso");
        }
    }

    @Transactional
    public Result BuscarDetalle(Integer id, Integer idB) {

        try{
            Usuario user = UsuariosService.findUsuarioSinPass(id);
            Usuario userBuscador = UsuariosService.findUsuarioSinPass(idB);

            return ok(BuscarUserDetalle.render(user, userBuscador));
        }
        catch(Exception e){
            return badRequest("Recurso inexistente");
        }
    }

    @Transactional
    public Result DashBoard(Integer id) {

        Usuario user = UsuariosService.findUsuarioSinPass(id);
        
        List<Notificacion> notificaciones = NotificacionService.findAll(id);
        String mensajes = mensajesDash(id);
        String proyectos = proyectosDash(id);

        return ok(DashBoard.render(user,mensajes, proyectos,"", notificaciones));
    }


    @Transactional
    public Result escribirUsuarioModificadoDashBoard(Integer id) {
        Form<Usuario> user = formFactory.form(Usuario.class).bindFromRequest();

            Usuario Userdash = UsuariosService.findUsuarioSinPass(id);
            List<Notificacion> notificaciones = NotificacionService.findAll(id);

            String mensajes = mensajesDash(id);
            String proyectos = proyectosDash(id);

        if(user.hasErrors()){
            return badRequest(DashBoard.render(Userdash,mensajes, proyectos,"Usuario no modificado", notificaciones));
        }
        
            Usuario usuario = user.get();
            Logger.debug("Usuario modificado: " + usuario.toString());
            usuario = UsuariosService.modificaUsuario(usuario);
            flash("modificar", "El usuario se ha modificado correctamente");
            return ok(DashBoard.render(Userdash,mensajes, proyectos,"usuario modificado", notificaciones));        
   }

    @Transactional
    public Result modificarImagenDashboard(Integer idUsuario) {
        Usuario Userdash    = UsuariosService.findUsuarioSinPass(idUsuario);
        String mensajes = mensajesDash(idUsuario);
        String proyectos = proyectosDash(idUsuario);
        Form<Usuario> user  = formFactory.form(Usuario.class).bindFromRequest();
        List<Notificacion> notificaciones = NotificacionService.findAll(idUsuario);

        if(user.hasErrors()){
            return badRequest(DashBoard.render(Userdash, mensajes, proyectos, "Imagen no actualizada", notificaciones));                   
        }
        
        Usuario usuario = user.get();
        Userdash= UsuariosService.modificaUsuario(usuario);

        return ok(DashBoard.render(Userdash, mensajes, proyectos, "Imagen actualizada", notificaciones));                   
    }

    @Transactional
    public Result CambiarColor() {
        Form<Usuario> user  = formFactory.form(Usuario.class).bindFromRequest();
        Usuario usuario = user.get();

        Boolean cambioColor = UsuariosService.cambiarColor(usuario.id, usuario.colordash);

        Usuario Userdash    = UsuariosService.findUsuarioSinPass(usuario.id);
        String mensajes = mensajesDash(usuario.id);
        String proyectos = proyectosDash(usuario.id);
        List<Notificacion> notificaciones = NotificacionService.findAll(usuario.id);


        if(cambioColor){
            return ok(DashBoard.render(Userdash, mensajes, proyectos, "Color Actualizado", notificaciones));
        }
        else{
            return badRequest(DashBoard.render(Userdash, mensajes, proyectos, "Color No Actualizado", notificaciones));
        }
        
    }

    //Auxiliares
    public String mensajesDash(Integer id){
        Integer total = MensajeService.mensajesTotalesEntrada(id);
        Integer noleido = MensajeService.mensajesSinleer(id);
        String contador = noleido+"/"+total;

        return contador;
    }

    public String proyectosDash(Integer id){
        Integer proPropiedad = ProyectosService.cantidadProyectosPropietario(id);
        Integer proColaborar = ProyectosService.cantidadProyectosColabora(id);

        String contador = "p:" + proPropiedad + " c:" + proColaborar;

        return contador;
    }

    @Transactional
    public Result subirImagen(Integer idUsuario) {
        return ok(subirImagen.render(formFactory.form(Usuario.class),"", idUsuario));
    }

    @Transactional
    public Result subirImagenAction(Integer idUsuario) {
        MultipartFormData<File> body = request().body().asMultipartFormData();
        FilePart<File> picture = body.getFile("picture");
        if (UsuariosService.subirImagen(picture, idUsuario)) {
            return badRequest(subirImagen.render(formFactory.form(Usuario.class),"Imagen subida correctamente", idUsuario));
        } else {
            flash("error", "Missing file");
            return badRequest(subirImagen.render(formFactory.form(Usuario.class),"Error al subir la imagen", idUsuario));
        }    
    }
}
