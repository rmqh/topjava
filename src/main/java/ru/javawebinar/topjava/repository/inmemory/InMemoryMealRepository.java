package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    // user id and 'meal id, meal' map
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.meals) {
            save(1, meal);
        }
        for (Meal meal : MealsUtil.meals2) {
            save(2, meal);
        }
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {}", meal);

        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        } else {
            return meals.computeIfPresent(meal.getId(), (k, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int userId, int mealId) {
        log.info("delete {}", mealId);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null && mealMap.remove(mealId) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        log.info("get {}", mealId);
        Map<Integer, Meal> mealMap = repository.getOrDefault(userId, Collections.emptyMap());
        return mealMap.get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("get all {}");
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getByDate(int userId, LocalDate fromDate, LocalDate toDate) {
        log.info("get by date {}");
        return filterByPredicate(userId, meal -> meal.getDate().isAfter(fromDate) && meal.getDate().isBefore(toDate));
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals.values().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
}

