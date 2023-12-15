# Usar la imagen oficial de Java
FROM eclipse-temurin:17-jdk

# Instalar Maven
RUN apt-get update && apt-get install -y maven

WORKDIR application

# Copiar el archivo pom.xml y descargar las dependencias
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar el proyecto
COPY src ./src
RUN mvn package -DskipTests

# Ejecutar la aplicación
CMD ["java", "-jar", "target/app-0.0.1-SNAPSHOT.jar"]