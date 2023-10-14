package com.example.agecalculator

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.agecalculator.ui.theme.AgeCalculatorTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView?  = null
    private var tvAgeInYears : TextView? = null
    private var tvAgeInHrs : TextView? = null
    private var tvAgeInDays : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvAgeInYears = findViewById(R.id.tvAgeInYears)
        tvAgeInDays = findViewById(R.id.tvAgeInDays)
        tvAgeInHrs = findViewById(R.id.tvAgeInHrs)
        val btnDatePicker : Button = findViewById(R.id.btnDataPicker)
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }

    }
    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener{_, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    val selectedDateInMinutes = theDate.time/60000
                    val selectedDateInHrs = selectedDateInMinutes/60
                    val selectedDateInDays = selectedDateInHrs/24
                    val selectedDateInYears = theDate.getYear()
                    val currentDateMin = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDateMin?.let {
                        val currentDateInMinutes = currentDateMin.time/60000
                        val currentDateInHrs = currentDateInMinutes/60
                        val currentDateInDays = currentDateInHrs/24
                        val currYear = myCalendar.get(Calendar.YEAR)
                        val yearsPassedTillNow = currYear - 1900
                        val differenceInYears = yearsPassedTillNow - selectedDateInYears
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        val differenceInHrs = currentDateInHrs - selectedDateInHrs
                        val differenceInDays = currentDateInDays - selectedDateInDays

                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                        tvAgeInYears?.text = differenceInYears.toString()
                        tvAgeInDays?.text = differenceInDays.toString()
                        tvAgeInHrs?.text = differenceInHrs.toString()
                    }

                }



            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}

