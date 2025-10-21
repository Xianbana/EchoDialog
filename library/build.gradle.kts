plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    `maven-publish`
}

android {
    namespace = "com.xian.echo"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}


publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = project.findProperty("libraryGroupId") as String? ?: "com.github.Xianbana"
            artifactId = project.findProperty("libraryArtifactId") as String? ?: "EchoDialog"
            version = project.findProperty("libraryVersion") as String? ?: "1.0.0"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("EchoDialog")
                description.set("A modern Android progress bar library with customizable animations and themes")
                url.set("https://github.com/Xianbana/EchoDialog")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/Xianbana/EchoDialog.git")
                    developerConnection.set("scm:git:ssh://github.com:Xianbana/EchoDialog.git")
                    url.set("https://github.com/Xianbana/EchoDialog")
                }
            }
        }
    }
}