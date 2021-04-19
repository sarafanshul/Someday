package com.example.cardveiwapp.adapters

import android.icu.text.CaseMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardveiwapp.R
import com.example.cardveiwapp.data.CardData
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detailed_info_recycler_view.view.*
import kotlin.properties.Delegates

class RecyclerViewTasksAdapter( ) : RecyclerView.Adapter<RecyclerViewTasksAdapter.TasksViewHolder>( ) {

    var cardData: CardData = CardData("" ,"" , mutableListOf() ,0)

    inner class TasksViewHolder( itemView : View) : RecyclerView.ViewHolder( itemView )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val _view = LayoutInflater.from( parent.context ).inflate(R.layout.activity_detailed_info_recycler_view, parent , false )
        return TasksViewHolder( _view )
    }

    override fun getItemCount(): Int = cardData.tasks.size

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.itemView.apply {
            activity_detailed_info_recycler_view_tw_main.text = cardData.tasks[ position ]
        }
    }

    fun setData( cardData: CardData ){
        this.cardData = cardData
        notifyDataSetChanged()
    }

    // https://www.youtube.com/watch?v=eEonjkmox-0
    private var removed_pos = 0
    private var removed_item : String = ""
    fun deleteItem( viewHolder: RecyclerView.ViewHolder) : Unit {
        removed_pos = viewHolder.adapterPosition
        removed_item = cardData.tasks[ removed_pos ]
        cardData.tasks.removeAt( removed_pos )
        notifyItemRemoved( removed_pos )
        Snackbar.make( viewHolder.itemView , "$removed_item Deleted." ,Snackbar.LENGTH_LONG ).apply {
            setAction("UNDO") {
                cardData.tasks.add(removed_pos, removed_item)
                notifyItemInserted(removed_pos)
            }
            anchorView = viewHolder.itemView.rootView.findViewById(R.id.activity_detailed_info_efab_add) // for on top of efab
        }.show()
    }
}