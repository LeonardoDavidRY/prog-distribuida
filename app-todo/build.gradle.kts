plugins {
    id("java")
    id("io.quarkus") version "3.35.2"
    id("io.freefair.lombok") version "9.2.0"
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

val quarkusVersion = "3.35.2"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

dependencies {
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:$quarkusVersion"))
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jsonb")
    implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    // app-todo tiene su propia base de datos ("tododb", separada de books20262026),
    // así que puede tener su propio Flyway con su propio historial de versiones sin
    // chocar con las migraciones de app-authors ni depender de ese módulo para nada.
    implementation("io.quarkus:quarkus-flyway")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:12.5.0")

    // CDI
    implementation("io.quarkus:quarkus-arc")

    // REST Client para consultar el sistema externo de usuarios (jsonplaceholder)
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jsonb")

    //--Service Discovery / Registro / Balanceador de carga dinámico
    implementation("io.quarkus:quarkus-smallrye-stork")
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-consul-client")
    implementation("io.smallrye.stork:stork-service-discovery-consul")

    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")

    //Prometheus
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")

    //MicroProfile Health
    implementation("io.quarkus:quarkus-smallrye-health")

    //MicroProfile Telemetry - Tracing (OpenTelemetry)
    implementation("io.quarkus:quarkus-opentelemetry")

    //Kubernetes + imagen Docker
    implementation("io.quarkus:quarkus-kubernetes")
    implementation("io.quarkus:quarkus-container-image-jib")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
