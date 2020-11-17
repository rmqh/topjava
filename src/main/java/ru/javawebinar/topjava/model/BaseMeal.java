package ru.javawebinar.topjava.model;

public abstract class BaseMeal {
    private int id;

    public BaseMeal() {
    }

    public BaseMeal(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseMeal{" +
                "id=" + id +
                '}';
    }
}
