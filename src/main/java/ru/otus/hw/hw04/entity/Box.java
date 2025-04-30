package ru.otus.hw.hw04.entity;


public class Box {
    private final int size;
    private String color;
    private boolean isOpen;
    private String item;

    public void setColor(String color) {
        this.color = color;
        System.out.println("Box color changed to: " + color);
    }

    public Box(int size, String color) {
        this.size = size;
        this.color = color;
        this.isOpen = false;
        this.item = null;
    }

    public void open() {
        isOpen = true;
        System.out.println("Box is now open");
    }

    public void close() {
        isOpen = false;
        System.out.println("Box is now closed");
    }

    public void putItem(String item) {
        if (!isOpen) {
            System.out.println("Cannot put item - box is closed");
            return;
        }
        if (this.item != null) {
            System.out.println("Cannot put item - box is not empty");
            return;
        }
        this.item = item;
        System.out.println("Item added to box: " + item);
    }

    public void removeItem() {
        if (!isOpen) {
            System.out.println("Cannot remove item - box is closed");
            return;
        }
        if (this.item == null) {
            System.out.println("Cannot remove item - box is empty");
            return;
        }
        String removedItem = this.item;
        this.item = null;
        System.out.println("Item removed from box: " + removedItem);
    }

    public void printInfo() {
        System.out.println("Box info:");
        System.out.println("Size: " + size);
        System.out.println("Color: " + color);
        System.out.println("Status: " + (isOpen ? "open" : "closed"));
        System.out.println("Contains item: " + (item != null ? item : "empty"));
        System.out.println("-".repeat(20));
    }
}
