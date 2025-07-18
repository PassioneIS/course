package models;

import java.io.Serializable;
import java.util.Objects;

public class RatingId implements Serializable {
    private Short user;
    private Integer post;

    public RatingId() {
    }

    public RatingId(Short user, Integer post) {
        this.user = user;
        this.post = post;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingId that)) return false;
        return Objects.equals(user, that.user) &&
                Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, post);
    }
}
