package com.penda.digitalturbinedemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    val context: Context = this
    var adList: ArrayList<Ad>? = ArrayList<Ad>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()

    }

    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.fetchCampaigns(resources.getString(R.string.adsurl))
        viewModel.returnBundle.observe(this, Observer {
            it?.let { item ->
                adList = item
                adList?.let{
                    setupRecyclerView()
                }
            }?:run{
                Toast.makeText(context, "No Menu Items Found!", Toast.LENGTH_LONG).show()
            }

        })
        viewModel.errorBundle.observe(this, Observer {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun setupRecyclerView(){
        recyclerview.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val adapter = MainAdapter(context, adList!!)
        recyclerview.adapter = adapter
        adapter.cardClicked.observe(this, Observer {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("ad", it)
            startActivity(intent)
        })
    }
}
