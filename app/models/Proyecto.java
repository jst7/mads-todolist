package models;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.data.format.*;

import java.util.List;
import java.util.ArrayList;

@Entity
public class Proyecto {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer id;
    @Constraints.Required
    public String nombre;

    @OneToMany(mappedBy="proyecto", cascade = CascadeType.REMOVE)
    public List<Estado> estados = new ArrayList<Estado>();

    @OneToMany(mappedBy="proyecto")
    public List<Tarea> tareas = new ArrayList<Tarea>();

    @ManyToOne
    @JoinColumn(name="propietarioId")
    public Usuario propietario;

    @ManyToMany(mappedBy="proyectoscolabora",cascade = CascadeType.PERSIST)
    public List<Usuario> usuariosColaboradores = new ArrayList<Usuario>();



    // Un constructor vacío necesario para JPA
    public Proyecto() {
    }

    // El constructor principal con los campos obligatorios
    public Proyecto(String nombre) {
        this.nombre = nombre;
    }

    public Proyecto(String nombre,Usuario propietario) {
        this.nombre = nombre;
        this.propietario = propietario;
    }

    public String toString() {
        //return String.format("Proyecto id: %s nombre: %s", id, nombre);
        return String.format("%s", nombre);
    }
    public Proyecto copy() {
        Proyecto nuevo 	= new Proyecto(this.nombre);
        nuevo.id 		= this.id;
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
        Proyecto other = (Proyecto) obj;
        // Si tenemos los ID, comparamos por ID
        if (id != null && other.id != null)
            return (id == other.id);
        // sino comparamos por campos obligatorios
        else {
            if (nombre == null) {
                if (other.nombre != null) return false;
            } else if (!nombre.equals(other.nombre)) return false;
        }
        return true;
    }
}
