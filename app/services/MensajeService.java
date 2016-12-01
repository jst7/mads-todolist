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
}
