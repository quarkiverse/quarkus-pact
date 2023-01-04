package io.quarkiverse.pact.testapp;

public class ConsumerAlpaca {

    private String colour = "?";
    private String name = "?";

    public String getColour() {
        return colour;
    }

    public String getName() {
        return name;
    }

    public void setColour(String newColour) {
        colour = newColour;
    }

    public void setName(String name) {
        this.name = name;
    }
}
