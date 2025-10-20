package eci.edu.dosw.dtos;

import java.util.List;

import lombok.Data;

import eci.edu.dosw.enums.TypeChef;

/**
 * Clase DTO para manejar la información de las recetas.
 */
@Data
public class RecipeDTO {
    private String id;
    private String name;
    private String title;
    private String description;
    private List<String> ingredients;
    private List<String> steps;
    private String chefName;
    private TypeChef typeOfChef;
    private Integer season;
   
}
