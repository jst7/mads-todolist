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

}
