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
public class Mensaje {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer id;
    public String subject;
    public String body;
    @Temporal(TemporalType.DATE)
    public Date fechaEnvio;

    @ManyToOne
    @JoinColumn(name="usuarioTo")
    public Usuario usuarioTo;

    @ManyToOne
    @JoinColumn(name="usuarioFrom")
    public Usuario usuarioFrom;

    // necesario un constructor vacío para JPA
    public Mensaje() {}

    // El campos obligatorios del constructor
    public Mensaje(Usuario usuarioFrom, Usuario usuarioTo, String subject, String body) {
        //this.usuarioFrom    = usuarioFrom;
        //this.usuarioTo      = usuarioTo;
        this.subject        = subject;
        this.body           = body;
    }

    public Mensaje copy() {
        Mensaje nuevo       = new Mensaje();
        nuevo.id            = this.id;
        nuevo.subject       = this.subject;
        nuevo.body          = this.body;
        nuevo.fechaEnvio    = this.fechaEnvio;
        return nuevo;
        }

    public String toString() {
        String fechaStr = null;
        if (fechaEnvio != null) {
            SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
            fechaStr = formateador.format(fechaEnvio);
        }
        return String.format("Mensaje id: %s subject: %s body: %s from: %s to: %s",
                      id, subject, body, fechaStr, usuarioFrom, usuarioTo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        Mensaje other = (Mensaje) obj;
        // Si tenemos los ID, comparamos por ID
        if (id != null && other.id != null)
            return (id == other.id);
        // sino comparamos por campos obligatorios
        else {
            if (subject == null) {
                if (other.subject != null) return false;
            } else if (!subject.equals(other.subject)) return false;
            if (body == null) {
                if (other.body != null) return false;
            } else if (!body.equals(other.body)) return false;
        }
        return true;
    }
    
}
