package br.com.alura.screenmatch.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TradutorLibre {

    public static String obterTraducao(String texto) {
        try {
            URL url = new URL("http://localhost:5000/translate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = String.format(
                    "{\"q\": \"%s\", \"source\": \"en\", \"target\": \"pt\", \"format\": \"text\"}",
                    texto.replace("\"", "\\\"")
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }


            String json = response.toString();
            int start = json.indexOf(":\"") + 2;
            int end = json.lastIndexOf("\"");
            return json.substring(start, end);

        } catch (Exception e) {
            System.out.println("Erro ao traduzir: " + e.getMessage());
            return texto;
        }
    }
}
