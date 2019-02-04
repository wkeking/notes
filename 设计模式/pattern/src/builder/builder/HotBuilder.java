package builder.builder;

public class HotBuilder extends Builder {
    @Override
    public void builderIngredients() {
        yxrs.setIngredients ("胡萝卜");
        yxrs.setIngredients ("青椒");
        yxrs.setIngredients ("肉丝");
    }

    @Override
    public void builderCondiment() {
        yxrs.setCondiment ("盐");
        yxrs.setCondiment ("酱油");
        yxrs.setCondiment ("辣椒");
    }
}
