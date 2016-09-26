package services;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import models.*;

public class UsuariosService {
        public static Usuario crearUsuario(Usuario usuario) {
        return UsuarioDAO.create(usuario);
        }
}