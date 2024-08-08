# Estructura de un Proyecto Android y Sistema de Build con Gradle

## Objetivos de la clase

1. Comprender en profundidad la estructura de un proyecto Android.
2. Explorar el sistema de build Gradle y su evolución.
3. Entender qué es Gradlew y su importancia.
4. Aprender a gestionar dependencias y librerías en proyectos Android.
5. Practicar la modificación de archivos de configuración Gradle.

## 1. Estructura detallada de un proyecto Android

### 1.1 Visión general de la estructura

```
MyApp/
│
├── app/
│   ├── build/
│   ├── libs/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   ├── androidTest/
│   │   └── test/
│   └── build.gradle
│
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── .gradle/
├── build/
├── .idea/
├── build.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── local.properties
└── settings.gradle
```

### 1.2 Descripción de componentes clave

#### 1.2.1 Directorio `app/`

- **build/**: Contiene los outputs del build.
- **libs/**: Para librerías locales (archivos .jar o .aar).
- **src/**: Contiene el código fuente y recursos.
  - **main/**: Código y recursos principales.
  - **androidTest/**: Pruebas instrumentadas.
  - **test/**: Pruebas unitarias.

#### 1.2.2 Archivo `AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.myapp">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

Este archivo declara los componentes de la app y sus capacidades.

#### 1.2.3 Directorio `res/`

Estructura típica de recursos:

```
res/
├── drawable/
├── layout/
├── mipmap/
├── values/
│   ├── colors.xml
│   ├── strings.xml
│   └── styles.xml
└── ...
```

Cada subdirectorio tiene un propósito específico para diferentes tipos de recursos.

## 2. Sistema de Build Gradle

### 2.1 ¿Qué es Gradle?

Gradle es un sistema de automatización de build que se basa en Groovy o Kotlin DSL. Es extremadamente flexible y se utiliza no solo en Android, sino en muchos otros tipos de proyectos.

### 2.2 Evolución del sistema de build en Android

1. **Ant (Antes de 2013)**: 
   - Sistema de build basado en XML.
   - Configuración compleja y poco flexible.

2. **Maven (Transitorio)**:
   - Usado brevemente en algunos proyectos Android.
   - Mejor manejo de dependencias que Ant.

3. **Gradle (2013 - Presente)**:
   - Introducido con Android Studio.
   - Altamente flexible y potente.
   - Mejor manejo de dependencias y configuraciones.

### 2.3 Archivos Gradle clave

#### 2.3.1 `build.gradle` (Proyecto)

```gradle
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:7.0.3"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31"
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

Este archivo define configuraciones a nivel de proyecto.

#### 2.3.2 `build.gradle` (Módulo: app)

```gradle
plugins {
    id 'com.android.application'
    id 'kotlin-android'
}

android {
    compileSdk 31

    defaultConfig {
        applicationId "com.example.myapp"
        minSdk 21
        targetSdk 31
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
}

dependencies {
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.0'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.2'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}
```

Este archivo define configuraciones específicas del módulo de la app.

## 3. Gradlew: El Gradle Wrapper

### 3.1 ¿Qué es Gradlew?

Gradlew (Gradle Wrapper) es un script que invoca una versión declarada de Gradle, descargándola de antemano si es necesario.

### 3.2 Importancia de Gradlew

1. **Consistencia**: Asegura que todos los desarrolladores y sistemas de CI usen la misma versión de Gradle.
2. **No requiere instalación**: No es necesario instalar Gradle en el sistema.
3. **Control de versiones**: La versión de Gradle se puede versionar junto con el código.

### 3.3 Archivos relacionados con Gradlew

- `gradlew`: Script shell para sistemas Unix.
- `gradlew.bat`: Script batch para Windows.
- `gradle/wrapper/gradle-wrapper.properties`: Configura la versión de Gradle a usar.

### 3.4 Uso de Gradlew

En lugar de usar `gradle`, se usa `./gradlew` en Unix o `gradlew.bat` en Windows:

```bash
./gradlew assembleDebug
```

Este comando compila la versión de debug de la app.

## 4. Gestión de dependencias con Gradle

### 4.1 Repositorios

Los repositorios se declaran en el `build.gradle` del proyecto:

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        // Otros repositorios personalizados si son necesarios
        maven { url 'https://jitpack.io' }
    }
}
```

### 4.2 Declaración de dependencias

Las dependencias se declaran en el `build.gradle` del módulo:

```gradle
dependencies {
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    testImplementation 'junit:junit:4.13.2'
}
```

### 4.3 Tipos de dependencias

- `implementation`: La dependencia es necesaria para compilar y ejecutar.
- `api`: Similar a `implementation`, pero expone la dependencia a módulos dependientes.
- `testImplementation`: Solo para pruebas unitarias.
- `androidTestImplementation`: Solo para pruebas instrumentadas.

### 4.4 Añadir una nueva librería

Para añadir una nueva librería, por ejemplo, Retrofit para networking:

1. Abre `app/build.gradle`.
2. En la sección `dependencies`, añade:
   ```gradle
   implementation 'com.squareup.retrofit2:retrofit:2.9.0'
   implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
   ```
3. Sincroniza el proyecto (Android Studio te pedirá que lo hagas).

## 5. Ejercicios prácticos

1. **Modificar la versión de compilación**:
   - Abre `app/build.gradle`.
   - Cambia `compileSdk 31` a `compileSdk 30`.
   - Sincroniza el proyecto y observa los cambios/advertencias.

2. **Añadir una nueva dependencia**:
   - Añade la librería Glide para carga de imágenes.
   - En `app/build.gradle`, agrega:
     ```gradle
     implementation 'com.github.bumptech.glide:glide:4.12.0'
     annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
     ```
   - Sincroniza el proyecto.

3. **Usar Gradlew para tareas de build**:
   - Abre una terminal en la raíz del proyecto.
   - Ejecuta `./gradlew tasks` para ver todas las tareas disponibles.
   - Ejecuta `./gradlew assembleDebug` para compilar la versión de debug.

4. **Crear un nuevo buildType**:
   - En `app/build.gradle`, dentro de `android { ... }`, añade:
     ```gradle
     buildTypes {
         debug { ... }
         release { ... }
         staging {
             initWith debug
             manifestPlaceholders = [app_name: "My App (Staging)"]
         }
     }
     ```
   - Sincroniza y observa el nuevo buildType en la configuración de build.

## 6. Conceptos avanzados

### 6.1 Flavors

Los flavors permiten crear diferentes versiones de tu app desde el mismo proyecto:

```gradle
android {
    ...
    flavorDimensions "version"
    productFlavors {
        free {
            dimension "version"
            applicationIdSuffix ".free"
            versionNameSuffix "-free"
        }
        paid {
            dimension "version"
            applicationIdSuffix ".paid"
            versionNameSuffix "-paid"
        }
    }
}
```

### 6.2 Proguard

Proguard es una herramienta para ofuscar y optimizar el código. Se configura en `app/proguard-rules.pro`:

```proguard
-keep public class MyImportantClass
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
```

### 6.3 Firma de la aplicación

Para firmar la app para release:

1. Crea un keystore: `keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-alias`
2. En `app/build.gradle`:
   ```gradle
   android {
       ...
       signingConfigs {
           release {
               storeFile file("my-release-key.jks")
               storePassword "password"
               keyAlias "my-alias"
               keyPassword "password"
           }
       }
       buildTypes {
           release {
               signingConfig signingConfigs.release
           }
       }
   }
   ```

## Conclusión

Entender la estructura de un proyecto Android y el sistema de build Gradle es fundamental para el desarrollo eficiente de aplicaciones. Esta clase te ha proporcionado una visión profunda de cómo se organizan los proyectos Android, cómo funciona Gradle y Gradlew, y cómo gestionar dependencias. Con este conocimiento, estarás mejor preparado para configurar, personalizar y optimizar tus proyectos Android.