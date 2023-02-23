package io.kairos.ratingsdataservice.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kairos.ratingsdataservice.models.RatingInfo;
import io.kairos.ratingsdataservice.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.util.Comparator;

@Service
public class RatingServiceImpl implements RatingService {

    @Value("${movie-api.url}")
    public String movieApiUrl;

    @Value("${movie-api.token}")
    public String apiToken;

    @Autowired
    WebClient webClient;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Mono<RatingInfo> getMovieRating(String movieName) {
        return makeMovieApiCall().filter(ratingInfo -> ratingInfo.getMovieName().equalsIgnoreCase(movieName)).next();
    }

    @Override
    public Flux<RatingInfo> getRatings() {
        return makeMovieApiCall();
    }

    @Override
    public Flux<RatingInfo> getTopMoviesByRating(int count) {
        return makeMovieApiCall().sort(Comparator.comparing(ratingInfo -> Float.valueOf(ratingInfo.getRating()))).take(5);
    }


    private Flux<RatingInfo> makeMovieApiCall() {
        return webClient.get()
                .uri(URI.create(movieApiUrl + "/titles"))
                .header("Authorization", "Bearer " + apiToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .flatMapMany(response -> {
                    JsonNode dataNode;
                    try {
                        dataNode = objectMapper.readTree(response).get("pagination").get("data");
                        return Flux.fromIterable(objectMapper.readerForListOf(RatingInfo.class).readValue(dataNode));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
