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

    public String toString() {
        return String.format("Tarea id: %s descripcion: %s", id, descripcion);
    }
}