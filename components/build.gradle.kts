import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

val production: Boolean = (parent!!.properties["production"] as String ).toBoolean()

buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.3.10"

    repositories {
        jcenter()
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    }

    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }
}

apply {
    plugin("kotlin2js")
}

// Not sure why this is needed, but it makes "compile(kotlinModule("stdlib-js", kotlinVersion))" line down below work
plugins {
    java
    id("com.moowork.node") version "1.2.0"
    `maven-publish`
}

val kotlinVersion: String by extra

repositories {
    jcenter()
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-dev") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-js-wrappers") }
}

dependencies {
    compile(kotlin("stdlib-js", kotlinVersion))
    compile("org.jetbrains", "kotlin-react", "16.5.2-pre.58-kotlin-1.3.0")
    compile("org.jetbrains", "kotlin-react-dom", "16.5.2-pre.58-kotlin-1.3.0")
    compile("org.jetbrains", "kotlin-styled", "1.0.0-pre.58-kotlin-1.3.0")
}


val compileKotlin2Js: Kotlin2JsCompile by tasks

compileKotlin2Js.kotlinOptions {
    sourceMap = true
    metaInfo = true
    outputFile = "${project.buildDir.path}/js/muirwik-components.js"
    main = "call"
    moduleKind = "commonjs"
}

publishing {
    repositories {
        mavenLocal()
    }

    publications {
        create("${project.name} Publication", MavenPublication::class.java) {
            from(components["java"])
//            artifact()
        }

    }
}
