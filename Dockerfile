FROM openjdk:8
COPY . /usr/src/vchat
WORKDIR /usr/src/vchat
RUN javac VServer.java ServerChat.java
CMD ["java", "VServer"]
