package models;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import javax.persistence.*;
import java.util.List;
import java.util.Date;

public class NotificacionDAO {
    public static boolean create (Notificacion notificacion) {
        JPA.em().persist(notificacion);
        // Hacemos un flush y un refresh para asegurarnos de que se realiza
        // la creación en la BD y se devuelve el id inicializado
        JPA.em().flush();
        JPA.em().refresh(notificacion);
        Logger.debug(notificacion.toString());
        return true;
    }

    public static List<Notificacion> findAll(String login) {
        TypedQuery<Notificacion> query = JPA.em().createQuery(
                  "SELECT u FROM Notificacion u WHERE user = :login AND leido = 0 ORDER BY id ASC", Notificacion.class);
        return query.setParameter("login", login).getResultList();
    }

    public static Notificacion find(Integer idNotificacion) {
        return JPA.em().find(Notificacion.class, idNotificacion);
    }

    public static Notificacion update(Notificacion notificacion) {
        return JPA.em().merge(notificacion);
    }
}
