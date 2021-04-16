package com.example.cardveiwapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_cardview.view.*
import java.util.*

class RecyclerViewCardAdapter(
    var cur_data : List<CardData>
) : RecyclerView.Adapter<RecyclerViewCardAdapter.CardViewHolder>() {

    inner class CardViewHolder( itemView : View ) : RecyclerView.ViewHolder( itemView )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val _view = LayoutInflater.from( parent.context ).inflate( R.layout.recycler_cardview , parent , false )
        return CardViewHolder( _view )
    }

    override fun getItemCount(): Int = cur_data.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.itemView.apply {
            recycler_cardview_tv_heading.text = cur_data[ position ].title
            recycler_cardview_tv_sub_heading.text = cur_data[ position ].subtitle
            recycler_cardview_tv_content.text = if(cur_data[ position ].tasks.size > 0) cur_data[ position ].tasks[ 0 ] else "NA"
            recycler_cardview_ll_main1.background = resources.obtainTypedArray( R.array.bg_colors ).getDrawable( cur_data[ position ]._color )
        }
    }
}