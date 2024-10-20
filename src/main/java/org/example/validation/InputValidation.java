package org.example.validation;

public class InputValidation {
    public InputValidation(){}

    public boolean isValidOption(String s, int start, int end) {
        try {
            int num = Integer.parseInt(s);
            if(num >= start && num <= end)
                return true;
            else {
                System.out.println("Not in range.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric input.");
            return false;
        }
    }

    public boolean isPosInt(String s) {
        try {
            int num = Integer.parseInt(s);
            return num >= 0;
        } catch (NumberFormatException e) {
            System.out.println("Value must be above 0.");
            return false;
        }
    }
}
