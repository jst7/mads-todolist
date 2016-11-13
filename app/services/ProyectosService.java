package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;

import java.util.List;
import java.util.ArrayList;

import models.*;

public class ProyectosService {
	public static Proyecto crearProyecto(Proyecto proyecto) {
<<<<<<< ec9c481102442f8791245bde99a62ad85f639bf0
			return ProyectoDAO.create(proyecto);
    }

    public static boolean deleteProyecto(Integer id) {
            
        try{
            ProyectoDAO.delete(id);
            return true;
        }
        catch(Exception e){
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

	public static Proyecto modificar(Proyecto proyecto) {
        ProyectoDAO.update(proyecto);
        return proyecto;
    }
=======
		return ProyectoDAO.create(proyecto);
    }

		
>>>>>>> TIC-2.4 Añadido service tarea asignar proyecto
}
