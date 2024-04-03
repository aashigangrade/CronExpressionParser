package org.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CronExpressionParserTest {
    @Test
    public void parserTest() {
        CronExpressionParser parser = new CronExpressionParser();
        String input = "*/15 0 1,15,25-30 * 1-5 /usr/bin/find";
        String output = parser.parser(input);
        String[] namesTimesPairs = output.split("\n");
        assertEquals(6, namesTimesPairs.length);
        String[] minutesDetails = namesTimesPairs[0].split("\\s+");
        assertEquals(5, minutesDetails.length);
        for(int i=0;i<4;i++) {
            assertEquals(String.valueOf(i*15),
                    minutesDetails[i+1]);
        }
    }

    @Test(expected = RuntimeException.class)
    public void parserFailedTest() {
        CronExpressionParser parser = new CronExpressionParser();
        String input = "*/15 0 1,15,25-30 * 1-5";
        parser.parser(input);
    }

    @Test(expected = NumberFormatException.class)
    public void parserFailedWrongFormattedTest() {
        CronExpressionParser parser = new CronExpressionParser();
        String input = "*/15 0 1,15,25-a * 1-5 aa";
        parser.parser(input);
    }

    @Test(expected = RuntimeException.class)
    public void parserFailedWrongRangeTest() {
        CronExpressionParser parser = new CronExpressionParser();
        String input = "*/15 0 40 * 1-5 aa";
        parser.parser(input);
    }
}