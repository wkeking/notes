package builder.director;

import builder.builder.Builder;
import builder.product.YXRS;

public class YXRSDirector {
    private Builder builder;
    public YXRSDirector(Builder builder) {
        this.builder = builder;
    }
    public YXRS construct() {
        builder.builderIngredients ();
        builder.builderCondiment ();
        return builder.getResult ();
    }
}
