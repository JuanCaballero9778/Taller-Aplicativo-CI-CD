package eci.edu.dosw.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import eci.edu.dosw.enums.TypeChef;

/**
 * Clase que maneja la información básica de las recetas.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "recipes")
public class Recipe {
    @Id
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
