package models;

//import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
//import org.hibernate.annotations.Type;

@Entity
@Table(name = "recipe_book_recipe")
public class RecipeBookRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_book_recipe_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "recipe_book_id")
    private RecipeBook recipeBook;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    /*
    @Type(StringArrayType.class)
    @Column(name = "nametag", columnDefinition = "varchar(31)[]")
    private String[] nametag;
    */

    @Column(name = "favorite")
    private boolean favorite;

    @Column(name = "is_public")
    private boolean isPublic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RecipeBook getRecipeBook() {
        return recipeBook;
    }

    public void setRecipeBook(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /*
    public String[] getNametag() {
        return nametag;
    }

    public void setNametag(String[] nametag) {
        this.nametag = nametag;
    }*/

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
