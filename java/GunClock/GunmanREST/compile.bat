cd WEB-INF/classes

javac -cp "../lib/jakarta.ws.rs-api-2.1.5.jar";"../lib/jersey-server.jar" gunman/*.java

cd ../..

jar cvf GunmanREST.war META-INF WEB-INF
