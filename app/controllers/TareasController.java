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
import java.util.Collections;

public class TareasController extends Controller {

    @Transactional(readOnly = true)
    public Result listaTareas(Integer usuarioId) {

        Usuario usuario = UsuariosService.findUsuario(usuarioId);
        if (usuario == null) {
            return notFound("Usuario no encontrado");
        } else {

            List<Tarea> tareas = TareasService.listaTareasUsuario(usuarioId);
            Collections.sort(tareas);
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
                switch(tarea.duracion){
                    case 0: tarea.tamano ="pequeña";
                    break;
                    case 1: tarea.tamano ="mediana";
                    break;
                    case 2: tarea.tamano ="grande";
                    break;
                    default: tarea.tamano ="Sin tamaño";
                }
            Tarea tareaMas = new Tarea(tarea.descripcion, usuario,tarea.duracion, tarea.tamano);
            tareaMas = TareasService.crearTarea(tareaMas);
            usuario.tareas.add(tareaMas);

            return ok(crearTareaFormulario.render(formFactory.form(Tarea.class), "Tarea añadida", usuario));
            }
            catch(Exception e){
            return badRequest(crearTareaFormulario.render(formFactory.form(Tarea.class), "Tarea no añadida", usuario));
            }
   }

   @Transactional
    public Result editarTarea(Integer id) {
        Tarea tarea = TareasService.findTarea(id);
        Usuario user = tarea.usuario;

        Form<Tarea> tareaForm = formFactory.form(Tarea.class);
        tareaForm = tareaForm.fill(tarea);

        return ok(editarTarea.render(tareaForm,""));
    }

    @Transactional
    public Result escribirTareaModificada() {

            Form<Tarea> task = formFactory.form(Tarea.class).bindFromRequest();

            if(task.hasErrors()){
                Form<Tarea> tareaFormbad = formFactory.form(Tarea.class);
                return badRequest(editarTarea.render(task,"Necesita descripcion para ser Modificada"));
            }
            else{
                int idTarea = task.get().id;
                Tarea tareaAnterior = TareasService.findTarea(idTarea);
                Tarea tarea = task.get();
                tareaAnterior.descripcion =tarea.descripcion;

                tareaAnterior.duracion = tarea.duracion;

                switch(tareaAnterior.duracion){
                    case 0: tareaAnterior.tamano ="pequeña";
                    break;
                    case 1: tareaAnterior.tamano ="mediana";
                    break;
                    case 2: tareaAnterior.tamano ="grande";
                    break;
                    default: tareaAnterior.tamano ="Sin tamaño";
                }

                tareaAnterior = TareasService.modificaTarea(tareaAnterior);
                Form<Tarea> tareaForm = formFactory.form(Tarea.class);
                tareaForm = tareaForm.fill(tareaAnterior);
                return ok(editarTarea.render(tareaForm,"Tarea Modificada"));
            }
   }

    @Transactional
    public Result borraTarea(Integer id) {
        boolean termina = TareasService.deleteTarea(id);
        if(termina){
            return ok();
        }else{
            return badRequest();
        }
    }

    @Transactional
    public Result AsignarProyecto(Integer id){
      Tarea tarea = TareasService.findTarea(id);
      List<Proyecto> proyectos = ProyectosService.findAllProyectos();
      return ok(AsignarProyecto.render(proyectos,tarea,""));
    }

    @Transactional
    public Result RealizarAsignacion(Integer idt,Integer idp){

      List<Proyecto> proyectos = ProyectosService.findAllProyectos();
      Tarea tarea= TareasService.findTarea(idt);
      Proyecto proyecto = ProyectosService.find(idp);

      if(TareasService.AsignarProyecto(tarea,proyecto)){
          return ok();
      }else{
          return badRequest();
      }
    }


}
