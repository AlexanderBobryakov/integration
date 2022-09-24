package com.my.kafka_test.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CustomType {
    @JsonProperty(value = "name")
    private String name;

    @JsonCreator
    public CustomType(@JsonProperty String name) {
        this.name = name;
    }
}
