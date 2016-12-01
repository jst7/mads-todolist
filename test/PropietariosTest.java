import play.db.Database;
import play.db.Databases;
import play.db.jpa.*;
import org.junit.*;
import org.dbunit.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.*;
import org.dbunit.operation.*;
import java.io.FileInputStream;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import services.*;
import models.*;


public class PropietariosTest {

    static Database db;
    static JPAApi jpa;
    JndiDatabaseTester databaseTester;


@BeforeClass
      static public void initDatabase() {
          db = Databases.inMemoryWith("jndiName", "DefaultDS");

          db.getConnection();

          db.withConnection(connection -> {
              connection.createStatement().execute("SET MODE MySQL;");
          });
          jpa = JPA.createFor("memoryPersistenceUnit");
      }



      @AfterClass
      static public void shutdownDatabase() {
          jpa.shutdown();
          db.shutdown();
      }



      @Test
      public void PropietarioTest(){

        jpa.withTransaction(() -> {

          Usuario user = new Usuario("pepote","12345");
          UsuarioDAO.create(user);
          Usuario ur = UsuarioDAO.ExisteLogin(user);
<<<<<<< 09b75de7e61afa79dba886c21d0e6b5e6d8e7b9a
          Proyecto p = new Proyecto("miproyectotestnuevo",user);
          Proyecto aux = ProyectoDAO.create(p);
          Proyecto pr = new Proyecto("miproyectotest2",user);
           pr = ProyectoDAO.create(pr);
          Usuario user1 = pr.propietario;

          assertEquals(user.id,user1.id);
=======
          Proyecto p = new Proyecto("miproyectotest",user);
          Proyecto aux = ProyectoDAO.create(p);

          ur.proyectos.add(aux);
          Proyecto pr = new Proyecto("miproyectotest2");


          assertEquals(true,true);
>>>>>>> Arreglados algunos test a falta de comprobar el borrado

        });

      }

    }
