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
    implementation("io.quarkus:quarkus-arc")
    
    // REST Client for calling other microservices
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jsonb")

    //--Service Discovery
    implementation("io.quarkus:quarkus-smallrye-stork")
//    implementation("io.smallrye.stork:stork-service-discovery-static-list")
//    implementation("io.smallrye.stork:stork-service-discovery-consul:2.6.3")
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-consul-client")
    implementation("io.smallrye.stork:stork-service-discovery-consul")


    //implementation("org.modelmapper:modelmapper:3.2.6")
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")

    //Prometheus - este no tiene dependencia con ninguna entidad
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")


}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}