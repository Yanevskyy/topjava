package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 400),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );


        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        UserMealWithExcess userMealWithExcess;

        for (UserMeal meal : meals) {
            if (mealTimeCompareToLocalTime(meal, startTime, endTime)) {

                int caloriesCounter = 0;

                for (UserMeal userMeal : meals) {
                    if (userMeal.getDateTime().toLocalDate().compareTo(meal.getDateTime().toLocalDate()) == 0) {
                        caloriesCounter += userMeal.getCalories();
                    }
                }

                boolean excess = caloriesCounter > caloriesPerDay;

                userMealWithExcess = new UserMealWithExcess(meal, excess);

                userMealWithExcesses.add(userMealWithExcess);
            }
        }


        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        meals.stream().
                filter(meal -> mealTimeCompareToLocalTime(meal, startTime, endTime)).
                forEach(meal -> userMealWithExcessList.add(new UserMealWithExcess(meal, false)));

        return userMealWithExcessList;
    }

    public static boolean mealTimeCompareToLocalTime(UserMeal meal, LocalTime startTime, LocalTime endTime) {
        if (meal.getDateTime().toLocalTime().compareTo(startTime) < 0) return false;
        if (meal.getDateTime().toLocalTime().compareTo(endTime) > 0) return false;
        return true;
    }

}
