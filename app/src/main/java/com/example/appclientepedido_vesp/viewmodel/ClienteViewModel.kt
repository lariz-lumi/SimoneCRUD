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
import kotlinx.coroutines.newFixedThreadPoolContext

class ClienteViewModel: ViewModel() {

    //Conexão Retrofit/http com a API
    val api = RetrofitAppApi.apiService

    //MutableStateFlow = leitura e escrita
    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    //StateFlow = só leitura
    val clientes : StateFlow<List<Cliente>> = _clientes //lista com todos os clientes
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _clienteSelecionado = MutableStateFlow<Cliente?>(null) //guarda o cliente escolhido

    private val _clienteBuscado = MutableStateFlow<Cliente?>(null) //guarda o resultado da busca por ID
    val clienteBuscado: StateFlow<Cliente?> = _clienteBuscado

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

    fun buscarPorId(id: Int){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val cliente = api.getClientePorId(id)
                _clienteBuscado.value = cliente //se é encontrado ta armazenado no stateflow
            }catch (e: Exception){
                e.printStackTrace()
                _clienteBuscado.value = _clientes.value.find { it.idCliente == id.toString()} //nao olha mais pra api
            }finally {
                _isLoading.value = false
            }
        }
    }
    fun adicionarCliente(idCliente: String, nome: String, telefone: String) {
        viewModelScope.launch {
            try {
                val novoId = (_clientes.value.size + 1).toString() // novo id com base na qtd

                val novoCliente = Cliente(
                    idCliente = novoId,
                    nome = nome,
                    telefone = telefone
                )
                val resposta = api.criarCliente(novoCliente)
                println("POST: $resposta")
                _clientes.value = _clientes.value + novoCliente //add o cliente na lista

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
            _clientes.value = _clientes.value.filter { it.idCliente != id.toString()} //se o 2 foi excluido o 3 mantem
        }
    }
    fun atualizarCliente(id: Int, nome: String, telefone: String) {
        viewModelScope.launch {
            val clienteEditado = Cliente(
                idCliente = id.toString(),
                nome = nome,
                telefone = telefone
            )
            try {
                val resposta = api.atualizarCliente(
                    id = id,
                    cliente = clienteEditado
                )
                println("PUT: $resposta")
                _clientes.value = _clientes.value.map {
                    if (it.idCliente == id.toString())
                        clienteEditado
                    else it
                } // percorre a lista e substitui o cliente editado pelo novo objeto

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}