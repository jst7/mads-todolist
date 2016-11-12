package services;

import java.util.List;
import java.util.ArrayList;

import models.*;

public class ProyectosService {
	public static Proyecto crearProyecto(Proyecto proyecto) {
		return ProyectoDAO.create(proyecto);		
    }
}