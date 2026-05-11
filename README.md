
# Proyecto Semestral - Sistema de Gestión de Cine (Microservicios)

## Descripción del Proyecto
Este proyecto consiste en el desarrollo de una arquitectura orientada a microservicios utilizando Spring Boot y Java 21 para la gestión de un sistema de Cine. El sistema está desacoplado en múltiples servicios independientes que interactúan entre sí, cada uno con su propia persistencia de datos y responsabilidades específicas.

## Equipo de Desarrollo
* Daniel Leyton
* Dael Espinoza
* Thomas Osorio

## Funcionalidades Implementadas
 ms-pelicula: Gestión del catálogo de películas (CRUD completo).
ms-director: Gestión de directores de cine y su vinculación.
ms-tickets: Gestión de entradas y asignación a clientes.
ms-cliente: Gestión de usuarios/clientes. 
  Implementación de validaciones (Bean Validation JSR 380).
  Comunicación síncrona mediante OpenFeign para obtener el detalle de clientes junto con sus tickets asociados.
  Respuestas HTTP estandarizadas mediante `ResponseEntity`.

## Pasos para Ejecutar
1. Clonar este repositorio en la máquina local: `git clone [URL_DEL_REPO]`
2. Asegurarse de tener instalado Java 21 y Maven.
3. Configurar las bases de datos MySQL locales para cada microservicio según las credenciales especificadas en cada `application.properties`.
4. Importar la carpeta raíz en un IDE (como VS Code, IntelliJ o Eclipse).
5. Ejecutar las clases principales de cada microservicio (`@SpringBootApplication`).
6. Probar los endpoints a través de Postman interactuando con los puertos asignados (ej. `http://localhost:8083/api/v1/clientes`).
:D