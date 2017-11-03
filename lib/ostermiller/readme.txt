Para usar estas librerías hay que instalarlas en el repositorio local, para lo
que se debe ejecutar lo siguiente

1.- Asegurarse de que maven 2 se encuentre instalado y que se encuentre en el
path
2.- Iniciar la consola de comandos
3.- navegar hasta la carpeta en la que estan las librerías a instalar.
4.- Ejecutar el siguiente comando

mvn install:install-file -Dfile=./ostermillerutils-1.08.01.jar -DgroupId=com.Ostermiller -DartifactId=util -Dversion=1.08.01 -Djavadoc=./ostermillerutils-1.08.01-javadoc.jar  -Dsources=ostermillerutils-1.08.01-sources.jar  -DgeneratePom=true -Dpackaging=jar

si maven no se encuentra en el path pueden ejecutar:

/directorio-maven/bin/mvn install:install-file -Dfile=./ostermillerutils-1.08.01.jar -DgroupId=com.Ostermiller -DartifactId=util -Dversion=1.08.01 -Djavadoc=./ostermillerutils-1.08.01-javadoc.jar  -Dsources=ostermillerutils-1.08.01-sources.jar  -DgeneratePom=true -Dpackaging=jar