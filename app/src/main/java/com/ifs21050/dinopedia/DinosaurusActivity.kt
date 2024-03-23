package com.ifs21050.dinopedia

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifs21050.dinopedia.databinding.ActivityDinosaurusBinding
import java.util.Locale

class DinosaurusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDinosaurusBinding
    private lateinit var listDinoAdapter: ListDinoAdapter

    private val dataDinosaurus = ArrayList<Dinosaurus>()
    private val filteredDinosaurus = ArrayList<Dinosaurus>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDinosaurusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataDinosaurus.addAll(getListDinosaurus())
        filteredDinosaurus.addAll(dataDinosaurus)
        listDinoAdapter = ListDinoAdapter(filteredDinosaurus) // Inisialisasi adapter
        showRecyclerList()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listDinoAdapter.searchByGroup(newText.orEmpty())
                return true
            }
        })
        return true
    }

    private fun filteredDinosaurus(query: String) {
        Log.d("DinosaurusActivity", "Query: $query")
        filteredDinosaurus.clear()
        val lowerCaseQuery = query.lowercase(Locale.ROOT)
        dataDinosaurus.forEach { dino ->
            Log.d("DinosaurusActivity", "dinoGroup: ${dino.group}, dinoNama: ${dino.name}")
            val nameMatch = dino.name.lowercase(Locale.ROOT).contains(lowerCaseQuery)
            val groupMatch = dino.group.lowercase(Locale.ROOT).contains(lowerCaseQuery)
            if (nameMatch || groupMatch) {
                filteredDinosaurus.add(dino)
            }
        }
        Log.d("DinosaurusActivity", "Filtered Dinosaurus: $filteredDinosaurus")
        listDinoAdapter.notifyDataSetChanged()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Kembali ke MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("Recycle")
    private fun getListDinosaurus(): ArrayList<Dinosaurus> {
        val dataNama = resources.getStringArray(R.array.dino_name)
        val dataGambar = resources.obtainTypedArray(R.array.dino_gambar)
        val dataDesc = resources.getStringArray(R.array.dino_desc)
        val dataGroup = resources.getStringArray(R.array.dino_group)
        val dataHab = resources.getStringArray(R.array.dino_habitat)
        val dataFood = resources.getStringArray(R.array.dino_food)
        val dataTinggi = resources.getStringArray(R.array.dino_tinggi)
        val dataBobot = resources.getStringArray(R.array.dino_bobot)
        val dataKelemahan = resources.getStringArray(R.array.dino_kelemahan)

        val listDinosaurus = ArrayList<Dinosaurus>()
        val minLength = minOf(
            dataNama.size, dataGambar.length(), dataDesc.size, dataGroup.size,
            dataHab.size, dataFood.size, dataTinggi.size, dataBobot.size, dataKelemahan.size
        )
        for (i in 0 until minLength) {
            val dinosaurus = Dinosaurus(
                dataNama[i],
                dataGambar.getResourceId(i, -1),
                dataDesc[i],
                dataGroup[i],
                dataHab[i],
                dataFood[i],
                dataTinggi[i],
                dataBobot[i],
                dataKelemahan[i]
            )
            listDinosaurus.add(dinosaurus)
        }
        dataGambar.recycle() // Memastikan TypedArray dilepaskan setelah digunakan
        return listDinosaurus
    }

    private fun showRecyclerList() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.dinoGambar.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.dinoGambar.layoutManager = LinearLayoutManager(this)
        }
        binding.dinoGambar.adapter = listDinoAdapter // Set adapter ke RecyclerView
        listDinoAdapter.setOnItemClickCallback(object : ListDinoAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Dinosaurus) {
                showSelectedDinosaurus(data)
            }
        })
    }

    private fun showSelectedDinosaurus(dinosaurus: Dinosaurus) {
        val intentWithData = Intent(this@DinosaurusActivity, DetailDinosaurus::class.java)
        intentWithData.putExtra(EXTRA_DINOSAURUS, dinosaurus)
        startActivity(intentWithData)
    }

    companion object {
        const val EXTRA_DINOSAURUS = "extra_dinosaurus"
    }
}
