package deedev.unitconverter

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.geometry.times
import androidx.compose.ui.unit.times
import kotlin.time.times

class MainActivity : AppCompatActivity() {
    private lateinit var categorySpinner: Spinner
    private lateinit var fromUnitSpinner: Spinner
    private lateinit var toUnitSpinner: Spinner
    private lateinit var inputValue: EditText
    private lateinit var convertButton: Button
    private lateinit var convertedValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // UI Elements
        categorySpinner = findViewById(R.id.categorySpinner)
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner)
        toUnitSpinner = findViewById(R.id.toUnitSpinner)
        inputValue = findViewById(R.id.inputValue)
        convertButton = findViewById(R.id.convertButton)
        convertedValue = findViewById(R.id.convertedValue)

        // Conversion Categories
        val categories = listOf("Distance", "Temperature", "Weight", "Volume", "Area", "Time")
        val categoryAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )
        categorySpinner.adapter = categoryAdapter

        // Set default units for Distance category
        setUpUnitSpinners("Distance")

        // Category Selection Listener
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = categories[position]
                setUpUnitSpinners(selectedCategory)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        // Convert Button Action
        convertButton.setOnClickListener {
            val fromUnit = fromUnitSpinner.selectedItem.toString()
            val toUnit = toUnitSpinner.selectedItem.toString()
            val input = inputValue.text.toString()
            val result = convertUnits(input, fromUnit, toUnit)

            convertedValue.text = if (result == "Invalid") {
                "Conversion not possible"
            } else {
                "Converted Value: $result $toUnit"
            }
        }
    }

    // Set up unit spinners based on the selected category
    private fun setUpUnitSpinners(category: String) {
        val units = when (category) {
            "Distance" -> listOf("Meters", "Feet", "Inches", "Centimeters", "Miles", "Kilometers", "Yards")
            "Temperature" -> listOf("Celsius", "Fahrenheit", "Kelvin")
            "Weight" -> listOf("Kilograms", "Pounds", "Grams", "Ounces")
            "Volume" -> listOf("Liters", "Gallons", "Cubic Meters")
            "Area" -> listOf("Square Meters", "Square Feet", "Square Kilometers", "Square Miles")
            "Time" -> listOf("Seconds", "Minutes", "Hours", "Days")
            else -> emptyList()
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            units
        )
        fromUnitSpinner.adapter = adapter
        toUnitSpinner.adapter = adapter
    }

    // Unit conversion logic

    private fun convertUnits(input: String, fromUnit: String, toUnit: String): String {
        return try {
            val value = input.toDouble() // Convert input string to Double
            when (fromUnit to toUnit) {
                "Meters" to "Feet" -> String.format("%.2f", value * 3.28084)
                "Feet" to "Meters" -> String.format("%.2f", value / 3.28084)
                "Kilometers" to "Miles" -> String.format("%.2f", value / 1.60934)
                "Miles" to "Kilometers" -> String.format("%.2f", value * 1.60934)
                "Inches" to "Centimeters" -> String.format("%.2f", value * 2.54)
                "Centimeters" to "Inches" -> String.format("%.2f", value / 2.54)
                "Yards" to "Meters" -> String.format("%.2f", value * 0.9144)
                "Meters" to "Yards" -> String.format("%.2f", value / 0.9144)

                "Feet" to "Inches" -> String.format("%.2f", value * 12)
                "Inches" to "Feet" -> String.format("%.2f", value / 12)
                "Kilometers" to "Meters" -> String.format("%.2f", value * 1000)
                "Meters" to "Kilometers" -> String.format("%.2f", value / 1000)
                "Miles" to "Yards" -> String.format("%.2f", value * 1760)
                "Yards" to "Miles" -> String.format("%.2f", value / 1760)
                "Kilometers" to "Yards" -> String.format("%.2f", value * 1094)
                "Yards" to "Kilometers" -> String.format("%.2f", value / 1094)
                "Miles" to "Feet" -> String.format("%.2f", value * 5280)
                "Feet" to "Miles" -> String.format("%.2f", value / 5280)
                "Inches" to "Centimeters" -> String.format("%.2f", value * 2.54)
                "Centimeters" to "Inches" -> String.format("%.2f", value / 2.54)

                "Celsius" to "Fahrenheit" -> String.format("%.2f", (value * 9 / 5) + 32)
                "Fahrenheit" to "Celsius" -> String.format("%.2f", (value - 32) * 5 / 9)
                "Kelvin" to "Celsius" -> String.format("%.2f", value - 273.15)
                "Celsius" to "Kelvin" -> String.format("%.2f", value + 273.15)
                "Kelvin" to "Fahrenheit" -> String.format("%.2f", (value - 273.15) * 9 / 5 + 32)
                "Fahrenheit" to "Kelvin" -> String.format("%.2f", (value - 32) * 5 / 9 + 273.15)


                "Celsius" to "Celsius" -> String.format("%.2f", value)
                "Kelvin" to "Kelvin" -> String.format("%.2f", value)

                "Fahrenheit" to "Fahrenheit" -> String.format("%.2f", value)
                "Kelvin" to "Fahrenheit" -> String.format("%.2f", (value - 273.15) * 9 / 5 + 32)

                "Kilograms" to "Pounds" -> String.format("%.2f", value * 2.20462)
                "Pounds" to "Kilograms" -> String.format("%.2f", value / 2.20462)
                "Grams" to "Ounces" -> String.format("%.2f", value * 0.035274)
                "Ounces" to "Grams" -> String.format("%.2f", value / 0.035274)

                "Kilograms" to "Grams" -> String.format("%.2f", value * 1000)
                "Grams" to "Kilograms" -> String.format("%.2f", value / 1000)
                "Pounds" to "Ounces" -> String.format("%.2f", value * 16)
                "Ounces" to "Pounds" -> String.format("%.2f", value / 16)


                "Kilograms" to "Kilograms" -> String.format("%.2f", value)
                "Pounds" to "Pounds" -> String.format("%.2f", value)
                "Grams" to "Grams" -> String.format("%.2f", value)
                "Ounces" to "Ounces" -> String.format("%.2f", value)


                "Liters" to "Gallons" -> String.format("%.2f", value * 0.264172)
                "Gallons" to "Liters" -> String.format("%.2f", value / 0.264172)
                "Cubic Meters" to "Liters" -> String.format("%.2f", value * 1000)
                "Liters" to "Cubic Meters" -> String.format("%.2f", value / 1000)

                "Gallons" to "Cubic Meters" -> String.format("%.2f", value * 0.00378541)
                "Cubic Meters" to "Gallons" -> String.format("%.2f", value / 0.00378541)

                "Liters" to "Liters" -> String.format("%.2f", value)
                "Gallons" to "Gallons" -> String.format("%.2f", value)
                "Cubic Meters" to "Cubic Meters" -> String.format("%.2f", value)

                "Square Meters" to "Square Feet" -> String.format("%.2f", value * 10.7639)
                "Square Feet" to "Square Meters" -> String.format("%.2f", value / 10.7639)
                "Square Kilometers" to "Square Miles" -> String.format("%.2f", value / 2.58999)
                "Square Miles" to "Square Kilometers" -> String.format("%.2f", value * 2.58999)

                "Square Meters" to "Square Kilometers" -> String.format("%.2f", value / 1_000_000)
                "Square Kilometers" to "Square Meters" -> String.format("%.2f", value * 1_000_000)
                "Square Feet" to "Square Kilometers" -> String.format("%.2f", value / 10_763_910)
                "Square Kilometers" to "Square Feet" -> String.format("%.2f", value * 10_763_910)
                "Square Miles" to "Square Feet" -> String.format("%.2f", value * 27_878_400)
                "Square Feet" to "Square Miles" -> String.format("%.2f", value / 27_878_400)

                "Square Meters" to "Square Meters" -> String.format("%.2f", value)
                "Square Feet" to "Square Feet" -> String.format("%.2f", value)
                "Square Kilometers" to "Square Kilometers" -> String.format("%.2f", value)
                "Square Miles" to "Square Miles" -> String.format("%.2f", value)

                "Seconds" to "Minutes" -> String.format("%.2f", value / 60)
                "Minutes" to "Seconds" -> String.format("%.2f", value * 60)
                "Hours" to "Minutes" -> String.format("%.2f", value * 60)
                "Minutes" to "Hours" -> String.format("%.2f", value / 60)
                "Days" to "Hours" -> String.format("%.2f", value * 24)
                "Hours" to "Days" -> String.format("%.2f", value / 24)

                "Seconds" to "Hours" -> String.format("%.2f", value / 3600)
                "Hours" to "Seconds" -> String.format("%.2f", value * 3600)
                "Minutes" to "Days" -> String.format("%.2f", value / 1440)
                "Days" to "Minutes" -> String.format("%.2f", value * 1440)
                "Seconds" to "Days" -> String.format("%.2f", value / 86400)
                "Days" to "Seconds" -> String.format("%.2f", value * 86400)

                
                "Seconds" to "Seconds" -> String.format("%.2f", value)
                "Minutes" to "Minutes" -> String.format("%.2f", value)
                "Hours" to "Hours" -> String.format("%.2f", value)
                "Days" to "Days" -> String.format("%.2f", value)

                else -> "Invalid" // Return invalid for unsupported conversions
            }
        } catch (e: Exception) {
            "Invalid" // Handle any errors gracefully
        }
    }




}
