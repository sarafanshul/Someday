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
) : ItemTouchHelper.SimpleCallback( 0 ,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT ) {
	override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
		TODO("Implement On Move ?")
		return false // because dragDirs : 0 (only Left , Right)
	}

	// called when swiped
	override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
		if( direction == 8 ) // for swipe right to add last
			adapter.moveToLast( viewHolder )
		else // for swipe left to delete
			adapter.deleteItem( viewHolder )
	}

	// for Child Draw
	private lateinit var deleteIcon : Drawable
	private lateinit var reIcon : Drawable
	private var swipeBackgroundDelete : ColorDrawable = ColorDrawable( Color.parseColor( "#CA0B00" ) )
	private var swipeBackgroundRe : ColorDrawable = ColorDrawable( Color.parseColor( "#1e88e5" ) )
	override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

		val itemView = viewHolder.itemView
		deleteIcon = ContextCompat.getDrawable( recyclerView.context ,
            R.drawable.ic_delete_white_24dp
        )!!

		reIcon = ContextCompat.getDrawable( recyclerView.context ,
				R.drawable.ic_baseline_schedule_24
		)!!

		val iconMargin_delete = ( itemView.height - deleteIcon.intrinsicHeight ) / 2
		val iconMargin_re = ( itemView.height - reIcon.intrinsicHeight ) / 2

		if( dX > 0) { // Swipe Right
			swipeBackgroundRe.setBounds( itemView.left , itemView.top , dX.toInt() , itemView.bottom )
			reIcon.setBounds(itemView.left + iconMargin_re , itemView.top + iconMargin_re ,
					itemView.left + iconMargin_re + deleteIcon.intrinsicHeight ,itemView.bottom - iconMargin_re )
			swipeBackgroundRe.draw( c )
		}else { // Swipe Left
			swipeBackgroundDelete.setBounds( itemView.right + dX.toInt() , itemView.top , itemView.right , itemView.bottom )
			deleteIcon.setBounds(itemView.right - iconMargin_delete - deleteIcon.intrinsicHeight , itemView.top + iconMargin_delete ,
					itemView.right - iconMargin_delete ,itemView.bottom - iconMargin_delete )
			swipeBackgroundDelete.draw( c )

		}

		if( dX > 0 ){
			c.clipRect(itemView.left , itemView.top , dX.toInt() , itemView.bottom)
			reIcon.draw( c )
		}
		else{
			c.clipRect(itemView.right + dX.toInt() , itemView.top , itemView.right , itemView.bottom)
			deleteIcon.draw( c )
		}

		super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
	} // https://www.youtube.com/watch?v=eEonjkmox-0
}