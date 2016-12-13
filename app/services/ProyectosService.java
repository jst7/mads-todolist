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

		List<Proyecto> lista = ProyectoDAO.findAllPropietario(id);
		lista.addAll(findAllProyectosColaborador(id));

		return lista;
		}

		public static List<Proyecto> findAllProyectosColaborador(Integer id){
			List<Proyecto> lista = ProyectoDAO.findAll();
			List<Proyecto> listaTotal = new ArrayList<Proyecto>();
			for(Proyecto proyecto: lista){
				if(ComprobarColabora(proyecto,id)){
					listaTotal.add(proyecto);
				}
			}
			return listaTotal;
		}

		public static boolean ComprobarColabora(Proyecto proyecto,Integer idUsuario){

			for(Usuario colaborador: proyecto.usuariosColaboradores){
				if(colaborador.id == idUsuario){
					return true;
				}
			}
			return false;
		}

	public static Integer cantidadProyectosPropietario(Integer id) {

		List<Proyecto> lista = ProyectoDAO.findAllPropietario(id);

		return lista.size();
	}


	public static Integer cantidadProyectosColabora(Integer id){

	List<Proyecto> lista = findAllProyectosColaborador(id);
	return lista.size();
	}



	public static Proyecto addColaborador(Proyecto proyecto,Integer id){

		Usuario user = UsuariosService.findUsuario(id);
		if(proyecto.usuariosColaboradores.add(user)){
			user.proyectoscolabora.add(proyecto);
		}

		return ProyectoDAO.update(proyecto);

	}



	public static List<Usuario> filtraUsuarios(Proyecto proyecto,List<Usuario> users){

		List<Usuario> usuarios = new ArrayList<Usuario>();
		for(Usuario usuario : users){
			boolean esta = false;
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

	public static List<Usuario> listaCOlab(Proyecto proyecto,List<Usuario> users){

		List<Usuario> usuarios = new ArrayList<Usuario>();
		for(Usuario usuario : users){
			boolean esta = false;
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


	public static List<Usuario> listarColaboradores(Proyecto proyecto,List<Usuario> users){

		List<Usuario> usuarios = new ArrayList<Usuario>();
		for(Usuario usuario : users){
			boolean esta = false;
			for (Usuario usuarioCol: proyecto.usuariosColaboradores) {
				if(usuarioCol == usuario){
					esta = true;
				}
			}
			if(esta || proyecto.propietario == usuario){
				usuarios.add(usuario);
			}
		}

		return usuarios;
	}

	public static boolean BorrarColaborador(Proyecto proyecto,Usuario colaborador){

		try{
				proyecto.usuariosColaboradores.remove(colaborador);
				colaborador.proyectoscolabora.remove(proyecto);
				ProyectoDAO.update(proyecto);
				return true;
			}catch(Exception ex){
			return false;
		}

	}

}
