-- Insertar usuarios
INSERT INTO users (name, password) VALUES
                                       ('juan', 'juan'),
                                       ('maria', 'maria');

-- Insertar libros de recetas para los usuarios
INSERT INTO recipe_book (user_id) VALUES
                                      (1),
                                      (2);

-- Insertar ingredientes de prueba
INSERT INTO ingredient (name) VALUES
                                           ('Tomato'),
                                           ('Onion' ),
                                           ('Garlic'),
                                           ('Chicken'),
                                           ('Rice'),
                                           ('Potato'),
                                           ('Carrot');

-- Insertar recetas
INSERT INTO recipe (name, preptime) VALUES
                                        ('Chicken Soup', 45),
                                        ('Garlic Rice', 20),
                                        ('Roasted Potatoes', 30);

-- Insertar pasos de receta
INSERT INTO recipe_step (recipe_id, position, text) VALUES
                                                        (1, 1, 'Boil water with chicken.'),
                                                        (1, 2, 'Add vegetables and cook for 30 minutes.'),
                                                        (2, 1, 'Fry garlic in oil.'),
                                                        (2, 2, 'Add rice and cook until done.'),
                                                        (3, 1, 'Peel and cut potatoes.'),
                                                        (3, 2, 'Bake in oven at 180°C for 25 minutes.');

-- Insertar relación receta-ingredientes
INSERT INTO recipe_ingredient (ingredient_id, recipe_id, amount) VALUES
                                                                     (4, 1, 1), -- Chicken Soup: Chicken
                                                                     (1, 1, 2), -- Chicken Soup: Tomato
                                                                     (2, 1, 1), -- Chicken Soup: Onion
                                                                     (3, 2, 2), -- Garlic Rice: Garlic
                                                                     (5, 2, 1), -- Garlic Rice: Rice
                                                                     (6, 3, 3); -- Roasted Potatoes: Potato

-- Insertar posts (solo si las recetas son públicas en recipe_book_recipe)
-- Aquí ejemplo manual
INSERT INTO post (recipe_id, user_id) VALUES
                                          (1, 1),
                                          (2, 2);

-- Insertar ratings de prueba
INSERT INTO rating (user_id, post_id, score) VALUES
                                                 (1, 1, 5),
                                                 (2, 1, 4),
                                                 (2, 2, 5);

-- Insertar calendario de prueba
INSERT INTO calendar (user_id, name) VALUES
                                         (1, 'Weekly Plan'),
                                         (2, 'Maria Meals');

-- Insertar calendario_receta
INSERT INTO calendar_recipe (calendar_id, recipe_id, date) VALUES
(1, 1, NOW()),
(1, 2, NOW() + INTERVAL '1 day'),
(2, 3, NOW());

-- Insertar recipe_book_recipe (usando is_public para activar triggers si lo deseas)
INSERT INTO recipe_book_recipe (recipe_book_id, recipe_id, nametag, favorite, is_public) VALUES
(1, 1, ARRAY['soup', 'lunch'], true, true),
(1, 2, ARRAY['rice', 'dinner'], false, false),
(2, 3, ARRAY['potato', 'bake'], true, true);
