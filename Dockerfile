FROM maven:3.8.6-jdk-8-slim

WORKDIR /app

COPY . /app

RUN mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V

CMD ["mvn", "verify"]
