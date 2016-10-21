package services;

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



public class UsuariosServiceTest {

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
    public void loginModificadoExiste() {
    	jpa.withTransaction(() -> {

    		Usuario usuario = null;
    		usuario = UsuariosService.findUsuario(2);

            Usuario desconectado = usuario.copy();
    		desconectado.login = "jorgest";

            try{
                UsuariosService.modificaUsuario(desconectado);
                fail("No se ha lanzado excepción login ya existe");
            }
            catch(UsuariosException ex){
                System.out.println("ERROR DE USUARIO");
            }

        });
    }

    @Test
    public void borrarUsuarioTest(){
        jpa.withTransaction(() -> {

            boolean encontrado = UsuariosService.deleteUsuario(71);
            assertFalse(encontrado);
            encontrado = UsuariosService.deleteUsuario(1);
            assertTrue(encontrado);

        });
    }

    @Test
    public void buscarUsuarioTest(){
        jpa.withTransaction(() -> {

            Usuario encontrado =  UsuariosService.findUsuario(71);
            assertNull(encontrado);
            encontrado =  UsuariosService.findUsuario(1);
            assertNotNull(encontrado);

        });
    }

    @Test
    public void existeUsuarioConPassTest(){
        jpa.withTransaction(() -> {
            Usuario userconpass = new Usuario("jorgest",null);
            Usuario usersinpass = new Usuario("anabel",null);

            boolean existe =  UsuariosService.existeUsuarioConPass(userconpass);
            assertTrue(existe);
            existe = UsuariosService.existeUsuarioConPass(usersinpass);
            assertFalse(existe);

        });
    }


    @Test
    public void existeLoginTest(){
        jpa.withTransaction(() -> {
            Usuario userExiste = new Usuario("jorgest",null);
            Usuario userNoExiste = new Usuario("hola",null);

            Usuario existe = UsuariosService.existeLogin(userExiste);
            assertNotNull(existe);
            try{
            Usuario noExiste = UsuariosService.existeLogin(userNoExiste);
            fail("no existe y no salta la excepción");
            }
            catch(Exception e){
            }
        });
    }

}
