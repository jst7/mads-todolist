# Sprint de Scrum

##### Integrantes
- Javier Molpeceres Gómez
- Adrián Gosálvez Maciá
- Alberto Sapiña Mora
- Jorge Segovia Tormo

##### Repositorio

https://github.com/jst7/mads-todolist

##### Tablero de Trello

https://trello.com/b/oXhIBL9Y/todolist-equipo-2

### Índice

1. Historias de usuario

2. Funcionalidades implementadas (breve descripción para el usuario y breve descripción técnica).

3. Informe sobre la metodología seguida (ejemplos de evolución del tablero, alguna métrica del desarrollo realizado en el sprint)

4. Informes sobre las reuniones de Scrum (planificación del sprint, 3 scrum diario, revisión).

5. Resultado de la retrospectiva: qué ha ido bien y qué se podría mejorar.


# Historias de usuario


### 1. Notificaciones

##### Descripción
	El usuario podrá ver notificaciones para estar informado de toda la actividad.

##### COS
	Se podran ver mensajes que formarán un registro de los cambios realizados e iteracciones de la aplicación



### 2. Mensajes

##### Descripción
	El usuario podrá enviar mensajes a otro usuario para poder comunicarse.

##### COS
	Un usuario puede enviar mensajes, recibirlos y leerlos.



### 3. Buscar tareas o usuarios

##### Descripción
	Como usuario quiero buscar tareas o usuarios para poder encontrarlas más fácilmente.

##### COS
	1, A partir de un texto poder filtrar tareas por su descripción, usuarios por login
	2. A partir de una operación de busqueda poder filtrar por alguna caracteristica del usuario o tarea.



### 4. Gestión de propietarios y colaboradores de proyectos

##### Descripción
	Como usuario de un proyecto quiero poder añadir colaboradores al proyecto.

##### COS
	1. Ver la lista de colaboradores de un proyecto y verificar que un nuevo usuario se ha añadido.
	2. Verificar que un usuario añadido a un proyecto puede acceder al proyecto.
	3. Borrar colaboradores de proyecto y vereficar que no tienen asignado ese proyecto.
	4. Comprobar que al borrar el proyecto se borra el proyecto a estos colaboradores.
	5. Añadir dueño del proyecto, este será el único que puede borrar y agregar colaboradores al proyecto.



### 5. Dashboard

##### Descripción
	El usuario quiere un dashboard para poder ver las tareas, notifiaciones y proyectos de un vistazo al inicio y saber el estado de todo rápidamente.
	El usuario quiere poder modificar los colores de su interfaz para su comodidad.

##### COS
	1. Se debe haber creado una vista que muestre proyectos, tareas y notificaciones de un usuario.
	2. Añadir botón para seleccionarlo y al interactuar con él cambiarán los colores según la elección.
	3. Poder seleccionar uno de los bloques del dashboard y poder ver su vista.



### 6. Añadir estado a una tarea

##### Descripción
    Como usuario quiero poder asignar el estado a una tarea para conocer el estado de cómo va el proyecto así como crear, modificar y eliminar estados personalizados de cada proyecto.

##### COS
    1. Gestionar estados del proyecto.
    2. Añadir un estado a una tarea y poder visualizar en qué estado se encuentra la misma.
    3. Visualizar campo estado en la lista de tareas.
    4. Resumen en la descripción del proyecto.



### 7. Ampliar información, color y archivado de una tarea

##### Descripción
    - Como usuario quiero poder asignar colores a una tarea para poder visualizar lógicamente el proyecto también añadir una fecha.
    - Como usuario quiero poder archivar una tarea para darla por terminada y eliminarla del tablón principal.

##### COS
    1. Añadir un color a una tarea y poder visualizar qué color tiene la misma.
    2. Poder archivar la tarea.

# Funcionalidades implementadas

#Gestión de propietarios y colaboradores de proyectos

## Para el desarrollador

En esta funcionalidad se implementa el comportamiento de los usuarios con los proyectos.Los usuarios interactuan con los proyectos a modo de propietario o de colaborador.

Los proyectos tienen 1 solo propietario, este será el único que podrá borrar y editar el proyecto, además solo el propietario podrá listar los colaboradores y añadir o borrar estos.

Por otro lado los usuarios pueden listar tanto los proyectos existentes en toda la página, como los proyectos de los cuales es propietario o colaborador.Solo podrá interactuar con estos últimos.

Se controlan estos comporatamientos con el siguiente código:

-devolviendo las listas de proyectos.

-propietario
```java
	public static Integer cantidadProyectosPropietario(Integer id) {

		List<Proyecto> lista = ProyectoDAO.findAllPropietario(id);

		return lista.size();
	}
```
-Colaborador

```java
	public static Integer cantidadProyectosColabora(Integer id){

	List<Proyecto> lista = findAllProyectosColaborador(id);
	return lista.size();
	}
```

Añadir colaborador.

```java

	public static Proyecto addColaborador(Proyecto proyecto,Integer id){

		Usuario user = UsuariosService.findUsuario(id);
		if(proyecto.usuariosColaboradores.add(user)){
			user.proyectoscolabora.add(proyecto);
		}

		return ProyectoDAO.update(proyecto);

```

## Para el Cliente

Como usuario podrá tener acceso a proyectos, podrá listar todos los proyectos existentes.

![1](Listaproyectostotal.png "Lista de pryectos totales")

![2](listaproyectospc.png "Lista de proyectos colaboradores")

Como usuario al ser propietario de un proyecto podrá modificar,borrar y listar colaboradores, si no es propietario y es colaborador solo podra cancelar su suscripción.

Como usuario propietario de un proyecto se podrá visualizar a los usuarios colaboradores y gestionarlos(borrar y añadir nuevos).

![3](colaboradoreslist.png "Lista de proyectos colaboradores")


#Compartir tarea

Esta funcionalidad ha sido sobreestimada.


Esta tarea pasa a ser sobreestimada ya que al realizar la comparticion de proyectos no tiene sentido que las tareas tengan una lista de colaboradores también puesto que es redundante y causa problemas de consistencia, dando a los usuarios un comportamiento no deseado.

#Buscador de Usuarios y Tareas
##Para el desarrollador
Tenemos un servicio para la busqueda de usuarios o de tareas independientes por si fuera necesario realizar un buscador independiente pero en la vista esta junto.

```java
        public static List<Usuario> busquedaUsuario(String param) {
            List<Usuario> lista = UsuarioDAO.busquedaUsuario(param);
            //param termino a buscar
            
            return lista;
        }
```
También un servicio para conseguir la cantidad de usuarios:
```java
        public static Integer CantidadUsuariosBusqueda(String param) {
            List<Usuario> lista = UsuarioDAO.busquedaUsuario(param);
            
            if (lista==null){
                return 0;
            }

            Integer cantidad = lista.size();
            return cantidad;
        }
```

```java
    public static List<Tarea> busquedaTarea(String param) {
        List<Tarea> lista = TareaDAO.busquedaTarea(param);
        //param termino a buscar
        
        return lista;
    }
```
También un servicio para conseguir la cantidad de tareas:
```java    
    public static Integer CantidadTareasBusqueda(String param) {
        List<Tarea> lista = TareaDAO.busquedaTarea(param);
        if (lista == null) { 
            return 0;
        }
        Integer cantidad = lista.size();
        return cantidad;
    }
```

##Para el cliente
El cliente podrá buscar por usuario o por tarea o por ambos a la vez en la misma vista.
Pordrá comenzar la busqueda desde el Dashboard o desde su propia página.

![1](buscador.png "Buscador")

#DashBoard
##Para el desarrollador
En el dashboard encontramos un conjunto de servicios como el contador de de mensajes o el contador de a proyectos propios o compartidos.

El contador de mensajes por ejemplo:

```java
	public static Integer mensajesSinleer(Integer id){
		Usuario user = new Usuario();
		user = UsuariosService.findUsuario(id);
		List<Mensaje> lista = MensajeDAO.findAllPorLeer(user.login);

    	return lista.size();
	}

	public static Integer mensajesTotalesEntrada(Integer id){
		Usuario user = new Usuario();
		user = UsuariosService.findUsuario(id);
		List<Mensaje> lista = MensajeDAO.findAllTotal(user.login);

    	return lista.size();
	}
```
Y los proyectos:

```java
	public static Integer cantidadProyectosPropietario(Integer id) {

		List<Proyecto> lista = ProyectoDAO.findAllPropietario(id);

		return lista.size();
	}


	public static Integer cantidadProyectosColabora(Integer id){

	List<Proyecto> lista = findAllProyectosColaborador(id);
	return lista.size();
	}
```


##Para el cliente
El cliente tiene un acceso directo a su perfil, modificar sus datos, la foto y su reemplazo, sus tareas, sus proyectos, las notificaciones...

Es un centro de mandos para el usuario.

![1](dashboard.png "Dashboard")

# Mensajes

## Para el desarrollador

Tenemos una entidad Mensaje mediante la cual nos servimos para crear una lógica de mensajes internos entre usuarios.

Aquí vemos el codigo para crear, buscar, leer y borrar mensajes

```java
public static boolean crearMensaje(Mensaje mensaje) {
		if (mensaje != null && mensaje.body != "") {
			NotificacionService.crearNotificacion(new Notificacion(mensaje.usuarioTo, "Mensaje", "Nuevo mensaje"));
			return MensajeDAO.create(mensaje);
		} else {
			return false;
		}
	}

	public static List<Mensaje> findAll() {
    	List<Mensaje> lista = MensajeDAO.findAll();
    	Logger.debug("Numero de mensajes: " + lista.size());
    	return lista;
	}

	public static List<Mensaje> findAllReceived(Integer idUsuario) {
		Usuario user = UsuariosService.findUsuario(idUsuario);
    	List<Mensaje> lista = MensajeDAO.findAllReceived(user.login);
    	Logger.debug("Numero de mensajes: " + lista.size());
    	return lista;
	}

	public static List<Mensaje> findAllSended(Integer idUsuario) {
		Usuario user = UsuariosService.findUsuario(idUsuario);
    	List<Mensaje> lista = MensajeDAO.findAllSended(user.login);
    	Logger.debug("Numero de mensajes: " + lista.size());
    	return lista;
	}

	public static Mensaje findMensaje(Integer idMensaje) {
    	Mensaje mensaje = MensajeDAO.find(idMensaje);
    	return mensaje;
	}

	public static Boolean leerMensaje(Integer idMensaje) {
		Boolean result = false;
		Mensaje mensaje = findMensaje(idMensaje);
		if (mensaje.body != "") {
			mensaje.leido = !mensaje.leido;
			mensaje = MensajeDAO.update(mensaje);	
			result = true;
		}
		return result;
	}

	public static Boolean borrarMensaje(Integer idMensaje) {
		Boolean result = false;
		Mensaje mensaje = findMensaje(idMensaje);
		if (mensaje.body != "") {
			mensaje.borrado = true;
			NotificacionService.crearNotificacion(new Notificacion(mensaje.usuarioTo, "Mensaje", "Mensaje: " + idMensaje + " eliminado"));
			mensaje = MensajeDAO.update(mensaje);	
			result = true;
		}
		return result;
	}

	public static Integer mensajesSinleer(Integer id){
		Usuario user = new Usuario();
		user = UsuariosService.findUsuario(id);
		List<Mensaje> lista = MensajeDAO.findAllPorLeer(user.login);

    	return lista.size();
	}

	public static Integer mensajesTotalesEntrada(Integer id){
		Usuario user = new Usuario();
		user = UsuariosService.findUsuario(id);
		List<Mensaje> lista = MensajeDAO.findAllTotal(user.login);

    	return lista.size();
	}
```

## Para el cliente

El cliente puede mandar y leer los mensajes recibidos que le aparezcan en la pantalla de mensajes.

![1](mensajes.png "Lista de notificaciones")

# Notificaciones

## Para el desarrollador

Tenemos una entidad Notificación y por cada evento que ocurre en la plataforma se crea una notificación que le aparecerá al usuario (indicado en un atributo de la Notifiación) en su dashboard.

Aquí vemos el codigo para crear, buscar y leer notificaciones

```java
public static boolean crearNotificacion(Notificacion notificacion) {
		if (notificacion != null && notificacion.tipo != "" && notificacion.descripcion != "") {
			notificacion.fecha = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());
			notificacion.leido = false;
			return NotificacionDAO.create(notificacion);
		} else {
			return false;
		}
	}

	public static List<Notificacion> findAll(Integer idUsuario) {
		if (idUsuario > 0 && idUsuario != null) {
			Usuario usuario = UsuariosService.findUsuario(idUsuario);
			List<Notificacion> lista = NotificacionDAO.findAll(usuario.login);
    		Logger.debug("Numero de notificacions: " + lista.size());
    		return lista;
		} else {
			return null;
		}
	}

	public static Notificacion findNotificacion(Integer idNotificacion) {
    	Notificacion notificacion = NotificacionDAO.find(idNotificacion);
    	return notificacion;
	}

	public static Boolean leerNotificacion(Integer idNotificacion) {
		Boolean result = false;
		Notificacion notificacion = NotificacionDAO.find(idNotificacion);
		if (notificacion.user != "" && notificacion.tipo != "" && notificacion.descripcion != "") {
			notificacion.leido = true;
			Notificacion not = NotificacionDAO.update(notificacion);
			if (not.leido) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
```

## Para el cliente

El cliente puede leer las notificaciones que le aparezcan en el dashboard

![1](notificaciones.png "Lista de notificaciones")

# Foto de perfil

## Para el desarrollador

Añadimos un atributo a la entidad Usuario donde se almacena la ruta de la imagen de perfil.

Aquí vemos el codigo para subir una imagen de perfil

```java
        public static Boolean subirImagen(FilePart<File> picture, Integer idUsuario) {
            if (picture != null) {
                Usuario user        = findUsuario(idUsuario);
                File file           = picture.getFile();
                String fileName     = picture.getFilename();
                String contentType  = picture.getContentType();
                String fullPath     = Play.application().path().getPath() + "/public/images";
                String extension    = fileName.substring(fileName.length() - 4);
                
                String fileNameNew  = idUsuario + "-" + user.login + extension;
                
                file.renameTo(new File(fullPath, fileNameNew));
                String fullPathBBDD = controllers.routes.Assets.versioned(new controllers.Assets.Asset("images/" + fileNameNew)).toString();
                
                Usuario usuario     = findUsuario(idUsuario);
                usuario.imagen      = fullPathBBDD;
                UsuariosService.modificaUsuario(usuario);

                return true;
            } else {
                return false;
            }
        }
```

## Para el cliente

![1](foto1.png "Foto de perfil")

![1](foto2.png "Foto de perfil 2")

# Informe sobre la metodología seguida

#####Hemos seguido la metodología Scrum y seguido el siguiente orden para la realización de las caracteristicas de la aplicación:

- Planificación de las caracteristicas necesarias para la implementación
- Desarrollo del código de programación
- Pruebas funcionales o test de código
- Revisión de código (pull request)
- Integración (Pruebas automatizadas con Travis)
- Integración con la rama principal del proyecto

# Informes sobre las reuniones de Scrum

En cuanto a las reuniones `dailys`, hemos realizado tres reuniones:

1. 03-12-2016

Features completadas:

- Feature 3

Features en dearrollo:

- Feature 4
- Feature 5
- Feature 6
- Feature 7
- Feature 8

![1](capturas/reunion3.jpeg "Reunión 3-12-16")

#####Estado del proyecto

######Features sin comenzar
- Feature 10, 11

######Features en desarrollo
- Features 4, 5, 6, 7, 8, 9

######Features completadas
- Features 3

2. 10-12-2016

Features completadas:

- Feature 3
- Feature 4

Features en dearrollo:

- Feature 5
- Feature 6
- Feature 7
- Feature 8
- Feature 9

![2](capturas/reunion10.jpeg "Reunión 10-12-16")

#####Estado del proyecto

######Features sin comenzar
- Feature 10, 11

######Features en desarrollo
- Features 5, 6, 7, 8, 9

######Features completadas
- Features 3, 4

3. 17-12-2016

Features completadas:

- Feature 3
- Feature 4
- Feature 5
- Feature 7
- Feature 8
- Feature 9

Features en dearrollo:

- Feature 6

Features sobreestiadas

- Feature 10 (Se explicará en la retrospectiva)

![3](capturas/reunion17.jpeg "Reunión 17-12-16")

#####Estado del proyecto

######Features sin comenzar
- Feature 11

######Features en desarrollo
- Feature 6

######Features completadas
- Feature 3, 4, 5, 7, 8 y 9

######Features HOTFIX
- Url: Cambiar el formato de url eliminando las querystring por formato REST
- Color de interfaz: Hacer permanente el color de la interfaz de usuario
- Integración imagen: Cambiar en el dashboard el poder subir la imagen con fichero físico
- Cambio de color: Cambiar el color negro por el azul

#####Pruebas funcionales

######TICs que no tienen tests

- 3: Mensajes
- 4: Buscador
		TIC4.5 Separar la búsqueda de usuario y tareas en interfaz
- 5: 
- 6: Estado de proyecto
		TIC6.5 No tiene test debido a que es la creación de una vista
- 7: Imagen de usuario
- 8: Dashboard
		TIC8.1, TIC8.2, TIC8.4, TIC8.6, TIC8.8 No tienen pruebas debido a que son interfaz
- 9: Notificaciones
- 10: Compartir tarea - FEATURE SOBREESTIMADA (No tiene pruebas)
- 11: 

# Retrospectiva

Pensamos que los puntos a desarrollar han sido los correctos y con ello nos hemos dado cuenta de las posibles ampliaciones y errores a corregir, que podemos hacer e integrar con el proyecto actual en próximos sprints o iteraciones.

### Ampliaciones

- Buscar por proyecto
- Listar las tareas de un proyecto
- Dividir las tareas en curso por porcentaje, para saber el estado real de la mismma
- Añadir sesión por cookies
- Añadir un administrador de la aplicación, para poder diferencia correctamente los roles de usuarios
- Mejorar los colores de la interfaz, la interfaz no es del todo amigable
- Mejorar el responsive design

### Errores

- Escogimos una tarea que parecía que iba a encajar perfectamente pero una vez integradas las demás, vimos que se producía un ciclo en el modelo relacional, por tanto, se solapaba "Compartir proyecto" con "Compartir tarea" y por temas de tiempo, ya que el sprint estaba ya definido, hemos tenido que ponerla como sobreestimada.
- Al eliminar un mensaje enviado se le elimina al receptor también


















