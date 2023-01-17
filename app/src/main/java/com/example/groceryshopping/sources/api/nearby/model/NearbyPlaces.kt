package com.example.groceryshopping.sources.api.nearby.model

data class NearbyPlaces(val html_attributes : List<String>,
                        val status : String,
                        val next_page_tokens : String,
                        val results : List<Results>)











