package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import models.*;

public class MensajeService {
	public static boolean crearMensaje(Mensaje mensaje) {
		if (mensaje != null && mensaje.body != "") {
			return MensajeDAO.create(mensaje);
		} else {
			return false;
		}
	}

	public static List<Mensaje> findAll() {
    	List<Mensaje> lista = MensajeDAO.findAll();
    	Logger.debug("Numero de mensajes: " + lista.size());
    	return lista;
	}

	public static List<Mensaje> findAllReceived(Integer idUsuario) {
		Usuario user = UsuariosService.findUsuario(idUsuario);
    	List<Mensaje> lista = MensajeDAO.findAllReceived(user.login);
    	Logger.debug("Numero de mensajes: " + lista.size());
    	return lista;
	}

	public static List<Mensaje> findAllSended(Integer idUsuario) {
		Usuario user = UsuariosService.findUsuario(idUsuario);
    	List<Mensaje> lista = MensajeDAO.findAllSended(user.login);
    	Logger.debug("Numero de mensajes: " + lista.size());
    	return lista;
	}

	public static Mensaje findMensaje(Integer idMensaje) {
    	Mensaje mensaje = MensajeDAO.find(idMensaje);
    	return mensaje;
	}

	public static Boolean leerMensaje(Integer idMensaje) {
		Boolean result = false;
		Mensaje mensaje = findMensaje(idMensaje);
		if (mensaje.body != "") {
			mensaje.leido = !mensaje.leido;
			mensaje = MensajeDAO.update(mensaje);	
			result = true;
		}
		return result;
	}

	public static Boolean borrarMensaje(Integer idMensaje) {
		Boolean result = false;
		Mensaje mensaje = findMensaje(idMensaje);
		if (mensaje.body != "") {
			mensaje.borrado = true;
			mensaje = MensajeDAO.update(mensaje);	
			result = true;
		}
		return result;
	}
}
