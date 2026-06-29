# Entrega Parcial 3 - Arquitectura de Microservicios Cine
**Estudiante:** Thomás Osorio
**Rama de Entrega:** `feature/ms-salas-datafaker`

## 1. Estrategia de Control de Versiones y Estado
Para proteger la integridad de los microservicios bajo mi responsabilidad (`ms_sucursales`, `ms_ubicacion`, `ms_salas_plural`, `ms_salas`) frente a la inestabilidad detectada en la rama `main` (causada por fallos de compilación en otros módulos), todo el desarrollo validado se centralizó y se entrega en la rama `feature/ms-salas-datafaker`. 

El sistema implementa el patrón de descubrimiento con **Eureka Server** y enrutamiento dinámico mediante **API Gateway** (puerto 8080, `discovery.locator.enabled: true`).

## 2. Troubleshooting y Compilación
Durante la fase de integración (build), se documentaron los siguientes incidentes técnicos:

* **Incompatibilidad Lombok vs JDK 21 (`TypeTag :: UNKNOWN`):** Se identificó un conflicto crítico del compilador de Maven al procesar anotaciones de Lombok en la versión actual de Java. 
* **Fallo en Build del Monorepo (`ms-cliente`):** Ejecutar `mvn clean install` en la raíz genera un fallo global debido a errores en módulos externos a esta entrega.

* **Ademas quiero comentar que tuvimos algunos problemas con conectar los microservicios por temas personales que me han pasado que se me han dificultado estar en mi casa por lo que los momentos que e tenido para el proyecto trato de aprender lo que pueda y seguir con los microservicios y tambien como bien sabe mi compañero por motivos personales no a podido seguir con el proyecto.Eso gracias profesor 