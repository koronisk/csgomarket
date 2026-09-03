package ru.let.csgomarket.request

import io.ktor.client.call.body
import ru.let.csgomarket.MarketClient
import ru.let.csgomarket.pricelist.FloatingPrice
import ru.let.csgomarket.pricelist.PriceList

internal suspend fun MarketClient.allOffersMock(): PriceList<FloatingPrice> =
    get("").body<PriceList<FloatingPrice>>()