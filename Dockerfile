FROM eclipse-temurin:17-jdk-jammy as build
WORKDIR /workspace/app

COPY mvnw .
RUN chmod +x mvnw
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN apt-get update && apt-get install -y stockfish
#COPY stockfish_14.1_linux_x64 /bin
	
	
RUN sed -i 's/\r$//' mvnw
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jdk-jammy
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build /usr/games/stockfish /bin/stockfish_ubuntu
#COPY --from=build /bin/stockfish_14.1_linux_x64 /bin/stockfish_14.1_linux_x64
ENTRYPOINT ["java","-cp","app:app/lib/*","com.modris.Main"]