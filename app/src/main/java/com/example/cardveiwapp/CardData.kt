package com.example.cardveiwapp

import android.graphics.drawable.Drawable
import kotlin.random.Random

data class CardData (
    val title : String = "Sunday" ,
    val subtitle : String = "Sub Context" ,
    var tasks : MutableList<String> ,
    var _color : Int = Random.nextInt(0 ,7)
)