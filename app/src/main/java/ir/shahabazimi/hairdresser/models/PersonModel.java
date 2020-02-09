package ir.shahabazimi.hairdresser.models;

import com.google.gson.annotations.SerializedName;

public class PersonModel {

    @SerializedName("buy_item_id")
    private String buyItemId;

    @SerializedName("buy_id")
    private String buyId;

    private String title;
    private String person;
    private String date;
    private String price;

    public String getBuyItemId() {
        return buyItemId;
    }

    public String getBuyId() {
        return buyId;
    }

    public String getTitle() {
        return title;
    }

    public String getPerson() {
        return person;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }
}
