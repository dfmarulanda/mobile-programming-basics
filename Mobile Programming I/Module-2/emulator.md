# Emuladores y ADB en el Desarrollo Android: Guía Completa

## Objetivos de la clase

1. Comprender en profundidad qué es un emulador y su importancia en el desarrollo de Android.
2. Aprender a configurar y utilizar diferentes tipos de emuladores en Android Studio.
3. Explorar las opciones avanzadas de configuración de emuladores.
4. Introducir ADB y su uso en conjunto con emuladores.
5. Practicar el uso de emuladores y ADB para probar y depurar aplicaciones.
6. Conocer las mejores prácticas y trucos para trabajar con emuladores y ADB.

## 1. Introducción a los Emuladores de Android

### 1.1 ¿Qué es un emulador?

Un emulador de Android es una máquina virtual que simula un dispositivo Android real en tu computadora. Permite probar aplicaciones en diferentes configuraciones de hardware y versiones de Android sin necesidad de tener dispositivos físicos.

### 1.2 Ventajas de usar emuladores

- Probar en múltiples versiones de Android y tamaños de pantalla.
- No requiere dispositivos físicos.
- Facilita la depuración y el testing.
- Permite simular condiciones específicas (como baja batería, ubicación GPS, etc.).

## 2. Configuración de Emuladores en Android Studio

### 2.1 Acceder al AVD Manager

1. Abre Android Studio.
2. Ve a "Tools" > "AVD Manager" o haz clic en el ícono de AVD Manager en la barra de herramientas.

### 2.2 Crear un Nuevo Emulador

1. En el AVD Manager, haz clic en "Create Virtual Device".
2. Selecciona una categoría de dispositivo (Phone, Tablet, TV, Wear OS).
3. Elige un dispositivo específico (por ejemplo, Pixel 4).
4. Selecciona una imagen del sistema (versión de Android).
   - Recomendación: Elige una imagen con Google APIs para tener acceso a servicios de Google.
5. Configura las opciones básicas:
   - AVD Name: Dale un nombre descriptivo.
   - Startup orientation: Vertical u Horizontal.
6. Haz clic en "Finish".

### 2.3 Ejemplo de configuración básica

```
AVD Name: Pixel_4_API_30
Device: Pixel 4
System Image: Android 11.0 Google APIs
RAM: 1536 MB
Internal Storage: 2048 MB
SD Card: 512 MB
```

## 3. Opciones Avanzadas de Configuración de Emuladores

### 3.1 Memoria y Almacenamiento

- RAM: Ajusta según las necesidades de tu app y la capacidad de tu computadora.
- VM Heap: Tamaño del heap de la máquina virtual.
- Internal Storage: Espacio de almacenamiento interno.
- SD Card: Simula una tarjeta SD.

### 3.2 Cámara

- Webcam0: Utiliza la cámara de tu computadora.
- Emulated: Usa una imagen estática o un video.

### 3.3 Red

- Speed: Simula diferentes velocidades de red (GSM, HSCSD, GPRS, EDGE, UMTS, HSDPA).
- Latency: Normal, Low, Medium, High.

### 3.4 Sensores

- Accelerometer: Simula movimientos del dispositivo.
- GPS: Simula ubicaciones geográficas.

## 4. Uso del Emulador

### 4.1 Iniciar el Emulador

1. En AVD Manager, selecciona un emulador.
2. Haz clic en el botón de play (triángulo verde).

### 4.2 Controles del Emulador

- Rotación: Botones para rotar el dispositivo.
- Volume: Controles de volumen.
- Power: Simula el botón de encendido.
- Back, Home, Overview: Botones de navegación.

### 4.3 Extended Controls (...)

- Location: Simula ubicaciones GPS.
- Cellular: Cambia el estado de la red.
- Battery: Simula diferentes niveles de batería.
- Phone: Simula llamadas entrantes y SMS.
- Fingerprint: Simula autenticación por huella digital.
- Settings: Ajustes adicionales del emulador.

## 5. Introducción a ADB (Android Debug Bridge)

### 5.1 ¿Qué es ADB?

ADB es una herramienta de línea de comandos versátil que permite comunicarse con dispositivos Android, incluyendo emuladores. Es parte del SDK de Android y es esencial para desarrolladores y testers.

### 5.2 Componentes de ADB

1. **Cliente**: Se ejecuta en tu máquina de desarrollo.
2. **Daemon (adbd)**: Se ejecuta en segundo plano en cada dispositivo o emulador.
3. **Servidor**: Se ejecuta en segundo plano en tu máquina de desarrollo, gestionando la comunicación entre el cliente y el daemon.

## 6. Uso de ADB con Emuladores

### 6.1 Conectar ADB al Emulador

ADB se conecta automáticamente a los emuladores en ejecución. Verifica con:

```bash
adb devices
```

### 6.2 Comandos Básicos de ADB para Emuladores

#### Instalar una aplicación en el emulador
```bash
adb install path/to/your_app.apk
```

#### Acceder a la shell del emulador
```bash
adb shell
```

#### Transferir archivos al emulador
```bash
adb push local_file.txt /sdcard/
```

#### Obtener logs del emulador
```bash
adb logcat
```

### 6.3 Depuración con ADB y Emuladores

#### Reiniciar el emulador
```bash
adb reboot
```

#### Captura de pantalla
```bash
adb shell screencap /sdcard/screen.png
adb pull /sdcard/screen.png
```

#### Simular eventos de entrada
```bash
adb shell input tap 500 500  # Simula un toque
adb shell input text "Hello World"  # Ingresa texto
```

## 7. Prácticas Avanzadas con Emuladores y ADB

### 7.1 Múltiples Emuladores

Ejecuta varios emuladores simultáneamente:

```bash
emulator -avd Emulator1 &
emulator -avd Emulator2 &
```

Para interactuar con un emulador específico:

```bash
adb -s emulator-5554 shell
```

### 7.2 Automatización de Pruebas

Usa ADB para automatizar pruebas en emuladores:

```bash
# Ejemplo de script de prueba
adb install app.apk
adb shell am start -n com.example.app/.MainActivity
adb shell input tap 500 500
adb shell screencap /sdcard/test_result.png
adb pull /sdcard/test_result.png
```

### 7.3 Simulación de Condiciones de Red

Usa los Extended Controls del emulador para simular diferentes condiciones de red, luego observa los logs con ADB:

```bash
adb logcat | grep "NetworkRequest"
```

### 7.4 Depuración de Rendimiento

Usa Android Profiler en conjunto con ADB para analizar el rendimiento:

1. Inicia tu app en el emulador.
2. Usa Android Profiler en Android Studio.
3. Simultáneamente, monitorea los logs:
   ```bash
   adb logcat | grep "performance"
   ```

## 8. Mejores Prácticas y Trucos

### 8.1 Aceleración de Hardware

Para mejorar el rendimiento, asegúrate de habilitar la aceleración de hardware:

1. En BIOS/UEFI, habilita la virtualización (VT-x para Intel, AMD-V para AMD).
2. En Android Studio, ve a SDK Manager > SDK Tools y asegúrate de tener instalado:
   - Intel x86 Emulator Accelerator (HAXM installer)
   - Android Emulator Hypervisor Driver for AMD Processors (para CPUs AMD)

### 8.2 Snapshots

Usa snapshots para guardar y cargar rápidamente el estado del emulador:

1. En AVD Manager, edita tu emulador.
2. En "Emulated Performance", marca "Store a snapshot for faster startup".

### 8.3 ADB sobre Wi-Fi

Para conectar ADB a un dispositivo físico vía Wi-Fi:

1. Conecta el dispositivo vía USB y asegúrate de que está en la misma red Wi-Fi que tu computadora.
2. Ejecuta:
   ```bash
   adb tcpip 5555
   adb connect DEVICE_IP:5555
   ```

## 9. Ejercicios Prácticos

1. **Configuración de Emuladores Variados**:
   - Crea emuladores para diferentes versiones de Android y tamaños de pantalla.
   - Usa ADB para instalar la misma app en todos ellos:
     ```bash
     for emulator in $(adb devices | grep emulator | cut -f1)
     do
         adb -s $emulator install your_app.apk
     done
     ```

2. **Simulación de Condiciones**:
   - Usa los Extended Controls para simular baja batería.
   - Con ADB, verifica cómo tu app responde:
     ```bash
     adb shell dumpsys battery
     adb logcat | grep "BatteryManager"
     ```

3. **Depuración de Red**:
   - Simula una conexión lenta en el emulador.
   - Usa ADB para monitorear las solicitudes de red de tu app:
     ```bash
     adb shell netstat
     ```

4. **Automatización de Pruebas UI**:
   - Escribe un script que use ADB para:
     1. Instalar tu app
     2. Navegar por varias pantallas (usando `input` commands)
     3. Capturar screenshots en cada paso
     4. Recopilar logs

5. **Comparación de Rendimiento**:
   - Ejecuta tu app en emuladores de gama alta y baja.
   - Usa ADB para recopilar métricas de rendimiento:
     ```bash
     adb shell dumpsys meminfo your.package.name
     adb shell dumpsys cpuinfo
     ```

## 10. Resolución de Problemas Comunes

1. **Emulador Lento**: 
   - Verifica la aceleración de hardware.
   - Reduce la resolución de la pantalla del emulador.
   - Aumenta la RAM asignada si tu computadora lo permite.

2. **El emulador no inicia**: 
   - Verifica los logs en `~/.android/avd/your_avd.avd/config.ini`.
   - Asegúrate de tener espacio suficiente en el disco.

3. **Problemas de red en el emulador**:
   - Verifica la configuración de proxy de Android Studio.
   - Intenta reiniciar ADB: `adb kill-server` seguido de `adb start-server`.

4. **ADB no detecta el emulador**:
   - Reinicia el servidor ADB:
     ```bash
     adb kill-server
     adb start-server
     ```
   - Verifica el estado del emulador:
     ```bash
     adb devices
     ```

## Conclusión

Los emuladores y ADB son herramientas fundamentales en el desarrollo de Android. Los emuladores proporcionan un entorno controlado para probar aplicaciones en diversas configuraciones, mientras que ADB ofrece un puente crucial para la comunicación y depuración. Dominar estas herramientas en conjunto mejorará significativamente tu flujo de trabajo de desarrollo, permitiéndote crear, probar y depurar aplicaciones de manera más eficiente y efectiva.

La combinación de emuladores y ADB te permite:
- Simular una amplia gama de dispositivos y escenarios.
- Automatizar pruebas y despliegues.
- Depurar problemas de manera más efectiva.
- Optimizar el rendimiento de tus aplicaciones.

A medida que continúes desarrollando para Android, encontrarás que estas herramientas se vuelven indispensables en tu kit de desarrollo diario.