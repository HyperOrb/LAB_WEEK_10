package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.database.TotalObject
import com.example.lab_week_10.viewmodels.TotalViewModel
import java.util.Date

class MainActivity : AppCompatActivity() {
    // Inisialisasi ViewModel secara lazy
    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    // Tambahkan properti
    private val db by lazy { prepareDatabase() }

    companion object {
        const val ID: Long = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initializeValueFromDatabase() // Panggil sebelum prepareViewModel
        prepareViewModel()
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text = 
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel() {
        viewModel.total.observe(this) { total ->
            updateText(total)
        }

        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }

    // Fungsi setup DB (Note: allowMainThreadQueries HANYA untuk latihan ini)
    private fun prepareDatabase(): TotalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java,
            "total-database"
        ).allowMainThreadQueries().build()
    }

    private fun initializeValueFromDatabase() {
        val totalList = db.totalDao().getTotal(ID)
        if (totalList.isEmpty()) {
            db.totalDao().insert(Total(id = ID, total = TotalObject(value = 0, date = Date().toString())))
        } else {
            val storedTotalObject = totalList.first().total
            viewModel.setTotal(storedTotalObject.value)
            Toast.makeText(this, "Last updated: ${storedTotalObject.date}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        // Simpan nilai terakhir ke DB
        val currentTotal = viewModel.total.value ?: 0
        val currentDate = Date().toString()
        val totalObj = TotalObject(currentTotal, currentDate)

        db.totalDao().update(Total(ID, totalObj))
    }
}