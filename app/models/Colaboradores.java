package models;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.data.format.*;

import java.util.List;
import java.util.ArrayList;

@Entity
public class Colaboradores{

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Integer id;

  @ManyToOne
  public Proyecto proyectos;

  @ManyToOne
  public Usuario propietario;

}
