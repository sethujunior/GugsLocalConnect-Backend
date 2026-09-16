package cput.ac.za.controller;

import cput.ac.za.domain.Category;
import cput.ac.za.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
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

    @GetMapping("/getAll")
    public List<Category> getAll() {
        return categoryService.getAll();
    }


}
