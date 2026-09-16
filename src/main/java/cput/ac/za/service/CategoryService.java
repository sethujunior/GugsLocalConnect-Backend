package cput.ac.za.service;

import cput.ac.za.domain.Category;
import cput.ac.za.repository.CategoryRepository;
import cput.ac.za.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategory{


    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category read(Long aLong) {
        return categoryRepository.findById(aLong).orElse(null);
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public boolean delete(Long Id) {
        if (categoryRepository.existsById(Id)) {
            categoryRepository.deleteById(Id);
            return true;
        }
        return false;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
