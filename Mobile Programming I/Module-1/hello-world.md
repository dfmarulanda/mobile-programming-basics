# Creación de una aplicación Android interactiva: "Hola Mundo Mejorado"

## Objetivos de la clase

1. Crear una interfaz de usuario más compleja con múltiples elementos.
2. Implementar interacciones básicas con el usuario.
3. Utilizar recursos de strings para internacionalización.
4. Introducir el concepto de estado de la aplicación.
5. Practicar el manejo de eventos en Android.

## 1. Diseño de la Interfaz de Usuario

Vamos a crear una interfaz que incluya:
- Un campo de texto para ingresar el nombre del usuario.
- Un botón para saludar.
- Un TextView para mostrar el saludo.
- Un botón para cambiar el idioma del saludo.

### 1.1 Modificación del archivo de diseño

Abre `res/layout/activity_main.xml` y reemplaza su contenido con:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/editTextName"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="@string/enter_name"
        android:inputType="textPersonName"
        android:layout_marginTop="16dp"
        android:layout_marginStart="16dp"
        android:layout_marginEnd="16dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <Button
        android:id="@+id/buttonGreet"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/greet"
        android:layout_marginTop="16dp"
        app:layout_constraintTop_toBottomOf="@id/editTextName"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <TextView
        android:id="@+id/textViewGreeting"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="24sp"
        android:layout_marginTop="32dp"
        app:layout_constraintTop_toBottomOf="@id/buttonGreet"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <Button
        android:id="@+id/buttonChangeLanguage"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/change_language"
        android:layout_marginTop="16dp"
        app:layout_constraintTop_toBottomOf="@id/textViewGreeting"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### 1.2 Actualización de los recursos de strings

Modifica `res/values/strings.xml`:

```xml
<resources>
    <string name="app_name">Hola Mundo Mejorado</string>
    <string name="enter_name">Ingresa tu nombre</string>
    <string name="greet">Saludar</string>
    <string name="hello">Hola, %1$s!</string>
    <string name="change_language">Cambiar Idioma</string>
</resources>
```

Crea un nuevo archivo `res/values-en/strings.xml` para el inglés:

```xml
<resources>
    <string name="app_name">Enhanced Hello World</string>
    <string name="enter_name">Enter your name</string>
    <string name="greet">Greet</string>
    <string name="hello">Hello, %1$s!</string>
    <string name="change_language">Change Language</string>
</resources>
```

## 2. Implementación de la Lógica

Modifica `MainActivity.kt`:

```kotlin
package com.ejemplo.holamundoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var buttonGreet: Button
    private lateinit var textViewGreeting: TextView
    private lateinit var buttonChangeLanguage: Button

    private var isSpanish = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        buttonGreet = findViewById(R.id.buttonGreet)
        textViewGreeting = findViewById(R.id.textViewGreeting)
        buttonChangeLanguage = findViewById(R.id.buttonChangeLanguage)

        buttonGreet.setOnClickListener {
            greetUser()
        }

        buttonChangeLanguage.setOnClickListener {
            changeLanguage()
        }
    }

    private fun greetUser() {
        val name = editTextName.text.toString()
        if (name.isNotEmpty()) {
            val greeting = getString(R.string.hello, name)
            textViewGreeting.text = greeting
        } else {
            textViewGreeting.text = ""
        }
    }

    private fun changeLanguage() {
        isSpanish = !isSpanish
        val newLocale = if (isSpanish) Locale("es") else Locale("en")
        updateLocale(newLocale)
    }

    private fun updateLocale(locale: Locale) {
        Locale.setDefault(locale)
        val resources = resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        recreate()
    }
}
```

## 3. Explicación del Código

### 3.1 Inicialización de Vistas

En `onCreate()`, inicializamos las referencias a nuestras vistas usando `findViewById()`. Esto nos permite interactuar con los elementos de la UI en nuestro código.

### 3.2 Manejo de Eventos

Usamos `setOnClickListener` para manejar los clics en los botones. Cuando se hace clic en un botón, se ejecuta la función correspondiente.

### 3.3 Función `greetUser()`

Esta función obtiene el nombre ingresado por el usuario, construye un saludo usando un string formateado y lo muestra en el `TextView`.

### 3.4 Cambio de Idioma

La función `changeLanguage()` alterna entre español e inglés. `updateLocale()` actualiza la configuración de idioma de la aplicación y recrea la actividad para aplicar los cambios.

## 4. Ejecución y Prueba

1. Ejecuta la aplicación en el emulador.
2. Ingresa un nombre y presiona "Saludar" para ver el saludo.
3. Presiona "Cambiar Idioma" para ver cómo cambia el texto de la interfaz.

## 5. Ejercicios Prácticos

1. Agrega un tercer idioma (por ejemplo, francés) y modifica la lógica para rotar entre los tres idiomas.
2. Implementa un `ToggleButton` para cambiar entre modo claro y oscuro de la aplicación.
3. Agrega un `ImageView` que muestre una imagen diferente dependiendo del idioma seleccionado.

## 6. Conceptos Avanzados (para discusión)

- **Persistencia de Estado**: ¿Qué sucede con el idioma seleccionado cuando cierras y vuelves a abrir la app? Investiga sobre `SharedPreferences` para guardar el estado.
- **Fragmentos**: Discute cómo podrías dividir esta UI en fragmentos reutilizables.
- **ViewModel**: Introduce el concepto de ViewModel de la arquitectura MVVM y cómo podría mejorar esta app.

## Conclusión

En esta clase, has creado una versión más interactiva y funcional de la aplicación "Hola Mundo". Has aprendido sobre manejo de eventos, internacionalización básica, y cómo actualizar dinámicamente la UI. Estos conceptos son fundamentales en el desarrollo de aplicaciones Android más complejas y te preparan para los próximos temas del curso.