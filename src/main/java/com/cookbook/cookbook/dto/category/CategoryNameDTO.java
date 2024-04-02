package com.cookbook.cookbook.dto.category;

public class CategoryNameDTO {
    private long id;
    private String name;

    public CategoryNameDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CategoryNameDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryNameDTO(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryNameDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
