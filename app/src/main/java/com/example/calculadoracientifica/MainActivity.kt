package com.example.calculadoracientifica

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    // Pantallas de arriba: la operación que voy escribiendo y el resultado final
    private lateinit var tvOperation: TextView
    private lateinit var tvResult: TextView
    private var expression: String = ""
    private var lastResult: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Ajuste de márgenes para que la barra de estado y navegación no tapen los botones, manteniendo el padding de 16dp
        val basePad = (16 * resources.displayMetrics.density).toInt()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + basePad,
                systemBars.top + basePad,
                systemBars.right + basePad,
                systemBars.bottom + basePad
            )
            insets
        }

        tvOperation = findViewById(R.id.tvOperation)
        tvResult = findViewById(R.id.tvResult)

        // Si giramos el móvil, recuperamos lo que había en pantalla antes de recrear la Activity
        if (savedInstanceState != null) {
            expression = savedInstanceState.getString("expression", "")
            lastResult = savedInstanceState.getString("lastResult", "0")
            tvOperation.text = expression
            tvResult.text = lastResult
        }

        setupThemeToggle()
        setupBasicButtons()
        setupScientificButtons()
    }

    // Guardamos la expresión y el último resultado antes de que Android destruya la vista al rotar
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("expression", expression)
        outState.putString("lastResult", lastResult)
    }

    // Botón para alternar entre modo claro y modo oscuro
    private fun setupThemeToggle() {
        findViewById<ImageButton?>(R.id.btnThemeToggle)?.setOnClickListener {
            val current = AppCompatDelegate.getDefaultNightMode()
            val next = if (current == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.MODE_NIGHT_NO
            } else {
                AppCompatDelegate.MODE_NIGHT_YES
            }
            AppCompatDelegate.setDefaultNightMode(next)
        }
    }

    // Conecta todos los botones básicos (números, operadores, limpiar, borrar, signo e igual)
    private fun setupBasicButtons() {
        val digits = mapOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2",
            R.id.btn3 to "3", R.id.btn4 to "4", R.id.btn5 to "5",
            R.id.btn6 to "6", R.id.btn7 to "7", R.id.btn8 to "8",
            R.id.btn9 to "9", R.id.btnDot to "."
        )
        digits.forEach { (id, value) ->
            findViewById<Button?>(id)?.setOnClickListener { appendToken(value) }
        }

        val operators = mapOf(
            R.id.btnAdd to "+", R.id.btnSub to "-",
            R.id.btnMul to "×", R.id.btnDiv to "÷",
            R.id.btnPercent to "%"
        )
        operators.forEach { (id, op) ->
            findViewById<Button?>(id)?.setOnClickListener { appendToken(op) }
        }

        // AC: resetea todo a cero
        findViewById<Button?>(R.id.btnClear)?.setOnClickListener {
            expression = ""
            lastResult = "0"
            tvOperation.text = ""
            tvResult.text = "0"
        }

        // Borra solo el último carácter escrito
        findViewById<Button?>(R.id.btnDelete)?.setOnClickListener {
            if (expression.isNotEmpty()) {
                expression = expression.dropLast(1)
                tvOperation.text = expression
            }
        }

        // Cambia el signo de la expresión actual o del último resultado si la pantalla está vacía
        findViewById<Button?>(R.id.btnSign)?.setOnClickListener {
            if (expression.isEmpty() && lastResult != "0" && lastResult != "Error") {
                expression = if (lastResult.startsWith("-")) lastResult.drop(1) else "-$lastResult"
            } else if (expression.startsWith("-")) {
                expression = expression.drop(1)
            } else if (expression.isNotEmpty()) {
                expression = "-($expression)"
            }
            tvOperation.text = expression
        }

        findViewById<Button?>(R.id.btnEqual)?.setOnClickListener {
            evaluateAndShow()
        }
    }

    /**
     * Botones científicos: solo existen en layout-land, por eso usan null safety (?).
     */
    private fun setupScientificButtons() {
        val sciTokens = mapOf(
            R.id.btnSin to "sin(",
            R.id.btnCos to "cos(",
            R.id.btnTan to "tan(",
            R.id.btnLn to "ln(",
            R.id.btnLog to "log(",
            R.id.btnSqrt to "√(",
            R.id.btnPow2 to "^2",
            R.id.btnPowY to "^",
            R.id.btnOpenParen to "(",
            R.id.btnCloseParen to ")",
            R.id.btnPi to "π",
            R.id.btnE to "e"
        )
        sciTokens.forEach { (id, token) ->
            findViewById<Button?>(id)?.setOnClickListener { appendToken(token) }
        }

        findViewById<Button?>(R.id.btnFact)?.setOnClickListener { appendToken("!") }
        findViewById<Button?>(R.id.btnInv)?.setOnClickListener {
            if (expression.isNotEmpty()) {
                expression = "1/($expression)"
                tvOperation.text = expression
            }
        }
        findViewById<Button?>(R.id.btnAbs)?.setOnClickListener {
            if (expression.isNotEmpty()) {
                expression = "abs($expression)"
                tvOperation.text = expression
            }
        }
    }

    private fun appendToken(token: String) {
        expression += token
        tvOperation.text = expression
    }

    private fun evaluateAndShow() {
        if (expression.isBlank()) return
        try {
            val value = ExpressionParser(expression).parse()
            if (value.isNaN() || value.isInfinite()) {
                lastResult = "Error"
            } else {
                lastResult = if (value % 1.0 == 0.0 && abs(value) < 1e15) {
                    value.toLong().toString()
                } else {
                    String.format(java.util.Locale.US, "%.8f", value)
                        .trimEnd('0')
                        .trimEnd('.')
                }
            }
        } catch (_: Exception) {
            lastResult = "Error"
        }
        tvResult.text = lastResult
    }

    /**
     * Parser descendente recursivo para expresiones aritméticas y científicas.
     */
    private class ExpressionParser(raw: String) {
        private val str = raw
            .replace("×", "*")
            .replace("÷", "/")
            .replace("π", Math.PI.toString())
            .replace(Regex("(?<![a-zA-Z])e(?![a-zA-Z])"), Math.E.toString())
            .replace(" ", "")

        private var pos = -1
        private var ch = 0

        private fun nextChar() {
            ch = if (++pos < str.length) str[pos].code else -1
        }

        private fun eat(charToEat: Int): Boolean {
            while (ch == ' '.code) nextChar()
            if (ch == charToEat) {
                nextChar()
                return true
            }
            return false
        }

        fun parse(): Double {
            nextChar()
            val x = parseExpression()
            if (pos < str.length) throw RuntimeException("Unexpected: ${ch.toChar()}")
            return x
        }

        private fun parseExpression(): Double {
            var x = parseTerm()
            while (true) {
                when {
                    eat('+'.code) -> x += parseTerm()
                    eat('-'.code) -> x -= parseTerm()
                    else -> return x
                }
            }
        }

        private fun parseTerm(): Double {
            var x = parsePower()
            while (true) {
                when {
                    eat('*'.code) -> x *= parsePower()
                    eat('/'.code) -> {
                        val denom = parsePower()
                        if (denom == 0.0) throw ArithmeticException("Division by zero")
                        x /= denom
                    }
                    eat('%'.code) -> x /= 100.0
                    else -> return x
                }
            }
        }

        private fun parsePower(): Double {
            var x = parseFactor()
            while (true) {
                if (eat('^'.code)) {
                    x = x.pow(parseFactor())
                } else if (eat('!'.code)) {
                    x = factorial(x.toInt())
                } else {
                    return x
                }
            }
        }

        private fun parseFactor(): Double {
            if (eat('+'.code)) return parseFactor()
            if (eat('-'.code)) return -parseFactor()

            var x: Double
            val startPos = pos
            if (eat('('.code)) {
                x = parseExpression()
                if (!eat(')'.code)) throw RuntimeException("Missing ')'")
            } else if ((ch in '0'.code..'9'.code) || ch == '.'.code) {
                while ((ch in '0'.code..'9'.code) || ch == '.'.code) nextChar()
                x = str.substring(startPos, pos).toDouble()
            } else if ((ch in 'a'.code..'z'.code) || ch == '√'.code) {
                while ((ch in 'a'.code..'z'.code) || ch == '√'.code) nextChar()
                val func = str.substring(startPos, pos)
                x = parseFactor()
                x = when (func) {
                    "√", "sqrt" -> sqrt(x)
                    "sin" -> sin(Math.toRadians(x))
                    "cos" -> cos(Math.toRadians(x))
                    "tan" -> tan(Math.toRadians(x))
                    "log" -> log10(x)
                    "ln" -> ln(x)
                    "abs" -> abs(x)
                    else -> throw RuntimeException("Unknown function: $func")
                }
            } else {
                throw RuntimeException("Unexpected: ${ch.toChar()}")
            }
            return x
        }

        private fun factorial(n: Int): Double {
            if (n < 0 || n > 20) throw IllegalArgumentException("Invalid factorial")
            var res = 1.0
            for (i in 2..n) res *= i
            return res
        }
    }
}
