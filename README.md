# Taller-Aplicativo-CI-CD

**Integrantes:**
- Juan Pablo Caballero Castellanos.
---
**Enlaces**
- https://masterchefapi-ead3ffcdhpbma7bu.brazilsouth-01.azurewebsites.net/swagger-ui/index.html#/

## Estrategia de Versionamiento y ramas.

**Template ramas**
`feature/Tarea`

- main: Versión estable para PREPROD
- develop: Rama principal de desarrollo

**Template Commits**
`feature: Tarea - Acción Realizada`
---

**Tecnologías utilizadas**
- Java 21
- Spring Boot
- MongoDB
- Swagger (OpenAPI)
- JaCoCo (cobertura de pruebas)
- SonarQube (análisis estático de código)
- Docker(Ejecutar la aplicación en contenedores)
- Maven (gestión de dependencias y build)
- Azure
- GitHub Actions
---

**Arquitectura**

El TALLER sigue el patrón MVC (Modelo - Vista - Controlador):

- Models: Entidades de negocio es decir , los objetos que representan la infomación central que maneja las recetas
- Repository: Manejo de persistencia en MongoDB, encargado del acceso y manejo de datos. Aqui se implementan operaciones de persistencia, encontrar, actulizar, eliminar documentos que nos permite abstraer la lógica de la base de datos del resto de la aplicación.

- Services: Lógica de negocio de la aplicación. Aqui se definen las operaciones y funcionalidades que que combinan y transforman datos, aplicando las reglas de negocio y coordina la interacción entre los modelos, los controladores y los repositorios.

- Controllers: Exposición de endpoints REST que permiten la comunicación con el usuario. Los controladores reciben solicitudes HTTP, delegan la ejecución de la lógica al servicio correspondiente y le devuelva respuestas al usuario.
---

**Pruebas Swagger**

[Ver Configuración (PDF)](docs/pdf/PruebasSwagger.pdf)

**Pruebas Cobertura**

[Ver Configuración (PDF)](docs/pdf/PruebasCobertura.pdf)

**Pruebas Azure**

[Ver Configuración (PDF)](docs/pdf/ConfiguraciónCICD.pdf)

