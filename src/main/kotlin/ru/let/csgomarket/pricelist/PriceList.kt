package ru.let.csgomarket.pricelist

import kotlinx.serialization.Serializable

@Serializable
class PriceList<T>(private val prices: Map<String, T>) {
    fun getPrice(itemId: String): T? = prices[itemId]
    fun count(): Int = prices.count()
}