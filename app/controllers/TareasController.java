package controllers;

import play.*;
import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.*;
import models.*;
import services.*;
import views.html.*;
import javax.inject.*;
import java.util.List;

public class TareasController extends Controller {

    @Transactional(readOnly = true)
    public Result listaTareas(Integer usuarioId) {

        Usuario usuario = UsuariosService.findUsuario(usuarioId);
        if (usuario == null) {
            return notFound("Usuario no encontrado");
        } else {
            List<Tarea> tareas = TareasService.listaTareasUsuario(usuarioId);
            return ok(listaTareas.render(tareas, usuario));
        }
    }
}