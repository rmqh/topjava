package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.*;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    // create

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(authUserId(), meal);
    }

    // read

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll(authUserId());
    }

    public List<MealTo> getAllWithExcess() {
        log.info("getAll");
        return getTos(getAll(), DEFAULT_CALORIES_PER_DAY);
    }

    public List<Meal> getByDate(LocalDate fromDate, LocalDate toDate) {
        log.info("get filtered by date");
        return service.getByDate(authUserId(), fromDate, toDate);
    }

    public List<MealTo> getByDateAndTimeWithExcess(String fromDate, String toDate, String fromTime, String toTime) {
        log.info("get date and time {}", String.format("Date: %s-%s, Time: %s-%s.", fromDate, toDate, fromTime, toTime));
        LocalDate fd = fromDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(fromDate);
        LocalDate td = toDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(toDate);
        LocalTime ft = fromTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(fromTime);
        LocalTime tt = toTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(toTime);

        return getFilteredTos(getByDate(fd, td), DEFAULT_CALORIES_PER_DAY, ft, tt);
    }

    public Meal getById(int mealId) {
        log.info("get by id {}", mealId);
        return service.get(authUserId(), mealId);
    }

    // update

    public void update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, mealId);
        assureIdConsistent(meal, mealId);
        service.update(authUserId(), meal);
    }

    // delete

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(authUserId(), mealId);
    }

}