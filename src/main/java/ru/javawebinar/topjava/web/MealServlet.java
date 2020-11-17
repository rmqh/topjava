package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static List<MealTo> mealWithExcess;

    @Override
    public void init() throws ServletException {
        mealWithExcess = MealsUtil.getAllWithExcess(2000);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Setting 'mealWithExcessList' attribute...");
        req.setAttribute("mealWithExcessList", mealWithExcess);

        log.debug("Redirecting to meals...");
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
