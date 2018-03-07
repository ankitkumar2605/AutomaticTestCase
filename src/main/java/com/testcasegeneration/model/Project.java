package com.testcasegeneration.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NotEmpty(message = "Please provide name")
    private String name;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private Set<UiElement> uiElement = new HashSet<>();

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private Set<Rule> rules = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UiElement> getUiElement() {
        return uiElement;
    }

    public void setUiElement(Set<UiElement> uiElement) {
        this.uiElement = uiElement;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }
}
