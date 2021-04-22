package com.projectdelta.someday.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.projectdelta.someday.R
import com.projectdelta.someday.data.CardData
import kotlinx.android.synthetic.main.recycler_cardview.view.*
import java.util.*

class RecyclerViewCardAdapter( ) : RecyclerView.Adapter<RecyclerViewCardAdapter.CardViewHolder>() {
    var cur_data : List<CardData> = emptyList<CardData>()
    var isSelected = Array(100){ false }

    inner class CardViewHolder( itemView : View ) : RecyclerView.ViewHolder( itemView )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val _view = LayoutInflater.from( parent.context ).inflate(R.layout.recycler_cardview, parent , false )
        return CardViewHolder( _view )
    }

    override fun getItemCount(): Int = cur_data.size


    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        holder.itemView.apply {
            recycler_cardview_tv_heading.text = cur_data[ position ].title
            recycler_cardview_tv_sub_heading.text = cur_data[ position ].subtitle
            recycler_cardview_ll_main1.background = resources.obtainTypedArray(R.array.bg_colors).getDrawable( cur_data[ position ]._color )
        }

        if(! isSelected[position]){
            holder.itemView.apply {
                recycler_cardview_tv_content.isSingleLine = true
                recycler_cardview_tv_content.text = if (cur_data[position].tasks.size > 0) cur_data[position].tasks[0] else "Every Thing Done !!"
            }
        }
    }

    fun setData( new_data : List<CardData> ){
        this.cur_data = new_data
        notifyDataSetChanged()
    }

}