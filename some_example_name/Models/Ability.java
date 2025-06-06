package io.github.some_example_name.Models;

public class Ability {
    private String name;
    private String description;

    public Ability(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    // برای دیباگ یا نمایش سریع
    @Override
    public String toString() {
        return name + ": " + description;
    }
}
