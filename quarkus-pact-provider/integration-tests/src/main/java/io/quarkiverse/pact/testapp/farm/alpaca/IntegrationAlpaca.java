package io.quarkiverse.pact.testapp.farm.alpaca;

public class IntegrationAlpaca {

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
