package eci.edu.dosw;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eci.edu.dosw.controller.RecipeController;
import eci.edu.dosw.dtos.RecipeDTO;
import eci.edu.dosw.enums.TypeChef;
import eci.edu.dosw.service.RecipeService;

class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    private RecipeDTO viewerRecipe;
    private RecipeDTO chefRecipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        viewerRecipe = new RecipeDTO();
        viewerRecipe.setId("1");
        viewerRecipe.setName("Ensalada de frutas");
        viewerRecipe.setTitle("Frutal");
        viewerRecipe.setDescription("Fácil y rápida");
        viewerRecipe.setIngredients(Arrays.asList("Manzana", "Banana", "Fresas"));
        viewerRecipe.setSteps(Arrays.asList("Cortar frutas", "Mezclar", "Servir"));
        viewerRecipe.setChefName("Juan");
        viewerRecipe.setTypeOfChef(TypeChef.VIEWER);
        viewerRecipe.setSeason(1);

        chefRecipe = new RecipeDTO();
        chefRecipe.setId("2");
        chefRecipe.setName("Pasta Boloñesa");
        chefRecipe.setTitle("Nutritiva");
        chefRecipe.setDescription("Salsa Boloñesa casera");
        chefRecipe.setIngredients(Arrays.asList("Pasta", "Carne", "Queso"));
        chefRecipe.setSteps(Arrays.asList("Cocer pasta", "Preparar salsa", "Mezclar"));
        chefRecipe.setChefName("Martin");
        chefRecipe.setTypeOfChef(TypeChef.JURIES);
        chefRecipe.setSeason(1);
    }

    @Test
    void testShouldGetAll() {
        when(recipeService.getAllRecipes()).thenReturn(Arrays.asList(viewerRecipe, chefRecipe));

        List<RecipeDTO> recipes = recipeController.getAll();

        assertEquals(2, recipes.size());
    }

    @Test
    void testShouldGetById() {
        when(recipeService.getById("1")).thenReturn(viewerRecipe);

        RecipeDTO recipe = recipeController.getById("1");

        assertEquals("Ensalada de frutas", recipe.getName());
    }

    @Test
    void testShouldRegisterViewerRecipe() {
        when(recipeService.registerRecipeTVviewer(viewerRecipe)).thenReturn(viewerRecipe);

        RecipeDTO saved = recipeController.registerViewerRecipe(viewerRecipe);

        assertEquals(TypeChef.VIEWER, saved.getTypeOfChef());
    }

    @Test
    void testShouldUpdateRecipeField() {
        RecipeDTO updatedRecipe = new RecipeDTO();
        updatedRecipe.setId("1");
        updatedRecipe.setName("BananaSplit");
        updatedRecipe.setTitle("Frutal");
        updatedRecipe.setTypeOfChef(TypeChef.VIEWER);
        updatedRecipe.setSeason(1);
        updatedRecipe.setDescription("Fácil y rápida");

        when(recipeService.getById("1")).thenReturn(viewerRecipe);
        when(recipeService.updateRecipe("1", viewerRecipe)).thenReturn(updatedRecipe);

        RecipeDTO result = recipeController.updateRecipeField("1", "name", "BananaSplit");

        assertEquals("BananaSplit", result.getName());
    }

    @Test
    void testShouldGetByType() {
        when(recipeService.getByTypeOfChef(TypeChef.JURIES)).thenReturn(Arrays.asList(chefRecipe));

        List<RecipeDTO> recipes = recipeController.getByType(TypeChef.JURIES);

        assertEquals(1, recipes.size());
        assertEquals(TypeChef.JURIES, recipes.get(0).getTypeOfChef());
    }

    @Test
    void testShouldUpdateRecipeTitle() {
        when(recipeService.getById("1")).thenReturn(viewerRecipe);
        when(recipeService.updateRecipe(eq("1"), any())).thenReturn(viewerRecipe);

        RecipeDTO result = recipeController.updateRecipeField("1", "title", "Nuevo Titulo");
        assertEquals("Nuevo Titulo", result.getTitle());
    }

    @Test
    void testShoudlUpdateRecipeDescription() {
        when(recipeService.getById("1")).thenReturn(viewerRecipe);
        when(recipeService.updateRecipe(eq("1"), any())).thenReturn(viewerRecipe);

        RecipeDTO result = recipeController.updateRecipeField("1", "description", "Nueva descripcion");
        assertEquals("Nueva descripcion", result.getDescription());
    }

    @Test
    void testShouldUpdateRecipeTypeOfChef() {
        when(recipeService.getById("1")).thenReturn(viewerRecipe);
        when(recipeService.updateRecipe(eq("1"), any())).thenReturn(viewerRecipe);

        RecipeDTO result = recipeController.updateRecipeField("1", "typeOfChef", "JURIES");
        assertEquals(TypeChef.JURIES, result.getTypeOfChef());
    }

    @Test
    void testShouldUpdateRecipeSeason() {
        when(recipeService.getById("1")).thenReturn(viewerRecipe);
        when(recipeService.updateRecipe(eq("1"), any())).thenReturn(viewerRecipe);

        RecipeDTO result = recipeController.updateRecipeField("1", "season", "5");
        assertEquals(5, result.getSeason());
    }

    @Test
    void testShouldUpdateRecipeInvalidField() {
        when(recipeService.getById("1")).thenReturn(viewerRecipe);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                recipeController.updateRecipeField("1", "invalidField", "value")
        );
        assertTrue(exception.getMessage().contains("Campo inválido"));
    }

    @Test
    void testShouldUpdateRecipeInvalidTypeOfChef() {
        when(recipeService.getById("1")).thenReturn(viewerRecipe);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                recipeController.updateRecipeField("1", "typeOfChef", "INVALID")
        );
        assertTrue(exception.getMessage().contains("Valor inválido para typeOfChef"));
    }

    @Test
    void testShouldUpdateRecipeInvalidSeason() {
        when(recipeService.getById("1")).thenReturn(viewerRecipe);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                recipeController.updateRecipeField("1", "season", "noNumber")
        );
        assertTrue(exception.getMessage().contains("Valor inválido para season"));
    }

    @Test
    void testShouldRegisterParticipantRecipe() {
        when(recipeService.registerParticipantRecipe(viewerRecipe)).thenReturn(viewerRecipe);

        RecipeDTO result = recipeController.registerParticipantRecipe(viewerRecipe);

        assertEquals(TypeChef.VIEWER, result.getTypeOfChef());
    }

    @Test
    void testShouldRegisterChefRecipe() {
        when(recipeService.registerRecipeChef(chefRecipe)).thenReturn(chefRecipe);

        RecipeDTO result = recipeController.registerChefRecipe(chefRecipe);
        
        assertEquals(TypeChef.JURIES, result.getTypeOfChef());
    }
    @Test
    void testShouldUpdateRecipe() {
        RecipeDTO updatedRecipe = new RecipeDTO();
        updatedRecipe.setId("1");
        updatedRecipe.setName("Caldo de papa");
        updatedRecipe.setTitle("Caldo deluxe");
        updatedRecipe.setDescription("Más delicioso");
        updatedRecipe.setTypeOfChef(TypeChef.VIEWER);
        updatedRecipe.setSeason(2);

        when(recipeService.updateRecipe("1", updatedRecipe)).thenReturn(updatedRecipe);

        RecipeDTO result = recipeController.updateRecipe("1", updatedRecipe);

        assertEquals("Caldo de papa", result.getName());
        assertEquals("Caldo deluxe", result.getTitle());
        assertEquals(2, result.getSeason());
    }

    @Test
    void testShouldDeleteRecipe() {

        recipeController.deleteRecipe("1");
        verify(recipeService, times(1)).deleteRecipe("1");
    }

}
