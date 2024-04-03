package org.example;

import org.example.components.*;

import java.util.StringJoiner;

// Class responsible for parsing a cron expression
public class CronExpressionParser {
    // Initialize parsers for different parts of the cron expression
    private static final MinuteParser minuteParser = new MinuteParser();
    private static final HourParser hourParser = new HourParser();
    private static final DayParser dayParser = new DayParser();
    private static final MonthParser monthParser = new MonthParser();
    private static final WeekParser weekParser = new WeekParser();
    private static final CommandParser commandParser = new CommandParser();

    // Main method to execute the parser
    public static void main(String[] args) {
        int argsLength = args.length;
        // Check if arguments are provided
        if (argsLength == 0)
            throw new RuntimeException("No arguments passed.");

        CronExpressionParser cronExpressionParser = new CronExpressionParser();
        // Parse the input cron expression
        String output = cronExpressionParser.parser(args[0]);
        System.out.println(output);
    }

    // Method to parse the input cron expression
    public String parser(String input) {
        // Split the input into individual parts
        String[] params = input.split("\\s+");
        int paramsLength = params.length;
        // Check if the input has correct number of parts
        if (paramsLength != 6) {
            throw new RuntimeException("Invalid length of arguments.");
        }

        StringJoiner output = new StringJoiner("\n");
        for (int i = 0; i < 6; i++) {
            // Switch case to parse each part of the cron expression
            switch (i) {
                case 0:
                    output.add(minuteParser.parse(params[i]));
                    break;
                case 1:
                    output.add(hourParser.parse(params[i]));
                    break;
                case 2:
                    output.add(dayParser.parse(params[i]));
                    break;
                case 3:
                    output.add(monthParser.parse(params[i]));
                    break;
                case 4:
                    output.add(weekParser.parse(params[i]));
                    break;
                default:
                    output.add(commandParser.parse(params[i]));
            }
        }
        return output.toString();
    }
}
