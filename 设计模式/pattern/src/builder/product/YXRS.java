package builder.product;

import java.util.ArrayList;
import java.util.List;

public class YXRS {
    private List<String> ingredients = new ArrayList<> ();
    private List<String> condiment = new ArrayList<> ();

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredient) {
        this.ingredients.add (ingredient);
    }

    public List<String> getCondiment() {
        return condiment;
    }

    public void setCondiment(String condiment) {
        this.condiment.add (condiment);
    }

    @Override
    public String toString() {
        return "YXRS{" +
                "ingredients=" + ingredients +
                ", condiment=" + condiment +
                '}';
    }
}
