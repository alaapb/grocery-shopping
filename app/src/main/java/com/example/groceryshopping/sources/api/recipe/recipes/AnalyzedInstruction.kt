package com.example.groceryshopping.sources.api.recipe.recipes

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)