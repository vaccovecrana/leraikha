plugins { id("io.vacco.oss.gitflow") version "0.9.8" }

group = "io.vacco.leraikha"
version = "0.1.0"

configure<io.vacco.oss.gitflow.GsPluginProfileExtension> {
  sharedLibrary(true, false)
  addJ8Spec()
}

val api by configurations

dependencies {
  api("jakarta.validation:jakarta.validation-api:3.0.2")
  api("io.vacco.sabnock:sabnock:0.1.0")
  api("com.google.code.gson:gson:2.10")
}
