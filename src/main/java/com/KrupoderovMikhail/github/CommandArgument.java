package com.KrupoderovMikhail.github;

import java.util.List;

public class CommandArgument {


    private final List<String> arguments;

    public CommandArgument(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean has() {
        return this.arguments.size() > 0;
    }

    public String get(int index) {
        return this.arguments.get(index);
    }

    public List<String> getArguments() {
        return this.arguments;
    }

    public int length() {
        return this.arguments.size();
    }

    @Override
    public String toString() {
        return String.join(" ", this.arguments);
    }
}
