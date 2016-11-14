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
    public Result crearProyectoFormulario() {
        return ok(crearProyectoFormulario.render(formFactory.form(Proyecto.class),""));
    }

    @Transactional
    public Result crearProyecto() {
        Form<Proyecto> proyecto = formFactory.form(Proyecto.class).bindFromRequest();
        String msg = "";

        if(proyecto.hasErrors()){
            return badRequest(crearProyectoFormulario.render(proyecto, "Los datos del formulario contienen errores"));
        } else {
            try {
                Proyecto proyectoNew    = proyecto.get();
                ProyectosService.crearProyecto(proyectoNew);
                msg = "Proyecto creado correctamente";
                return ok(crearProyectoFormulario.render(formFactory.form(Proyecto.class), msg));
            } catch (Exception e) {
                msg = "Proyecto no creado";
                return badRequest(crearProyectoFormulario.render(proyecto, msg));
            }
        }
    }

    @Transactional(readOnly = true)
    public Result listaProyectos() {
        // Obtenemos el mensaje flash guardado en la petición por el controller crearUsuario
        String mensaje = flash("crearProyecto");
        List<Proyecto> proyectos = ProyectosService.findAllProyectos();
        return ok(listaProyectos.render(proyectos));
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

    public Result editarProyectoView(Integer idProyecto) {
        Form<Proyecto> proyectoForm = formFactory.form(Proyecto.class);
        Proyecto proyecto           = ProyectosService.find(idProyecto);
        proyectoForm                = proyectoForm.fill(proyecto);
        return ok(editarProyecto.render(proyectoForm, "", idProyecto));
    }

    @Transactional
    public Result editarProyectoAction(Integer idProyecto) {
        Form<Proyecto> project = formFactory.form(Proyecto.class).bindFromRequest();
        if (project.hasErrors()) {
            Form<Proyecto> ProyectoFormbad = formFactory.form(Proyecto.class);
            return badRequest(editarProyecto.render(project, "Necesita nombre para ser modificado", idProyecto));
        } else {
            Proyecto proyectoAux        = ProyectosService.find(idProyecto);
            Proyecto proyecto           = project.get();
            proyectoAux.nombre          = proyecto.nombre;
            proyectoAux                 = ProyectosService.modificar(proyectoAux);
            List<Proyecto> proyectos    = ProyectosService.findAllProyectos();
            return ok(listaProyectos.render(proyectos));
        }
   }
}