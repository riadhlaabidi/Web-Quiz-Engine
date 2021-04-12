package com.example.engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Quiz {

    private static int generatedId = 1;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final int id = generatedId++;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @NotNull
    @Size(min = 2)
    private String[] options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer = new ArrayList<>();
}
