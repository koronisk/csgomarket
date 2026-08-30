package ru.let.csgomarket.pricelist

class PriceList<T>(private val prices: Map<String, T>) {
    fun getPrice(itemId: String): T? = prices[itemId]
    fun count(): Int = prices.count()
}