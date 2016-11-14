package services;

import java.util.List;
import java.util.ArrayList;

import models.*;

public class TareasService {

    public static List<Tarea> listaTareasUsuario(Integer usuarioId) {
        Usuario usuario = UsuarioDAO.find(usuarioId);
         System.out.println(usuarioId + "SALTA" + "");
        if (usuario != null) {
            return usuario.tareas;
        } else {
            throw new UsuariosException("Usuario no encontrado");
        }
    }

	public static Tarea crearTarea(Tarea tarea) {
    	return TareaDAO.create(tarea);
    }
    public static Tarea findTarea(Integer id) {
        return TareaDAO.find(id);
    }

    public static Tarea modificaTarea(Tarea tarea) {
        TareaDAO.update(tarea);
        return tarea;
    }
    public static boolean deleteTarea(Integer id) {

        try{
            TareaDAO.delete(id);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    public static boolean AsignarProyecto(Tarea tarea, Proyecto proyecto){
      try{
        tarea.proyecto = proyecto;
        TareaDAO.update(tarea);
        return true;
      }catch(Exception e){
          return false;
      }
    }
}
