package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import models.*;

public class ProyectosService {
	public static Proyecto crearProyecto(Proyecto proyecto) {
        try {
            findByName(proyecto);
            return null;
        } catch (Exception e) {
            NotificacionService.crearNotificacion(new Notificacion("allUsers", "Proyecto", "Nuevo proyecto"));
            return ProyectoDAO.create(proyecto);
        }
    }

    public static boolean deleteProyecto(Integer id) {
        try {
            NotificacionService.crearNotificacion(new Notificacion("allUsers", "Proyecto", "Proyecto " + id));
            ProyectoDAO.delete(id);
            return true;
        } catch(Exception e){
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

    public static Proyecto findByName(Proyecto proyecto) {
        return ProyectoDAO.findByName(proyecto);
    }

	public static Proyecto modificar(Proyecto proyecto) {
        NotificacionService.crearNotificacion(new Notificacion("allUsers", "Proyecto", "Modificación de proyecto: " + proyecto.id + " por: " + proyecto.propietario .login));
        ProyectoDAO.update(proyecto);
        return proyecto;
    }

		public static List<Proyecto> findAllProyectosPropietario(Integer id) {
			Logger.debug("entro : ");
			List<Proyecto> lista = ProyectoDAO.findAllPropietario(id);
			Logger.debug("Numero de proyectos propietario: " + lista.size());
			return lista;
		}

		public static Proyecto addColaborador(Proyecto proyecto,Integer id){

			Usuario user = UsuariosService.findUsuario(id);
			if(proyecto.usuariosColaboradores.add(user)){
				user.proyectoscolabora.add(proyecto);
			}

			return ProyectoDAO.update(proyecto);

		}

		public static List<Usuario> filtraUsuarios(Proyecto proyecto,List<Usuario> users){

			boolean esta = false;
			List<Usuario> usuarios = new ArrayList<Usuario>();
			for(Usuario usuario : users){
				for (Usuario usuarioCol: proyecto.usuariosColaboradores) {
					if(usuarioCol == usuario){
						esta = true;
					}
				}
				if(!esta && proyecto.propietario != usuario){
					usuarios.add(usuario);
				}
			}

			return usuarios;

		}

}
