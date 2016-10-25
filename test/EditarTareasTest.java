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

public class EditarTareasTest {

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
        databaseTester.setDataSet(initialDataSet);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
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
    public void EditarTareaTestDAO() {
            jpa.withTransaction(() -> {

                List<Tarea> tareasUsuario = TareasService.listaTareasUsuario(1);
                Tarea tarea = tareasUsuario.get(0);
                int cantidadInicial = tareasUsuario.size();//Almacenamos la cantidad de tareas inicial

                tarea.descripcion="cambiada";
                tarea = TareaDAO.update(tarea);

                int cantidadFinal = TareasService.listaTareasUsuario(1).size();//Almacenamos la cantidad de tareas final para comprobar si se ha añadido otra por el camino

                assertEquals("cambiada", TareasService.findTarea(tarea.id).descripcion);
                assertEquals(cantidadInicial, 3); 
            });
    }

    @Test
    public void EditarTareaTestService() {
            jpa.withTransaction(() -> {

                List<Tarea> tareasUsuario = TareasService.listaTareasUsuario(1);
                Tarea tarea = tareasUsuario.get(0);
                int cantidadInicial = tareasUsuario.size();//Almacenamos la cantidad de tareas inicial

                tarea.descripcion="cambiada Service";
                tarea = TareasService.modificaTarea(tarea);

                int cantidadFinal = TareasService.listaTareasUsuario(1).size();//Almacenamos la cantidad de tareas final para comprobar si se ha añadido otra por el camino

                assertEquals("cambiada Service", TareasService.findTarea(tarea.id).descripcion);
                assertEquals(cantidadInicial, 3); 
            });
    }
}