package models;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import javax.persistence.*;

public class TareaDAO {
    public static Tarea find(Integer idTarea) {
        return JPA.em().find(Tarea.class, idTarea);
    }

    public static Tarea create(Tarea tarea) {

        JPA.em().persist(tarea);
        // Hacemos un flush y un refresh para asegurarnos de que se realiza
        // la creación en la BD y se devuelve el id inicializado
        JPA.em().flush();
        JPA.em().refresh(tarea);
        Logger.debug(tarea.toString());
        
        return tarea;
    }

    public static Tarea update(Tarea tarea) {
        return JPA.em().merge(tarea);
    }

    public static void delete(Integer idTarea) {
        Tarea tarea = JPA.em().getReference(Tarea.class, idTarea);
        JPA.em().remove(tarea);
    }
}