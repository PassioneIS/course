package models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "recipe_book")
public class RecipeBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_book_id")
    private Short id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "recipeBook", fetch = FetchType.EAGER)
    private List<RecipeBookRecipe> recipeBookRecipes;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public List<RecipeBookRecipe> getRecipeBookRecipes() {
        return recipeBookRecipes;
    }

    public void setRecipeBookRecipes(List<RecipeBookRecipe> recipeBookRecipes) {
        this.recipeBookRecipes = recipeBookRecipes;
    }
}