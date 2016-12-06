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


public class ColaboradoresTest {

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
      public void ColaboradorTest(){

        jpa.withTransaction(() -> {

          Usuario user = new Usuario("pepote","12345");
          UsuarioDAO.create(user);
          Usuario usuario1 = new Usuario("juanito","12345");
          UsuarioDAO.create(usuario1);
          Usuario ur = UsuarioDAO.ExisteLogin(user);
          Usuario ur2 = UsuarioDAO.ExisteLogin(usuario1);
          Proyecto p = new Proyecto("miproyectotestnuevo",user);
          Proyecto aux = ProyectoDAO.create(p);

          p.usuariosColaboradores.add(ur2);
          //assertEquals(user.id,user1.id);


        });

      }

    }
