FROM openjdk:8
COPY ./src /usr/src/vchat
WORKDIR /usr/src/vchat
RUN javac com/vivian/java/vchat/VServer.java com/vivian/java/vchat/ServerChat.java
CMD ["java", "com.vivian.java.vchat.VServer"]
