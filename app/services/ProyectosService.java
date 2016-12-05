package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import models.*;

public class ProyectosService {
	public static Proyecto crearProyecto(Proyecto proyecto) {
        try {
            findByName(proyecto);
            return null;
        } catch (Exception e) {
            NotificacionService.crearNotificacion(new Notificacion("allUsers", "Proyecto", "Nuevo proyecto"));
            return ProyectoDAO.create(proyecto);
        }
    }

    public static boolean deleteProyecto(Integer id) {
        try {
            NotificacionService.crearNotificacion(new Notificacion("allUsers", "Proyecto", "Proyecto " + id));
            ProyectoDAO.delete(id);
            return true;
        } catch(Exception e){
            return false;
        }
    }

	public static List<Proyecto> findAllProyectos() {
		List<Proyecto> lista = ProyectoDAO.findAll();
		Logger.debug("Numero de proyectos: " + lista.size());
		return lista;
	}

	public static Proyecto find(Integer id) {
        return ProyectoDAO.find(id);
	}

    public static Proyecto findByName(Proyecto proyecto) {
        return ProyectoDAO.findByName(proyecto);
    }

	public static Proyecto modificar(Proyecto proyecto) {
        NotificacionService.crearNotificacion(new Notificacion("allUsers", "Proyecto", "Modificación de proyecto: " + proyecto.id + " por: " + proyecto.propietario .login));
        ProyectoDAO.update(proyecto);
        return proyecto;
    }

}
