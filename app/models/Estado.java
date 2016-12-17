package models;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.data.format.*;


@Entity
public class Estado {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="proyectoId")
    public Proyecto proyecto;
    public String nombre;

    public Estado() {
        nombre = "";
    }
    public Estado(String n) {
        nombre = n;
    }
    public String toString() {
        //return String.format("Proyecto id: %s nombre: %s", id, nombre);
        return String.format("%s", nombre);
    }
    public Estado copy() {
        Estado nuevo 	= new Estado(this.nombre);
        nuevo.id 		= this.id;
        nuevo.proyecto = this.proyecto;
        return nuevo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        Estado other = (Estado) obj;
        // Si tenemos los ID, comparamos por ID
        if (id != null && other.id != null)
            return (id == other.id);
        // sino comparamos por campos obligatorios
        else {
            if (nombre == null) {
                if (other.nombre != null) return false;
            } else if (!nombre.equals(other.nombre)) { return false;
            } else if (other.proyecto == null) {
                if(other.proyecto != null) {return false;}
            } else if(!proyecto.equals(other.proyecto)) {return false;}

        }
        return true;
    }
}
