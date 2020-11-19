package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(int userId, Meal meal) {
        return repository.save(userId, meal);
    }

    public void delete(int userId, int mealId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(userId, mealId), mealId);
    }

    public Meal get(int userId, int mealId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(userId, mealId), mealId);
    }

    public void update(int userId, Meal meal) {
        checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Meal> getByDate(int userId, LocalDate fromDate, LocalDate toDate) {
        return repository.getByDate(userId, fromDate, toDate);
    }
}