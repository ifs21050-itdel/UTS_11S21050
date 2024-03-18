package com.ifs21050.dinopedia

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifs21050.dinopedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val listFamily = ArrayList<Family>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.dinoImg.setHasFixedSize(true)
        listFamily.addAll(getListFamily())
        showRecyclerList()
    }

    private fun getListFamily(): List<Family> {
        val dataFamilyName = resources.getStringArray(R.array.info_dino_name)
        val dataFamilyIcon = resources.obtainTypedArray(R.array.info_dino_icon)
        val dataFamilyDescription = resources.getStringArray(R.array.info_dino_family)

        val listFamily = mutableListOf<Family>()

        // Cek panjang array agar sesuai
        val minLength = minOf(dataFamilyName.size, dataFamilyIcon.length(), dataFamilyDescription.size)

        for (i in 0 until minLength) {
            val family = Family(dataFamilyName[i], dataFamilyIcon.getResourceId(i, -1), dataFamilyDescription[i])
            listFamily.add(family)
        }
        dataFamilyIcon.recycle() // Recycle the typed array to avoid memory leaks
        return listFamily
    }

    private fun showRecyclerList() {
        val layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(this, 2)
        } else {
            LinearLayoutManager(this)
        }
        binding.dinoImg.layoutManager = layoutManager
        val listFamilyAdapter = ListFamilyAdapter(listFamily)
        binding.dinoImg.adapter = listFamilyAdapter
        listFamilyAdapter.setOnItemClickCallback(object : ListFamilyAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Family) {
                showSelectedFamily(data)
            }
        })
    }

    private fun showSelectedFamily(family: Family) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_FAMILY, family)
        startActivity(intent)
    }
}
