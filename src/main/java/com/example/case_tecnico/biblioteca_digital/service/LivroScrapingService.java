package com.example.case_tecnico.biblioteca_digital.service;

import com.example.case_tecnico.biblioteca_digital.dto.LivroDTO;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.regex.Matcher;

@Slf4j
@Service
public class LivroScrapingService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://www.amazon.com.br")
            .defaultHeader("User-Agent", "Mozilla/5.0")
            .build();

    public LivroDTO extrairDadosLivro(String url, Long autorId, Long categoriaId) {
        try {
            String asin = extrairAsinDaUrl(url);
            if (asin == null || asin.isBlank()) {
                throw new IllegalArgumentException("URL inválida. ASIN não encontrado.");
            }

            String html;
            try {
                html = webClient.get()
                        .uri("/dp/{asin}", asin)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
            } catch (WebClientResponseException e) {
                log.error("Erro HTTP ao acessar a página do produto: Status {}, Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
                throw new RuntimeException("Erro ao acessar a página do livro. Verifique a URL.");
            } catch (WebClientRequestException e) {
                log.error("Erro de conexão ao acessar a página do produto: {}", e.getMessage());
                throw new RuntimeException("Erro de conexão ao tentar acessar o site.");
            }

            if (html == null || html.isBlank()) {
                throw new RuntimeException("Página vazia ou não encontrada.");
            }

            Document doc = Jsoup.parse(html);

            String titulo = doc.select("#productTitle").text().trim();
            String precoStr = doc.select(".a-price .a-offscreen").text().replace("R$", "").replace(",", ".").trim();
            String ano = extrairAnoPublicacao(doc);

            double preco = precoStr.isBlank() ? 0.0 : Double.parseDouble(precoStr);
            int anoPublicacao = ano.isBlank() ? 0 : Integer.parseInt(ano);

            return new LivroDTO(
                    null,
                    titulo,
                    asin, // usar o ASIN como ISBN fictício
                    anoPublicacao,
                    preco,
                    autorId,
                    categoriaId
            );

        } catch (Exception e) {
            log.error("Erro ao extrair dados do livro: {}", e.getMessage());
            throw new RuntimeException("Falha ao extrair dados do livro da URL fornecida.");
        }
    }

    private String extrairAsinDaUrl(String url) {
        Pattern pattern = Pattern.compile("/dp/([A-Z0-9]{10})");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String extrairAnoPublicacao(Document doc) {
        for (Element row : doc.select("#detailBullets_feature_div li")) {
            if (row.text().toLowerCase().contains("data de publicação")) {
                return row.text().replaceAll(".*?(\\d{4}).*", "$1");
            }
        }

        for (Element row : doc.select("#productDetails_techSpec_section_1 tr")) {
            String label = row.select("th").text().toLowerCase();
            String value = row.select("td").text();
            if (label.contains("publicação") || label.contains("data")) {
                return value.replaceAll(".*?(\\d{4}).*", "$1");
            }
        }

        return "";
    }
}