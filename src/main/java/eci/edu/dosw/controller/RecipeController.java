package eci.edu.dosw.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import lombok.RequiredArgsConstructor;

import eci.edu.dosw.dtos.RecipeDTO;
import eci.edu.dosw.enums.TypeChef;
import eci.edu.dosw.service.RecipeService;

/**
 * Clase controlador para  el CRUD de las recetas y sus funcionalidades.
 */
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @Operation(summary = "Registrar receta de un televidente")
    @PostMapping("/viewer")
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDTO registerViewerRecipe(@RequestBody RecipeDTO recipeDTO) {
        return recipeService.registerRecipeTVviewer(recipeDTO);
    }

    @Operation(summary = "Registrar receta de un participante")
    @PostMapping("/participant")
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDTO registerParticipantRecipe(@RequestBody RecipeDTO recipeDTO) {
        return recipeService.registerParticipantRecipe(recipeDTO);
    }

    @Operation(summary = "Registrar receta de un chef")
    @PostMapping("/chef")
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDTO registerChefRecipe(@RequestBody RecipeDTO recipeDTO) {
        return recipeService.registerRecipeChef(recipeDTO);
    }

    @Operation(summary = "Obtener todas las recetas", description = "Devuelve todas las recetas registradas")
    @GetMapping
    public List<RecipeDTO> getAll() {
        return recipeService.getAllRecipes();
    }

    @Operation(summary = "Obtener receta por ID")
    @GetMapping("/{id}")
    public RecipeDTO getById(@Parameter(description = "ID de la receta a buscar") @PathVariable String id) {
        return recipeService.getById(id);
    }
    
    @Operation(summary = "Obtener recetas por tipo de chef")
    @GetMapping("/type/{typeChef}")
    public List<RecipeDTO> getByType(@Parameter(description = "Tipo de chef: VIEWER, CONTESTAND o CHEF") @PathVariable TypeChef typeChef) {
        return recipeService.getByTypeOfChef(typeChef);
    }

    @Operation(summary = "Buscar recetas por nombre")
    @GetMapping("/search")
    public List<RecipeDTO> searchRecipesByName(@RequestParam String name) {
        return recipeService.searchRecipesByName(name);
    }

    @Operation(summary = "Obtener recetas por temporada")
    @GetMapping("/season/{season}")
    public List<RecipeDTO> getRecipesBySeason(@PathVariable int season) {
        return recipeService.getRecipesBySeason(season);
    }

    @Operation(summary = "Actualizar receta")
    @PutMapping("/{id}")
    public RecipeDTO updateRecipe(@PathVariable String id, @RequestBody RecipeDTO recipeDTO) {
        return recipeService.updateRecipe(id, recipeDTO);
    }

    @Operation(summary = "Eliminar receta")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@Parameter(description = "ID de la receta a buscar") @PathVariable String id) {
        recipeService.deleteRecipe(id);
    }

    @Operation(summary = "Actualizar un campo de la receta")
    @PatchMapping("/{id}")
    public RecipeDTO updateRecipeField(@Parameter(description = "ID de la receta a actualizar") @PathVariable String id, 
            @Parameter(description = "Campo a actualizar: name, title, description, typeOfChef, season") @RequestParam String field,
            @Parameter(description = "Valor del campo a actualizar") @RequestParam String value) {

        RecipeDTO recipe = recipeService.getById(id);

        switch (field.toLowerCase()) {
            case "name":
                recipe.setName(value);
                break;
            case "title":
                recipe.setTitle(value);
                break;
            case "description":
                recipe.setDescription(value);
                break;
            case "typeofchef":
                try {
                    recipe.setTypeOfChef(TypeChef.valueOf(value.toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException("Valor inválido para typeOfChef. Valores válidos: " 
                            + java.util.Arrays.toString(TypeChef.values()));
                }
                break;
            case "season":
                try {
                    recipe.setSeason(Integer.parseInt(value));
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Valor inválido para season. Debe ser un número.");
                }
                break;
            default:
                throw new IllegalArgumentException("Campo inválido. Los campos válidos son: name, title, description, typeOfChef, season");
        }

        return recipeService.updateRecipe(id, recipe);
    }
}
