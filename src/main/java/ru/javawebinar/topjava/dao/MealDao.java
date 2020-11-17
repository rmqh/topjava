package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDao implements BaseDao<Meal> {
    private final AtomicInteger counter = new AtomicInteger(0);
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private final List<Meal> meals = Arrays.asList(
            new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public MealDao() {
        meals.forEach(meal -> mealMap.put(meal.getId(), meal));
    }

    private int getId() {
        return counter.incrementAndGet();
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(getId());
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Collection<Meal> getAll() {
        return mealMap.values();
    }

    @Override
    public Meal getById(int id) {
        return mealMap.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }
}
