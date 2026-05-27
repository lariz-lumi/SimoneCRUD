package com.example.appclientepedido_vesp.model

import com.google.gson.annotations.SerializedName

data class Pedido(

    @SerializedName("idPedido")
    val idPedido: Int = 0,

    @SerializedName("dataPedido")
    val dataPedido: String,

    @SerializedName("valorTotal")
    val valorTotal: Double,

    @SerializedName("idCliente")
    val idCliente: Int

)
