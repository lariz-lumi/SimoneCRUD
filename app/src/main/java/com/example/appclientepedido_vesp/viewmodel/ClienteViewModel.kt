package com.example.appclientepedido_vesp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appclientepedido_vesp.model.Cliente
import com.example.appclientepedido_vesp.service.IService
import com.example.appclientepedido_vesp.service.RetrofitAppApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClienteViewModel: ViewModel() {

    //Conexão Retrofit/http com a API
    val api = RetrofitAppApi.apiService

    //MutableStateFlow = leitura e escrita
    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    //StateFlow = só leitura
    val clientes : StateFlow<List<Cliente>> = _clientes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init{
        carregarCliente()
    }

    fun carregarCliente() {

        viewModelScope.launch {
            try {
                _clientes.value = api.getClientes()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            finally {
                _isLoading.value = false
            }
        }

    }

    fun adicionarCliente(idCliente: String, nome:String, telefone:String){

        viewModelScope.launch {
            try {
                val novoCliente = Cliente(idCliente = idCliente ,nome=nome, telefone=telefone)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun excluirCliente(id:Int){

        viewModelScope.launch {
            try {
                api.deletarCliente(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }






}