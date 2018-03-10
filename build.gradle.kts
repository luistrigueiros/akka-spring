plugins {
    kotlin("jvm") version "1.2.0"
    application
}

application {
    mainClassName = "AppKt"
}

val springVersion = "5.0.4.RELEASE"

dependencies {
    compile(kotlin("stdlib"))
    compile("com.typesafe.akka:akka-actor_2.12:2.5.3")
    compile("org.springframework:spring-context:$springVersion")
    compile("org.springframework:spring-test:$springVersion")
    compile("com.google.guava:guava:23.0")
    testCompile("junit:junit:4.12")
}

repositories {
    jcenter()
}
