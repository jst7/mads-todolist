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
        Usuario usuario = UsuariosService.findUsuario(idUsuario);
        if (usuario == null) {
            return notFound("Usuario no encontrado");
        } else {
            return ok(crearProyectoFormulario.render(formFactory.form(Proyecto.class),"", usuario));
        }
    }

    @Transactional
    public Result crearProyecto(Integer idUsuario) {
        Usuario usuario = UsuariosService.findUsuario(idUsuario);
        Form<Proyecto> proyecto = formFactory.form(Proyecto.class).bindFromRequest();
        String msg = "";

        if(proyecto.hasErrors()){
            return badRequest(crearProyectoFormulario.render(proyecto, "Los datos del formulario contienen errores", usuario));
        } else {
            Proyecto pAux           = proyecto.get();
            Logger.debug("Proyecto nuevo: " + pAux.toString());
            Proyecto proyectoNew    = new Proyecto(pAux.nombre, usuario);
            //usuario.proyectos.add(proyectoNew);
            ProyectosService.crearProyecto(proyectoNew);
            try {
                msg = "Proyecto creado correctamente";
                return ok(crearProyectoFormulario.render(formFactory.form(Proyecto.class), msg, usuario));                   
            } catch (Exception e) {
                msg = "Proyecto no creado";
                return badRequest(crearProyectoFormulario.render(proyecto, msg, usuario));
            }
        }
    }
}