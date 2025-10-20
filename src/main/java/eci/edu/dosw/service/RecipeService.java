package eci.edu.dosw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import eci.edu.dosw.dtos.RecipeDTO;
import eci.edu.dosw.enums.TypeChef;

/**
 * Clase interfaz de servicio donde tiene los métodos de receta.
 */
@Service
public interface RecipeService {

    RecipeDTO registerRecipeTVviewer(RecipeDTO recipeDTO);
    RecipeDTO registerParticipantRecipe(RecipeDTO recipeDTO);
    RecipeDTO registerRecipeChef(RecipeDTO recipeDTO);
    List<RecipeDTO> getAllRecipes();
    RecipeDTO getById(String id);
    void deleteRecipe(String id);
    RecipeDTO updateRecipe(String id, RecipeDTO recipeDTO);
    List<RecipeDTO> getByTypeOfChef(TypeChef typeChef);
}
