package lu.voxhost.LuxoRadio.api;

import java.util.List;

import lu.voxhost.LuxoRadio.models.Category;
import lu.voxhost.LuxoRadio.models.Channels;
import lu.voxhost.LuxoRadio.models.Report;
import lu.voxhost.LuxoRadio.models.Settings;
import lu.voxhost.LuxoRadio.models.UserToken;
import lu.voxhost.LuxoRadio.models.VP;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("api")
    Call<List<Category>> getCategory(@Query("apicall") String call, @Query("key") String key);
    @GET("api")
    Call<List<Category>> getLatestCategory(@Query("apicall") String call, @Query("latest") int limit, @Query("key") String key);
    @GET("api")
    Call<List<Channels>> getChannelsByCat(@Query("apicall") String call, @Query("getChannelsByCat") int id, @Query("key") String key);
    @GET("api")
    Call<List<Channels>> getSlider(@Query("apicall") String call, @Query("slider") int limit, @Query("key") String key);
    @GET("api")
    Call<List<Channels>> getLatestChannels(@Query("apicall") String call, @Query("latest") int limit, @Query("key") String key);
    @GET("api")
    Call<List<Channels>> getFavChannels(@Query("apicall") String call, @Query("byid") String fav, @Query("key") String key);
    @GET("api")
    Call<List<Channels>> getMostViewedChannels(@Query("apicall") String call, @Query("mostview") int limit, @Query("key") String key);
    @GET("api")
    Call<List<Settings>> getAdSettings(@Query("apicall") String call, @Query("key") String key);
    @GET("api")
    Call<VP> VP(@Query("do") String call, @Query("pcode") String pcode);
    @FormUrlEncoded
    @POST("api")
    Call<List<Channels>> postView(@Query("apicall") String call, @Query("key") String key,
                                   @Field("id") int id);
    @FormUrlEncoded
    @POST("api")
    Call<List<UserToken>> addUserToken(@Query("apicall") String call, @Query("key") String key,
                                       @Field("token") String old_token, @Field("new_token") String new_token);
    @FormUrlEncoded
    @POST("api")
    Call<List<Report>> doReport(@Query("apicall") String call, @Query("key") String key,
                                @Field("radio_id") int radio_id, @Field("radio_name") String radio_name, @Field("report") String report);
}
