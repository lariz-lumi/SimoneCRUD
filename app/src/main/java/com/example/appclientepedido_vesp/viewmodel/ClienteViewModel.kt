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

    private val _clienteSelecionado = MutableStateFlow<Cliente?>(null)
    val clienteSelecionado: StateFlow<Cliente?> = _clienteSelecionado

    init{
        carregarCliente()
    }

    fun carregarCliente() {
        _isLoading.value = true

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

    /*fun adicionarCliente(idCliente: String, nome:String, telefone:String){

        viewModelScope.launch {
            try {
                val novoCliente = Cliente(idCliente = idCliente ,nome=nome, telefone=telefone)
                val clienteSalvo = api.criarCliente(novoCliente)

                _clientes.value = _clientes.value + clienteSalvo
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }*/
    fun adicionarCliente(idCliente: String, nome: String, telefone: String) {
        viewModelScope.launch {
            try {
                val novoId = (_clientes.value.size + 1).toString()
                val novoCliente = Cliente(idCliente = novoId, nome = nome, telefone = telefone)
                api.criarCliente(novoCliente)
                _clientes.value = _clientes.value + novoCliente

            } catch (e: Exception) {
                e.printStackTrace()
                val novoId = (_clientes.value.size + 1).toString()
                val novoCliente = Cliente(idCliente = novoId, nome = nome, telefone = telefone)
                _clientes.value = _clientes.value + novoCliente
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
            _clientes.value = _clientes.value.filter { it.idCliente != id.toString()}
        }
    }

   /* fun atualizarCliente(id: Int, nome: String, telefone: String){
        viewModelScope.launch {
            try {
                val clienteEditado = Cliente(idCliente = id.toString(), nome = nome, telefone = telefone)
                api.atualizarCliente(id, clienteEditado)
                _clientes.value = _clientes.value.map{
                    if(it.idCliente == id.toString()) clienteEditado else it
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }*/
   fun atualizarCliente(id: Int, nome: String, telefone: String) {
       viewModelScope.launch {

           val clienteEditado = Cliente(idCliente = id.toString(), nome = nome, telefone = telefone)
           try {
               api.atualizarCliente(id, clienteEditado)
               _clientes.value = _clientes.value.map {
                   if (it.idCliente == id.toString()) clienteEditado else it
               }
           } catch (e: Exception) {
               e.printStackTrace()
               _clientes.value = _clientes.value.map {
                   if (it.idCliente == id.toString()) clienteEditado else it
               }
           }
       }
   }






}