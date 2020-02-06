package ir.shahabazimi.hairdresser.models;

public class FieldModel {

    private String title;
    private String price;
    private String person;

    public FieldModel(String title, String price, String person) {
        this.title = title;
        this.price = price;
        this.person = person;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getPerson() {
        return person;
    }
}
