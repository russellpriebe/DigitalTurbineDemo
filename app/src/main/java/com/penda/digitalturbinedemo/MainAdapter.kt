package com.penda.digitalturbinedemo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.ad_element.view.*

class MainAdapter(private val context: Context?, private val cardList: ArrayList<Ad>) : androidx.recyclerview.widget.RecyclerView.Adapter<MainAdapter.ViewHolder>(){
    var cardClicked = MutableLiveData<Ad>()


    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.ad_element, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = cardList[position].appId
        holder.rating.text = cardList[position].rating
        context?.let {
            Glide.with(context).load(cardList[position].productThumbnail).into(holder.thumb)
            holder.playButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cardList[position].clickProxyURL))
                startActivity(context, intent, null )
            }
        }
        holder.card.setOnClickListener {
            cardClicked.postValue(cardList[position])
        }
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view){
        val name: TextView = view.textView
        val rating: TextView = view.textView2
        val playButton: ImageView = view.imageView3
        val thumb: ImageView = view.imageView2
        val card: CardView = view.cardview
    }
}