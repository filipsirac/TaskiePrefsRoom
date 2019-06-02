package hr.ferit.filipsirac.taskie.model

import androidx.room.TypeConverter
import hr.ferit.brunozoric.taskie.R
import hr.ferit.brunozoric.taskie.model.Priority

class Converter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromPriority(value: Priority): Int {
            return value.getColor()
        }

        @TypeConverter
        @JvmStatic
        fun toPriority(value: Int): Priority{
            return if(R.color.colorLow == value) Priority.LOW
            else if(R.color.colorMedium == value) Priority.MEDIUM
            else if(R.color.colorHigh == value) Priority.HIGH
            else Priority.LOW
        }


    }
}