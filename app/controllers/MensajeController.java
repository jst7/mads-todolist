package controllers;

import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class MensajeController extends Controller {
    @Inject FormFactory formFactory;

    @Transactional
    public Result crearMensajeFormulario(Integer idUsuario) {
        List<Usuario> usuarios = UsuariosService.findAllUsuarios();
        Usuario user = UsuariosService.findUsuario(idUsuario);
        return ok(enviarMensajeView.render(formFactory.form(Mensaje.class),"", usuarios, user));
    }

    @Transactional
    public Result enviarMensajeAction(Integer idUsuario) {
        Usuario user = UsuariosService.findUsuario(idUsuario);
        List<Usuario> usuarios = UsuariosService.findAllUsuarios();
        Form<Mensaje> mensajeForm = formFactory.form(Mensaje.class).bindFromRequest();
        if (mensajeForm.hasErrors()) {
            return badRequest(enviarMensajeView.render(mensajeForm, "Los datos del formulario contienen errores", usuarios, user));
        }
        Mensaje mensaje = mensajeForm.get();
        mensaje.leido = false;
        mensaje.borrado = false;
        mensaje.fechaEnvio = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());
        Logger.debug("Mensaje nuevo: " + mensaje.toString());
        if (MensajeService.crearMensaje(mensaje)) {
            return ok(enviarMensajeView.render(mensajeForm, "Mensaje enviado", usuarios, user));   
        } else {
            return ok(enviarMensajeView.render(mensajeForm, "Mensaje no enviado", usuarios, user));                   
        }     
    }

    @Transactional
    public Result listarMensajes(Integer idUsuario) {
        List<Mensaje> mensajes = MensajeService.findAll();
        Usuario user = UsuariosService.findUsuario(idUsuario);
        return ok(listarMensajes.render(mensajes, "", user));
    }
}
