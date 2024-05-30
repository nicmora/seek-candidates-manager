# 🎯 Seek Candidates Manager

Seek Candidates Manager es una aplicación diseñada para gestionar los candidatos que se postulan al proceso de selección de Seek.

## 📁 Repositorio

El código fuente de este proyecto está disponible en el [repositorio](https://github.com/nicmora/seek-candidates-manager) de GitHub.

## ⚙️ Requisitos

Para ejecutar esta aplicación, necesitas tener instalados los siguientes componentes:

- Java 17 (OpenJDK 17)
- Docker Engine y Docker Compose

## 💻 Instalación

Para instalar y ejecutar la aplicación, sigue estos pasos:

1. Clona el repositorio:
    ```sh
    git clone https://github.com/nicmora/seek-candidates-manager.git
    ```

2. Accede al directorio del proyecto:
    ```sh
    cd seek-candidates-manager
    ```

3. Inicia los servicios utilizando Docker Compose:
    ```sh
    docker compose up
    ```

## 📄 Documentación

### Postman

Puedes encontrar la documentación completa de la API en Postman en el siguiente enlace:

[Documentación de la API en Postman](https://documenter.getpostman.com/view/13470508/2sA3Qv7qSi#auth-info-909703d2-7113-46bf-9510-b360ebe7e88e)

## Decisiones Tomadas

1. **Identificación por ID:**
    - Se optó por utilizar el ID del candidato para las operaciones CRUD, simplificando la lógica. Otra opción habría sido utilizar el campo de email.

2. **Validaciones del Email:**
    - El campo de email no puede repetirse al crear un nuevo candidato ni modificarse una vez creado.

3. **Eliminación sin verificación de existencia:**
    - No se verifica la existencia del candidato antes de eliminarlo, lo que podría aumentar la seguridad al agregar esta validación.

4. **Manejo de Excepciones:**
    - Se devuelve un mensaje de error "unknown error" para excepciones no personalizadas.

5. **Configuración de la Base de Datos:**
    - Se utiliza el usuario root para la base de datos, aunque se recomendaría crear un usuario personalizado con permisos específicos.

6. **Autenticación Preconfigurada:**
    - Se preconfigura un usuario "seekuser" y contraseña "seekpass" para la autenticación. Otra alternativa sería utilizar una base de datos con gestión de usuarios y roles.
