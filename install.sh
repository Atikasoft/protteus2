#!/bin/sh
if [ -n "$1" ]; then 
    case $1 in
      "ATK") echo "INSTALANDO PROFILE PRUEBAS ATIKA......................."
             mvn clean install -Dmaven.test.skip=true  -P pruebasATK;;
      "MQ") echo "INSTALANDO PROFILE PRUEBAS MQ......................."
              mvn clean install -Dmaven.test.skip=true  -P pruebasMQ
              cp ./proteus-ear/target/proteus-ear.ear /opt/glassfish3/glassfish/domains/domain1/autodeploy;;

      "PROD") echo "INSTALANDO PROFILE PRODUCCION......................."
              mvn clean install -Dmaven.test.skip=true  -P produccion;;
      *) echo "!! PROFILE NO EXISTE !!!";;
    esac
else
    echo "INSTALANDO PROFILE DESARROLLO......................."
    mvn clean install -Dmaven.test.skip=true 
    cp ./proteus-ear/target/proteus-ear.ear ~/Tools/glassfish3/glassfish/domains/domain1/autodeploy

fi


