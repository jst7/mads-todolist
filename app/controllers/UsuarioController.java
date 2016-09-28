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
            usuario = UsuariosService.crearUsuario(usuario);
            flash("crearUsuario", "El usuario se ha creado correctamente");
            return badRequest(crearUsuarioFormulario.render(user, "funciona bien"));        
            
    
   }

    @Transactional(readOnly = true)
    public Result listaUsuarios() {
        // Obtenemos el mensaje flash guardado en la petición por el controller crearUsuario
        String mensaje = flash("crearUsuario");
        List<Usuario> usuarios = UsuariosService.findAllUsuarios();
        return ok(listarUsuarios.render(usuarios, mensaje));
    }

    @Transactional
    public Result detalleUsuario(String id) {
        Usuario user = UsuariosService.findUsuario(id);
        
        return ok(detalleUsuario.render(user));
    }

    @Transactional
    public Result editarUsuario(String id) {        
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
            return badRequest(editarUsuario.render(user, "funciona bien el modificar"));        
   }

    @Transactional
    public Result borraUsuario(String id) {
        boolean termina = UsuariosService.deleteUsuario(id);
        if(termina){
            return ok();
        }else{
            return badRequest();
        }
    }

}