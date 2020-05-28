package ir.shahabazimi.hairdresser.models;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("user_code")
    private String userCode;

    @SerializedName("user_number")
    private String userNumber;

    @SerializedName("user_bday")
    private String userBday;

    @SerializedName("user_mday")
    private String userMday;

    @SerializedName("user_reg")
    private String userReg;

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public String getUserBday() {
        return userBday;
    }

    public String getUserMday() {
        return userMday;
    }

    public String getUserReg() {
        return userReg;
    }
}
