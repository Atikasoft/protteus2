Para usar estas librerías hay que instalarlas en el repositorio local, para lo
que se debe ejecutar lo siguiente

1.- Asegurarse de que maven 2 se encuentre instalado y que se encuentre en el
path
2.- Iniciar la consola de comandos
3.- navegar hasta la carpeta en la que estan las librerías a instalar.
4.- Ejecutar el siguiente comando

mvn install:install-file -Dfile=./ydoc.jar -DgroupId=com.yworks -DartifactId=umldoclet -Dversion=3.0.02 -DgeneratePom=true -Dpackaging=jar
mvn install:install-file -Dfile=./styleed.jar -DgroupId=com.yworks -DartifactId=style-umldoclet -Dversion=3.0.02 -DgeneratePom=true -Dpackaging=jar


