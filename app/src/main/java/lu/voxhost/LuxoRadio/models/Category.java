package lu.voxhost.LuxoRadio.models;

public class Category {
    private int cat_id;
    private String category_name;
    private String category_image;

    public Category(int cat_id, String category_name, String category_image) {
        this.cat_id = cat_id;
        this.category_name = category_name;
        this.category_image = category_image;
    }

    public int getCat_id() {
        return cat_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getCategory_image() {
        return category_image;
    }
}
