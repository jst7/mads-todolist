package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.data.format.*;

import java.util.List;
import java.util.ArrayList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Entity
public class Notificacion {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer id;
    @Constraints.Required
    public String user;
    @Constraints.Required
    public String tipo;
    @Constraints.Required
    public String descripcion;
    public String fecha;
    public Boolean leido;

    // necesario un constructor vacío para JPA
    public Notificacion() {}

    // El campos obligatorios del constructor
    public Notificacion(String user, String tipo, String descripcion) {
        this.user          = user;
        this.tipo           = tipo;
        this.descripcion    = descripcion;
    }

    public Notificacion copy() {
        Notificacion nuevo  = new Notificacion();
        nuevo.id            = this.id;
        nuevo.user         = this.user;
        nuevo.tipo          = this.tipo;
        nuevo.descripcion   = this.descripcion;
        nuevo.fecha         = this.fecha;
        nuevo.leido         = this.leido;
        return nuevo;
        }

    public String toString() {
        return String.format("Notificacion id: %s user: %s tipo: %s descripcion: %s fecha: %s leido: %s",
                      id, user, tipo, descripcion, fecha, leido);
    }    
}
