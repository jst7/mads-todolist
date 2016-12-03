package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import models.*;

public class UsuariosService {

        public static boolean crearUsuario(Usuario usuario) {
        	return UsuarioDAO.create(usuario);
        }

        public static List<Usuario> findAllUsuarios() {
        	List<Usuario> lista = UsuarioDAO.findAll();
        	Logger.debug("Numero de usuarios: " + lista.size());

        	return lista;
    	}

    	public static Usuario findUsuario(Integer id) {
        	Usuario user = UsuarioDAO.find(id);

        	return user;
    	}
        public static Usuario modificaUsuario(Usuario usuario) {
            Usuario antes = UsuarioDAO.ExisteLogin(usuario);

            if (antes != null && usuario.id != antes.id){
                throw new UsuariosException("Login ya existe:" + usuario.login);
            }
            UsuarioDAO.update(usuario);
            return usuario;
        }

        public static boolean deleteUsuario(Integer id) {

            try{
                Usuario u = findUsuario(id);
                if(u.tareas.size() > 0){
                    for(int i=u.tareas.size()-1;i>-1;i--){
                        TareasService.deleteTarea(u.tareas.get(i).id);
                    }
                }
                UsuarioDAO.delete(id);
                return true;
            }
            catch(Exception e){
                return false;
            }
        }

        public static boolean existeUsuarioConPass(Usuario user) {
            try{
                Usuario a=UsuarioDAO.ExisteLoginConPass(user);
                return true;//Tiene pass
            }
            catch(Exception e){
                return false;//no tiene pass
            }
        }

        public static Usuario existeLogin(Usuario user) {
                return UsuarioDAO.ExisteLogin(user);

        }
        public static boolean loginUsuario(Usuario user) {
            return UsuarioDAO.LoginUsuario(user);
        }

        public static List<Usuario> busquedaUsuario(String param) {
            List<Usuario> lista = UsuarioDAO.busquedaUsuario(param);
            

            return lista;
        }

        public static Integer CantidadUsuariosBusqueda(String param) {
            List<Usuario> lista = UsuarioDAO.busquedaUsuario(param);
            
            if (lista==null){
                return 0;
            }

            Integer cantidad = lista.size();
            return cantidad;
        }

        public static Usuario findUsuarioSinPass(Integer id) {
            Usuario user = UsuarioDAO.find(id);

            Usuario usuario = user.SinPass();

            return usuario;
        }

}
