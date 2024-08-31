import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.vanniktech.maven.publish") version "0.29.0"
}

group = "io.github.meet-miyani"
version = "1.0.1"

android {
    namespace = "avinya.tech.eventics"
    compileSdk = 34

    defaultConfig {
        minSdk = 19

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
}

mavenPublishing {
    coordinates(
        groupId = "io.github.meet-miyani",
        artifactId = "eventics-library",
        version = "1.0.1",
    )

    pom {
        name.set("Eventics Library")
        description.set("A library for managing and logging events in Android applications.")
        inceptionYear.set("2024")
        url.set("https://github.com/Meet-Miyani/Eventics")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("meet-miyani")
                name.set("Meet Miyani")
                email.set("miyanimeet02@gmail.com")
            }
        }

        scm {
            connection.set("scm:git:git://github.com/Meet-Miyani/Eventics.git")
            developerConnection.set("scm:git:ssh://github.com:Meet-Miyani/Eventics.git")
            url.set("https://github.com/Meet-Miyani/Eventics")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}