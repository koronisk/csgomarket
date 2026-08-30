package ru.let.csgomarket.request

import io.ktor.client.call.body
import kotlinx.coroutines.delay
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import ru.let.csgomarket.MarketClient
import ru.let.csgomarket.common.Currency
import ru.let.csgomarket.response.AllOffersFullResponse
import ru.let.csgomarket.response.AllOffersItem
import ru.let.csgomarket.response.AllOffersResponse
import ru.let.csgomarket.response.BestOffersResponse
import kotlin.time.Duration.Companion.milliseconds

internal suspend fun MarketClient.bestOffers(currency: Currency): BestOffersResponse =
    get("api/v2/prices/${currency.code}.json").body<BestOffersResponse>()

internal suspend fun MarketClient.allOffers(currency: Currency): AllOffersResponse =
    get("api/full-export/${currency.code}.json").body<AllOffersResponse>()

internal suspend fun MarketClient.allOffersFull(currency: Currency): AllOffersFullResponse {
    val allOffers = allOffers(currency)
    val format = allOffers.format

    val formattedItems = mutableListOf<AllOffersItem>()

    allOffers.batches.forEach { batch ->
        val items = get("api/full-export/$batch").body<List<List<JsonElement>>>()
        items.forEach { item ->
            val name = "${item[format.indexOf("market_hash_name")].jsonPrimitive.contentOrNull}"
            if (name.contains("Sticker ") || name.contains("Graffiti ")) return@forEach

            val formattedItem = AllOffersItem(
                price = item[format.indexOf("price")].jsonPrimitive.doubleOrNull,
                id = item[format.indexOf("id")].jsonPrimitive.longOrNull,
                marketHashName = item[format.indexOf("market_hash_name")].jsonPrimitive.contentOrNull,
                classId = item[format.indexOf("classid")].jsonPrimitive.longOrNull,
                instanceId = item[format.indexOf("instanceid")].jsonPrimitive.longOrNull,
                realInstance = item[format.indexOf("real_instance")].jsonPrimitive.longOrNull,
                asset = item[format.indexOf("asset")].jsonPrimitive.longOrNull,
                oldPrice = item[format.indexOf("old_price")].jsonPrimitive.intOrNull,
                stamp = item[format.indexOf("stamp")].jsonPrimitive.contentOrNull,
                baseId = item[format.indexOf("base_id")].jsonPrimitive.intOrNull,
                float = item[format.indexOf("float")].jsonPrimitive.intOrNull,
                phase = item[format.indexOf("phase")].jsonPrimitive.contentOrNull,
                paintSeed = item[format.indexOf("paintseed")].jsonPrimitive.contentOrNull,
                paintIndex = item[format.indexOf("paintindex")].jsonPrimitive.intOrNull,
                stickers = item[format.indexOf("stickers")].jsonPrimitive.contentOrNull,
                type = item[format.indexOf("type")].jsonPrimitive.contentOrNull,
                chanceToTransfer = item[format.indexOf("chance_to_transfer")].jsonPrimitive.intOrNull,
                source = item[format.indexOf("source")].jsonPrimitive.contentOrNull
            )

            formattedItems.add(formattedItem)
        }

        delay(1500.milliseconds)
    }

    val response = AllOffersFullResponse(
        success = true,
        time = allOffers.time,
        currency = allOffers.currency,
        items = formattedItems
    )
    
    return response
}