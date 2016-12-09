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

public class NotificacionTest {

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
        FileInputStream("test/resources/tareas_dataset.xml"));
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
    public void CrearNotificacion(){
      jpa.withTransaction(() -> {
        Notificacion not = new Notificacion("Juan", "Mensaje", "Mi nuevo mensaje");
        Boolean resultado = NotificacionService.crearNotificacion(not);
        assertTrue(resultado);
      });
    }

    @Test
    public void LeerNotificacion(){
      jpa.withTransaction(() -> {
        List<Notificacion> lista = NotificacionService.findAll(1);
        Integer tam = lista.size();
        Boolean res = false;
        if (tam > 0) {
            res = true;
        } else {
            res = false;
        }
        assertTrue(res);
      });
    }

    @Test
    public void LeerNotificacionSimple(){
      jpa.withTransaction(() -> {
        Notificacion newNot = new Notificacion("adrian", "miTipo", "miDesc");
        Boolean resCreate = NotificacionService.crearNotificacion(newNot);
    });
      jpa.withTransaction(() -> {
        Notificacion not = NotificacionService.findNotificacion(2);
        Boolean res = false;
        if (not.tipo == "miTipo") {
            res = true;
        } else {
            res = false;
        }
        assertTrue(res);
      });
    }

    @Test
    public void ComprobarLeerNotificacion(){
      jpa.withTransaction(() -> {
        Boolean resCreate = NotificacionService.leerNotificacion(1);
    });
      jpa.withTransaction(() -> {
        List<Notificacion> lista = NotificacionService.findAll(1);
        Integer tam = lista.size();
        Boolean res = false;
        if (tam > 1) {
            res = true;
        } else {
            res = false;
        }
        assertFalse(res);
      });
    }
}