package com.example.engine.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.engine.security.Authority;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;


@Getter
@Setter
@Entity
public class User {

    @Id
    @NotNull
    @Pattern(regexp = "(?i)[\\w!#$%&'*+/=?`{|}~^-]+" +
            "(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@" +
            "(?:[a-z0-9-]+\\.)+[a-z]{2,6}")
    private String email;

    @NotBlank
    @Size(min = 5, max = 255)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Quiz> quizzes;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<QuizCompletion> completions;
}