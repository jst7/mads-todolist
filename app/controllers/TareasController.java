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
        System.out.println("AAAAAAAAAAAAA: "+tarea.fecha);
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
            Tarea tareaMas = new Tarea(tarea.descripcion, usuario,tarea.duracion, tarea.tamano, tarea.fecha);
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
                return badRequest(editarTarea.render(task,"Hay errores en los campos"));
            }
            else{
                int idTarea = task.get().id;
                Tarea tareaAnterior = TareasService.findTarea(idTarea);
                Tarea tarea = task.get();
                tareaAnterior.descripcion =tarea.descripcion;
                tareaAnterior.duracion = tarea.duracion;
                tareaAnterior.fecha = tarea.fecha;
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

        String proyectoNombre="";
        if(tarea.proyecto!=null){
          proyectoNombre=tarea.proyecto.nombre;
        }

        List<Proyecto> proyectos = ProyectosService.findAllProyectos();

        return ok(AsignarProyecto.render(proyectos,tarea,proyectoNombre,""));
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

    @Transactional
    public Result DesAsignarProyecto(Integer idt) {
        Tarea tarea = TareasService.findTarea(idt);

        if(TareasService.DesAsignarProyecto(tarea)){
            return ok();
        }else {
            return badRequest();
        }
    }

    @Transactional
    public Result BuscarDetalle(Integer id, Integer idB) {

        try{
            Tarea task = TareasService.findTarea(id);
            Usuario userBuscador = UsuariosService.findUsuarioSinPass(idB);


        return ok(BuscarTaskDetalle.render(task, userBuscador));
        }
        catch(Exception e){
            return badRequest("Recurso inexistente");
        }

    }

    @Transactional
    public Result CambiarEstado(Integer idUsuario, Integer idTarea, String estado) {
        Tarea tarea = TareasService.findTarea(idTarea);
        tarea.estado = estado;
        tarea = TareasService.modificaTarea(tarea);
        Usuario usuario = UsuariosService.findUsuario(idUsuario);
        return ok(listaTareas.render(usuario.tareas, usuario));
    }

    @Transactional
    public Result CambiarColor(Integer idUsuario, Integer idTarea, String color) {
        Tarea tarea = TareasService.findTarea(idTarea);
        tarea.color = color;
        tarea = TareasService.modificaTarea(tarea);
        Usuario usuario = UsuariosService.findUsuario(idUsuario);
        return ok(listaTareas.render(usuario.tareas, usuario));
    }

    @Transactional
    public Result ArchivadasView(Integer idUsuario) {
        Usuario usuario = UsuariosService.findUsuario(idUsuario);
        if (usuario == null) {
            return notFound("Usuario no encontrado");
        } else {
            List<Tarea> tareas = TareasService.listaTareasUsuario(idUsuario);
            return ok(tareasArchivadas.render(tareas, usuario));
        }
    }
    @Transactional
    public Result ArchivarTarea(Integer idUsuario, Integer idTarea) {
        Tarea tarea = TareasService.findTarea(idTarea);
        tarea.archivada = true;
        TareasService.modificaTarea(tarea);
        Usuario usuario = UsuariosService.findUsuario(idUsuario);
        return ok(listaTareas.render(usuario.tareas, usuario));
    }

}
