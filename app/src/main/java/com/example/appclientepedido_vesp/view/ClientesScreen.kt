package com.example.appclientepedido_vesp.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appclientepedido_vesp.viewmodel.ClienteViewModel

import androidx.compose.foundation.lazy.items

@Composable
fun ClientesScreen(viewModel: ClienteViewModel = viewModel()) {

    var nomeInput by remember { mutableStateOf("") }

    var telefoneInput by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()

    val clientes by viewModel.clientes.collectAsState()


    Column(
        modifier = Modifier.padding(15.dp)
    ) {

        Text("Gerenciamento de Clientes", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(15.dp))

        //Formulário para incluir cliente

        OutlinedTextField(
            value = nomeInput,
            onValueChange = { nomeInput = it},
            label = {Text("Nome do Cliente")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = telefoneInput,
            onValueChange = { telefoneInput = it },
            label = {Text("Telefone")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                if(nomeInput.isNotBlank() && telefoneInput.isNotBlank()) {

                }
            }
        ) {

            Text("Adicionar Cliente")
        }

        Spacer(modifier = Modifier.height(15.dp))

        if(isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }else{
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(clientes){
                    cliente ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(15.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column() {
                                Text(cliente.idCliente, fontSize = 15.sp)
                                Text(cliente.nome, fontSize = 15.sp)
                                Text(cliente.telefone, fontSize = 15.sp)
                            }
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Excluir Cliente",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }


    }
}