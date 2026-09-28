package cput.ac.za.controller;

import cput.ac.za.domain.Category;
import cput.ac.za.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Plain GET /api/categories, matching the Angular BusinessService call.
    @GetMapping({"", "/getAll"})
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @PostMapping("/create")
    public Category create(@RequestBody Category category) {
        return categoryService.create(category);
    }

    @GetMapping("/read/{categoryId}")
    public Category read(@PathVariable Long categoryId) {
        return categoryService.read(categoryId);
    }

    @PutMapping("/update")
    public Category update(@RequestBody Category category) {
        return categoryService.update(category);
    }

    @DeleteMapping("/delete/{categoryId}")
    public boolean delete(@PathVariable Long categoryId) {
        return categoryService.delete(categoryId);
    }
}
