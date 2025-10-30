# Integrantes: 
Thomas Abner de Queiroz - RM: 550347
Weverton Luis Santana de Sá - RM: 98730


# Monitor de Crypto Moedas - BITCOIN (Jetpack Compose)

Este é um projeto de aplicativo Android simples que exibe a cotação atual do Bitcoin. O objetivo principal do projeto é demonstrar o uso de tecnologias modernas do Android, incluindo o Jetpack Compose para a construção da interface de utilizador (UI).

O aplicativo consome a API pública do [Mercado Bitcoin](https://www.mercadobitcoin.net/) para obter os dados em tempo real.

## 📱 Funcionalidades

* Consulta a cotação mais recente do Bitcoin (BTC).
* Exibe o valor da última transação em Reais (BRL).
* Exibe a data e hora da última cotação.
* Possui um botão "Atualizar" para recarregar os dados da API manualmente.

## 🛠️ Tecnologias Utilizadas

* **[Kotlin](https://kotlinlang.org/):** Linguagem de programação principal.
* **[Jetpack Compose](https://developer.android.com/jetpack/compose):** Utilizado para construir toda a UI de forma declarativa.
* **[Material 3](https://m3.material.io/):** Componentes visuais (Botão, TopAppBar, etc.).
* **[Retrofit](https://square.github.io/retrofit/):** Cliente HTTP para fazer as chamadas à API do Mercado Bitcoin.
* **[Coroutines (Kotlinx)](https://github.com/Kotlin/kotlinx.coroutines):** Utilizadas para gerir as operações assíncronas (chamadas de rede).
* **[Gestão de Estado do Compose](https://developer.android.com/jetpack/compose/state):** Utiliza `mutableStateOf` e `remember` para gerir o estado da UI de forma reativa.

## 🔌 API

Este projeto utiliza a API pública do **Mercado Bitcoin**.
* **Endpoint:** `https://www.mercadobitcoin.net/api/BTC/ticker/`

## 🚀 Como Executar

1.  Clone este repositório:
    ```bash
    git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)
    ```
2.  Abra o projeto no [Android Studio](https://developer.android.com/studio).
3.  Aguarde o Gradle sincronizar todas as dependências.
4.  Execute o aplicativo num emulador ou dispositivo físico.

*Nota: É necessária uma ligação à Internet para que o aplicativo possa obter os dados da API.*
