package com.example.appclientepedido_vesp.service

import com.example.appclientepedido_vesp.model.Cliente
import com.example.appclientepedido_vesp.model.Pedido
import retrofit2.http.*


interface IService {

    @GET("clientes")
    suspend fun getClientes(): List<Cliente>

    @GET("clientes/{id}")
    suspend fun getClientePorId(@Path("id") id: Int): Cliente

    @POST("clientes")
    suspend fun criarCliente(@Body cliente: Cliente): Cliente

    @PUT("clientes/{id}")
    suspend fun atualizarCliente(@Path("id") id: Int, @Body cliente: Cliente): Cliente

    @DELETE("clientes/{id}")
    suspend fun deletarCliente(@Path("id") id: Int)

    @GET("pedidos")
    suspend fun getPedidos(): List<Pedido>


}