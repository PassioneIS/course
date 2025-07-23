package services;

import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import models.Recipe;
import models.Ingredient;
import models.RecipeIngredient;
import models.RecipeStep;
import models.RecipeBook;
import models.RecipeBookRecipe;

import dao.impl.RecipeDaoImpl;
import dao.impl.RecipeIngredientDaoImpl;
import dao.impl.RecipeStepDaoImpl;
import dao.impl.RecipeBookDaoImpl;
import dao.interfaces.RecipeBookDao;
import dao.impl.RecipeBookRecipeDaoImpl;

import java.io.File;
import java.util.List;

import java.io.IOException;


public class PDFService {

    public void downloadPDF(Recipe recipe){

        IngredientService ingredientService = new IngredientService();
        BookRecipeService bookRecipeService = new BookRecipeService();


        String recipeName =  recipe.getName();
        String prepTime = recipe.getPreptime().toString();

        List<RecipeIngredient> recipeIngredients = ingredientService.getRecipeIngredients(recipe);
        List<RecipeStep> recipeStepList = ingredientService.getRecipeStepbyRecipe( recipe );

        RecipeBookRecipe recipeBookRecipe = bookRecipeService.findByRecipe(recipe);
        List<String> nameTags = recipeBookRecipe.getNametag();
        boolean isFavorite = recipeBookRecipe.isFavorite();


        try (PDDocument document = new PDDocument()) {


            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar receta como PDF");
            fileChooser.setInitialFileName(recipe.getName() + ".pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));

            File file = fileChooser.showSaveDialog(null);
            if (file == null) return;
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);
            content.setFont(PDType1Font.HELVETICA, 12);
            content.beginText();
            content.newLineAtOffset(50, 700);

            content.showText(recipeName);
            content.newLineAtOffset(0, -20);

            content.showText("Tiempo de preparación:" + prepTime + " minutos");
            content.newLineAtOffset(0, -20);

            content.showText("Ingredientes:");
            content.newLineAtOffset(0, -20);

            for(RecipeIngredient recipeIngredient : recipeIngredients){
                String ingredientName = (ingredientService.getIngredientById( recipeIngredient.getIngredient().getId() ) ).getName();
                int amount = recipeIngredient.getAmount();

                content.showText(" *" + amount + " " + ingredientName);
                content.newLineAtOffset(0, -20);
            }

            content.showText("Pasos:");
            content.newLineAtOffset(0, -20);

            for(RecipeStep recipeStep: recipeStepList){
                int numStep = recipeStep.getPosition();
                String text = recipeStep.getText();

                content.showText("* Paso " + numStep + ": " + text);
                content.newLineAtOffset(0, -20);
            }

            content.showText(" Tags ");
            content.newLineAtOffset(0, -20);

            for(String tag : nameTags){
                content.showText(" *" + tag);
                content.newLineAtOffset(0, -20);
            }

            if(isFavorite){
                content.showText("La receta es favorita");
                content.newLineAtOffset(0, -20);
            }

            content.endText();
            content.close();

            //document.save("Recetas/" + recipeName +".pdf");
            //System.out.println("PDF generado correctamente.");
            document.save(file);
            System.out.println("PDF guardado en: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}

