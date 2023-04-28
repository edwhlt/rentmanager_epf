FROM openjdk:8-jdk-alpine

COPY . /usr/src/app

# Définition du répertoire de travail
WORKDIR /usr/src/app

# Installation de Maven
RUN apk add --no-cache maven

# Compilation du projet avec Maven
RUN mvn clean package

# Exposez le port sur lequel votre application écoute
EXPOSE 8080

# Lancez votre application en exécutant la commande "mvn exec:java"
CMD ["mvn", "tomcat7:run"]
