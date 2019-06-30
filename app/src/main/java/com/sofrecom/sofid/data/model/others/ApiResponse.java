package com.sofrecom.sofid.data.model.others;

import com.sofrecom.sofid.data.model.api.ApiError;
import com.sofrecom.sofid.utils.Status;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.sofrecom.sofid.utils.Status.API_ERROR;
import static com.sofrecom.sofid.utils.Status.INTERNAL_ERROR;
import static com.sofrecom.sofid.utils.Status.LOADING;
import static com.sofrecom.sofid.utils.Status.SUCCESS;

/**
 * Created by oibnchahdia on 03/05/2019
 */
public class ApiResponse {

    public final Status status;

    @Nullable
    public final Object data;

    @Nullable
    public final Throwable internalError;

    @Nullable
    public final ApiError apiError;

    public ApiResponse(Status status,@Nullable Object data,@Nullable Throwable internalError, @Nullable ApiError apiError) {
        this.status = status;
        this.data = data;
        this.internalError = internalError;
        this.apiError = apiError;
    }

    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null,null);
    }

    public static ApiResponse success(@NonNull Object data) {
        return new ApiResponse(SUCCESS, data, null,null);
    }

    public static ApiResponse internalError(@NonNull Throwable error) {
        return new ApiResponse(INTERNAL_ERROR, null, error,null);
    }

    public static ApiResponse apiError(@NonNull ApiError error) {
        return new ApiResponse(API_ERROR, null, null,error);
    }
}
