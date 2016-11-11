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

    public Integer duracion=0;
    public String tamano="Sin tamaño";
    
    // Un constructor vacío necesario para JPA
    public Tarea() {}

    public Tarea(String descripcion,int duracion) {
          this.descripcion = descripcion;
          this.duracion = duracion;
    }
    public Tarea(String descripcion,int duracion, String tamano) {
          this.descripcion = descripcion;
          this.duracion = duracion;
          this.tamano = tamano;
    }
    // El constructor principal con los campos obligatorios
    public Tarea(String descripcion) {
        this.descripcion = descripcion;
        this.duracion = duracion;
    }

    public Tarea(String descripcion, Usuario usuario) {
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public Tarea(String descripcion, Usuario usuario,int duracion, String tamano) {
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.duracion = duracion;
        this.tamano = tamano;
    }

    public String toString() {
        return String.format("Tarea id: %s descripcion: %s duracion %s ", id, descripcion,duracion);
    }
    public Tarea copy() {
        Tarea nueva = new Tarea(this.descripcion,this.duracion, this.tamano);
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
            if (duracion == null) {
                if (other.duracion != null) return false;
            } else if (!duracion.equals(other.duracion)) return false;
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