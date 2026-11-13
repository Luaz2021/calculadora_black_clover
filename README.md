# ♣️ Calculadora Científica — Black Clover Edition

![Kotlin](https://img.shields.io/badge/Kotlin-Android-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Gradle](https://img.shields.io/badge/Build-Gradle%20KTS-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Theme](https://img.shields.io/badge/Theme-Black%20Clover-111111?style=for-the-badge)

Aplicación Android de **calculadora científica** desarrollada en **Kotlin**, con diseño adaptativo para orientación **vertical** y **horizontal**, paleta inspirada en el grimorio de **Black Clover** (negro carbón, dorado y acentos oscuros/claros) y **pruebas instrumentadas** con Espresso.

---

## ✨ Qué trae esta calculadora

- 📱 **Modo Vertical (`layout/`):** calculadora clásica rápida y limpia con operaciones básicas (`+`, `-`, `×`, `÷`), porcentaje (`%`), cambio de signo (`+/-`), borrado (`DEL`) y reinicio (`C`).
- 🔬 **Modo Horizontal (`layout-land/`):** al girar el dispositivo se despliega el panel científico completo (`sin`, `cos`, `tan`, `ln`, `log`, `√`, `x²`, `xʸ`, `π`, `e`, `!`, `1/x`, `|x|`, paréntesis).
- 🌗 **Modo Claro / Oscuro:** botón superior para alternar el tema dinámicamente con `AppCompatDelegate`.
- 🔄 **Persistencia al rotar:** conserva la expresión actual y el último resultado mediante `onSaveInstanceState`.
- 🎨 **Identidad Black Clover:** colores centralizados en `res/values/colors.xml` aplicados de forma uniforme en ambos layouts.
- 🧪 **Tests Instrumentados:** validación automática de la interfaz y cálculos en ambos modos (`portrait` y `landscape`).

---

## 🛠️ Stack y Requisitos

- **Lenguaje:** Kotlin
- **Interfaz:** XML (`LinearLayout` + `TableLayout` con pesos proporcionales y soporte `EdgeToEdge`)
- **Build System:** Gradle (Kotlin DSL `.kts`) + Version Catalog (`gradle/libs.versions.toml`)
- **Testing:** JUnit + Espresso (`ExampleInstrumentedTest.kt`)
- **IDE recomendado:** Android Studio (Ladybug / Meerkat o superior) con JDK 17+

---

## 🚀 Cómo clonar y ejecutar

### 1. Clonar el repositorio

```bash
git clone https://github.com/Luaz2021/calculadora_black_clover.git
cd calculadora_black_clover
```

### 2. Abrir en Android Studio

1. Abre **Android Studio** → **Open** y selecciona la carpeta `calculadora_black_clover`.
2. Espera a que **Gradle Sync** descargue las dependencias.
3. Selecciona un emulador (por ejemplo *Medium Phone*) o dispositivo físico con depuración USB y pulsa **Run** (`Shift + F10`).

### 3. Compilar y probar por terminal

```bash
# Compilar APK de debug
./gradlew assembleDebug

# Compilar APK de pruebas instrumentadas
./gradlew assembleDebugAndroidTest

# Ejecutar tests instrumentados (requiere emulador o dispositivo conectado)
./gradlew connectedDebugAndroidTest
```

---

## 📂 Estructura del Proyecto

```text
calculadora_black_clover/
├── app/src/
│   ├── main/
│   │   ├── java/com/example/calculadoracientifica/
│   │   │   └── MainActivity.kt              # Lógica, parser, tema y persistencia
│   │   └── res/
│   │       ├── layout/activity_main.xml     # Interfaz vertical (básica)
│   │       ├── layout-land/activity_main.xml# Interfaz horizontal (científica)
│   │       └── values/colors.xml            # Paleta Black Clover
│   └── androidTest/java/com/example/calculadoracientifica/
│       └── ExampleInstrumentedTest.kt       # Tests Espresso (vertical y horizontal)
├── build.gradle.kts
├── gradle/libs.versions.toml
└── README.md
```

---

## 👨‍💻 Autor

- **Alexander Martínez** — [Luaz2021](https://github.com/Luaz2021)
