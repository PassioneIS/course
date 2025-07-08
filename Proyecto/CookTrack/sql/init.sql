CREATE TABLE users (
  user_id SMALLSERIAL PRIMARY KEY,
  name VARCHAR(30) NOT NULL UNIQUE,
  password VARCHAR(127) NOT NULL
);

CREATE TABLE recipe_book (
  recipe_book_id SMALLSERIAL PRIMARY KEY,
  user_id SMALLINT NOT NULL REFERENCES users (user_id)
);
/*orde recipe*/

CREATE TABLE recipe (
  recipe_id SERIAL PRIMARY KEY,
  name VARCHAR(127) NOT NULL,
  preptime INTEGER CHECK (preptime > 0)
);

CREATE TABLE ingredient (
  ingredient_id SERIAL PRIMARY KEY,
  name VARCHAR(31) NOT NULL
);

CREATE TABLE calendar (
  calendar_id SMALLSERIAL PRIMARY KEY,
  user_id SMALLINT NOT NULL REFERENCES users (user_id),
  name VARCHAR(31)
  /*PRIMARY KEY (calendar_id, user_id)*/
);

CREATE TABLE post (
  post_id SERIAL PRIMARY KEY,
  recipe_id INTEGER NOT NULL REFERENCES recipe (recipe_id),
  user_id SMALLINT NOT NULL REFERENCES users (user_id),
  published_at timestamp NOT NULL DEFAULT NOW()
);

CREATE TABLE rating (
  user_id SMALLINT REFERENCES users (user_id),
  post_id INTEGER REFERENCES post (post_id),
  score SMALLINT NOT NULL CHECK (score BETWEEN 1 AND 5),
  rated_at timestamp NOT NULL DEFAULT NOW(),
  PRIMARY KEY (user_id, post_id)
);


CREATE TABLE recipe_book_recipe (
  recipe_book_recipe_id SERIAL PRIMARY KEY,
  recipe_book_id SMALLINT NOT NULL REFERENCES recipe_book (recipe_book_id),
  recipe_id INTEGER NOT NULL REFERENCES recipe (recipe_id),
  nametag VARCHAR(31)[],
  favorite BOOLEAN DEFAULT FALSE,
  is_public BOOLEAN DEFAULT FALSE
);

CREATE TABLE recipe_ingredient (
  ingredient_id INTEGER NOT NULL REFERENCES ingredient (ingredient_id),
  recipe_id INTEGER NOT NULL REFERENCES recipe (recipe_id),
  amount SMALLINT NOT NULL CHECK (amount > 0),
  PRIMARY KEY (ingredient_id, recipe_id)
);

CREATE TABLE recipe_step (
  step_id SERIAL PRIMARY KEY,
  recipe_id INTEGER NOT NULL REFERENCES recipe (recipe_id),
  position SMALLINT NOT NULL CHECK (position > 0),
  text TEXT NOT NULL
);


CREATE TABLE calendar_recipe (
  calendar_id SMALLINT NOT NULL REFERENCES calendar (calendar_id),
  recipe_id INTEGER NOT NULL REFERENCES recipe (recipe_id),
  date timestamp NOT NULL,
  PRIMARY KEY (calendar_id, recipe_id)
);


/*
	TODO:
  Función crear post al cambiar receta a pública
  Función impedir cambiar de público a privado
*/

CREATE OR REPLACE FUNCTION check_unique_recipe_name_per_user()
RETURNS TRIGGER AS $$
DECLARE
  new_recipe_name TEXT;
BEGIN
  SELECT name INTO new_recipe_name
  FROM recipe
  WHERE recipe_id = NEW.recipe_id;

  IF EXISTS (
    SELECT 1
    FROM recipe_book_recipe rbr
    JOIN recipe_book rb ON rbr.recipe_book_id = rb.recipe_book_id
    JOIN recipe r ON r.recipe_id = rbr.recipe_id
    WHERE rb.user_id = (
      SELECT user_id FROM recipe_book WHERE recipe_book_id = NEW.recipe_book_id
    )
    AND LOWER(r.name) = LOWER(new_recipe_name)
    AND r.recipe_id <> NEW.recipe_id
  ) THEN
    RAISE EXCEPTION 'User already owns a recipe named "%".', new_recipe_name;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_unique_recipe_name_per_user
BEFORE INSERT OR UPDATE ON recipe_book_recipe
FOR EACH ROW
EXECUTE FUNCTION enforce_unique_recipe_name_per_user();

CREATE OR REPLACE FUNCTION create_post_on_public_recipe()
RETURNS TRIGGER AS $$
DECLARE
  recipe_owner_id SMALLINT;
BEGIN
  IF (OLD.is_public IS DISTINCT FROM TRUE) AND (NEW.is_public = TRUE) THEN

    SELECT user_id INTO recipe_owner_id
    FROM recipe_book
    WHERE recipe_book_id = NEW.recipe_book_id;

    INSERT INTO post (recipe_id, user_id)
    VALUES (NEW.recipe_id, recipe_owner_id);
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_create_post_on_public_recipe
AFTER UPDATE ON recipe_book_recipe
FOR EACH ROW
EXECUTE FUNCTION create_post_on_public_recipe();

CREATE OR REPLACE FUNCTION prevent_public_demotion()
RETURNS TRIGGER AS $$
BEGIN
  IF (OLD.is_public = TRUE) AND (NEW.is_public = FALSE) THEN
    RAISE EXCEPTION 'Once a recipe is made public, it cannot be made private again.';
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_prevent_public_demotion
BEFORE UPDATE ON recipe_book_recipe
FOR EACH ROW
EXECUTE FUNCTION prevent_public_demotion();