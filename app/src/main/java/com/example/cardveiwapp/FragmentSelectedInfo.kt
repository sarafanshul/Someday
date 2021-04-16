package com.example.cardveiwapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.fragment_selected_info.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentSelectedInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentSelectedInfo( ) : Fragment() {
	private var param1: String? = null
	private var param2: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			param1 = it.getString(ARG_PARAM1)
			param2 = it.getString(ARG_PARAM2)
		}
	}

	@SuppressLint("ResourceType")
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View? {
		// Inflate the layout for this fragment
		val _viewOnCreate =  inflater.inflate(R.layout.fragment_selected_info, container, false)
		val tw = _viewOnCreate.findViewById<TextView>( R.id.fragment_selected_info_tw_1 )
		val fl_cosntraintCiew = _viewOnCreate.findViewById<ConstraintLayout>( R.id.fragment_selected_info_cl_main )

		fl_cosntraintCiew.apply{
			background = resources.obtainTypedArray( R.array.bg_colors ).getDrawable( 7 )
		}

		tw.apply {
			text = "Inside Recycler View Fragment"
			setTextColor( resources.getColor(R.color.black) )
		}
//		fragment_selected_info_tw_1.text = "Inside Fragment View"

		return _viewOnCreate
	}

}