package app.demo.com.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MClient {

    @GET
    Call<ResponseBody> getJson(
            @Url String url,
            @Query("service") String service,
            @Query("version") String version,
            @Query("request") String req,
            @Query("typeName") String typeName,
            @Query("maxFeatures") String maxF,
            @Query("outputFormat") String outPut
    );
}
