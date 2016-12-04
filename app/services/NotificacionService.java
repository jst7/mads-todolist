package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import models.*;

public class NotificacionService {
	public static boolean crearNotificacion(Notificacion notificacion) {
		if (notificacion != null && notificacion.tipo != "" && notificacion.descripcion != "") {
			return NotificacionDAO.create(notificacion);
		} else {
			return false;
		}
	}

	public static List<Notificacion> findAll(Integer idUsuario) {
		if (idUsuario > 0 && idUsuario != null) {
			Usuario usuario = UsuariosService.findUsuario(idUsuario);
			List<Notificacion> lista = NotificacionDAO.findAll(usuario.login);
    		Logger.debug("Numero de notificacions: " + lista.size());
    		return lista;
		} else {
			return null;
		}
	}

	public static Notificacion findNotificacion(Integer idNotificacion) {
    	Notificacion notificacion = NotificacionDAO.find(idNotificacion);
    	return notificacion;
	}

	public static Boolean leerNotificacion(Integer idNotificacion) {
		Boolean result = false;
		Notificacion notificacion = find(idNotificacion);
		if (notificacion.usuario != "" && notificacion.tipo != "" && notificacion.descripcion != "") {
			notificacion.leido = true;
			notificacion = ;	
			if (NotificacionDAO.update(notificacion)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
