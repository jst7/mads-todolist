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

public class NotificacionController extends Controller {
    @Inject FormFactory formFactory;

    @Transactional
    public Result listarNotificacionsRecibidos(Integer idUsuario) {
        return ok();
    }

    /*@Transactional
    public Result leerNotificacion(Integer idNotificacion, Integer idUsuario) {
        Boolean leido = NotificacionService.leerNotificacion(idNotificacion);
        List<Notificacion> notificacions = NotificacionService.findAll();
        Usuario user = UsuariosService.findUsuario(idUsuario);
        return ok(listarNotificacionsEnviados.render(notificacions, "", user));
    }*/
}
