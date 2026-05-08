plugins {
    id("java")
    id("war")
}

group = "com.progAvanzada"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
// Source: https://mvnrepository.com/artifact/jakarta.ws.rs/jakarta.ws.rs-api
    compileOnly("jakarta.ws.rs:jakarta.ws.rs-api:4.0.0")
    //implementation es para ambos compilar y codigo
    //compile es solo para el war
    //runtime solo para la bd ya que no se necesita un jdk
    //binsutils es un implementacion que se necesita de esa forma
    //los driver para
}

tasks.test {
    useJUnitPlatform()
}