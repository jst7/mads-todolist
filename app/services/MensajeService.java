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
			NotificacionService.crearNotificacion(new Notificacion(mensaje.usuarioTo, "Mensaje", "Nuevo mensaje"));
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
			NotificacionService.crearNotificacion(new Notificacion(mensaje.usuarioTo, "Mensaje", "Mensaje: " + idMensaje + " eliminado"));
			mensaje = MensajeDAO.update(mensaje);	
			result = true;
		}
		return result;
	}

	public static Integer mensajesSinleer(Integer id){
		Usuario user = new Usuario();
		user = UsuariosService.findUsuario(id);
		List<Mensaje> lista = MensajeDAO.findAllPorLeer(user.login);

    	return lista.size();
	}

	public static Integer mensajesTotalesEntrada(Integer id){
		Usuario user = new Usuario();
		user = UsuariosService.findUsuario(id);
		List<Mensaje> lista = MensajeDAO.findAllTotal(user.login);

    	return lista.size();
	}
}
