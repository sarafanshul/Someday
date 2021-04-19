package com.example.cardveiwapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardveiwapp.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detailed_info_recycler_view.view.*

class RecyclerViewTasksAdapter(
    var tasks : MutableList<String>
) : RecyclerView.Adapter<RecyclerViewTasksAdapter.TasksViewHolder>( ) {

    inner class TasksViewHolder( itemView : View) : RecyclerView.ViewHolder( itemView )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val _view = LayoutInflater.from( parent.context ).inflate(R.layout.activity_detailed_info_recycler_view, parent , false )
        return TasksViewHolder( _view )
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.itemView.apply {
            activity_detailed_info_recycler_view_tw_main.text = tasks[ position ]
        }
    }

    // https://www.youtube.com/watch?v=eEonjkmox-0
    private var removed_pos = 0
    private var removed_item : String = ""
    fun deleteItem( viewHolder: RecyclerView.ViewHolder) : Unit {
        removed_pos = viewHolder.adapterPosition
        removed_item = tasks[ removed_pos ]
        tasks.removeAt( removed_pos )
        notifyItemRemoved( removed_pos )
        Snackbar.make( viewHolder.itemView , "$removed_item Deleted." ,Snackbar.LENGTH_LONG ).apply {
            setAction("UNDO") {
                tasks.add(removed_pos, removed_item)
                notifyItemInserted(removed_pos)
            }
            anchorView = viewHolder.itemView.rootView.findViewById(R.id.activity_detailed_info_efab_add) // for on top of efab
        }.show()
    }
}