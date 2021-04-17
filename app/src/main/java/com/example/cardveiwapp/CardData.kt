package com.example.cardveiwapp

import java.io.Serializable


private var color_num = 0
private val _size = 7 //

data class CardData (
    var title : String = "Sunday" ,
    var subtitle : String = "Sub Context" ,
    var tasks : MutableList<String> = mutableListOf() ,
    var _color : Int = ( color_num++ )%( _size )
) : Serializable