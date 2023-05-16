import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Knitter {

    // This is only here to make the example compile, so remove most function
    public String knit(String name) {
        String colour = "hardcoded";
        return "a nice " + colour + " sweater";
    }
}
