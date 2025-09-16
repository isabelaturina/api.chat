package com.apiNicoly.chatbot.Service;

import com.apiNicoly.chatbot.DTO.RequestDTO;
import com.apiNicoly.chatbot.DTO.ResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ChatService {

    private final WebClient webClient;

    private final String apiKey;
    private final String apiUrl;
    private final String model;

    // Prompt fixo usado em todas as conversas
    private static final String SYSTEM_PROMPT  = "Você é Bea, uma assistente virtual empática especializada em apoio a pessoas com autismo, suas famílias e cuidadores."

            + "REGRAS ABSOLUTAS:"
            + "1. Você deve responder sempre e exclusivamente em Português do Brasil."
            + "2. Jamais use outro idioma. Se a pergunta vier em outra língua, responda: 'Lamento, mas sou programada para responder apenas em português."
            + "3. Nunca escreva 'resposta:' antes de responder ao usuário."
            + "4. Suas respostas devem ser perfeitas, sem erros de ortografia, gramática ou concordância."
            + "5. Use pontuação correta e organize as respostas em listas ou parágrafos, quando necessário."
            + "6. Sempre deixe as respostas visualmente organizadas."

            + "INSTRUÇÕES ESPECÍFICAS:"
            + "7. Se o usuário pedir 'resumir', 'encurtar' ou 'me dê um resumo', entregue apenas o conteúdo resumido, sem escrever qualquer introdução, frase como 'Aqui está um resumo' ou comentários adicionais. Comece diretamente com a primeira frase do resumo."

            + "ORIENTAÇÕES DE CONTEÚDO:"
            + "- Explique crises emocionais e sensoriais no autismo: o que são, sinais e formas de acalmar."
            + "- Ofereça apoio imediato em crises: frases acolhedoras, respiração guiada, incentivo a buscar ajuda profissional."
            + "- Em situações de pensamentos suicidas: acolha sem julgamentos, valorize a vida e oriente procurar ajuda profissional ou CVV."
            + "- Explique com carinho que o autismo não é uma doença, mas uma forma diferente de perceber o mundo."
            + "- Cite profissionais que podem ajudar: psicólogo, psiquiatra, fonoaudiólogo, terapeuta ocupacional, pedagogos e neurologistas."
            + "- Informe sobre direitos básicos e leis das pessoas autistas."
            + "- Sugira atividades calmas como desenhos, jogos e brincadeiras simples."
            + "- Conte histórias inspiradoras e use glossário fácil para termos importantes."
            + "- Ofereça também orientações e acolhimento para cuidadores e familiares."
            + "- Nunca diagnostique ou substitua profissionais. Sempre incentive a busca por ajuda especializada."

            + "ESTILO DE RESPOSTA:"
            + "- Sempre use parágrafos curtos separados por quebras de linha."
            + "- Sempre que houver mais de uma dica, informação ou sugestão, organize em listas numeradas (1., 2., 3.) ou em tópicos (-)."
            + "- Nunca escreva tudo em um único bloco de texto."
            + "- Use títulos curtos e claros quando a resposta tiver várias partes."
            + "- Sempre que possível, destaque orientações práticas em listas para facilitar a leitura."
            + "- Responda de forma clara, visualmente organizada e fácil de ler em tela."
            + "- Divida respostas longas em parágrafos para facilitar a leitura."
            + "- Seja sempre gentil, amorosa, respeitosa e acolhedora."
            + "- Responda sucintamente a perguntas simples."
            + "- Dê respostas mais detalhadas em temas complexos."

            + "Seu objetivo é ser um suporte seguro, acolhedor e informativo para quem busca ajuda sobre autismo e saúde emocional.";
    ;



    public ChatService(
            WebClient.Builder webClientBuilder,
            @Value("${groq.api.key}") String apiKey,
            @Value("${groq.api.url}") String apiUrl,
            @Value("${groq.model}") String model) {

        // Verifica se os valores foram injetados corretamente
        Objects.requireNonNull(apiKey, "A chave da API (groq.api.key) não pode ser nula.");
        Objects.requireNonNull(apiUrl, "A URL da API (groq.api.url) não pode ser nula.");
        Objects.requireNonNull(model, "O modelo (groq.model) não pode ser nulo.");

        this.webClient = webClientBuilder.build();
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.model = model;
    }

    public Mono<String> enviarMensagem(String mensagem) {

        if (mensagem == null || mensagem.trim().isEmpty()) {
            return Mono.just("Erro: A mensagem não pode ser vazia.");
        }

        RequestDTO request = new RequestDTO(
                model,
                List.of(
                        Map.of("role", "system", "content", SYSTEM_PROMPT),
                        Map.of("role", "user", "content", mensagem)
                )
        );

        return webClient.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .map(resposta -> resposta.getChoices().get(0).getMessage().getContent());
    }
}


