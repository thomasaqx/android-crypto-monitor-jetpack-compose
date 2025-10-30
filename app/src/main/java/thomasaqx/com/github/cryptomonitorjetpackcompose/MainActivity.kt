package thomasaqx.com.github.cryptomonitorjetpackcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import carreiras.com.github.cryptomonitor.service.MercadoBitcoinServiceFactory
import carreiras.com.github.cryptomonitor.ui.theme.KotlinandroidcryptomonitorTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val service = MercadoBitcoinServiceFactory().create()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // 3. Definir o estado que a UI vai observar
            // Estes 'remember' guardam o estado mesmo quando a UI é redesenhada
            var lastValue by remember { mutableStateOf(stringResource(id = R.string.label_value)) }
            var date by remember { mutableStateOf(stringResource(id = R.string.label_date)) }

            val coroutineScope = rememberCoroutineScope()
            val context = LocalContext.current

            // 4. Lógica de rede adaptada para atualizar o estado
            fun makeRestCall() {
                coroutineScope.launch(Dispatchers.Main) {
                    try {
                        val response = service.getTicker()

                        if (response.isSuccessful) {
                            val tickerResponse = response.body()

                            // 5. ATUALIZAR O ESTADO
                            // A UI vai "reagir" a estas mudanças automaticamente
                            val lastValueDouble = tickerResponse?.ticker?.last?.toDoubleOrNull()
                            if (lastValueDouble != null) {
                                val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
                                lastValue = numberFormat.format(lastValueDouble) // Atualiza o estado
                            }

                            val timestamp = tickerResponse?.ticker?.date
                            if (timestamp != null) {
                                val dateObj = Date(timestamp * 1000L)
                                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                                date = sdf.format(dateObj) // Atualiza o estado
                            }

                        } else {
                            val errorMessage = when (response.code()) {
                                400 -> "Bad Request"
                                401 -> "Unauthorized"
                                403 -> "Forbidden"
                                404 -> "Not Found"
                                else -> "Unknown error"
                            }
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        }

                    } catch (e: Exception) {
                        Toast.makeText(context, "Falha na chamada: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }

            // 6. Executa a chamada de rede na primeira vez que a UI é composta
            LaunchedEffect(key1 = Unit) {
                makeRestCall()
            }

            // 7. Aplicar o tema principal do Compose
            KotlinandroidcryptomonitorTheme {
                // 8. Scaffold é um layout base que nos dá uma TopBar
                Scaffold(
                    topBar = {
                        // 9. A nossa TopAppBar (antiga Toolbar)
                        TopAppBar(
                            title = {
                                Text(
                                    text = stringResource(id = R.string.app_title),
                                    color = colorResource(id = R.color.white) // Cor do ficheiro colors.xml
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorResource(id = R.color.primary) // Cor do ficheiro colors.xml
                            )
                        )
                    }
                ) { paddingValues ->
                    // 10. O conteúdo principal da aplicação
                    // Usamos a Column para empilhar os elementos verticalmente
                    Column(
                        modifier = Modifier
                            .fillMaxSize() // Ocupa o ecrã todo
                            .padding(paddingValues), // Respeita o espaço da TopBar
                        verticalArrangement = Arrangement.Center, // Alinha ao centro (vertical)
                        horizontalAlignment = Alignment.CenterHorizontally // Alinha ao centro (horizontal)
                    ) {
                        Text(
                            text = stringResource(id = R.string.label_rate),
                            fontSize = 20.sp
                        )

                        Text(
                            text = lastValue, // Lê o estado
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Text(
                            text = date, // Lê o estado
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        // 11. O nosso botão de Atualizar
                        Button(
                            onClick = { makeRestCall() }, // Chama a função de rede ao clicar
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .width(120.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.success) // Cor do ficheiro colors.xml
                            ),
                            shape = RoundedCornerShape(10.dp) // Substitui o shape_button_refresh.xml
                        ) {
                            Text(
                                text = stringResource(id = R.string.label_refresh),
                                color = colorResource(id = R.color.white)
                            )
                        }
                    }
                }
            }
        }
    }
}