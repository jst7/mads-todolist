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

public class CrearProyectoTest {

    static Database db;
    static JPAApi jpa;
    JndiDatabaseTester databaseTester;

@BeforeClass
      static public void initDatabase() {
          db = Databases.inMemoryWith("jndiName", "DefaultDS");
          // Necesario para inicializar el nombre JNDI de la BD
          db.getConnection();
          // Se activa la compatibilidad MySQL en la BD H2
          db.withConnection(connection -> {
              connection.createStatement().execute("SET MODE MySQL;");
          });
          jpa = JPA.createFor("memoryPersistenceUnit");
      }

      @Before
      public void initData() throws Exception {
          databaseTester = new JndiDatabaseTester("DefaultDS");
          IDataSet initialDataSet = new FlatXmlDataSetBuilder().build(new
          FileInputStream("test/resources/proyectos_dataset.xml"));
          databaseTester.setTearDownOperation(DatabaseOperation.DELETE);
          databaseTester.setDataSet(initialDataSet);
          databaseTester.onSetup();
      }

      @After
      public void clearData() throws Exception {
          databaseTester.onTearDown();
      }

      @AfterClass
      static public void shutdownDatabase() {
          jpa.shutdown();
          db.shutdown();
      }

    @Test
    public void CreateProyectoDAO(){
        jpa.withTransaction(() -> {
          Proyecto p = new Proyecto("miproyectotest");
          Proyecto aux = ProyectoDAO.create(p);
        });

        jpa.withTransaction(() -> {
          boolean finded = false;
          try {
            Proyecto p = new Proyecto("miproyectotest");
            Proyecto aux = ProyectoDAO.findByName(p);
            finded = true;
          } catch (Exception e) {
            finded = false;
          }
          assertTrue(finded);
        });
    }

    @Test
    public void CreateProyectoService(){
        jpa.withTransaction(() -> {
          Proyecto p = new Proyecto("miproyectotest");
          Proyecto aux = ProyectosService.crearProyecto(p);
        });

        jpa.withTransaction(() -> {
          boolean finded = false;
          try {
            Proyecto p = new Proyecto("miproyectotest");
            Proyecto aux = ProyectosService.findByName(p);
            finded = true;
          } catch (Exception e) {
            finded = false;
          }
          assertTrue(finded);
        });
    }
}
