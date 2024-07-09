package org.bohdan.mlh;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;


public class ProductsDB {

    private static final Products monSunProd = new Products(14, 2, 5.0,
            6.0, 1.0, 1.0, 0.5, 4.0, 1.0, 1.0,
            5, 1, 3.0, 1.0,
            1.0, 1.0, 1, 1, 1);
    private static final Products tuesdayProd = new Products(14, 1, 4.0,
            5.0, 1.0, 1.0, 0.5, 4.0, 0.5, 1.0,
            4, 1, 2.0, 1.0, 1.0, 1.0,
            1, 1, 1);
    private static final Products wednesdayProd = new Products(14, 2, 5.0,
            6.0, 1.0, 1.0, 0.5, 6.0, 1.0, 1.0,
            5, 1, 3.0, 1.0,
            1.0, 2.0, 1, 1, 1);
    private static final Products thursFriSaturProd = new Products(14, 3, 6.0,
            7.0, 2.0, 1.0, 0.5, 6.0, 1.0, 2.0,
            6, 1, 4.0,
            2.0, 2.0, 2.0, 1, 1, 1);

    public static Products getTodayMissingProducts (Products beginProd){
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        String today = dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH);
        Products result = new Products();

        switch (today) {
            case "Monday":
            case "Sunday":
                result = Products.subtract(monSunProd, beginProd);
                break;
            case "Tuesday":
            result = Products.subtract(tuesdayProd, beginProd);
                break;
            case "Wednesday":
                result = Products.subtract(wednesdayProd, beginProd);
                break;
            case "Thursday":
            case "Friday":
            case "Saturday":
                result = Products.subtract(thursFriSaturProd, beginProd);
                break;
        }

        result.replaceNegativeValues();

        return result;
    }
}
