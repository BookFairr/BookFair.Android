package bookfair.android.api;

import bookfair.android.api.models.LogInResult;
import bookfair.android.api.models.SignUpResult;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BookFairApiService {

    @Headers("Accept:application/json")
    @POST("api/register")
    Call<SignUpResult> attemptSignUp(@Query("email") String email, @Query("fullname") String fullname,
                                     @Query("username") String username, @Query("password") String password);
}
