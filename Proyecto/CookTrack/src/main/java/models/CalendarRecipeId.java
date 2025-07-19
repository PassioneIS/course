package models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CalendarRecipeId implements Serializable {

    @Column(name = "calendar_id")
    private short calendarId;

    @Column(name = "recipe_id")
    private int recipeId;

    public CalendarRecipeId() {
    }

    public CalendarRecipeId(short calendarId, int recipeId) {
        this.calendarId = calendarId;
        this.recipeId = recipeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarRecipeId that)) return false;
        return calendarId == that.calendarId && recipeId == that.recipeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(calendarId, recipeId);
    }

    // Getters y setters
}
