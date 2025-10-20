package eci.edu.dosw.mappers;

import org.mapstruct.Mapper;

import eci.edu.dosw.dtos.RecipeDTO;
import eci.edu.dosw.models.Recipe;

/**
 * Interfaz que mapea el DTO y Entity de la receta.
 */
@Mapper(componentModel = "spring")
public interface RecipeMapper {
    RecipeDTO toDTO(Recipe recipe);
    Recipe toEntity(RecipeDTO recipeDTO);
}
