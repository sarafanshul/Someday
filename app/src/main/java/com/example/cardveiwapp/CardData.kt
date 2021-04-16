package com.example.cardveiwapp


private var color_num = 0
private val _size = 7 //

data class CardData (
    val title : String = "Sunday" ,
    val subtitle : String = "Sub Context" ,
    var tasks : MutableList<String> ,
    var _color : Int = ( color_num++ )%( _size )
)