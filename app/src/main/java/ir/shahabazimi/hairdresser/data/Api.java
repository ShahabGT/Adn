package ir.shahabazimi.hairdresser.data;

import java.util.ArrayList;

import ir.shahabazimi.hairdresser.models.GeneralResponse;
import ir.shahabazimi.hairdresser.models.PersonResponse;
import ir.shahabazimi.hairdresser.models.PointsResponse;
import ir.shahabazimi.hairdresser.models.StatResponse;
import ir.shahabazimi.hairdresser.models.StatResponse2;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("register.php")
    Call<GeneralResponse> register(
            @Field("name") String name,
            @Field("number") String number,
            @Field("bday") String birthday,
            @Field("mday") String weddingDate,
            @Field("code") String code);

    @FormUrlEncoded
    @POST("bride.php")
    Call<GeneralResponse> bride(
            @Field("userid") String userId,
            @Field("wday") String weddingDate,
            @Field("cday") String contractDate,
            @Field("snumber") String spouseNumber,
            @Field("price") String price);

    @FormUrlEncoded
    @POST("buy.php")
    Call<GeneralResponse> buy(
            @Field("user_id") String userId,
            @Field("amount") String amount,
            @Field("wallet") String walley,
            @Field("pay") String pay,
            @Field("title[]") ArrayList<String> title,
            @Field("price[]")ArrayList<String> price,
            @Field("person[]")ArrayList<String> person
    );


    @FormUrlEncoded
    @POST("search.php")
    Call<GeneralResponse> search(
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST("getstats.php")
    Call<StatResponse> getstats(
            @Field("year") String year
    );

    @FormUrlEncoded
    @POST("getstats2.php")
    Call<StatResponse2> getstats2(
            @Field("year") String year,
            @Field("month") String month
    );

    @FormUrlEncoded
    @POST("getpersonstats.php")
    Call<PersonResponse> getpersonstats(
            @Field("year") String year,
            @Field("month") String month,
            @Field("person") String person
    );

    @FormUrlEncoded
    @POST("sendsms.php")
    Call<GeneralResponse> sendsms(
            @Field("text") String text);

    @GET("getpoints.php")
    Call<PointsResponse> getpoints();


    @FormUrlEncoded
    @POST("setpoints.php")
    Call<GeneralResponse> setpoints(
            @Field("point") String point,
            @Field("wallet") String wallet

    );

    @FormUrlEncoded
    @POST("getuserpoints.php")
    Call<GeneralResponse> getuserpoints(
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST("setuserpoints.php")
    Call<GeneralResponse> setuserpoints(
            @Field("user_id") String userId,
            @Field("points") String points

    );
}
