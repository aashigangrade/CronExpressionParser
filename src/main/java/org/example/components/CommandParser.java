package org.example.components;

public class CommandParser extends BaseParser{
    public CommandParser() {
        super(-1, -1, "command");
    }

    @Override
    public String parse(String input) {
        return this.name +
                " ".repeat(Math.max(0, nameColumnLength - name.length() + 1)) +
                input;
    }
}