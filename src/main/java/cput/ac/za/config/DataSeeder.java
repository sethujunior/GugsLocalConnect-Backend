package cput.ac.za.config;

import cput.ac.za.domain.Category;
import cput.ac.za.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

// Without this, business signup fails on a fresh database: it looks up a
// Category by name and throws if none exists, but nothing was ever
// creating categories in the first place. Seeds the same categories shown
// as pills on the Angular homepage, only if the table is empty.
@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataSeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            return;
        }

        List<String> defaults = List.of(
                "Hair & Beauty", "Food & Catering", "Trades", "Tutoring", "Transport", "Spaza"
        );

        for (String name : defaults) {
            categoryRepository.save(new Category.Builder().setName(name).build());
        }
    }
}
