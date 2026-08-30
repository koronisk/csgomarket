package ru.let.csgomarket.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.let.csgomarket.pricelist.PriceList

@Serializable
internal data class BestOffersResponse(
    val success: Boolean,
    val time: Long,
    val currency: String,
    val items: Set<BestOffersItem>
)

@Serializable
internal data class BestOffersItem(
    @SerialName("market_hash_name") val marketHashName: String,
    val volume: Int,
    val price: Double
)