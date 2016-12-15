package controllers;

import java.util.List;
import javax.inject.*;

import play.*;
import play.mvc.*;
import views.html.*;
import static play.libs.Json.*;
import play.data.Form;
import play.data.FormFactory;
import play.data.DynamicForm;
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
        Usuario user = UsuariosService.findUsuario(idUsuario);
        return ok(listaProyectos.render(proyectos,user,"todos"));
    }

    @Transactional(readOnly = true)
    public Result listaProyectosPropietario(Integer idUsuario) {
        List<Proyecto> proyectos = ProyectosService.findAllProyectosPropietario(idUsuario);
        Usuario user = UsuariosService.findUsuario(idUsuario);
        return ok(listaProyectos.render(proyectos,user,"participa"));
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
    public Result AddColaboradorView(Integer idUsuario,Integer idProyecto) {


      Proyecto proyecto           = ProyectosService.find(idProyecto);

        List<Usuario> usuarios = UsuariosService.findAllUsuarios();
        usuarios=ProyectosService.filtraUsuarios(proyecto,usuarios);
        return ok(AddColaborador.render(proyecto,usuarios, "",idUsuario,idProyecto));
    }

    @Transactional
    public Result AddColaborador(Integer idUsuario,Integer id,Integer idColaborador) {

      try{
        Proyecto proyecto = ProyectosService.find(id);
        Proyecto pr = ProyectosService.addColaborador(proyecto,idColaborador);
        return ok();
      }catch(Exception ex){
        return badRequest();
      }

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

   @Transactional
   public Result listarColaboradores(Integer idUsuario,Integer idProyecto) {

     Proyecto proyecto           = ProyectosService.find(idProyecto);

       List<Usuario> usuarios = UsuariosService.findAllUsuarios();
       usuarios=ProyectosService.listarColaboradores(proyecto,usuarios);
       return ok(listarColaboradores.render(usuarios,idUsuario,proyecto,""));

   }

   @Transactional
   public Result borraColaborador(Integer idProyecto,Integer idColaborador) {

       Proyecto proyecto           = ProyectosService.find(idProyecto);
       Usuario colaborador         = UsuariosService.findUsuario(idColaborador);
       boolean termina = ProyectosService.BorrarColaborador(proyecto,colaborador);
       if(termina){
           return ok();
       }else{
           return badRequest();
       }
   }

   @Transactional
   public Result estadosProyectoView(Integer idProyecto, Integer idUsuario) {
        Proyecto proyecto = ProyectosService.find(idProyecto);
        return ok(estadosProyecto.render(proyecto, idUsuario));
   }

   @Transactional
   public Result crearEstado(Integer idProyecto){
        Proyecto proyecto = ProyectosService.find(idProyecto);
        DynamicForm requestData = formFactory.form().bindFromRequest();
        proyecto = ProyectosService.AddEstado(proyecto, requestData.get("estado"));
        return ok(estadosProyecto.render(proyecto, proyecto.propietario.id));
   }
}
