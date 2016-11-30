package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import models.*;

public class MensajeService {
	public static boolean crearMenaje(Mensaje mensaje) {
		return MensajeDAO.create(mensaje);
	}

	public static List<Mensaje> findAllUsuarios() {
		List<Mensaje> lista = MensajeDAO.findAll();
		Logger.debug("Numero de mensajes: " + lista.size());
		return lista;
	}
}
