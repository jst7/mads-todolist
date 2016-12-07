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

public class ProyectosController extends Controller {

    @Inject FormFactory formFactory;
    @Transactional

    public Result crearProyectoFormulario(Integer idUsuario) {
        return ok(crearProyectoFormulario.render(formFactory.form(Proyecto.class),idUsuario,""));

    }

    @Transactional
    public Result crearProyecto(Integer idUsuario) {
        Form<Proyecto> proyecto = formFactory.form(Proyecto.class).bindFromRequest();
        String msg = "";

        Usuario user = UsuariosService.findUsuario(idUsuario);
        if(proyecto.hasErrors()){
            return badRequest(crearProyectoFormulario.render(proyecto,idUsuario, "Los datos del formulario contienen errores"));
        } else {
            try {
                Proyecto proyectoNew    = proyecto.get();
                proyectoNew.propietario = user;
                Proyecto pAux = ProyectosService.crearProyecto(proyectoNew);
                if (pAux != null) {
                    msg = "Proyecto creado correctamente";
                    return ok(crearProyectoFormulario.render(formFactory.form(Proyecto.class),idUsuario, msg));
                } else {
                    msg = "Proyecto repetido. Por favor, introduzca otro nombre";
                    return badRequest(crearProyectoFormulario.render(proyecto,idUsuario, msg));
                }
            } catch (Exception e) {
                msg = "Proyecto no creado";
                return badRequest(crearProyectoFormulario.render(proyecto,idUsuario, msg));

            }
        }
    }

    @Transactional(readOnly = true)
    public Result listaProyectos(Integer idUsuario) {
        // Obtenemos el mensaje flash guardado en la petición por el controller crearUsuario
        String mensaje = flash("crearProyecto");
        List<Proyecto> proyectos = ProyectosService.findAllProyectos();
        return ok(listaProyectos.render(proyectos,idUsuario,"todos"));
    }

    @Transactional(readOnly = true)
    public Result listaProyectosPropietario(Integer idUsuario) {
        List<Proyecto> proyectos = ProyectosService.findAllProyectosPropietario(idUsuario);
        return ok(listaProyectos.render(proyectos,idUsuario,"participa"));
    }

    @Transactional
    public Result borraProyecto(Integer id) {
        boolean termina = ProyectosService.deleteProyecto(id);
        if(termina){
            return ok();
        }else{
            return badRequest();
        }
    }

    @Transactional
    public Result editarProyectoView(Integer idUsuario,Integer idProyecto) {
        Form<Proyecto> proyectoForm = formFactory.form(Proyecto.class);
        Proyecto proyecto           = ProyectosService.find(idProyecto);
        proyectoForm                = proyectoForm.fill(proyecto);
        return ok(editarProyecto.render(proyectoForm, "",idUsuario,idProyecto));
    }

    @Transactional
    public Result ADDColaborador(Integer idUsuario,Integer idProyecto) {

        Form<Proyecto> proyectoForm = formFactory.form(Proyecto.class);
        Proyecto proyecto           = ProyectosService.find(idProyecto);
        proyectoForm                = proyectoForm.fill(proyecto);

        List<Usuario> usuarios = UsuariosService.findAllUsuarios();
        usuarios=ProyectosService.filtraUsuarios(proyecto,usuarios);
        return ok(editarProyecto.render(proyectoForm, "",idUsuario,idProyecto));
    }

    @Transactional
    public Result editarProyectoAction(Integer idUsuario,Integer idProyecto) {
        Form<Proyecto> project = formFactory.form(Proyecto.class).bindFromRequest();
        if (project.hasErrors()) {
            Form<Proyecto> ProyectoFormbad = formFactory.form(Proyecto.class);
            return badRequest(editarProyecto.render(project, "Necesita nombre para ser modificado",idUsuario,idProyecto));
        } else {
            Proyecto proyectoAux        = ProyectosService.find(idProyecto);
            Proyecto proyecto           = project.get();
            proyectoAux.nombre          = proyecto.nombre;
            proyectoAux                 = ProyectosService.modificar(proyectoAux);
            List<Proyecto> proyectos    = ProyectosService.findAllProyectos();
            return ok(editarProyecto.render(project, "Proyecto modificado",idUsuario,idProyecto));
        }
   }
}
