package cput.ac.za.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;
    private String name;

    public Category() {
    }

    public Category(Builder builder) {
        this.categoryID = builder.categoryID;
        this.name = builder.name;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryID=" + categoryID +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder {
        private Long categoryID;
        private String name;

        public Builder setCategoryID(Long categoryID) {
            this.categoryID = categoryID;
            return this;
        }
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder copy(Category category) {
            this.categoryID = category.categoryID;
            this.name = category.name;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}