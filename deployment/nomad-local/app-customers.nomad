job "book-store" {
  datacenters = ["dc1"]

  group "servers" {

    count=1

    task "app-customers" {
      driver = "java"

      config {
        jar_path    = "C:\tools\distri\prog-distribuida\app-customers\build\libs\app-customers-0.0.1-SNAPSHOT.jar"
        jvm_options = ["-Xmx2048m", "-Xms256m"]
      }
      env {
        SERVER_PORT = "${NOMAD_PORT_http}"
      }

      service {
        provider = "nomad"
        name     = "app-customers"
      }

      }
    }
  }
}
