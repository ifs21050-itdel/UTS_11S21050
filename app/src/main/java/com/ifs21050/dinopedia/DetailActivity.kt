package com.ifs21050.dinopedia

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

        family = intent.getParcelableExtra(EXTRA_FAMILY)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (family != null) {
            supportActionBar?.title = "Kelompok ${family!!.name}"
            loadData(family!!)
        } else {
            finish()
        }
    }

    private fun loadData(family: Family) {
        binding.dinoIconElla.setImageResource(family.icon)
        binding.dinoFamilyElla.text = family.description
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_FAMILY = "extra_family"
    }
}
