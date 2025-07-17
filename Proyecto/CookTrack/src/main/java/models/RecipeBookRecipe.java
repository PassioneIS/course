package models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "recipe_book_recipe")
public class RecipeBookRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_book_recipe_id")
    private int id;

    @Column(name = "recipe_book_id")
    private int recipeBookId;

    @Column(name = "recipe_id")
    private int recipeId;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(
            name = "nametag",
            columnDefinition = "varchar(31)[]"
    )
    private List<String> nametag;

    public int getId() {return id;}

    public int getRecipeBookId() {return recipeBookId;}

    public void setRecipeBookId(int recipeBookId) {this.recipeBookId = recipeBookId;}

    public int getRecipeId() {return recipeId;}

    public void setRecipeId(int recipeId) {this.recipeId = recipeId;}

    public List<String> getNametag() {return nametag;}

    public void addNametag(String nametag) {this.nametag.add(nametag);}

}
