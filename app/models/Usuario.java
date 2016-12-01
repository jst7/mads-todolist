package models;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.data.format.*;

import java.util.List;
import java.util.ArrayList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer id;
    @Constraints.Required
    public String login;
    public String password;
    public String nombre;
    public String apellidos;
    public String eMail;
    @Formats.DateTime(pattern="dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    public Date fechaNacimiento;

    // necesario un constructor vacío para JPA
    public Usuario() {}

    // El campos obligatorios del constructor
    public Usuario(String login, String password) {
        this.login = login;
        this.password = password;
    }


    public Usuario copy() {
        Usuario nuevo = new Usuario();
        nuevo.id = this.id;
        nuevo.login = this.login;
        nuevo.password = this.password;
        nuevo.apellidos = this.apellidos;
        nuevo.eMail = this.eMail;
        nuevo.fechaNacimiento = this.fechaNacimiento;
        return nuevo;
        }

    public Usuario SinPass() {
        Usuario nuevo = new Usuario();
        nuevo.id = this.id;
        nuevo.login = this.login;
        nuevo.apellidos = this.apellidos;
        nuevo.eMail = this.eMail;
        nuevo.fechaNacimiento = this.fechaNacimiento;
        return nuevo;
        }

    // Sustituye por null todas las cadenas vacías que pueda tener
    // un usuario en sus atributos
    public void nulificaAtributos() {
        if (nombre != null && nombre.isEmpty()) nombre = null;
        if (apellidos != null && apellidos.isEmpty()) apellidos = null;
        if (eMail != null && eMail.isEmpty()) eMail = null;
    }

    public String toString() {
        String fechaStr = null;
        if (fechaNacimiento != null) {
            SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
            fechaStr = formateador.format(fechaNacimiento);
        }
        return String.format("Usuario id: %s login: %s passworld: %s nombre: %s " +
                      "apellidos: %s eMail: %s fechaNacimiento: %s",
                      id, login, password, nombre, apellidos, eMail, fechaStr);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        Usuario other = (Usuario) obj;
        // Si tenemos los ID, comparamos por ID
        if (id != null && other.id != null)
            return (id == other.id);
        // sino comparamos por campos obligatorios
        else {
            if (login == null) {
                if (other.login != null) return false;
            } else if (!login.equals(other.login)) return false;
            if (password == null) {
                if (other.password != null) return false;
            } else if (!password.equals(other.password)) return false;
        }
        return true;
    }
    @OneToMany(mappedBy="usuario")
    public List<Tarea> tareas = new ArrayList<Tarea>();


    @OneToMany(mappedBy="propietario")
    public List<Proyecto> proyectos = new ArrayList<Proyecto>();
<<<<<<< a5d809dbb2fdf8a037bcf82130aeb2d3d0a25e40

=======
>>>>>>> Arreglados algunos test a falta de comprobar el borrado
}
