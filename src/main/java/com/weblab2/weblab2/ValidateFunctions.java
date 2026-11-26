package com.weblab2.weblab2;

import java.util.*;
import java.util.regex.Pattern;

public class ValidateFunctions {

    private static final String OK_MESSAGE = "Верное значение.";
    private static final Pattern INT = Pattern.compile("-?\\d{1,9}");
    private static final Pattern DECIMAL = Pattern.compile("-?\\d+([.,]\\d+)?");

    public static PointList validateParams(List<String>xs_str, String y_str, String r_str) throws ValidationException{
        String xMsg = OK_MESSAGE;
        String yMsg = OK_MESSAGE;
        String rMsg = OK_MESSAGE;
        PointList pointList = new PointList();
        List<Integer> validatedXs = new ArrayList<>();
        List<String> xErrors = new ArrayList<>();
        Double y = null;
        Double r = null;

        try {
            y = validateY(y_str);
        } catch (ValidationException e){
            yMsg = e.getMessage();
        }

        try {
            r = validateR(r_str);
        } catch (ValidationException e){
            rMsg = e.getMessage();
        }

        if (xs_str == null || xs_str.isEmpty()){
            xMsg = "Выберите хотя бы одно значение X.";
        }
        else if (xs_str.size() > 9){
            xMsg = "Слишком много значений X.";
        }
        else {
            for (String x : xs_str) {
                try {
                    validatedXs.add(validateX(x));
                } catch (ValidationException e){
                    xErrors.add(e.getMessage());
                }
            }
            if (!xErrors.isEmpty()){
                xMsg = String.join(" ", xErrors);
            }
        }
        if (!xMsg.equals(OK_MESSAGE) || !yMsg.equals(OK_MESSAGE) || !rMsg.equals(OK_MESSAGE)){
            throw new ValidationException(List.of(xMsg, yMsg, rMsg));
        }
        for (Integer x : validatedXs){
            pointList.addPoint(new Point(x, y, r));
        }
        return pointList;
    }

    public static int validateX(String s) {
        if (s == null || s.trim().isEmpty()){
            throw new ValidationException("Не введён X.");
        }
        if (!INT.matcher(s.trim()).matches()) {
            throw new ValidationException("X должен быть целым числом");
        }
        int x = Integer.parseInt(s.trim());
        if (x < -5 || x > 3) {
            throw new ValidationException("X должен быть от -5 до 3");
        }
        return x;
    }

    public static double validateY(String s) {
        if (s == null || s.trim().isEmpty()){
            throw new ValidationException("Не введён Y.");
        }
        if (!DECIMAL.matcher(s.trim()).matches()) {
            throw new ValidationException("Y должен быть числом");
        }
        double y = Double.parseDouble(s.trim().replace(',', '.'));
        if (y <= -5 || y >= 3) {
            throw new ValidationException("Y должен быть в интервале (-5, 3)");
        }
        return y;
    }

    public static double validateR(String s) {
        if (s == null || s.trim().isEmpty()){
            throw new ValidationException("Не введён R.");
        }
        if (!DECIMAL.matcher(s.trim()).matches()) {
            throw new ValidationException("R должен быть числом");
        }
        double r = Double.parseDouble(s.trim().replace(',', '.'));
        if (r <= 2 || r >= 5) {
            throw new ValidationException("Y должен быть в интервале (2, 5)");
        }
        return r;
    }

    public static boolean checkIfInsideFigure(double x, double y, double r) {
        if (x <= 0 && y >= 0) return x * x + y * y <= (double) r * r;
        if (x >= 0 && y <= 0) return y >= -r && x <= r / 2.0;
        if (x <= 0 && y <= 0) return y >= -2.0 * x - r;
        return false;
    }
}
