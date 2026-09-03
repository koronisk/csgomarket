package ru.let.csgomarket.format

import ru.let.csgomarket.pricelist.FloatingPrice
import ru.let.csgomarket.pricelist.PriceList
import ru.let.csgomarket.response.AllOffersFullResponse
import ru.let.csgomarket.response.BestOffersResponse
import kotlin.math.roundToInt

/** A price above anchor * OUTLIER_MULTIPLIER is treated as an inflated/junk listing. */
private const val OUTLIER_MULTIPLIER = 3.0

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
        val sortedPrices = group.map { it.price!! }.sorted()
        if (sortedPrices.isEmpty()) return@forEach

        val lowestPrice = sortedPrices.first()
        val highestPrice = sortedPrices.last()

        val median = sortedPrices.median()
        val withoutLowOutlier = if (sortedPrices.size >= 3 && lowestPrice * OUTLIER_MULTIPLIER < median) {
            sortedPrices.drop(1)
        } else {
            sortedPrices
        }

        val anchor = withoutLowOutlier.first()
        val cleanPrices = withoutLowOutlier
            .filter { it <= anchor * OUTLIER_MULTIPLIER }
            .ifEmpty { withoutLowOutlier }

        val averagePrice = cleanPrices.average()

        val price = FloatingPrice(
            lowestPrice = ((lowestPrice * 100.0).roundToInt() / 100.0) / 100,
            averagePrice = ((averagePrice * 100.0).roundToInt() / 100.0) / 100,
            highestPrice = ((highestPrice * 100.0).roundToInt() / 100.0) / 100
        )

        prices[hashName] = price
    }

    return PriceList(prices)
}

private fun List<Double>.median(): Double {
    val mid = size / 2
    return if (size % 2 == 0) (this[mid - 1] + this[mid]) / 2.0 else this[mid]
}