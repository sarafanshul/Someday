package com.example.cardveiwapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_detailed_info_recycler_view.view.*

class RecyclerViewTasksAdapter(
    var tasks : MutableList<String>
) : RecyclerView.Adapter<RecyclerViewTasksAdapter.TasksViewHolder>( ) {

    inner class TasksViewHolder( itemView : View) : RecyclerView.ViewHolder( itemView )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val _view = LayoutInflater.from( parent.context ).inflate( R.layout.activity_detailed_info_recycler_view , parent , false )
        return TasksViewHolder( _view )
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.itemView.apply {
            activity_detailed_info_recycler_view_tw_main.text = tasks[ position ]
        }
    }
}