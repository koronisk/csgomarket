package ru.let.csgomarket.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AllOffersResponse(
    val success: Boolean,
    val time: Long,
    val currency: String,
    val format: List<String>,
    @SerialName("items") val batches: List<String>
)

internal data class AllOffersFullResponse(
    val success: Boolean,
    val time: Long,
    val currency: String,
    val items: List<AllOffersItem>
)

internal data class AllOffersItem(
    val price: Double?,
    val id: Long?,
    val marketHashName: String?,
    val classId: Long?,
    val instanceId: Long?,
    val realInstance: Long?,
    val asset: Long?,
    val oldPrice: Int?,
    val stamp: String?,
    val baseId: Int?,
    val float: Int?,
    val phase: String?,
    val paintSeed: String?,
    val paintIndex: Int?,
    val stickers: String?,
    val type: String?,
    val chanceToTransfer: Int?,
    val source: String?
)