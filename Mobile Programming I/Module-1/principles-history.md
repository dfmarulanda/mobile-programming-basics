# 1.1 Principios de la tecnología móvil

## Objetivos de aprendizaje
- Comprender la evolución de la tecnología móvil
- Identificar los componentes principales de un smartphone moderno
- Reconocer el impacto de la evolución del hardware en el desarrollo de aplicaciones

## Contenido teórico

### Historia de los smartphones y su hardware

1. **Era pre-smartphone (1973-2007)**
   - 1973: Primer teléfono móvil (Motorola DynaTAC)
   - 1994: IBM Simon, considerado el primer "smartphone"
   - 2002: BlackBerry 5810, popular entre profesionales

2. **La revolución del smartphone (2007-presente)**
   - 2007: Lanzamiento del iPhone
   - 2008: Primer dispositivo Android (HTC Dream)
   - 2010-presente: Evolución rápida de hardware y software

### Evolución de las tecnologías móviles

1. **Redes móviles**
   - 1G: Analógica (1980s)
   - 2G: Digital, SMS (1990s)
   - 3G: Internet móvil (2000s)
   - 4G: Alta velocidad, video streaming (2010s)
   - 5G: Ultra baja latencia, IoT (2020s)

2. **Hardware**
   - Procesadores: De single-core a octa-core y beyond
   - Memoria: De MB a GB de RAM
   - Almacenamiento: De MB a TB
   - Pantallas: De monocromáticas a OLED de alta resolución
   - Cámaras: De VGA a múltiples sensores de alta resolución
   - Sensores: GPS, acelerómetro, giroscopio, etc.

## Actividad práctica: Explorar las capacidades del dispositivo

En esta actividad, crearemos una aplicación simple que muestre información sobre el hardware del dispositivo. Esto nos ayudará a entender cómo acceder a las características del dispositivo desde una aplicación Android.

### Paso 1: Crear un nuevo proyecto

1. Abre Android Studio
2. Clic en "File" > "New" > "New Project"
3. Selecciona "Empty Activity"
4. Nombra tu proyecto "DeviceInfoApp"
5. Selecciona API 21 como Minimum SDK
6. Clic en "Finish"

### Paso 2: Modificar el layout

Abre `res/layout/activity_main.xml` y reemplaza su contenido con:

```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <TextView
            android:id="@+id/tvDeviceInfo"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="16sp" />

    </LinearLayout>
</ScrollView>
```

### Paso 3: Modificar MainActivity

Abre `MainActivity.java` (o `MainActivity.kt` si estás usando Kotlin) y reemplaza su contenido con:

```kotlin
package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvDeviceInfo: TextView = findViewById(R.id.tvDeviceInfo)
        tvDeviceInfo.text = getDeviceInfo()

        val tvCameraInfo: TextView = findViewById(R.id.tvCameraInfo)
        tvCameraInfo.text = getCameraInfo()
    }

    private fun getDeviceInfo(): String {
        val sb = StringBuilder()
        sb.append("Modelo: ").append(Build.MODEL).append("\n")
        sb.append("Fabricante: ").append(Build.MANUFACTURER).append("\n")
        sb.append("Versión de Android: ").append(Build.VERSION.RELEASE).append("\n")
        sb.append("SDK: ").append(Build.VERSION.SDK_INT).append("\n")
        sb.append("Procesador: ").append(Build.HARDWARE).append("\n")
        sb.append("Display Info: ").append(Build.DISPLAY).append("\n")
        sb.append("Densidad:").append(resources.displayMetrics.densityDpi).append(" DPI \n")
        sb.append("Ram: ").append(Runtime.getRuntime().totalMemory()/(1024 * 1024)).append(" MB \n")

        return sb.toString()
    }

    private fun getCameraInfo(): String {
        val cameraInfo = StringBuilder()
        val cameraManager = getSystemService(CAMERA_SERVICE) as android.hardware.camera2.CameraManager
        cameraManager.cameraIdList.forEachIndexed {index, id ->
            val characteristics = cameraManager.getCameraCharacteristics(id)
            val facing = characteristics.get(android.hardware.camera2.CameraCharacteristics.LENS_FACING)
            val facingString = when(facing) {
                android.hardware.camera2.CameraCharacteristics.LENS_FACING_FRONT -> "Frontal"
                android.hardware.camera2.CameraCharacteristics.LENS_FACING_BACK -> "Trasera"
                else -> "Otra"
            }
            cameraInfo.append("Camara ${index + 1}: $facingString\n" )
        }
        return cameraInfo.toString()
    }
}
```

### Paso 4: Ejecutar la aplicación

1. Conecta un dispositivo Android o inicia un emulador
2. Presiona el botón "Run" en Android Studio
3. Selecciona el dispositivo/emulador y haz clic en "OK"

La aplicación mostrará información básica sobre el dispositivo.

## Ejercicio para los estudiantes

1. Modifica la aplicación para mostrar información adicional:
   - Tamaño de la pantalla en pulgadas
   - Densidad de píxeles de la pantalla
   - Cantidad de memoria RAM disponible
   - Espacio de almacenamiento total y disponible

2. Investiga y presenta la evolución de un fabricante de smartphones específico (por ejemplo, Samsung, Apple, Huawei). Incluye:
   - Hitos importantes en su historia
   - Evolución del hardware en sus dispositivos
   - Innovaciones tecnológicas introducidas

## Recursos adicionales

- [Android Developer: Build](https://developer.android.com/reference/android/os/Build)
- [Android Developer: Display Metrics](https://developer.android.com/reference/android/util/DisplayMetrics)
- [Historia de los smartphones](https://www.techradar.com/news/mobile-computing/10-moments-that-changed-the-mobile-world-700451)
