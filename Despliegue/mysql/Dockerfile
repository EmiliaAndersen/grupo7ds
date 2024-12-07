 # Use the official MySQL image as the base
 FROM mysql:8

 # Guardo el arhcivo que quiero que corra mysql cuando arranque
 COPY init.sql /docker-entrypoint-initdb.d/
 
 # Expose port 3306
 EXPOSE 3306
 