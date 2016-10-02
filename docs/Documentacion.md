#MADS_ToDoList#




Jorge Sgovia Tormo
***

# Documentación #

##Para el desarrollador##

Hemos creado 3 peticiones, una GET y dos POST

`GET    /usuarios/inicio             controllers.UsuarioController.paginaInicio()`

`POST    /usuarios/login             controllers.UsuarioController.entrarLogin()`

`POST    /usuarios/registro         controllers.UsuarioController.registrarUsuario()`


La primera carga la página de inicio en la que usamos javascript para comprobar los campos de los formularios y mostrar los mensajes de error.

En el segundo simplemente se usa para comprobar que el existe la dupla Login, password.

El tercero se usa para el registro, devolviendo los mensajes segun sean necesarios:
1. Usuario ya existe
2. Usuario actualizado (caso en el que el login existe sin contraseña)
3. Usuario creado de 0.

* * *

##Para el usuario##


Para iniciar sesión, registrarnos o completar el registro con un nombre de usuario ya existente, tenemos la siguiente pantalla:
![1](/docs/1.png "Página de Inicio")

En la barra de arriba, en los recuadros de *Usuario*, *Contraseña* y boton *Entrar*. Se usa para iniciar sesión teniendo **usuario** y **contraseña**. En el caso de que algún campo sea incorrecto recibiremos el siguiente mensaje de error:
![2](/docs/2.png "Mensaje de error, Iniciando sesión")

En el caso contrario, en el que todo vaya bien pasaremos a la pantalla de saludo al usuario:
![3](/docs/3.png "Saludo al usuario recien logueado")


_____

Para registrarnos necesitamos completar el formulario con tres recuadros, que corresponden a **Usuario**, **Contraeña** y **Repetir contraseña**.

Para ello deberemos elegir un nombre de usuario, una contraseña y repetir la misma en la casilla de debajo.
La primera casilla no tiene ningun tipo de limitación, la segunda, para la contraseña te pide un mínimo de 7 caracteres, y la tercera te pide coincidir con la segunda; en el momento de que estas dos coincidan quedará remarcado con ticks de verificación.

![4](/docs/4.png "Formulario de Registro")
![5](/docs/5.png "Formulario de Registro 2, introduciendo password")
![6](/docs/6.png "Formulario de Registro 3, Repitiendo password y no coincide")
![6](/docs/7.png "Formulario de Registro 4, Repitiendo password y coincide")

Aquí pueden darse 3 casos.
El usuario ya existe, y daria este mensaje de error:
![8](/docs/8.png "Usuario ya existe")

El usuario ya existe, pero no tenia contraseña por lo que se actualizaría:
![9](/docs/9.png "Usuario actualizado")

El usuario no existe, por lo tanto se crea de 0:
![10](/docs/10.png "Usuario creado de cero")

***
