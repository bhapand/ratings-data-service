package io.kairos.ratingsdataservice.resources;

import io.kairos.ratingsdataservice.dto.Movie;
import io.kairos.ratingsdataservice.models.RatingInfo;
import io.kairos.ratingsdataservice.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/rating")
public class RatingResource {

    @Autowired
    RatingService ratingService;

    @PostMapping(value = "/rating-info")
    public Mono<RatingInfo> getMovieByName(@RequestBody Movie movie) {
        return ratingService.getMovieRating(movie.getName());
    }

    @GetMapping(value = "all")
    public Flux<RatingInfo> getRatings() {
        return ratingService.getRatings();
    }

    @GetMapping
    public Flux<RatingInfo> getTopRatedMovies(@RequestParam(value = "count") int count) {
        return ratingService.getTopMoviesByRating(count);
    }
}
