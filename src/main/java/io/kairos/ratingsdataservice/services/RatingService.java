package io.kairos.ratingsdataservice.services;

import io.kairos.ratingsdataservice.models.RatingInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RatingService {

    public Mono<RatingInfo> getMovieRating(String movieName);

    public Flux<RatingInfo> getTopMoviesByRating(int count);

    public Flux<RatingInfo> getRatings();
}
