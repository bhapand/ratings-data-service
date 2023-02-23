package io.kairos.ratingsdataservice.resources;

import io.kairos.ratingsdataservice.dto.Movie;
import io.kairos.ratingsdataservice.models.RatingInfo;
import io.kairos.ratingsdataservice.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class RatingRouter {

    @Autowired
    RatingService ratingService;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route(RequestPredicates.POST("/rating_router/rating-info"),
                        request -> request.bodyToMono(Movie.class)
                                .flatMap(movie -> ServerResponse.ok().body(ratingService.getMovieRating(movie.getName()), RatingInfo.class)))
                .andRoute(RequestPredicates.GET("/rating_router/all"),
                        request -> ServerResponse.ok().body(ratingService.getRatings(), RatingInfo.class))
                .andRoute(RequestPredicates.GET("/rating_router"),
                        request -> ServerResponse.ok().body(ratingService.getTopMoviesByRating(Integer.parseInt(request.queryParam("count").orElse("0"))), RatingInfo.class));
    }
}
