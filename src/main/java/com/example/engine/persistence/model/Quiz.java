package com.example.engine.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Quiz {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @NotNull
    @Size(min = 2)
    @ElementCollection
    @CollectionTable(
            name = "QUIZ_OPTIONS",
            joinColumns = @JoinColumn(name = "QUIZ_ID")
    )
    @Column(name = "QUIZ_OPTION")
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    @CollectionTable(
            name = "QUIZ_ANSWERS",
            joinColumns = @JoinColumn(name = "QUIZ_ID")
    )
    @Column(name = "QUIZ_ANSWER")
    private Set<Integer> answer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = true)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private Set<QuizCompletion> completions;
}