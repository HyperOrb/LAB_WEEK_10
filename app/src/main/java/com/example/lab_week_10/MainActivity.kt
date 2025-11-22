package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.viewmodels.TotalViewModel

class MainActivity : AppCompatActivity() {
    // Inisialisasi ViewModel secara lazy
    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        prepareViewModel()
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text = 
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel() {
        // Observe manual (akan diganti LiveData nanti)
        // Kita set text awal dari data yg tersimpan di ViewModel
        updateText(viewModel.total)

        findViewById<Button>(R.id.button_increment).setOnClickListener {
            val newTotal = viewModel.incrementTotal()
            updateText(newTotal)
        }
    }
}