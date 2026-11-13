# Calculadora Científica — Black Clover Edition ♣️

Aplicación Android de calculadora científica desarrollada en **Kotlin**, con soporte para orientación vertical y horizontal, tema visual inspirado en **Black Clover** y pruebas instrumentadas.

## ✨ Características

- **Modo vertical (Portrait):** operaciones aritméticas básicas (`+`, `-`, `×`, `÷`), porcentaje, cambio de signo, paréntesis y borrado.
- **Modo horizontal (Landscape):** panel científico completo con funciones trigonométricas (`sin`, `cos`, `tan`), logaritmos (`log`, `ln`), potencias, raíz cuadrada y constantes matemáticas.
- **Persistencia de estado:** conserva la expresión y el resultado actual al rotar la pantalla (`onSaveInstanceState`).
- **Paleta Black Clover:** colores personalizados definidos en `res/values/colors.xml` y aplicados de forma uniforme en ambos layouts.

## 🛠️ Stack Técnico

- **Lenguaje:** Kotlin
- **Interfaz:** XML (`LinearLayout` + `TableLayout`) con layouts diferenciados (`layout/` y `layout-land/`)
- **Testing:** Pruebas instrumentadas con Espresso (`ExampleInstrumentedTest.kt`) para validar la UI y el cálculo en ambos modos de pantalla.

## 📂 Estructura Principal

```text
app/src/
├── main/
│   ├── java/com/example/calculadoracientifica/
│   │   └── MainActivity.kt
│   └── res/
│       ├── layout/activity_main.xml
│       ├── layout-land/activity_main.xml
│       └── values/colors.xml
└── androidTest/java/com/example/calculadoracientifica/
    └── ExampleInstrumentedTest.kt
```

## 🚀 Compilación y Pruebas

```bash
./gradlew assembleDebug
./gradlew assembleDebugAndroidTest
```

## 🔗 Repositorio

- [Luaz2021/calculadora_black_clover](https://github.com/Luaz2021/calculadora_black_clover)
