package services;

import java.util.List;
import java.util.ArrayList;

import models.*;

public class TareasService {

    public static List<Tarea> listaTareasUsuario(Integer usuarioId) {
        Usuario usuario = UsuarioDAO.find(usuarioId);
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

    public static boolean DesAsignarProyecto(Tarea tarea) {
        try{
            tarea.proyecto = null;
            TareaDAO.update(tarea);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public static List<Tarea> busquedaTarea(String param) {
        List<Tarea> lista = TareaDAO.busquedaTarea(param);
            

            return lista;
    }
    
    public static Integer CantidadTareasBusqueda(String param) {
        List<Tarea> lista = TareaDAO.busquedaTarea(param);
            
            if (lista==null){
                return 0;
            }

            Integer cantidad = lista.size();
            return cantidad;
        }

}
