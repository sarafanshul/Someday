package com.example.cardveiwapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.io.Serializable


private var color_num = 0
private val _size = 7 //

@Entity
data class CardData (
    @PrimaryKey
    val title : String ,

    var subtitle : String = "Sub Context" ,

    @TypeConverters( Converters::class )
    var tasks : MutableList<String> = mutableListOf() ,

    var _color : Int = ( color_num++ )%( _size )
) : Serializable