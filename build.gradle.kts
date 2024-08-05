import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.hibernate.orm") version "7.0.0.Alpha3"
    id("com.google.devtools.ksp") version "2.0.0-1.0.23"
    application
}

group = "com.cludivers"
version = "0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.dagger:dagger-compiler:2.51.1")
    ksp("com.google.dagger:dagger-compiler:2.51.1")
}

val targetJavaVersion = 22
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}

application {
    mainClass.set("com.cludivers.prototyping.PrototypingKt")
}

fun getFile(propertyName: String, environmentName: String, isMissingCritical: Boolean): String {
    val file: String? = project.findProperty(propertyName) as? String ?: System.getenv(environmentName)
    handleMissingProperty(propertyName, file, isMissingCritical)
    return file ?: ""
}

fun handleMissingProperty(propertyName: String, file: String?, isMissingCritical: Boolean) {
    if (file != null){
        return
    }

    val errorMessage: String = if (isMissingCritical) {
        "Mandatory property $propertyName missing. Use -P$propertyName=<file>"
    } else {
        "Property $propertyName not specified. Use -P$propertyName=<file>"
    }
    if (isMissingCritical){
        throw GradleException(errorMessage)
    } else {
        logger.warn(errorMessage)
    }
}


fun getServerJar(): String {
    return getFile("serverJar", "SERVER_JAR", true)
}

fun getServerDirectory(): String {
    return getFile("serverDir", "SERVER_DIR", true)
}

tasks.register<Copy>("deployPlugin") {
    description = "Custom deployment task"
    dependsOn("shadowJar")

    outputs.upToDateWhen { false }
    // Specify the destination directory based on a property or environment variable

    val serverDir = getServerDirectory()

    // Explicitly specify the inputs (JAR file) for the task
    from(project.buildDir.resolve("libs/$shadowJarFileName"))

    into("$serverDir/plugins/")

    doLast {
        println("Jar deployed to: $serverDir/plugins/")
    }
}

tasks.register<JavaExec>("startServer") {
    description = "Start the server"

    val serverDir = getServerDirectory()
    val serverJar = getServerJar()

    val launchCommand = "java -jar $serverDir/$serverJar"

    val process = ProcessBuilder(launchCommand)
        .directory(File(serverDir))
        .start()

    // Optionally provide input to the process
//    if (project.hasProperty("reload")) {
//        process.outputStream.bufferedWriter().use { it.write(reloadCommand) }
//    }

    val exitCode = process.waitFor()
    if (exitCode == 0) {
        println("Server reloaded/relaunched successfully.")
    } else {
        println("Failed to reload/relaunch server. Exit code: $exitCode")
    }

//    main = "-jar"
    classpath = files("$serverDir/$serverJar")

}
val shadowJarFileName = "kzSurvivor.jar"
tasks.withType<ShadowJar> {
    archiveFileName.set(shadowJarFileName)
}



