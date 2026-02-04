# 📘 Intercambio de Apuntes

## 📌 Descripción del proyecto

Intercambio de Apuntes es una aplicación web desarrollada con Spring Boot que permite a los usuarios compartir apuntes de distintas asignaturas, así como comentarlos y valorarlos.

Los usuarios pueden:
- Registrarse e iniciar sesión
- Crear, editar y eliminar sus propios apuntes
- Comentar y valorar apuntes de otros usuarios
- Buscar apuntes por título
- Filtrar apuntes por asignatura

Existe también un usuario administrador con permisos para editar y eliminar cualquier apunte.

La aplicación incluye una página de contacto, donde los mensajes se guardan en base de datos y se envían por correo electrónico.

---

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Spring Boot**
    - Spring MVC
    - Spring Data JPA
    - Spring Security
    - Spring Mail
- **Thymeleaf**
- **H2 Database**
- **HTML5 / CSS**
- **Maven**
- **IntelliJ IDEA**

---

## 💻 Requisitos para ejecutar la aplicación

Es necesario tener instalado:

- **Java JDK 17 o superior**
- **Maven**
- **IntelliJ IDEA** (recomendado)

---

## ▶️ Pasos para arrancar el proyecto en IntelliJ IDEA

1. Abrir **IntelliJ IDEA**
2. Seleccionar **File → Open**
3. Abrir la carpeta raíz del proyecto
4. Esperar a que Maven descargue todas las dependencias
5. Ejecutar la clase principal:
6. Acceder desde el navegador a: http://localhost:9010


---

## 👤 Usuarios de prueba

La aplicación inicializa automáticamente usuarios y datos de ejemplo.

| Usuario   | Contraseña   | Rol   |
|----------|-------------|-------|
| admin    | admin123    | ADMIN |
| usuario1 | usuario123  | USER  |
| usuario2 | usuario123  | USER  |

También se crean automáticamente:
- Asignaturas
- Apuntes

---

## 📂 Notas adicionales

- Los datos se almacenan en una base de datos **H2 persistente**, por lo que no se pierden al reiniciar la aplicación.
- El proyecto está preparado para ser ejecutado y evaluado sin configuración adicional.

