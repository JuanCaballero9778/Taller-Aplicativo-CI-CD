package eci.edu.dosw.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import eci.edu.dosw.enums.TypeChef;
import eci.edu.dosw.models.Recipe;


public interface RecipeRepository extends MongoRepository<Recipe, String> {
    List<Recipe> findByTypeOfChef(TypeChef typeChef);
}
