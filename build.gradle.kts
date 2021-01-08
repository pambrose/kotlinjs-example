import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val logging_version: String by project
val serialization_version: String by project
val css_version: String by project

plugins {
  application
  kotlin("multiplatform") version "1.4.30-M1"
  kotlin("plugin.serialization") version "1.4.30-M1"
  id("com.github.ben-manes.versions") version "0.36.0"
}

group = "com.github.pambrose"
version = "1.0-SNAPSHOT"

repositories {
  maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
  maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
  mavenCentral()
  jcenter()
}

kotlin {
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "1.8"
    }

    testRuns["test"].executionTask.configure {
      useJUnitPlatform()
    }
    withJava()
  }
  js(IR) {
    browser {
      webpackTask {
        cssSupport.enabled = true
      }
      runTask {
        cssSupport.enabled = true
      }
      testTask {
        useKarma {
          useChromeHeadless()
          webpackConfig.cssSupport.enabled = true
        }
      }
      binaries.executable()
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }

    val jvmMain by getting {
      dependencies {
        implementation("org.jetbrains:kotlin-css-jvm:$css_version")

        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version")
        implementation("io.ktor:ktor-server-cio:$ktor_version")
        implementation("io.ktor:ktor-html-builder:$ktor_version")
        implementation("io.ktor:ktor-websockets:$ktor_version")
        implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
        implementation("io.github.microutils:kotlin-logging:$logging_version")
        implementation("ch.qos.logback:logback-classic:$logback_version")
      }
    }

    val jvmTest by getting {
      dependencies {
        implementation(kotlin("test-junit5"))
        implementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
        runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
      }
    }

    val jsMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-html:0.7.2")
      }
    }

    val jsTest by getting {
      dependencies {
        implementation(kotlin("test-js"))
      }
    }

    all {
      languageSettings
        .apply {
          useExperimentalAnnotation("kotlin.time.ExperimentalTime")
          //useExperimentalAnnotation("io.ktor.util.KtorExperimentalAPI")
          useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }
    }
  }
}

application {
  mainClassName = "Server"
}

tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack") {
  outputFileName = "jscode.js"
}

tasks.getByName<Jar>("jvmJar") {
  dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
  val jsBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
  from(File(jsBrowserProductionWebpack.destinationDirectory, jsBrowserProductionWebpack.outputFileName))
}

tasks.getByName<JavaExec>("run") {
  dependsOn(tasks.getByName<Jar>("jvmJar"))
  classpath(tasks.getByName<Jar>("jvmJar"))
}