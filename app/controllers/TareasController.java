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

    @Inject FormFactory formFactory;
    @Transactional
    public Result crearTareaFormulario(Integer usuarioId) {
        Usuario usuario = UsuariosService.findUsuario(usuarioId);
        if (usuario == null) {
            return notFound("Usuario no encontrado");
        } else {
        return ok(crearTareaFormulario.render(formFactory.form(Tarea.class),"",usuario));
    }
   }

    @Transactional
    public Result crearTarea(Integer usuarioId) {

        Usuario usuario = UsuariosService.findUsuario(usuarioId);

        Form<Tarea> task = formFactory.form(Tarea.class).bindFromRequest();
        if(task.hasErrors()){
            return badRequest(crearTareaFormulario.render(formFactory.form(Tarea.class), "Errores en los campos de Tarea", usuario));
        }
        Tarea tarea = task.get();

            try{
            Tarea tareaMas = new Tarea(tarea.descripcion, usuario);
            tareaMas = TareasService.crearTarea(tareaMas);
            usuario.tareas.add(tareaMas);

            return ok(crearTareaFormulario.render(formFactory.form(Tarea.class), "Tarea añadida", usuario));                   
            }
            catch(Exception e){
            return badRequest(crearTareaFormulario.render(formFactory.form(Tarea.class), "Tarea no añadida", usuario));  
            }
   }
}