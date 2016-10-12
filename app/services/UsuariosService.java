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
        	Logger.debug("Se accede al usuario: "+ user.id + " " + user.nombre);

        	return user;
    	}
        public static Usuario modificaUsuario(Usuario usuario) {
            Logger.debug(usuario.toString());
            return UsuarioDAO.update(usuario);
        }
        public static boolean deleteUsuario(Integer id) {
            
            try{
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
}