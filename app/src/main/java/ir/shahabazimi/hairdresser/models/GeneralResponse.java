package ir.shahabazimi.hairdresser.models;

import com.google.gson.annotations.SerializedName;

public class GeneralResponse {

    private String message;

    @SerializedName("user_id")
    private String id;

    @SerializedName("user_name")
    private String name;

    @SerializedName("user_number")
    private String number;

    private String wallet;

    private String points;

    public String getPoints() {
        return points;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getWallet() {
        return wallet;
    }
}
