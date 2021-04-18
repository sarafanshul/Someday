package com.example.cardveiwapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_cardview.view.*
import java.util.*

class RecyclerViewCardAdapter( ) : RecyclerView.Adapter<RecyclerViewCardAdapter.CardViewHolder>() {
    var cur_data : List<CardData> = emptyList<CardData>()

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
            recycler_cardview_tv_content.text = if(cur_data[ position ].tasks.size > 0) cur_data[ position ].tasks[ 0 ] else "Every Thing Done !!"
            recycler_cardview_ll_main1.background = resources.obtainTypedArray( R.array.bg_colors ).getDrawable( cur_data[ position ]._color )

            // Highlight Current Day Because sort dose NOT work
            if( Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == cur_data[position]._color + 1 ){
                recycler_cardview_cv_1.strokeWidth = resources.getDimension(R.dimen.card_stroke_width).toInt()
                recycler_cardview_cv_1.strokeColor = resources.getColor(R.color.neon_green)
            }
        }
    }

    fun setData( new_data : List<CardData> ){
        this.cur_data = new_data
        notifyDataSetChanged()
    }

     fun setTodayOnTop(FROM : String = "Sunday" ){
        var temp = this.cur_data
        temp.sortedWith( compareBy( { it._color } ) )
         Log.d("Curr Data" , temp.toString())
        val dateInfo = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        Collections.rotate( temp , -dateInfo + 1)
        this.cur_data = temp
    }

}