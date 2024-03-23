package com.ifs21050.dinopedia

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifs21050.dinopedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dataFamily = ArrayList<Family>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.dinoIcon.setHasFixedSize(false)
        dataFamily.addAll(getListFamily())
        showRecyclerList()

        binding.showAllButton.setOnClickListener {
            showDinosaurs()
        }
    }

    private fun showDinosaurs() {
        val intent = Intent(this, DinosaurusActivity::class.java)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("Recycle")
    private fun getListFamily(): ArrayList<Family> {
        val dataName = resources.getStringArray(R.array.info_dino_name)
        val dataIcon = resources.obtainTypedArray(R.array.info_dino_icon)
        val dataDescription = resources.getStringArray(R.array.info_dino_description)
        val dataCharacteristic = resources.getStringArray(R.array.info_dino_characteristic)
        val dataHabitat = resources.getStringArray(R.array.info_dino_habitat)
        val dataBehavior = resources.getStringArray(R.array.info_dino_behavior)
        val dataClassification = resources.getStringArray(R.array.info_dino_classification)

        val listFamily = ArrayList<Family>()
        val minLength = minOf(
            dataName.size, dataIcon.length(), dataDescription.size, dataCharacteristic.size,
            dataHabitat.size, dataBehavior.size, dataClassification.size
        )
        for (i in 0 until minLength) {
            val family = Family(
                dataName[i],
                dataIcon.getResourceId(i, -1),
                dataDescription[i],
                dataCharacteristic[i],
                dataHabitat[i],
                dataBehavior[i],
                dataClassification[i]
            )
            listFamily.add(family)
        }
        dataIcon.recycle() // Memastikan TypedArray dilepaskan setelah digunakan
        return listFamily
    }

    private fun showRecyclerList() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.dinoIcon.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.dinoIcon.layoutManager = LinearLayoutManager(this)
        }
        val listFamilyAdapter = ListFamilyAdapter(dataFamily)
        binding.dinoIcon.adapter = listFamilyAdapter
        listFamilyAdapter.setOnItemClickCallback(object : ListFamilyAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Family) {
                showSelectedFamily(data)
            }
        })
    }

    private fun showSelectedFamily(family: Family) {
        val intentWithData = Intent(this@MainActivity, DetailActivity::class.java)
        intentWithData.putExtra(DetailActivity.EXTRA_FAMILY, family)
        startActivity(intentWithData)
    }

}
