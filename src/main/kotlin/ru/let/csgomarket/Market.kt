package ru.let.csgomarket

import ru.let.csgomarket.common.Currency
import ru.let.csgomarket.format.priceList
import ru.let.csgomarket.pricelist.FloatingPrice
import ru.let.csgomarket.pricelist.PriceList
import ru.let.csgomarket.request.allOffersFull
import ru.let.csgomarket.request.allOffersMock
import ru.let.csgomarket.request.bestOffers

class Market(
    private val host: String = "https://market.csgo.com/"
) {
    private val client: MarketClient = MarketClient(host = host)

    suspend fun fetchPrices(currency: Currency): PriceList<FloatingPrice> =
        client.allOffersFull(currency).priceList()

    suspend fun fetchBestPrices(currency: Currency): PriceList<Double> =
        client.bestOffers(currency).priceList()
    
    suspend fun mockPrices(): PriceList<FloatingPrice> = client.allOffersMock()

    fun close() {
        client.close()
    }
}   