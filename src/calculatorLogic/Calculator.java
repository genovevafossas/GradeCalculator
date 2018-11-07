package calculatorLogic;

import calculatorUi.UI;
import calculatorUi.NumField;

import java.util.*;

/**
 *
 * @author gfoss
 */

public class Calculator {

    private static UI ui;

    public static void main(String[] args)
    {
        ui = new UI();
    }

    public static double calculateGradeWeight (ArrayList<NumField> earned, ArrayList<NumField>percent)
    {
        double total = 0;
        double earnedVal;
        double percentVal;
        double totalPercent = 0;
        int len = earned.size();

        for (NumField field : percent) {
            totalPercent += field.getValue();
        }

        for (int i = 0; i<len; i++) {
            earnedVal = earned.get(i).getValue();
            percentVal = percent.get(i).getValue();

            total += earnedVal * (percentVal/totalPercent);
        }
        return total;
    }


    public static double calculateGradePoints (ArrayList<NumField> earnedPoints, ArrayList<NumField>totalPoints)
    {
        double totalEarned = 0, totalPossible = 0;

        for (NumField field : earnedPoints) {
            totalEarned += field.getValue();
        }
        for (NumField field : totalPoints) {
            totalPossible += field.getValue();
        }

        return (totalEarned/totalPossible)*100.00;
    }
}
