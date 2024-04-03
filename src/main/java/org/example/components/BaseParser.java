package org.example.components;

import java.util.StringJoiner;

// Abstract base class for parsing cron expression components
abstract class BaseParser {
    // Properties
    public int start;
    public int end;
    public String name;
    public final int nameColumnLength = 14;

    // Constructor
    public BaseParser(int start, int end, String name) {
        this.start = start;
        this.end = end;
        this.name = name;
    }

    // Method to parse the input cron component
    public String parse(String input) {
        return name +
                " ".repeat(Math.max(0, nameColumnLength - name.length() + 1)) +
                parseTimes(input);
    }

    // Method to parse different types of cron component values
    public String parseTimes(String input) {
        if (input.strip().contains(",")) {
            return commaParser(input);
        } else if (input.strip().contains("/")) {
            return stepParser(input);
        } else if (input.strip().contains("-")) {
            return rangeParser(input);
        } else if (input.strip().equals("*")) {
            return universeParser();
        }

        // Handle validation and return input if it's a single value
        validation(input);
        return input;
    }

    /**
     * Method to parse comma-separated values.
     *
     * @param input The input string containing comma-separated values
     * @return The parsed string
     */
    public String commaParser(String input) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        String[] inputs = input.split(",");
        for (String inp : inputs) {
            stringJoiner.add(parseTimes(inp));
        }
        return stringJoiner.toString();
    }

    /**
     * Method to parse values with steps.
     *
     * @param input The input string containing values with steps
     * @return The parsed string
     */
    public String stepParser(String input) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        int index = input.indexOf("/");
        int start = 0;
        if (input.charAt(index - 1) == '*')
            start = this.start;
        else {
            // Handle validation and parse start value
            validation(input.substring(0, index));
            this.start = Integer.parseInt(input.substring(0, index));
        }

        int step = Integer.parseInt(input.substring(index + 1));

        // Generate values with steps
        for (int i = start; i <= this.end; i = i + step) {
            stringJoiner.add(String.valueOf(i));
        }
        return stringJoiner.toString();
    }

    /**
     * Method to parse values within a range.
     *
     * @param input The input string containing a range of values
     * @return The parsed string
     */
    public String rangeParser(String input) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        int length = input.length();
        int index = input.indexOf("-");

        // Handle validation and parse start and end values
        validation(input.substring(0, index));
        validation(input.substring(index + 1, length));

        int start = Integer.parseInt(input.substring(0, index));
        int end = Integer.parseInt(input.substring(index + 1, length));

        // Generate values within the specified range
        for (int i = start; i <= end; i++) {
            stringJoiner.add(String.valueOf(i));
        }
        return stringJoiner.toString();
    }

    /**
     * Method to parse all possible values.
     *
     * @return The parsed string
     */
    public String universeParser() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (int i = this.start; i <= this.end; i++) {
            stringJoiner.add(String.valueOf(i));
        }
        return stringJoiner.toString();
    }

    /**
     * Method to validate input values.
     *
     * @param value The value to validate
     */
    private void validation(String value) {
        try {
            int integerValue = Integer.parseInt(value);
            if (integerValue >= start && integerValue <= end) {
                return; // Value is valid
            }
            throw new RuntimeException(value + " is not a valid value for " + name);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(value + " is not a correct input for " + name);
        }
    }
}
