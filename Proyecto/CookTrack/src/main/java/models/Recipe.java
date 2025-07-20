package models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Integer id;

    @Column(nullable = false, length = 127)
    private String name;

    @Column(nullable = false)
    private Integer preptime;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> recipeIngredients;

    @OneToMany(mappedBy = "recipe")
    private List<CalendarRecipe> calendarRecipes;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPreptime() {
        return preptime;
    }

    public void setPreptime(Integer preptime) {
        this.preptime = preptime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}