package com.botsheloramela.calculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.botsheloramela.calculatorapp.ui.theme.CalculatorAppTheme
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var expression by remember { mutableStateOf("") }

    var result by remember { mutableStateOf("") }

    val buttonRows = listOf(
        listOf("AC", "⌫", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "+"),
        listOf("1", "2", "3", "-"),
        listOf("%", "0", ".", "=")
    )

    val functionButtons = setOf("AC", "⌫", "÷", "×", "+", "-", "%", "=")

    val defaultOnClick: (String) -> Unit = { expression += it }

    val onClickMap = mapOf(
        "AC" to { _: String -> expression = ""; result = "" },
        "⌫" to { _: String -> expression = delCharacter(expression) },
        "=" to { _: String -> result = solveExpression(expression)}
    )

    Column (modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {

        Column (modifier = Modifier.weight(1f).padding(16.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth().testTag("expression_input"),
                text = expression,
                maxLines = 3,
                style = TextStyle(fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End)
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = result,
                style = TextStyle(fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End)
            )
        }

        buttonRows.forEach { rowButtons ->
            Row(modifier = modifier.fillMaxWidth()) {
                rowButtons.forEach { buttonText ->
                    val weight = if (buttonText.length == 2) 2f else 1f
                    CalculatorButton(
                        text = buttonText,
                        isFunction = buttonText in functionButtons,
                        modifier = modifier.weight(weight),
                        onClick = onClickMap[buttonText] ?: defaultOnClick
                    )
                }
            }
        }
    }
}

fun solveExpression(expression: String): String {
    val modifiedExpression = expression
        .replace("×", "*")
        .replace("÷", "/")

    val result = when {
        expression.endsWith('%') -> modifiedExpression.substringBefore('%').toDouble() / 100
        '%' in modifiedExpression -> {
            val parts = modifiedExpression.split('%')
            parts[0].toDouble() % parts[1].toDouble()
        }
        else -> Expression(modifiedExpression).calculate()
    }

    // Check if the result is an integer or a decimal number and return the result accordingly
    return if (result % 1 == 0.0) result.toInt().toString()
        else result.toString().takeIf { it != "NaN" } ?: "Error"
}

fun delCharacter(expression: String): String = if (expression.isNotEmpty()) expression.dropLast(1) else expression


@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    text: String = "0",
    isFunction: Boolean = false,
    onClick: (String) -> Unit = {}) {

    val tag = "button_$text"
    
    val containerColors = when {
        isFunction && (text == "AC" || text == "=") -> MaterialTheme.colorScheme.primary
        isFunction -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.tertiary
    }

    Button(modifier = modifier
        .size(72.dp).padding(4.dp).clip(CircleShape)
        .testTag(tag),
        onClick = { onClick(text) },
        colors = ButtonDefaults.buttonColors(containerColor = containerColors)
        ) {
        Text(text = text, style = TextStyle(fontSize = 24.sp))
    }
}

@Preview
@Composable
fun CalculatorButtonPreview() {
    CalculatorButton()
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen()
}