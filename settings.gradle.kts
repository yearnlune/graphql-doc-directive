import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        flatDir { dirs("./plugins") }
        gradlePluginPortal()
    }
}
rootProject.name = "graphql-doc"
include("graphql-doc-directive")
include("graphql-doc-directive-gradle-plugin")

val getGitTag = { ->
    val stdout = ByteArrayOutputStream()
    val stderr = ByteArrayOutputStream()
    var tag = System.getenv("CI_COMMIT_TAG")
    if (tag == null) {
        exec {
            commandLine("git", "describe", "--tags", "--exact-match")
            standardOutput = stdout
            errorOutput = stderr
        }
        tag = stdout.toString(Charset.defaultCharset()).split("\n")[0].trim()
    }
    if (tag.startsWith("v")) {
        tag = tag.substring(1)
    }
    tag
}

gradle.extra["getGitTag"] = getGitTag.invoke()