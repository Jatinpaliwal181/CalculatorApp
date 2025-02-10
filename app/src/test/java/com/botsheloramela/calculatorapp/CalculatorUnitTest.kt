package com.botsheloramela.calculatorapp

import org.junit.Test
import org.junit.Assert.*

class CalculatorUnitTest {
    @Test
    fun `test simple addition`() {
        val expression = "5+3"
        val expected = "8"
        assertEquals(expected, solveExpression(expression))
    }

    @Test
    fun `test simple subtraction`() {
        val expression = "10-4"
        val expected = "6"
        assertEquals(expected, solveExpression(expression))
    }

    @Test
    fun `test simple multiplication`() {
        val expression = "6*4"
        val expected = "24"
        assertEquals(expected, solveExpression(expression))
    }

    @Test
    fun `test simple division`() {
        val expression = "10/2"
        val expected = "5"
        assertEquals(expected, solveExpression(expression))
    }

    @Test
    fun `test expression with decimal result`() {
        val expression = "10/3"
        val expected = "3.3333333333333335"
        assertEquals(expected, solveExpression(expression))
    }

    @Test
    fun `test expression with percentage`() {
        val expression = "10%"
        val expected = "0.1"
        assertEquals(expected, solveExpression(expression))
    }

    @Test
    fun `test expression with percentage and modulo`() {
        val expression = "20%4"
        val expected = "0"
        assertEquals(expected, solveExpression(expression))
    }

    @Test
    fun `test expression with invalid input`() {
        val expression = "10/0"
        val expected = "Error"
        assertEquals(expected, solveExpression(expression))
    }
}