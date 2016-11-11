 package models;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.data.format.*;


@Entity
public class Tarea {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer id;
    @Constraints.Required
    public String descripcion;

    // Un constructor vacío necesario para JPA
    public Tarea() {}

    // El constructor principal con los campos obligatorios
    public Tarea(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tarea(String descripcion, Usuario usuario) {
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public String toString() {
        return String.format("Tarea id: %s descripcion: %s", id, descripcion);
    }
    public Tarea copy() {
        Tarea nueva = new Tarea(this.descripcion);
        nueva.id = this.id;
        return nueva;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime + ((descripcion == null) ? 0 : descripcion.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        Tarea other = (Tarea) obj;
        // Si tenemos los ID, comparamos por ID
        if (id != null && other.id != null)
            return (id == other.id);
        // sino comparamos por campos obligatorios
        else {
            if (descripcion == null) {
                if (other.descripcion != null) return false;
            } else if (!descripcion.equals(other.descripcion)) return false;
        }
        return true;
    }
    @ManyToOne
    @JoinColumn(name="usuarioId")
    public Usuario usuario;

    @ManyToOne
    @JoinColumn(name="proyectoId")
    public Proyecto proyecto;
}