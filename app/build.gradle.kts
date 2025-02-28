
import com.heyanle.buildsrc.Android
import com.heyanle.buildsrc.Config
import com.heyanle.buildsrc.RoomSchemaArgProvider
import com.heyanle.buildsrc.Version
import com.heyanle.buildsrc.androidTestImplementation
import com.heyanle.buildsrc.implementation
import com.heyanle.buildsrc.project

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
}

android {
    namespace = "com.heyanle.easybangumi4"
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = "com.heyanle.easybangumi4"
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "String",
            Config.APP_CENTER_SECRET,
            "\"${Config.getPrivateValue(Config.APP_CENTER_SECRET)}\""
        )

        ksp {
            arg("room.generateKotlin", "true")
            arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
        }

    }

    sourceSets {
        // Adds exported schema location as test app assets.
        getByName("androidTest").assets.srcDir("$projectDir/schemas")
    }


    packaging {
        resources.excludes.add("META-INF/beans.xml")
    }

    buildTypes {
        release {
            postprocessing {
                isRemoveUnusedCode = true
                isRemoveUnusedResources = true
                isObfuscate = false
                isOptimizeCode = true
                proguardFiles("proguard-rules.pro")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-Xjvm-default=all"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }

}



dependencies {


    implementation("com.github.bumptech.glide:glide:${Version.glide}")

    implementation("com.github.heyanLE.okkv2:okkv2-mmkv:${Version.okkv2}")

    implementation("com.squareup.okhttp3:okhttp:${Version.okhttp3}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Version.okhttp3}")

    implementation("androidx.core:core-ktx:${Version.androidx_core_ktx}")
    implementation("androidx.appcompat:appcompat:${Version.androidx_appcompat}")
    implementation("com.google.android.material:material:${Version.google_material}")
    implementation("androidx.activity:activity-ktx:${Version.androidx_activity_ktx}")
    implementation("androidx.fragment:fragment-ktx:${Version.androidx_fragment_ktx}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Version.androidx_lifecycle_runtime_ktx}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.androidx_lifecycle_view_model}")
    implementation("androidx.test.ext:junit-ktx:1.1.5")

    implementation("androidx.preference:preference-ktx:${Version.androidx_preference}")

    debugImplementation( "com.squareup.leakcanary:leakcanary-android:${Version.leakcanary}")

    implementation("androidx.paging:paging-runtime-ktx:${Version.paging}")
    implementation("androidx.paging:paging-compose:${Version.paging}")
    implementation("com.github.easybangumiorg:EasyPlayer2:${Version.easy_player}")
//    implementation("loli.ball:easyplayer2:1.0.0.test") // for local maven test only

    // implementation(platform("androidx.compose:compose-bom:${Version.compose_bom}"))
    implementation("androidx.compose.ui:ui:${Version.compose_ui}")
    implementation("androidx.compose.ui:ui-graphics:${Version.compose_ui}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Version.compose_ui}")
    implementation("androidx.compose.material3:material3:${Version.compose_material3}")
    implementation("androidx.compose.material:material-icons-core:${Version.compose_material}")
    implementation("androidx.compose.material:material-icons-extended:${Version.compose_material}")
    implementation("androidx.compose.runtime:runtime-livedata:${Version.compose_runtime}")
    implementation("androidx.compose.animation:animation:${Version.compose_animation}")
    implementation("androidx.compose.animation:animation-core:${Version.compose_animation}")
    implementation("androidx.compose.animation:animation-graphics:${Version.compose_animation}")
    implementation("androidx.compose.foundation:foundation:${Version.compose_foundation}")
    implementation("androidx.compose.foundation:foundation-layout:${Version.compose_foundation}")


    implementation("androidx.window:window:${Version.androidx_window}")
    implementation("androidx.compose.material3:material3-window-size-class:${Version.compose_material3}")

    debugImplementation("androidx.compose.ui:ui-tooling:${Version.compose_ui}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${Version.compose_ui}")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:${Version.compose_ui}")

    implementation("com.google.accompanist:accompanist-navigation-animation:${Version.accompanist}")
    implementation("com.google.accompanist:accompanist-systemuicontroller:${Version.accompanist}")
    implementation("com.google.accompanist:accompanist-pager:${Version.accompanist}")
    implementation("com.google.accompanist:accompanist-pager-indicators:${Version.accompanist}")
    implementation("com.google.accompanist:accompanist-swiperefresh:${Version.accompanist}")
    implementation("com.google.accompanist:accompanist-insets:${Version.accompanist}")
    implementation("com.google.accompanist:accompanist-insets-ui:${Version.accompanist}")
    implementation("com.google.accompanist:accompanist-flowlayout:${Version.accompanist}")

    implementation("androidx.navigation:navigation-compose:${Version.navigation_compose}")

    implementation("io.coil-kt:coil-compose:${Version.coil}")
    implementation("io.coil-kt:coil-gif:${Version.coil}")

    implementation("com.google.android.exoplayer:exoplayer:${Version.exoplayer}")
    implementation("com.google.android.exoplayer:extension-rtmp:${Version.exoplayer}")

    implementation("androidx.media:media:${Version.media}")

    implementation("androidx.room:room-runtime:${Version.androidx_room}")
    implementation("androidx.room:room-ktx:${Version.androidx_room}")
    implementation("androidx.room:room-paging:${Version.androidx_room}")
    implementation("androidx.room:room-common:${Version.androidx_room}")

    testImplementation("androidx.room:room-testing:${Version.androidx_room}")
    androidTestImplementation("androidx.room:room-testing:${Version.androidx_room}")

    ksp("androidx.room:room-compiler:${Version.androidx_room}")

    implementation("com.microsoft.appcenter:appcenter-analytics:${Version.app_center}")
    implementation("com.microsoft.appcenter:appcenter-crashes:${Version.app_center}")
    implementation("com.microsoft.appcenter:appcenter-distribute:${Version.app_center}")

    implementation("com.google.code.gson:gson:${Version.gson}")

    implementation("org.jsoup:jsoup:${Version.jsoup}")

    implementation("androidx.webkit:webkit:${Version.androidx_webkit}")

    implementation("org.apache.commons:commons-text:${Version.commons_text}")

    implementation("org.fourthline.cling:cling-core:${Version.cling}")
    implementation("org.fourthline.cling:cling-support:${Version.cling}")

    implementation("org.burnoutcrew.composereorderable:reorderable:${Version.compose_lazy_reorder}")

    implementation(project(":easy-dlna"))
    implementation(project(":easy-crasher"))
    implementation(project(":easy-i18n"))
    implementation(project(":injekt"))
    implementation(project(":extension:extension-core"))
    implementation(com.heyanle.buildsrc.SourceExtension.extensionApi)



    testImplementation ("junit:junit:${Version.junit}")

    androidTestImplementation ("androidx.test.ext:junit:${Version.androidx_test_junit}")
    androidTestImplementation ("androidx.test.espresso:espresso-core:${Version.espresso_core}")


}