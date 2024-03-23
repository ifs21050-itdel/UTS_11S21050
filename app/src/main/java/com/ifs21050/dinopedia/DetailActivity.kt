package com.ifs21050.dinopedia

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ifs21050.dinopedia.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var family: Family? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        family = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_FAMILY,
                Family::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_FAMILY)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (family != null) {
            supportActionBar?.title = "Family ${family!!.name}"
            loadData(family!!)
        } else {
            finish()
        }
    }
    private fun loadData(family: Family) {
        binding.dinoIcon.setImageResource(family.icon)
        binding.dinoName.text = family.name
        binding.dinoDesc.text = family.description
        binding.dinoCharacteristic.text = family.characteristic
        binding.dinoHabitat.text = family.habitat
        binding.dinoBehavior.text = family.behavior
        binding.dinoClassification.text = family.classification.toString()
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
        const val EXTRA_FAMILY = "extra_family"
    }
}

