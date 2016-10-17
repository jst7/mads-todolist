package dao;

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

import models.*;

public class UsuarioDaoDbUnitTest {

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
        FileInputStream("test/resources/usuarios_dataset.xml"));
        databaseTester.setDataSet(initialDataSet);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE);
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
    public void findUsuarioPorLogin() {
        jpa.withTransaction(() -> {
            Usuario prueba = new Usuario("juan",null);
            Usuario usuario = UsuarioDAO.ExisteLogin(prueba);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            try {
                Date diezDiciembre93 = sdf.parse("10-12-1993");
                assertTrue(usuario.login.equals("juan") &&
                           usuario.fechaNacimiento.compareTo(diezDiciembre93) == 0);
            } catch (java.text.ParseException ex) {
            }
        });
    }

    @Test
    public void actualizaUsuario() {
        jpa.withTransaction(() -> {
            Usuario usuario = UsuarioDAO.find(2);
            usuario.apellidos = "Anabel Pérez";
            UsuarioDAO.update(usuario);
        });

        jpa.withTransaction(() -> {
            Usuario usuario = UsuarioDAO.find(2);
            assertThat(usuario.apellidos, equalTo("Anabel Pérez"));
        });
    }

    @Test
    public void borrarUsuario() {
        jpa.withTransaction(() -> {
            UsuarioDAO.delete(1);
        });

        jpa.withTransaction(() -> {

            Usuario usuario=null;
            try{
                usuario = UsuarioDAO.find(1);
            }
            catch(Exception e){
                usuario = null;
            }
            assertNull(usuario);
        });
    }

    @Test
    public void ExisteLoginConPass() {
            
        jpa.withTransaction(() -> {
            Usuario prueba = new Usuario("jorgest",null);
            Usuario usuario = null;
            
            try{
                usuario = UsuarioDAO.ExisteLoginConPass(prueba);
            }catch(Exception e){
                usuario = null;
            }
            assertThat(usuario.login, equalTo("jorgest"));
        });

    }

    @Test
    public void Autenticacion() {
            
        jpa.withTransaction(() -> {
            Usuario prueba = new Usuario("jorgest","1234567");
            boolean usuario = false;

            try{
                usuario = UsuarioDAO.LoginUsuario(prueba);
            }catch(Exception e){
                usuario = false;
            }
            assertTrue(usuario);
        });

    }


}