FROM openjdk:17-jdk-slim-buster as build
WORKDIR /workspace/app

COPY mvnw .
RUN chmod +x mvnw
COPY .mvn .mvn
COPY pom.xml .
COPY src src

COPY stockfish_ubuntu /bin

RUN sed -i 's/\r$//' mvnw
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:17-jdk-slim-buster
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build /bin/stockfish_ubuntu /bin/stockfish_ubuntu
ENTRYPOINT ["java","-cp","app:app/lib/*","com.modris.Main"]