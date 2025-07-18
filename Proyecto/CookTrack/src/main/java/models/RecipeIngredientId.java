package models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecipeIngredientId implements Serializable {

    @Column(name = "ingredient_id")
    private int ingredientId;

    @Column(name = "recipe_id")
    private int recipeId;

    // Constructors
    public RecipeIngredientId() {
    }

    public RecipeIngredientId(int ingredientId, int recipeId) {
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
    }

    // Getters, setters, equals, hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeIngredientId that)) return false;
        return ingredientId == that.ingredientId && recipeId == that.recipeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, recipeId);
    }
}
