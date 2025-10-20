package eci.edu.dosw;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eci.edu.dosw.dtos.RecipeDTO;
import eci.edu.dosw.enums.TypeChef;
import eci.edu.dosw.mappers.RecipeMapper;
import eci.edu.dosw.models.Recipe;
import eci.edu.dosw.repository.RecipeRepository;
import eci.edu.dosw.service.impl.RecipeServiceImpl;
import eci.edu.dosw.util.RecipeNotFoundException;

class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeMapper recipeMapper;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private Recipe recipeEntity;
    private RecipeDTO recipeDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        recipeEntity = new Recipe();
        recipeEntity.setId("1");
        recipeEntity.setName("Ensalada de frutas");
        recipeEntity.setDescription("Fácil y rápida");
        recipeEntity.setIngredients(Arrays.asList("Manzana", "Banana"));
        recipeEntity.setSteps(Arrays.asList("Cortar frutas", "Mezclar"));
        recipeEntity.setTypeOfChef(TypeChef.VIEWER);
        recipeEntity.setSeason(1);

        recipeDTO = new RecipeDTO();
        recipeDTO.setId("1");
        recipeDTO.setName("Ensalada de frutas");
        recipeDTO.setDescription("Fácil y rápida");
        recipeDTO.setIngredients(Arrays.asList("Manzana", "Banana"));
        recipeDTO.setSteps(Arrays.asList("Cortar frutas", "Mezclar"));
        recipeDTO.setTypeOfChef(TypeChef.VIEWER);
        recipeDTO.setSeason(1);
    }

    @Test
    void testShouldRegisterRecipeTVviewer() {
        when(recipeMapper.toEntity(recipeDTO)).thenReturn(recipeEntity);
        when(recipeRepository.save(recipeEntity)).thenReturn(recipeEntity);
        when(recipeMapper.toDTO(recipeEntity)).thenReturn(recipeDTO);

        RecipeDTO result = recipeService.registerRecipeTVviewer(recipeDTO);

        assertEquals(TypeChef.VIEWER, result.getTypeOfChef());
    }

    @Test
    void testShouldRegisterParticipantRecipe() {
        when(recipeMapper.toEntity(recipeDTO)).thenReturn(recipeEntity);

        RecipeDTO savedDTO = new RecipeDTO();
        savedDTO.setId("1");
        savedDTO.setName(recipeDTO.getName());
        savedDTO.setDescription(recipeDTO.getDescription());
        savedDTO.setIngredients(recipeDTO.getIngredients());
        savedDTO.setSteps(recipeDTO.getSteps());
        savedDTO.setTypeOfChef(TypeChef.CONTESTAND); 
        savedDTO.setSeason(recipeDTO.getSeason());

        when(recipeRepository.save(recipeEntity)).thenReturn(recipeEntity);
        when(recipeMapper.toDTO(recipeEntity)).thenReturn(savedDTO);

        RecipeDTO result = recipeService.registerParticipantRecipe(recipeDTO);

        assertEquals(TypeChef.CONTESTAND, result.getTypeOfChef());
    }


    @Test
    void testShouldRegisterRecipeChef() {
        when(recipeMapper.toEntity(recipeDTO)).thenReturn(recipeEntity);

        RecipeDTO savedDTO = new RecipeDTO();
        savedDTO.setId("1");
        savedDTO.setName(recipeDTO.getName());
        savedDTO.setDescription(recipeDTO.getDescription());
        savedDTO.setIngredients(recipeDTO.getIngredients());
        savedDTO.setSteps(recipeDTO.getSteps());
        savedDTO.setTypeOfChef(TypeChef.JURIES); 
        savedDTO.setSeason(null);         

        when(recipeRepository.save(recipeEntity)).thenReturn(recipeEntity);
        when(recipeMapper.toDTO(recipeEntity)).thenReturn(savedDTO);

        RecipeDTO result = recipeService.registerRecipeChef(recipeDTO);

        assertEquals(TypeChef.JURIES, result.getTypeOfChef());
        assertNull(result.getSeason());
    }

    @Test
    void testShouldHandleNullSeasonInRecipesBySeason() {
        Recipe recipeWithNullSeason = new Recipe();
        recipeWithNullSeason.setId("3");
        recipeWithNullSeason.setName("Postre sin temporada");
        recipeWithNullSeason.setSeason(null);
        recipeWithNullSeason.setTypeOfChef(TypeChef.CONTESTAND);

        Recipe recipeWithSeason = new Recipe();
        recipeWithSeason.setId("4");
        recipeWithSeason.setName("Sopa de Tomate");
        recipeWithSeason.setSeason(2);
        recipeWithSeason.setTypeOfChef(TypeChef.CONTESTAND);

        RecipeDTO recipeDTOWithSeason = new RecipeDTO();
        recipeDTOWithSeason.setId("4");
        recipeDTOWithSeason.setName("Sopa de Tomate");
        recipeDTOWithSeason.setSeason(2);
        recipeDTOWithSeason.setTypeOfChef(TypeChef.CONTESTAND);

        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipeWithNullSeason, recipeWithSeason));
        when(recipeMapper.toDTO(recipeWithSeason)).thenReturn(recipeDTOWithSeason);

        List<RecipeDTO> result = recipeService.getRecipesBySeason(2);

        assertEquals(1, result.size());
        assertEquals("Sopa de Tomate", result.get(0).getName());
        assertEquals(2, result.get(0).getSeason());
    }



    @Test
    void testShouldSearchRecipesByName() {
        Recipe recipe2 = new Recipe();
        recipe2.setId("2");
        recipe2.setName("Sopa de Tomate");
        recipe2.setSeason(3);
        recipe2.setTypeOfChef(TypeChef.CONTESTAND);

        RecipeDTO recipeDTO2 = new RecipeDTO();
        recipeDTO2.setId("2");
        recipeDTO2.setName("Sopa de Tomate");
        recipeDTO2.setSeason(3);
        recipeDTO2.setTypeOfChef(TypeChef.CONTESTAND);

        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipeEntity, recipe2));
        when(recipeMapper.toDTO(recipeEntity)).thenReturn(recipeDTO);
        when(recipeMapper.toDTO(recipe2)).thenReturn(recipeDTO2);

        List<RecipeDTO> result = recipeService.searchRecipesByName("ensalada");

        assertEquals(1, result.size());
        assertEquals("Ensalada de frutas", result.get(0).getName());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void testShouldGetRecipesBySeason() {
        Recipe recipe2 = new Recipe();
        recipe2.setId("2");
        recipe2.setName("Sopa de Tomate");
        recipe2.setSeason(2);
        recipe2.setTypeOfChef(TypeChef.CONTESTAND);

        RecipeDTO recipeDTO2 = new RecipeDTO();
        recipeDTO2.setId("2");
        recipeDTO2.setName("Sopa de Tomate");
        recipeDTO2.setSeason(2);
        recipeDTO2.setTypeOfChef(TypeChef.CONTESTAND);

        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipeEntity, recipe2));
        when(recipeMapper.toDTO(recipeEntity)).thenReturn(recipeDTO);
        when(recipeMapper.toDTO(recipe2)).thenReturn(recipeDTO2);

        List<RecipeDTO> result = recipeService.getRecipesBySeason(1);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getSeason());
        assertEquals("Ensalada de frutas", result.get(0).getName());
    }


    @Test
    void testShouldGetAllRecipes() {
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipeEntity));
        when(recipeMapper.toDTO(recipeEntity)).thenReturn(recipeDTO);

        List<RecipeDTO> results = recipeService.getAllRecipes();

        assertEquals(1, results.size());
    }

    @Test
    void testShouldGetById() {
        when(recipeRepository.findById("1")).thenReturn(Optional.of(recipeEntity));
        when(recipeMapper.toDTO(recipeEntity)).thenReturn(recipeDTO);

        RecipeDTO result = recipeService.getById("1");

        assertEquals("Ensalada de frutas", result.getName());
    }

    @Test
    void testShouldGetByIdNotFound() {
        when(recipeRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.getById("2"));
    }

    @Test
    void testShouldDeleteRecipe() {
        when(recipeRepository.existsById("1")).thenReturn(true);

        recipeService.deleteRecipe("1");

        verify(recipeRepository, times(1)).deleteById("1");
    }

    @Test
    void testShouldDeleteRecipeNotFound() {
        when(recipeRepository.existsById("2")).thenReturn(false);

        assertThrows(RecipeNotFoundException.class, () -> recipeService.deleteRecipe("2"));
    }

    @Test
    void testShouldUpdateRecipe() {
        when(recipeRepository.findById("1")).thenReturn(Optional.of(recipeEntity));
        when(recipeRepository.save(recipeEntity)).thenReturn(recipeEntity);
        when(recipeMapper.toDTO(recipeEntity)).thenReturn(recipeDTO);

        RecipeDTO updatedDTO = recipeService.updateRecipe("1", recipeDTO);

        assertEquals(recipeDTO.getName(), updatedDTO.getName());
    }

    @Test
    void testShouldUpdateRecipeNotFound() {
        when(recipeRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.updateRecipe("2", recipeDTO));
    }

    @Test
    void testShouldGetByTypeOfChef() {
        when(recipeRepository.findByTypeOfChef(TypeChef.VIEWER)).thenReturn(Arrays.asList(recipeEntity));
        when(recipeMapper.toDTO(recipeEntity)).thenReturn(recipeDTO);

        List<RecipeDTO> results = recipeService.getByTypeOfChef(TypeChef.VIEWER);

        assertEquals(1, results.size());
    }
}
