/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/5.5.1-20190724234647+0000/userguide/tutorial_java_projects.html
 */

object Ext {
    const val mpanalyzerDir = "lib/MPAnalyzer"
    const val mpanalyzerUrl = "https://github.com/YoshikiHigo/MPAnalyzer.git"
}

plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    application

    // Grgit
    id("org.ajoberstar.grgit").version("3.1.1")
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
    mavenCentral()
}

dependencies {
    val commonscliVersion = "1.2"
    val jgitVersion = "4.4.0+"
    val poiVersion = "3.12"
    val sqliteVersion = "3.8.10.1"
    val svnkitVersion = "1.8.10"

    // This dependency is used by the application.
    implementation("com.google.guava:guava:27.1-jre")

    implementation("commons-cli:commons-cli:$commonscliVersion")
    implementation("org.eclipse.jgit:org.eclipse.jgit:$jgitVersion")
    implementation("org.apache.poi:poi:$poiVersion")
    implementation("org.apache.poi:poi-ooxml:$poiVersion")
    implementation("org.xerial:sqlite-jdbc:$sqliteVersion")
    implementation("org.tmatesoft.svnkit:svnkit:$svnkitVersion")
    implementation(files("${Ext.mpanalyzerDir}/lib/commentremover/CR.jar"))

    // Use JUnit test framework
    testImplementation("junit:junit:4.12")
}

sourceSets.main.configure {
    java.srcDir("src/nh3")
    java.srcDir("lib/MPAnalyzer/src")
}


application {
    // Define the main class for the application
    mainClassName = "nh3.ammonia.gui.Ammonia"
}


fun updateGitRepo(
        path: String,
        gitUrl: String,
        branch: String = "master")
{
    val repo = kotlin.runCatching {
        org.ajoberstar.grgit.Grgit.open(mapOf("dir" to path))
    }.getOrElse {
        org.ajoberstar.grgit.Grgit.clone(mapOf("dir" to path, "uri" to gitUrl))
    }
    repo.pull(mapOf("branch" to branch))
}

tasks.register("updateLib") {
    updateGitRepo(
            path=Ext.mpanalyzerDir,
            gitUrl=Ext.mpanalyzerUrl
    )
}
tasks.get("build").dependsOn(tasks.get("updateLib"))