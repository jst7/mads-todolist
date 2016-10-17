package dao;

import play.db.Database;
import play.db.Databases;
import play.db.jpa.*;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import models.*;

public class UsuarioDaoTest {

    Database db;
    JPAApi jpa;

    @Before
    public void initDatabase() {
        db = Databases.inMemoryWith("jndiName", "DefaultDS");
        // Necesario para inicializar el nombre JNDI de la BD
        db.getConnection();
        // Se activa la compatibilidad MySQL en la BD H2
        db.withConnection(connection -> {
            connection.createStatement().execute("SET MODE MySQL;");
        });
        jpa = JPA.createFor("memoryPersistenceUnit");
    }

    @After
    public void shutdownDatabase() {
        db.withConnection(connection -> {
            connection.createStatement().execute("DROP TABLE Usuario;");
        });
        jpa.shutdown();
        db.shutdown();
    }

    @Test
    public void creaBuscaUsuario() {
        Integer id = jpa.withTransaction(() -> {
            Usuario nuevo = new Usuario("pepe", "pepe");
            boolean creado = UsuarioDAO.create(nuevo);
            if(creado){
                nuevo = UsuarioDAO.ExisteLogin(nuevo);
            }
            return nuevo.id;
        });

        jpa.withTransaction(() -> {
            Usuario usuario = UsuarioDAO.find(id);
            assertThat(usuario.login, equalTo("pepe"));
        });
    }

    @Test
    public void buscaUsuarioLogin() {
        jpa.withTransaction(() -> {
            Usuario prueba = new Usuario("pepe", null);
            Usuario usuario = null;
            try{
                usuario = UsuarioDAO.ExisteLogin(prueba);
            }
            catch(Exception e){
                usuario = null;
            }
            
            assertNull(usuario);
        });
    }

    @Test
    public void comprobarAutenticacion(){
        jpa.withTransaction(() -> {
            Usuario prueba = new Usuario("pepe", "1234567");
            Usuario nobd = new Usuario("pope", "1234567");

            UsuarioDAO.create(prueba);

            boolean usuario = false;

            usuario = UsuarioDAO.LoginUsuario(prueba);
            assertTrue(usuario);
            usuario = UsuarioDAO.LoginUsuario(nobd);
            assertFalse(usuario);
        });
    }

    @Test
    public void comprobarBorrarUserAutenticacion(){
        jpa.withTransaction(() -> {
            Usuario prueba = new Usuario("pepe", "1234567");

            UsuarioDAO.create(prueba);//Inserto Usuario

            boolean usuario = false;

            usuario = UsuarioDAO.LoginUsuario(prueba);
            assertTrue(usuario);//Compruebo que el usuario autentifique

            Usuario paraId = UsuarioDAO.ExisteLogin(prueba);//Recupero el usuario
            UsuarioDAO.delete(paraId.id);//elimino el usuario por id
            usuario = UsuarioDAO.LoginUsuario(prueba);//Compruebo que no autentifique
            assertFalse(usuario);

        });
    }
    @Test
    public void comprobarUsuarioConPass(){
        jpa.withTransaction(() -> {
            Usuario prueba = new Usuario("pepe", null);
            Usuario usuario = null;

            UsuarioDAO.create(prueba);
            try{
                usuario = UsuarioDAO.ExisteLoginConPass(prueba);
                //comprueba que tenga contraseña, si tiene falla
            }
            catch(Exception e){
                usuario = null;
            }
            
            assertNull(usuario);
        });

    }
}