package com.ifs21050.dinopedia

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // Tampilkan tombol kembali di toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Tangani kejadian saat tombol di toolbar diklik
        when (item.itemId) {
            android.R.id.home -> {
                // Panggil metode finish() untuk menutup aktivitas AboutActivity
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
