# Usa la imagen oficial de Tomcat
FROM tomcat:10.1-jdk17-temurin

# Elimina la aplicación por defecto de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copia tu WAR dentro del contenedor (ajusta el nombre si cambia)
COPY target/ProyectoTurnero-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war


# Expone el puerto 8080
EXPOSE 8080

# Comando de inicio (Tomcat)
CMD ["catalina.sh", "run"]



