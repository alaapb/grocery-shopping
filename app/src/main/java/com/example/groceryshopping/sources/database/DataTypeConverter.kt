package com.example.groceryshopping.sources.database

import androidx.room.TypeConverter

class DataTypeConverter {
    @TypeConverter
    fun stringToList(value:String) : List<String> {
        val temp : List<String> = listOf(*value.split(", ").toTypedArray())
        return temp
    }

    @TypeConverter
    fun listToString(list: List<String>) : String {
        val temp = listOf(list).joinToString()
        return temp
    }

//    @TypeConverter
//    fun nullToString(string : String) : String {
//        if(string.equals(null)) {
//            return "null"
//        }
//    }
//
//    @TypeConverter
//    fun recipeToString(recipe : ExtendedRecipe) : String {
//        return Gson().toJson(recipe)
//    }
//
//    @TypeConverter
//    fun stringToRecipe(recipe : String) : ExtendedRecipe {
//        return Gson().fromJson(recipe, ExtendedRecipe::class.java)
//    }
}