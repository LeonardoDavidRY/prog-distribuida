package com.programacion.distribuida.authors.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;

@Path("/config")
public class TestConfig {

  @ConfigProperty(name ="quarkus.http.port", defaultValue = "8080")
  Integer port;

  @GET
  public String test() {
    //Obtener acceso a la aconfiguracion y punto de entrada
    Config config = ConfigProvider.getConfig();
    // listar fuentes de configuracion
    config.getConfigSources().forEach(it ->
    {
      System.out.printf("[%d]\t %s\n", it.getOrdinal(), it.getName());
    });

    //recueperar un vvalo desde configuracion
    String url = config.getValue("quarkus.datasource.jdbc.url", String.class);
    Integer puerto = config.getValue("quarkus.http.port", Integer.class);
    // String title = config.getValue("app.title", String.class);
    Optional<String> title = config.getOptionalValue("app.tittle", String.class);

    System.out.println("-------------------------");
    System.out.println("url: " + url);
    System.out.println("puerto: " + puerto);
    if (title.isPresent()) {
      System.out.println("TITLE: " + title.get());
    } else {
      System.out.println("TITLE: NO EXISTE");
    }

    System.out.println("-------------------------");
    System.out.println("Port ID: " + port);

    return "OK";
  }
}
