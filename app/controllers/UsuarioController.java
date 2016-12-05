package controllers;

import java.util.List;
import javax.inject.*;

import play.*;
import play.mvc.*;
import views.html.*;
import static play.libs.Json.*;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.*;

import java.io.File;

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
                Integer total = MensajeService.mensajesTotalesEntrada(userRecu.id);
                Integer noleido = MensajeService.mensajesSinleer(userRecu.id);

                String contador = noleido+"/"+total;

                return ok(DashBoard.render(userRecu,contador,"Bienvenido "+userRecu.login));
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
    public Result Buscar(Integer id, String busqueda) {

        try{
            Usuario user = UsuariosService.findUsuario(id);

            List<Usuario> usuarios = UsuariosService.busquedaUsuario(busqueda);
            List<Tarea> tareas = TareasService.busquedaTarea(busqueda);

            int cantidadU = UsuariosService.CantidadUsuariosBusqueda(busqueda);
            int cantidadT = TareasService.CantidadTareasBusqueda(busqueda);

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
        Integer total = MensajeService.mensajesTotalesEntrada(id);
        Integer noleido = MensajeService.mensajesSinleer(id);

        String contador = noleido+"/"+total;

        return ok(DashBoard.render(user,contador,""));
    }

    @Transactional
    public Result escribirUsuarioModificadoDashBoard(Integer id) {
        Form<Usuario> user = formFactory.form(Usuario.class).bindFromRequest();

            Usuario Userdash = UsuariosService.findUsuarioSinPass(id);
            Integer total = MensajeService.mensajesTotalesEntrada(id);
            Integer noleido = MensajeService.mensajesSinleer(id);

            String contador = noleido+"/"+total;

        if(user.hasErrors()){
            return badRequest(DashBoard.render(Userdash,contador,"Usuario no modificado"));
        }
        
            Usuario usuario = user.get();
            Logger.debug("Usuario modificado: " + usuario.toString());
            usuario = UsuariosService.modificaUsuario(usuario);
            flash("modificar", "El usuario se ha modificado correctamente");
            return ok(DashBoard.render(Userdash,contador,"usuario modificado"));        
   }

    @Transactional
    public Result modificarImagenDashboard(Integer idUsuario) {
        Usuario Userdash    = UsuariosService.findUsuarioSinPass(idUsuario);
        Integer total       = MensajeService.mensajesTotalesEntrada(idUsuario);
        Integer noleido     = MensajeService.mensajesSinleer(idUsuario);
        String contador     = noleido + "/" + total;
        Form<Usuario> user  = formFactory.form(Usuario.class).bindFromRequest();

        if(user.hasErrors()){
            return badRequest(DashBoard.render(Userdash, contador, "Imagen no actualizada"));                   
        }
        
        Usuario usuario = user.get();
        Userdash= UsuariosService.modificaUsuario(usuario);

        return ok(DashBoard.render(Userdash, contador, "Imagen actualizada"));                   
    }
}