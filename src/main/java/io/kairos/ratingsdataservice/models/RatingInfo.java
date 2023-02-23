package io.kairos.ratingsdataservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingInfo {

    @JsonProperty(value = "name")
    private String movieName;

    @JsonProperty(value = "rating")
    private String rating;

}
