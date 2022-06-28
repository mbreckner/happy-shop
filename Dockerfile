FROM gradle:7.4.2-jdk11 as build

WORKDIR /build
COPY build.gradle /build
COPY src /build/src
# /build should contain:
# ---build.gradle
# ---src/
RUN gradle wrapper
RUN ./gradlew build

FROM build as run

WORKDIR /app
ARG JAR_FILE=build/libs/*RELEASE.jar
COPY --from=build /build/${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]