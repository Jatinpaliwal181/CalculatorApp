package com.botsheloramela.calculatorapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verifyButtonsAndInput() {
        // Verify the presence of specific buttons and input fields on the calculator screen

        // Verify the presence of the AC button
        composeTestRule.onNodeWithText("AC").assertIsDisplayed()

        // Verify the presence of numeric buttons
        composeTestRule.onNodeWithText("0").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("2").assertIsDisplayed()
        composeTestRule.onNodeWithText("3").assertIsDisplayed()
        composeTestRule.onNodeWithText("4").assertIsDisplayed()
        composeTestRule.onNodeWithText("5").assertIsDisplayed()
        composeTestRule.onNodeWithText("6").assertIsDisplayed()
        composeTestRule.onNodeWithText("7").assertIsDisplayed()
        composeTestRule.onNodeWithText("8").assertIsDisplayed()
        composeTestRule.onNodeWithText("9").assertIsDisplayed()

        // Verify the presence of operator buttons
        composeTestRule.onNodeWithText("÷").assertIsDisplayed()
        composeTestRule.onNodeWithText("×").assertIsDisplayed()
        composeTestRule.onNodeWithText("+").assertIsDisplayed()
        composeTestRule.onNodeWithText("-").assertIsDisplayed()
        composeTestRule.onNodeWithText("%").assertIsDisplayed()
        composeTestRule.onNodeWithText(".").assertIsDisplayed()
        composeTestRule.onNodeWithText("=").assertIsDisplayed()

        // Verify the presence of the input field
        composeTestRule.onNodeWithTag("expression_input").assertIsDisplayed()
    }

    @Test
    fun verifyInputAndCalculation() {
        // Verify input and calculation functionality of the calculator

        // Input a simple expression
        composeTestRule.onNodeWithTag("button_1").performClick()
        composeTestRule.onNodeWithTag("button_2").performClick()
        composeTestRule.onNodeWithTag("button_+").performClick()
        composeTestRule.onNodeWithTag("button_3").performClick()
        composeTestRule.onNodeWithTag("button_4").performClick()
        composeTestRule.onNodeWithTag("button_=").performClick()

        // Verify the result is displayed correctly
        composeTestRule.onNodeWithText("46").assertIsDisplayed()
    }

    @Test
    fun verifyClearButton() {
        // Verify clear (AC) button functionality

        // Input an expression
        composeTestRule.onNodeWithTag("button_1").performClick()
        composeTestRule.onNodeWithTag("button_2").performClick()

        // Click on AC button
        composeTestRule.onNodeWithTag("button_AC").performClick()

        // Verify the input field is cleared
        composeTestRule.onNodeWithTag("expression_input").assertTextEquals("")
    }

    @Test
    fun verifyDeleteButton() {
        // Verify delete (⌫) button functionality

        // Input an expression
        composeTestRule.onNodeWithTag("button_1").performClick()
        composeTestRule.onNodeWithTag("button_2").performClick()
        composeTestRule.onNodeWithTag("button_⌫").performClick()

        // Verify the last digit is deleted
        composeTestRule.onNodeWithTag("expression_input").assertTextEquals("1")
    }

    @Test
    fun verifyDivisionByZero() {
        // Verify handling of division by zero scenario

        // Input an expression with division by zero
        composeTestRule.onNodeWithTag("button_1").performClick()
        composeTestRule.onNodeWithTag("button_÷").performClick()
        composeTestRule.onNodeWithTag("button_0").performClick()
        composeTestRule.onNodeWithTag("button_=").performClick()

        // Verify "Error" is displayed
        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }

    @Test
    fun verifyPercentageCalculation() {
        // Verify percentage (%) calculation

        // Input an expression with percentage
        composeTestRule.onNodeWithTag("button_2").performClick()
        composeTestRule.onNodeWithTag("button_5").performClick()
        composeTestRule.onNodeWithTag("button_%").performClick()
        composeTestRule.onNodeWithTag("button_=").performClick()

        // Verify the percentage value is calculated correctly
        composeTestRule.onNodeWithText("0.25").assertIsDisplayed()
    }
}