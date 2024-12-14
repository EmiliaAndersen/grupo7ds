##ESTE ES EL DOCKERFILE PARA LA IMAGEN DE LA APP

# Use a base image with Java and Maven
FROM maven:3.9.5-eclipse-temurin-17

# Install Git
RUN apt-get update && apt-get install -y git && apt-get clean

# Clone the repository
RUN git clone https://github.com/JCardosoLandin/2024-DDS-TPA-Grupo_7-Servicio_1.git

# Set the working directory to the specific folder within the cloned repo
WORKDIR /2024-DDS-TPA-Grupo_7-Servicio_1/

# Build the Maven project
RUN mvn clean install -Dmaven.test.skip=true


# Expose the port Javalin is listening on
EXPOSE 8080

# Run the application
CMD ["mvn", "exec:java", "-Dexec.mainClass=dds.api_res1.Aplication"]

