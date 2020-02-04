package ir.shahabazimi.hairdresser.data;

import ir.shahabazimi.hairdresser.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("register.php")
    Call<GeneralResponse> register(
            @Field("name") String name,
            @Field("number") String number,
            @Field("bday") String birthday,
            @Field("mday") String weddingDate,
            @Field("code") String code,
            @Field("isbride") boolean isBride
    );
}
