CAMBIO

# Sesión Especial: Migración de Java a Kotlin (Ampliado)

## Índice
1. [Introducción](#introducción)
2. [Java vs Kotlin: Análisis Detallado](#java-vs-kotlin-análisis-detallado)
3. [Sintaxis de Kotlin en Profundidad](#sintaxis-de-kotlin-en-profundidad)
4. [Características Avanzadas de Kotlin](#características-avanzadas-de-kotlin)
5. [Proceso de Migración Detallado](#proceso-de-migración-detallado)
6. [Interoperabilidad Java-Kotlin](#interoperabilidad-java-kotlin)
7. [Patrones de Diseño en Kotlin](#patrones-de-diseño-en-kotlin)
8. [Optimización de Rendimiento](#optimización-de-rendimiento)
9. [Ejercicios Prácticos Avanzados](#ejercicios-prácticos-avanzados)
10. [Recursos Adicionales](#recursos-adicionales)

## 1. Introducción

Kotlin es un lenguaje de programación moderno desarrollado por JetBrains. Fue anunciado en 2011 y se convirtió en el lenguaje oficial para el desarrollo de Android en 2017. Kotlin está diseñado para interoperar completamente con Java, lo que permite a los desarrolladores migrar gradualmente sus proyectos existentes.

## 2. Java vs Kotlin: Análisis Detallado

### 2.1 Manejo de Nulos

Java:
```java
String str = null;
int length = str.length(); // Puede causar NullPointerException en tiempo de ejecución
```

Kotlin:
```kotlin
var str: String? = null
val length = str?.length // Retorna null si str es null
// o
val length = str!!.length // Lanza NullPointerException si str es null
```

Kotlin introduce el concepto de tipos nullables y no nullables. Un tipo nullable se denota con `?` después del tipo. Esto obliga a los desarrolladores a manejar explícitamente los casos nulos, reduciendo significativamente los NullPointerExceptions.

### 2.2 Funciones de Extensión

Java no tiene funciones de extensión nativas. Se pueden simular con clases de utilidad estáticas.

Java:
```java
public class StringUtils {
    public static boolean isEmail(String str) {
        return str.contains("@");
    }
}

// Uso
if (StringUtils.isEmail(email)) { ... }
```

Kotlin:
```kotlin
fun String.isEmail(): Boolean = this.contains("@")

// Uso
if (email.isEmail()) { ... }
```

Las funciones de extensión en Kotlin permiten añadir métodos a clases existentes sin modificarlas, lo que mejora la legibilidad y la organización del código.

### 2.3 Clases de Datos

Java requiere mucho código boilerplate para clases que principalmente contienen datos.

Java:
```java
public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
```

Kotlin:
```kotlin
data class Person(val name: String, val age: Int)
```

Kotlin genera automáticamente `equals()`, `hashCode()`, `toString()`, y funciones de copia para clases de datos, reduciendo significativamente el código boilerplate.

### 2.4 Funciones de Orden Superior y Lambdas

Java (pre-Java 8):
```java
Collections.sort(list, new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
});
```

Kotlin:
```kotlin
list.sortWith { s1, s2 -> s1.compareTo(s2) }
```

Kotlin tiene un soporte más robusto y una sintaxis más concisa para funciones de orden superior y lambdas, lo que facilita la programación funcional.

## 3. Sintaxis de Kotlin en Profundidad

### 3.1 Declaración de Variables

```kotlin
val immutableVar: String = "No puede cambiar"
var mutableVar: Int = 5
mutableVar = 10 // Esto es válido

// Inferencia de tipos
val inferredType = "Kotlin infiere que esto es un String"

// Declaración tardía
lateinit var lateInitVar: String
// Debe inicializarse antes de su uso o lanzará UninitializedPropertyAccessException
```

### 3.2 Control de Flujo

#### When Expression
```kotlin
val result = when (x) {
    1 -> "x es 1"
    2, 3 -> "x es 2 o 3"
    in 4..10 -> "x está entre 4 y 10"
    !in 11..20 -> "x no está entre 11 y 20"
    else -> "ninguno de los anteriores"
}
```

#### For Loops
```kotlin
for (item in collection) println(item)

for (i in 1..10) println(i) // 1 to 10
for (i in 10 downTo 1) println(i) // 10 to 1
for (i in 1 until 10) println(i) // 1 to 9
```

### 3.3 Funciones

```kotlin
// Función básica
fun sum(a: Int, b: Int): Int {
    return a + b
}

// Función de una sola expresión
fun sum(a: Int, b: Int) = a + b

// Parámetros por defecto
fun greet(name: String = "World") = println("Hello, $name!")

// Funciones de extensión
fun String.lastChar() = this[length - 1]

// Funciones infix
infix fun Int.times(str: String) = str.repeat(this)
// Uso: 3 times "Hello" 
```

### 3.4 Clases y Objetos

```kotlin
// Clase básica
class Person(val name: String) {
    var age: Int = 0
    
    fun introduce() = "I'm $name and I'm $age years old"
}

// Herencia
open class Animal(val name: String)
class Dog(name: String): Animal(name)

// Object (Singleton)
object DatabaseConfig {
    val url = "jdbc:mysql://localhost/test"
}

// Companion Object
class MyClass {
    companion object {
        fun create(): MyClass = MyClass()
    }
}
```

## 4. Características Avanzadas de Kotlin

### 4.1 Corrutinas

Las corrutinas son una característica poderosa de Kotlin para manejar operaciones asíncronas de manera secuencial.

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello,")
}
```

### 4.2 Delegación de Propiedades

Kotlin permite delegar la implementación de propiedades a otras clases.

```kotlin
class Example {
    var p: String by Delegate()
}

class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}
```

### 4.3 Sealed Classes

Las sealed classes son útiles para representar jerarquías de clases restringidas.

```kotlin
sealed class Result
class Success(val data: String) : Result()
class Error(val message: String) : Result()

fun handleResult(result: Result) = when(result) {
    is Success -> println("Success: ${result.data}")
    is Error -> println("Error: ${result.message}")
}
```

## 5. Proceso de Migración Detallado

### 5.1 Conversión Automática

1. Abre un archivo Java en Android Studio.
2. Ve a "Code > Convert Java File to Kotlin File".
3. Revisa el código resultante cuidadosamente.

### 5.2 Optimización Manual

Después de la conversión automática, considera las siguientes optimizaciones:

1. Reemplazar getters y setters con propiedades de Kotlin.
2. Utilizar funciones de extensión donde sea apropiado.
3. Convertir clases de datos Java a data classes de Kotlin.
4. Aprovechar las características de null-safety de Kotlin.
5. Utilizar las expresiones `when` en lugar de múltiples `if-else`.

### 5.3 Ejemplo de Migración y Optimización

Java original:
```java
public class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean isAdult() {
        return age >= 18;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + "}";
    }
}
```

Kotlin después de la conversión automática:
```kotlin
class User(var name: String, var age: Int) {
    fun isAdult(): Boolean {
        return age >= 18
    }

    override fun toString(): String {
        return "User{name='$name', age=$age}"
    }
}
```

Kotlin optimizado:
```kotlin
data class User(var name: String, var age: Int) {
    val isAdult get() = age >= 18
}
```

## 6. Interoperabilidad Java-Kotlin

Kotlin está diseñado para interoperar perfectamente con Java. Esto significa que puedes llamar a código Java desde Kotlin y viceversa.

### 6.1 Llamando a Java desde Kotlin

```kotlin
// Suponiendo que tenemos una clase Java llamada JavaClass
val javaObject = JavaClass()
javaObject.javaMethod()

// Kotlin trata las propiedades de Java como propiedades de Kotlin
val name = javaObject.name
javaObject.age = 25
```

### 6.2 Llamando a Kotlin desde Java

```java
// Suponiendo que tenemos una clase Kotlin llamada KotlinClass
KotlinClass kotlinObject = new KotlinClass();
kotlinObject.kotlinMethod();

// Los getters y setters de Kotlin se llaman como métodos en Java
String name = kotlinObject.getName();
kotlinObject.setAge(25);
```

### 6.3 Anotaciones para Mejorar la Interoperabilidad

Kotlin proporciona anotaciones para mejorar la interoperabilidad con Java:

- `@JvmStatic`: Hace que un método o propiedad de un objeto companion sea estático en Java.
- `@JvmField`: Expone una propiedad de Kotlin como un campo público en Java.
- `@JvmOverloads`: Genera sobrecargadas de Java para funciones con parámetros por defecto.

```kotlin
class KotlinClass {
    companion object {
        @JvmStatic fun staticMethod() = println("Static method")
    }

    @JvmField
    var publicField: String = "Public field"

    @JvmOverloads
    fun overloadedMethod(param1: String, param2: Int = 0) = println("$param1, $param2")
}
```

## 7. Patrones de Diseño en Kotlin

Kotlin permite implementar patrones de diseño de manera más concisa y segura que Java.

### 7.1 Singleton

Java:
```java
public class Singleton {
    private static Singleton instance;
    private Singleton() {}
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

Kotlin:
```kotlin
object Singleton
```

### 7.2 Builder

Kotlin no necesita un patrón Builder separado debido a los parámetros nombrados y valores por defecto:

```kotlin
class Pizza(
    val size: String,
    val cheese: Boolean = false,
    val pepperoni: Boolean = false,
    val bacon: Boolean = false
)

// Uso
val myPizza = Pizza(size = "large", cheese = true, pepperoni = true)
```

### 7.3 Factory Method

```kotlin
sealed class Vehicle
class Car : Vehicle()
class Bike : Vehicle()

object VehicleFactory {
    fun createVehicle(type: String): Vehicle = when (type.toLowerCase()) {
        "car" -> Car()
        "bike" -> Bike()
        else -> throw IllegalArgumentException("Unknown vehicle type")
    }
}
```

## 8. Optimización de Rendimiento

### 8.1 Inline Functions

Las funciones inline pueden mejorar el rendimiento al eliminar la sobrecarga de las llamadas a funciones:

```kotlin
inline fun measureTimeMillis(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}
```

### 8.2 Lazy Initialization

```kotlin
val heavyObject: HeavyObject by lazy {
    println("Inicializando objeto pesado")
    HeavyObject()
}
```

### 8.3 Secuencias

Las secuencias pueden mejorar el rendimiento en operaciones encadenadas:

```kotlin
val result = sequence {
    yield(1)
    yield(2)
    yield(3)
}.map { it * 2 }
.filter { it > 3 }
.first()
```

## 9. Ejercicios Prácticos Avanzados

1. **Migración Compleja**: Toma una clase Java que utilice genéricos, herencia y una interfaz interna. Conviértela a Kotlin y optimízala.

2. **Corrutinas**: Implementa un cargador de imágenes asíncrono utilizando corrutinas de Kotlin.

3. **DSL**: Crea un DSL (Domain Specific Language) simple para construir estructuras HTML en Kotlin.

4. **Interoperabilidad**: Crea una biblioteca en Kotlin que sea fácilmente