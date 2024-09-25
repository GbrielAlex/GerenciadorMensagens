# Cliente Email App

Este é um aplicativo Java que utiliza Swing para criar uma interface gráfica que lista clientes e seus respectivos emails. Os dados dos clientes são buscados de um servidor local via HTTP.

## Funcionalidades

- Exibe uma lista de clientes com seus nomes e CPFs.
- Permite visualizar todos os emails enviados por um cliente ao clicar duas vezes em seu nome.
- Caso um cliente não tenha enviado emails, uma mensagem será exibida informando isso.

## Tecnologias Utilizadas

- **Java 11+**: Para o desenvolvimento da aplicação.
- **Java Swing**: Para a criação da interface gráfica do usuário.
- **Java HttpClient**: Para realizar requisições HTTP e obter os dados dos clientes.
- **Gson**: Para realizar a serialização e deserialização de JSON.

## Estrutura do Projeto

ClienteEmailApp │
├── src │
  ├── ClienteEmailApp.java # Classe principal que contém a lógica do aplicativo. │ 
    ├── Cliente.java # Classe representando um cliente. │
        └── Email.java # Classe representando um email. │
        └── README.md # Este arquivo.
