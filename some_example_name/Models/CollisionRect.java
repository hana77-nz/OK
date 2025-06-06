package io.github.some_example_name.Models;

public class CollisionRect {  //تعریف مستطیل فرضی برای کاراکترها
    float x, y , width, height;

    public CollisionRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean collidesWith(CollisionRect rect) {
        return x < rect.x + rect.width && y <rect.y + rect.height && x + width > rect.x && y + height > rect.y;
    }
}
