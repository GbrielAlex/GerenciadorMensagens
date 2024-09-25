package com.gabrielDEv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // Faz a requisição HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/clientes")) // URL do endpoint local
                .build();

        // Pega a resposta como uma String
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Formata o JSON recebido
        String jsonString = response.body();

        // Usa o Gson para "pretty print" o JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(gson.fromJson(jsonString, Object.class));

        // Exibe o JSON formatado
        System.out.println(prettyJson);
    }
}