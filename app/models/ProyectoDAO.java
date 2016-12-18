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

    public static List<Proyecto> findAllPropietario(Integer id) {

        TypedQuery<Proyecto> query = JPA.em().createQuery(
                  "select p from Proyecto p  where propietario = " + id
                  + " ", Proyecto.class);
        return query.getResultList();
    }

    public static void delete(Integer idProyecto) {
        Proyecto proyecto = JPA.em().getReference(Proyecto.class, idProyecto);
        JPA.em().remove(proyecto);
    }

    public static Proyecto find(Integer id) {
        return JPA.em().find(Proyecto.class, id);
    }

    public static Proyecto update(Proyecto proyecto) {
        return JPA.em().merge(proyecto);
    }

    public static Proyecto findByName(Proyecto proyecto) {
        String query = "SELECT p FROM Proyecto p WHERE nombre = :nombre";
        TypedQuery<Proyecto> result = JPA.em().createQuery(query, Proyecto.class);
        return result.setParameter("nombre", proyecto.nombre).getSingleResult();
    }

    public static Estado addEstado(Estado estado) {
        JPA.em().persist(estado);
        JPA.em().flush();
        JPA.em().refresh(estado);
        Logger.debug(estado.toString());
        return estado;
    }

    public static Estado findEstado(Integer idEstado) {
        return JPA.em().find(Estado.class, idEstado);
    }

    public static void deleteEstado(Integer idEstado) {
        Estado estado = JPA.em().getReference(Estado.class, idEstado);
        JPA.em().remove(estado);
    }

}
