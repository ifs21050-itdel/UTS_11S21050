package com.ifs21050.dinopedia

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ifs21050.dinopedia.databinding.ActivityDetailDinosaurusBinding

class DetailDinosaurus : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDinosaurusBinding
    private var dinosaurus: Dinosaurus? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDinosaurusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dinosaurus = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DINOSAURUS,
                Dinosaurus::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DINOSAURUS)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (dinosaurus != null) {
            supportActionBar?.title = "Dinosaurus ${dinosaurus!!.name}"
            loadData(dinosaurus!!)
        } else {
            finish()
        }
    }
    private fun loadData(dinosaurus: Dinosaurus) {
        binding.dinoGambar.setImageResource(dinosaurus.gambar)
        binding.dinoNama.text = dinosaurus.name
        binding.dinoDesk.text = dinosaurus.desc
        binding.dinoGroup.text = dinosaurus.group
        binding.dinoHab.text = dinosaurus.habitat
        binding.dinoFood.text = dinosaurus.food
        binding.dinoTinggi.text = dinosaurus.tinggi
        binding.dinoBobot.text = dinosaurus.bobot
        binding.dinoKelemahan.text = dinosaurus.kelemahan
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    companion object {
        const val EXTRA_DINOSAURUS = "extra_dinosaurus"
    }
}

