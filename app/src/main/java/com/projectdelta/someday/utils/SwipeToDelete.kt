package com.projectdelta.someday.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.projectdelta.someday.R
import com.projectdelta.someday.adapters.RecyclerViewTasksAdapter

// https://www.youtube.com/watch?v=eEonjkmox-0
class SwipeToDelete(
		var adapter : RecyclerViewTasksAdapter
) : ItemTouchHelper.SimpleCallback( 0 ,ItemTouchHelper.LEFT ) {
	override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
		TODO("Implement On Move ?")
		return false // because dragDirs : 0 (only Left , Right)
	}

	// called when swiped
	override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
		adapter.deleteItem( viewHolder )
	}

	// for Child Draw
	private lateinit var deleteIcon : Drawable
	private var swipeBackground : ColorDrawable = ColorDrawable( Color.parseColor( "#CA0B00" ) )
	override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

		val itemView = viewHolder.itemView
		deleteIcon = ContextCompat.getDrawable( recyclerView.context ,
            R.drawable.ic_delete_white_24dp
        )!!
		val iconMargin = ( itemView.height - deleteIcon.intrinsicHeight ) / 2


		if( dX > 0) { // Swipe Right
			swipeBackground.setBounds( itemView.left , itemView.top , dX.toInt() , itemView.bottom )
			deleteIcon.setBounds(itemView.left + iconMargin , itemView.top + iconMargin ,
					itemView.left + deleteIcon.intrinsicHeight ,itemView.bottom - iconMargin )
		}else { // Swipe Left
			swipeBackground.setBounds( itemView.right + dX.toInt() , itemView.top , itemView.right , itemView.bottom )
			deleteIcon.setBounds(itemView.right - iconMargin - deleteIcon.intrinsicHeight , itemView.top + iconMargin ,
					itemView.right - iconMargin ,itemView.bottom - iconMargin )
		}
		swipeBackground.draw( c )

		if( dX > 0 )
			c.clipRect(itemView.left , itemView.top , dX.toInt() , itemView.bottom)
		else
			c.clipRect(itemView.right + dX.toInt() , itemView.top , itemView.right , itemView.bottom)

		deleteIcon.draw( c )

		super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
	}
}