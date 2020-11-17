package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.BaseDao;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MealController extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealController.class);

    private static final String ACTION = "action";
    private static final String ADD = "add";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String MEAL_PAGE = "/meal.jsp";
    private static final String MEALS_PAGE = "/meals.jsp";

    private BaseDao<Meal> mealDao;

    private static final int CALORIES_LIMIT = 2000;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void init() throws ServletException {
        super.init();
        mealDao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter(ACTION);
        log.debug(String.format("Received action: '%s'", action));

        String forward = MEAL_PAGE;
        int mealId;

        if (action != null) {
            switch (action) {
                case UPDATE:
                    mealId = Integer.parseInt(request.getParameter("mealId"));
                    Meal meal = mealDao.getById(mealId);
                    log.debug(String.format("Meal to be updated: '%s'", meal.toString()));

                    request.setAttribute("meal", meal);
                    break;
                case DELETE:
                    mealId = Integer.parseInt(request.getParameter("mealId"));
                    log.debug(String.format("Meal id to be deleted: '%d'", mealId));

                    mealDao.delete(mealId);
                    response.sendRedirect("meals");
                    return;
                default:
                    break;
            }
        } else {
            log.debug("Listing all meals...");
            forward = MEALS_PAGE;
            request.setAttribute("mealWithExcessList",
                    MealsUtil.filteredByStreams(new ArrayList<>(mealDao.getAll()), LocalTime.MIN, LocalTime.MAX, CALORIES_LIMIT));
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter(ACTION);
        log.debug(String.format("Received action: '%s'", action));

        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("datetime-local").replace("T", " "), formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(0, localDateTime, description, calories);

        switch (action) {
            case UPDATE:
                meal.setId(Integer.parseInt(request.getParameter("id")));
                log.debug(String.format("Meal to be updated: '%s'", meal.toString()));

                mealDao.update(meal);
                break;
            case ADD:
                mealDao.add(meal);
                break;
            default:
                break;
        }

        request.setAttribute("mealWithExcessList",
                MealsUtil.filteredByStreams(new ArrayList<>(mealDao.getAll()), LocalTime.MIN, LocalTime.MAX, CALORIES_LIMIT));
        request.getRequestDispatcher(MEALS_PAGE).forward(request, response);
    }
}
