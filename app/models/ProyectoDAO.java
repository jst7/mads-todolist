package models;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import javax.persistence.*;

import java.util.List;
import java.util.ArrayList;

public class ProyectoDAO {
    public static Proyecto create(Proyecto proyecto) {
        JPA.em().persist(proyecto);
        JPA.em().flush();
        JPA.em().refresh(proyecto);
        Logger.debug(proyecto.toString());

        return proyecto;
    }

    public static List<Proyecto> findAll() {
        TypedQuery<Proyecto> query = JPA.em().createQuery(
                  "select p from Proyecto p ORDER BY id", Proyecto.class);
        return query.getResultList();
    }

    public static void delete(Integer idProyecto) {
        Proyecto proyecto = JPA.em().getReference(Proyecto.class, idProyecto);
        JPA.em().remove(proyecto);
    }    
}
