package com.example.groceryshopping.sources.api.nearby.model

import com.example.groceryshopping.sources.api.nearby.model.Coordinates

data class Geometry(val location : Coordinates,
                    val viewport : ViewPort)