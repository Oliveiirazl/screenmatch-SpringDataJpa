package br.com.alura.screenmatch.service;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;

import java.util.List;

public class ConsultaChatGPTService {

    public static String obterTraducao(String texto) {
        try {
            OpenAiService service = new OpenAiService("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");

            ChatMessage systemMessage = new ChatMessage("system", "Você é um tradutor de inglês para português.");
            ChatMessage userMessage = new ChatMessage("user", "Traduza o seguinte texto para o português: " + texto);

            ChatCompletionRequest requisicao = ChatCompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .messages(List.of(systemMessage, userMessage))
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            var resposta = service.createChatCompletion(requisicao);
            return resposta.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            System.out.println("Erro ao consultar ChatGPT: " + e.getMessage());
            return texto;
        }
    }
}
