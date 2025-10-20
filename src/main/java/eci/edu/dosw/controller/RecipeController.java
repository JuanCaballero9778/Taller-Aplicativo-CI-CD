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

    @Operation(summary = "Obtener recetas por tipo de chef")
    @GetMapping("/type/{type}")
    public List<RecipeDTO> getByType(@Parameter(description = "Tipo de chef: VIEWER, PARTICIPANT o CHEF") @PathVariable("type") String type) {
        TypeChef typeChef;
        try {
            typeChef = TypeChef.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Tipo de chef inválido. Valores válidos: " + java.util.Arrays.toString(TypeChef.values()));
        }
        return recipeService.getByTypeOfChef(typeChef);
    }
}
