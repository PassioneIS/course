package models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "calendar_recipe")
public class CalendarRecipe {

    @EmbeddedId
    private CalendarRecipeId id;

    @ManyToOne
    @MapsId("calendarId")
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public CalendarRecipeId getId() {
        return id;
    }

    public void setId(CalendarRecipeId id) {
        this.id = id;
    }

}

