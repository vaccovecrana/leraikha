configure<io.vacco.oss.gitflow.GsPluginProfileExtension> {
  sharedLibrary(true, false)
  addJ8Spec()
}

val api by configurations

dependencies {
  api("jakarta.validation:jakarta.validation-api:3.0.2")
}