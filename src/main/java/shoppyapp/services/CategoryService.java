package shoppyapp.services;

import shoppyapp.beans.CategoryBean;
import shoppyapp.entities.Category;
import shoppyapp.repositories.CategoryRepository;
import shoppyapp.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryService {

  private final CategoryRepository categoryRepository;

    public CategoryService() {
      this.categoryRepository = new CategoryRepository();
    }

    /**
     * This method is used to get a category by its ID.
     *
     * @param id This is the ID of the category to be fetched.
     * @return Optional<CategoryBean> This returns an Optional that contains the CategoryBean if it exists, else returns an Optional.empty().
     */
    public Optional<CategoryBean> getCategory(int id) {
      // Fetch the category from the repository using the provided ID
      Optional<Category> categoryOptional = categoryRepository.findById(id);

      // Check if the category is present
      if(categoryOptional.isPresent()) {
        // Get the category from the Optional
        Category category = categoryOptional.get();

        // Return a new CategoryBean with the category details
        return Optional.of(
                new CategoryBean(
                        category.getId(),
                        category.getName(),
                        category.getDescription()
                )
        );
      }

      // If the category is not present, return an empty Optional
      return Optional.empty();
    }

    /**
     * This method is used to get a category by its name.
     *
     * @param name This is the name of the category to be fetched.
     * @return Optional<CategoryBean> This returns an Optional that contains the CategoryBean if it exists, else returns an Optional.empty().
     */
    public Optional<CategoryBean> getCategory(String name) {
      // Fetch the category from the repository using the provided name
      Optional<Category> categoryOptional = categoryRepository.findByName(name);

      // Check if the category is present
      if (categoryOptional.isPresent()) {
        // Get the category from the Optional
        Category category = categoryOptional.get();

        // Return a new CategoryBean with the category details
        return Optional.of(
                new CategoryBean(
                        category.getId(),
                        category.getName(),
                        category.getDescription()
                )
        );
      }

      // If the category is not present, return an empty Optional
      return Optional.empty();
    }

    /**
     * This method is used to add a new category.
     *
     * @param name This is the name of the category to be added.
     * @param description This is the description of the category to be added.
     * @return Optional<CategoryBean> This returns an Optional that contains the CategoryBean if it was added successfully, else returns an Optional.empty().
     */
    public Optional<CategoryBean> addCategory(String name, String description) {
      // Check if existing category with the same name exists
      Optional<Category> existingCategory = categoryRepository.findByName(name);

      // If the category already exists, return an empty Optional
      if(existingCategory.isPresent()) {
        return Optional.empty();
      }

      // Create a new category with the provided name and description
      Category category = new Category(name, description);

      // Save the category to the repository
      categoryRepository.save(category);

      // Return a new CategoryBean with the category details
      return Optional.of(
        new CategoryBean(
                category.getId(),
                category.getName(),
                category.getDescription()
        )
      );
    }

    /**
     * This method is used to update an existing category.
     *
     * @param id This is the ID of the category to be updated.
     * @param name This is the new name of the category.
     * @param description This is the new description of the category.
     * @return Optional<CategoryBean> This returns an Optional that contains the updated CategoryBean if it was updated successfully, else returns an Optional.empty().
     */
    public Optional<CategoryBean> updateCategory(int id, String name, String description) {
      // Fetch the category from the repository using the provided ID
      Optional<Category> categoryOptional = categoryRepository.findById(id);

      // Check if the category is present
      if (categoryOptional.isPresent()) {
        // Get the category from the Optional
        Category category = categoryOptional.get();

        // Update the category with the new name and description
        category.setName(name);
        category.setDescription(description);

        // Update the category in the repository
        Optional<Category> updatedCategoryOptional = categoryRepository.update(category);

        // Check if the category was updated successfully
        if (updatedCategoryOptional.isPresent()) {
          // Get the updated category from the Optional
          Category updatedCategory = updatedCategoryOptional.get();

          // Return a new CategoryBean with the updated category details
          return Optional.of(
                  new CategoryBean(
                          updatedCategory.getId(),
                          updatedCategory.getName(),
                          updatedCategory.getDescription()
                  )
          );
        }
      }

      // If the category is not present or was not updated successfully, return an empty Optional
      return Optional.empty();
    }

    /**
     * This method is used to delete a category.
     *
     * @param id This is the ID of the category to be deleted.
     */
    public boolean deleteCategory(int id) {
      // Fetch the category from the repository using the provided ID
      Optional<Category> categoryOptional = categoryRepository.findById(id);

      // Check if the category is present
      if (categoryOptional.isPresent()) {
        // Get the category from the Optional
        Category category = categoryOptional.get();

        // Delete the category from the repository
        boolean deleted = categoryRepository.delete(category);
        if (deleted) {
          // Log a message if the category is deleted successfully
//          LoggerUtil.logMessage("Category deleted with ID: " + id);
        }
        return deleted;
      }

      // Log a message if the category is not present
//      LoggerUtil.logMessage("Category not found with ID: " + id);

      return false;
    }

    // Fetch all categories
    public List<CategoryBean> getAllCategories() {
      // Fetch all categories from the repository
      List<Category> categories = categoryRepository.findAll();

      // Create a new list to store the CategoryBeans
      List<CategoryBean> categoryBeans = new ArrayList<>();

      // Iterate through the categories and create a new CategoryBean for each category
      for (Category category : categories) {
        categoryBeans.add(
                new CategoryBean(
                        category.getId(),
                        category.getName(),
                        category.getDescription()
                )
        );
      }

      // Return the list of CategoryBeans
      return categoryBeans;
    }

}
