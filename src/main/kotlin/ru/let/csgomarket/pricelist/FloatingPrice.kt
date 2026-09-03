package ru.let.csgomarket.pricelist

import kotlinx.serialization.Serializable

@Serializable
data class FloatingPrice(
    val lowestPrice: Double,
    val averagePrice: Double,
    val highestPrice: Double
)