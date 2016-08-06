package connect.exzeo.com.connect.data.remote;

import java.util.ArrayList;
import java.util.List;

import connect.exzeo.com.connect.modal.Comments;
import connect.exzeo.com.connect.modal.User;
import connect.exzeo.com.connect.modal.UserNew;
import modal.ADDRESS;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by subodh on 5/8/16.
 */
public interface APIConnect {
    /*@GET("search")
    Call<List<ADDRESS>> gobarSearch(@Query("q") String searchString);*/

    @FormUrlEncoded
    @PUT("user/update")
    Call<User> updateUser(@Field("firstName") String firstName, @Field("lastName") String last, @Field("mobile") String phoneNumber, @Field("email") String email, @Field("password") String password
            , @Field("gender") String gender, @Field("role") String role, @Field("lat") String latitude, @Field("long") String longitude, @Field("skills[]") ArrayList<String> services, @Field("rating") String ratings  );

    @FormUrlEncoded
    @POST("/user/register")
    Call<User> registerUser(@Field("mobile") String phoneNumber);


    @GET("/search")
    Call<List<UserNew>> getSearchResult(@Query("skill") String skill, @Query("lat") String latitude, @Query("long") String longitude);

    @GET("/comments")
    Call<List<Comments>> getComments(@Query("mobile") String phoneNumber);

    @FormUrlEncoded
    @POST("/post/comment")
    Call<User> postComment(@Field("userMobile") String userMobile, @Field("ownerMobile") String ownerMobile, @Field("comment") String comment, @Field("rating") String rating);
}
