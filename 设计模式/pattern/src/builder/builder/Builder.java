package builder.builder;

import builder.product.YXRS;

public abstract class Builder {
    protected YXRS yxrs = new YXRS ();
    public abstract void builderIngredients();
    public abstract void builderCondiment();
    public YXRS getResult() {
        return yxrs;
    }
}
