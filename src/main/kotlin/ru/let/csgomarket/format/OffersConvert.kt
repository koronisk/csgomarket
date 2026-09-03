package ru.let.csgomarket.format

import ru.let.csgomarket.pricelist.FloatingPrice
import ru.let.csgomarket.pricelist.PriceList
import ru.let.csgomarket.response.AllOffersFullResponse
import ru.let.csgomarket.response.BestOffersResponse
import kotlin.math.roundToInt

internal fun BestOffersResponse.priceList(): PriceList<Double> {
    val prices = items.associate { it.marketHashName to it.price }
    return PriceList(prices)
}

internal fun AllOffersFullResponse.priceList(): PriceList<FloatingPrice> {
    val prices = mutableMapOf<String, FloatingPrice>()
    val grouped = items
        .filter { it.marketHashName != null && it.price != null }
        .groupBy { it.marketHashName!! }

    grouped.forEach { (hashName, group) ->
        val sorted = group.sortedBy { it.price }
        if (sorted.isEmpty()) return@forEach

        val lowestPrice = sorted.first().price!!
        val highestPrice = sorted.last().price!!

        val q1 = sorted[(sorted.size * 0.25).toInt()].price!!
        val q3 = sorted[(sorted.size * 0.75).toInt()].price!!
        val iqr = q3 - q1

        val lowerBound = q1 - 1.5 * iqr
        val upperBound = q3 + 1.5 * iqr

        val cleanPrices = sorted.filter { it.price!! in lowerBound..upperBound }
        val averagePrice = cleanPrices.map { it.price!!.toInt() }.average()
        
        val price = FloatingPrice(
            lowestPrice = ((lowestPrice * 100.0).roundToInt() / 100.0) / 100,
            averagePrice = ((averagePrice * 100.0).roundToInt() / 100.0) / 100,
            highestPrice = ((highestPrice * 100.0).roundToInt() / 100.0) / 100
        )
        
        prices[hashName] = price
    }
    
    return PriceList(prices)
}