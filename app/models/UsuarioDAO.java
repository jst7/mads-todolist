package models;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import javax.persistence.*;
import java.util.List;
import java.util.Date;

public class UsuarioDAO {
    public static Usuario create (Usuario usuario) {
        usuario.nulificaAtributos();
        JPA.em().persist(usuario);
        // Hacemos un flush y un refresh para asegurarnos de que se realiza
        // la creación en la BD y se devuelve el id inicializado
        JPA.em().flush();
        JPA.em().refresh(usuario);
        Logger.debug(usuario.toString());
        return usuario;
    }

    public static Usuario find(String idUsuario) {
        return JPA.em().find(Usuario.class, idUsuario);
    }

    public static Usuario update(Usuario usuario) {
        return JPA.em().merge(usuario);
    }

    public static void delete(String idUsuario) {
        Usuario usuario = JPA.em().getReference(Usuario.class, idUsuario);
        JPA.em().remove(usuario);
    }

    public static List<Usuario> findAll() {
        TypedQuery<Usuario> query = JPA.em().createQuery(
                  "select u from Usuario u ORDER BY id", Usuario.class);
        return query.getResultList();
    }
    public static Usuario ExisteLoginConPass(Usuario user) {
        TypedQuery<Usuario> result = JPA.em().createQuery(
            "select u from Usuario u WHERE login = :login AND password IS NOT NULL ", Usuario.class);
        
        return result.setParameter("login", user.login).getSingleResult();
    }
    public static Usuario ExisteLogin(Usuario user) {
        TypedQuery<Usuario> result = JPA.em().createQuery(
            "select u from Usuario u WHERE login = :login", Usuario.class);
        return result.setParameter("login", user.login).getSingleResult();
    }
    public static boolean LoginUsuario(Usuario user) {
        List<Usuario> result = (List<Usuario>) JPA.em().createQuery(
            "select u from Usuario u WHERE login = '" 
            + user.login + "' AND password ='"+ user.password + "'", Usuario.class).getResultList();
        return (result.size() == 1);
    }


}
