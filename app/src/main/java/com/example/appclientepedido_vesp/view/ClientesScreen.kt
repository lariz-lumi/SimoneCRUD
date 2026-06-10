package com.example.appclientepedido_vesp.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appclientepedido_vesp.viewmodel.ClienteViewModel

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ClientesScreen(viewModel: ClienteViewModel = viewModel()) {

    var nomeInput by remember { mutableStateOf("") }
    var telefoneInput by remember { mutableStateOf("") }
    var idClienteSendoEditado by remember {mutableStateOf<String?>(null)}
    val context = LocalContext.current
    var idBuscaInput by remember { mutableStateOf("") }
    var abaAtiva by remember { mutableStateOf("gerenciar") }

    val isLoading by viewModel.isLoading.collectAsState()
    val clientes by viewModel.clientes.collectAsState()
    val clienteBuscado by viewModel.clienteBuscado.collectAsState()

    val corFundo = Color(0xFF1E1E24)
    val corAmareloClaro = Color(0xFFC69214)
    val corAmareloEscuro = Color(0xFF4A3B1C)
    val corTextoClaro = Color(0xFFF5F5F5)
    val corVermelho = Color(0xFF8B0000)
    val corCardFundo = Color(0xFF2A2A32)

    /*Column(
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
                    if(idClienteSendoEditado == null){
                        viewModel.adicionarCliente("", nomeInput, telefoneInput)
                        Toast.makeText(context, "Cliente adicionado!", Toast.LENGTH_LONG).show()
                    }else{
                        viewModel.atualizarCliente(idClienteSendoEditado!!.toInt(), nomeInput, telefoneInput)
                        Toast.makeText(context, "Cliente atualizado!", Toast.LENGTH_LONG).show()
                        idClienteSendoEditado = null
                    }
                    nomeInput = ""
                    telefoneInput = ""
                }
            }
        ) {

            Text(if (idClienteSendoEditado == null) "Adicionar Cliente" else "Salvar Alterações")
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
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable{
                            idClienteSendoEditado = cliente.idCliente
                            nomeInput = cliente.nome
                            telefoneInput = cliente.telefone
                        },
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
                                onClick = {
                                    val idInt = cliente.idCliente.toIntOrNull() ?: 0
                                    viewModel.excluirCliente(idInt)
                                    Toast.makeText(context, "Cliente removido com sucesso!", Toast.LENGTH_LONG).show()
                                }
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
}*/
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(corFundo)
            .padding(15.dp)
    ) {

        Text(
            text = "GERENCIAMENTO DE CLIENTES",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = corAmareloClaro,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val abas = listOf("gerenciar" to "Gerenciar", "listar" to "Listar", "buscar" to "Buscar ID")
            abas.forEach { (chave, texto) ->
                Button(
                    onClick = { abaAtiva = chave },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (abaAtiva == chave) corAmareloClaro else corAmareloEscuro,
                        contentColor = corTextoClaro
                    )
                ) {
                    Text(texto, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (abaAtiva) {
            "gerenciar" -> {

                OutlinedTextField(
                    value = nomeInput,
                    onValueChange = { nomeInput = it },
                    label = { Text("Nome do Cliente", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = corAmareloClaro,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = corAmareloClaro,
                        unfocusedTextColor = corTextoClaro,
                        focusedTextColor = corTextoClaro
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(
                    value = telefoneInput,
                    onValueChange = { telefoneInput = it },
                    label = { Text("Telefone", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = corAmareloClaro,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = corAmareloClaro,
                        unfocusedTextColor = corTextoClaro,
                        focusedTextColor = corTextoClaro
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {
                        if (nomeInput.isNotBlank() && telefoneInput.isNotBlank()) {
                            if (idClienteSendoEditado == null) {
                                viewModel.adicionarCliente("", nomeInput, telefoneInput)
                                Toast.makeText(context, "Cliente adicionado!", Toast.LENGTH_LONG).show()
                            } else {
                                viewModel.atualizarCliente(idClienteSendoEditado!!.toInt(), nomeInput, telefoneInput)
                                Toast.makeText(context, "Cliente atualizado!", Toast.LENGTH_LONG).show()
                                idClienteSendoEditado = null
                            }
                            nomeInput = ""
                            telefoneInput = ""
                        }
                    },
                    shape = RoundedCornerShape(4.dp), //arredonda o canto
                    colors = ButtonDefaults.buttonColors(containerColor = corAmareloClaro, contentColor = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (idClienteSendoEditado == null) "Adicionar Cliente" else "Salvar Alterações",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = corAmareloClaro
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(clientes) { cliente ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        idClienteSendoEditado = cliente.idCliente
                                        nomeInput = cliente.nome
                                        telefoneInput = cliente.telefone
                                    },
                                shape = RoundedCornerShape(6.dp),
                                colors = CardDefaults.cardColors(containerColor = corCardFundo),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(15.dp).fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("ID: ${cliente.idCliente}", fontSize = 12.sp, color = corAmareloClaro, fontWeight = FontWeight.Bold)
                                        Text(cliente.nome, fontSize = 16.sp, color = corTextoClaro, fontWeight = FontWeight.SemiBold)
                                        Text(cliente.telefone, fontSize = 14.sp, color = Color.LightGray)
                                    }
                                    IconButton(
                                        onClick = {
                                            val idInt = cliente.idCliente.toIntOrNull() ?: 0 //converte de string pra int
                                            viewModel.excluirCliente(idInt)
                                            Toast.makeText(context, "Cliente removido com sucesso!", Toast.LENGTH_LONG).show()
                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Excluir Cliente",
                                            tint = corVermelho
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            "listar" -> {

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = corAmareloClaro
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(clientes) { cliente ->
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                                shape = RoundedCornerShape(6.dp),
                                colors = CardDefaults.cardColors(containerColor = corCardFundo),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(modifier = Modifier.padding(15.dp)) {
                                    Text("ID: ${cliente.idCliente}", fontSize = 11.sp, color = corAmareloClaro, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("NOME: ${cliente.nome}", fontSize = 16.sp, color = corTextoClaro, fontWeight = FontWeight.SemiBold)
                                    Text("TELEFONE: ${cliente.telefone}", fontSize = 14.sp, color = Color.LightGray)
                                }
                            }
                        }
                    }
                }
            }

            "buscar" -> {

                OutlinedTextField(
                    value = idBuscaInput,
                    onValueChange = { idBuscaInput = it },
                    label = { Text("Buscar cliente por ID", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = corAmareloClaro,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = corAmareloClaro,
                        unfocusedTextColor = corTextoClaro,
                        focusedTextColor = corTextoClaro
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {
                        val idInt = idBuscaInput.toIntOrNull()
                        if (idInt != null) {
                            viewModel.buscarPorId(idInt)
                        } else {
                            Toast.makeText(context, "Digite um ID válido!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = corAmareloClaro, contentColor = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("BUSCAR CLIENTE", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(25.dp))

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = corAmareloClaro
                    )
                } else {
                    clienteBuscado?.let { cliente -> //se o cliente nao for nulo vai executar
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(6.dp),
                            colors = CardDefaults.cardColors(containerColor = corCardFundo),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text("CLIENTE ENCONTRADO", fontSize = 12.sp, color = corAmareloClaro, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("ID: ${cliente.idCliente}", fontSize = 13.sp, color = corTextoClaro)
                                Text("Nome: ${cliente.nome}", fontSize = 17.sp, color = corTextoClaro, fontWeight = FontWeight.Bold)
                                Text("Telefone: ${cliente.telefone}", fontSize = 14.sp, color = Color.LightGray)
                            }
                        }
                    } ?: run { //se digita um id que nao tem aparece que nao foi localizado
                        if (idBuscaInput.isNotBlank()) {
                            Text("Nenhum registro localizado.", color = Color.Gray, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}