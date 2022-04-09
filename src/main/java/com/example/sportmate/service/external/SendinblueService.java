package com.example.sportmate.service.external;

import com.example.sportmate.exception.BadRequestException;
import com.example.sportmate.exception.ExternalException;
import com.example.sportmate.record.external.SendinblueSendEmailRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class SendinblueService {
    private static final String API_KEY = "api-key";
    private static final String SEND_TRANSACTIONAL_EMAIL = "https://api.sendinblue.com/v3/smtp/email";

    @Value("${api.key.sendinblue}")
    private String apiKey;

    public void sendEmail(final SendinblueSendEmailRequest sendinblueSendEmailRequest) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(SEND_TRANSACTIONAL_EMAIL))
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(sendinblueSendEmailRequest)))
                    .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .header(ACCEPT, APPLICATION_JSON_VALUE)
                    .header(API_KEY, apiKey)
                    .build();
            HttpClient.newHttpClient().send(request, null);
        } catch (final JsonProcessingException e) {
            throw new BadRequestException("Erreur lors de la transformation de l'object en string: " + e.getMessage());
        } catch (final IOException e) {
            throw new BadRequestException("Erreur lors de l'envoie du mail: " + e.getMessage());
        } catch (final URISyntaxException e) {
            throw new BadRequestException("Erreur lors du parsing de l'url: " + e.getMessage());
        } catch (final InterruptedException e) {
            throw new ExternalException("Indisponibilité de l'api externe pour envoyer l'email: " + e.getMessage());
        }
    }
}
