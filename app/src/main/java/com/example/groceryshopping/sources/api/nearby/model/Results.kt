package com.example.groceryshopping.sources.api.nearby.model

data class Results(val icon : String,
                   val geometry : Geometry,
                   val photos : List<Photos>,
                   val id : String,
                   val place_id : String,
                   val price_level : Int,
                   val rating : Double,
                   val reference : String,
                   val scope : String,
                   val type : List<String>,
                   val vicinity : String,
                   val opening_hours : OpeningHours,
                   val name : String)
