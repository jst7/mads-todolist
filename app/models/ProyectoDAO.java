package models;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import javax.persistence.*;

public class ProyectoDAO {
    public static Proyecto create(Proyecto proyecto) {
        JPA.em().persist(proyecto);
        JPA.em().flush();
        JPA.em().refresh(proyecto);
        Logger.debug(proyecto.toString());
        
        return proyecto;
    }
}