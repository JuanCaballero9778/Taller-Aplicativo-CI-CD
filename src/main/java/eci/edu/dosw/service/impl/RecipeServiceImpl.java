package eci.edu.dosw.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import eci.edu.dosw.dtos.RecipeDTO;
import eci.edu.dosw.enums.TypeChef;
import eci.edu.dosw.mappers.RecipeMapper;
import eci.edu.dosw.models.Recipe;
import eci.edu.dosw.repository.RecipeRepository;
import eci.edu.dosw.service.RecipeService;
import eci.edu.dosw.util.RecipeNotFoundException;

/**
 * Clase que implementa la interfaz de servicio para las recetas.
 */
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Override
    public RecipeDTO registerRecipeTVviewer(RecipeDTO recipeDTO) {
        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        recipe.setTypeOfChef(TypeChef.VIEWER);
        recipe.setSeason(null);
        return recipeMapper.toDTO(recipeRepository.save(recipe));
    }

    @Override
    public RecipeDTO registerParticipantRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        recipe.setTypeOfChef(TypeChef.CONTESTAND);
        return recipeMapper.toDTO(recipeRepository.save(recipe));
    }

    @Override
    public RecipeDTO registerRecipeChef(RecipeDTO recipeDTO) {
        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        recipe.setTypeOfChef(TypeChef.JURIES);
        recipe.setSeason(null);
        return recipeMapper.toDTO(recipeRepository.save(recipe));
    }

    @Override
    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toDTO)
                .toList();
    }

    @Override
    public RecipeDTO getById(String id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("No se encontró la receta con id: " + id));
        return recipeMapper.toDTO(recipe);
    }

    @Override
    public void deleteRecipe(String id) {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException("No se puede eliminar. No existe la receta con id: " + id);
        }
        recipeRepository.deleteById(id);
    }

    @Override
    public RecipeDTO updateRecipe(String id, RecipeDTO recipeDTO) {
        Recipe existing = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("No se puede actualizar. No existe la receta con id: " + id));

        existing.setName(recipeDTO.getName());
        existing.setDescription(recipeDTO.getDescription());
        existing.setIngredients(recipeDTO.getIngredients());
        existing.setSteps(recipeDTO.getSteps());
        existing.setSeason(recipeDTO.getSeason());

        return recipeMapper.toDTO(recipeRepository.save(existing));
    }

    @Override
    public List<RecipeDTO> getByTypeOfChef(TypeChef typeChef) {
        List<Recipe> recipes = recipeRepository.findByTypeOfChef(typeChef);
        return recipes.stream()
                .map(recipeMapper::toDTO)
                .toList();
    }
}
