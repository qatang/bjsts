package com.bjsts.core.api.component.request;

import com.google.common.collect.Lists;
import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;

import java.util.List;
import java.util.function.BiFunction;

/**
 * author: sunshow.
 */
public class ApiPageRequestHelper {

    public static <T> List<T> request(ApiRequest apiRequest, ApiRequestPage apiRequestPage, BiFunction<ApiRequest, ApiRequestPage, ApiResponse<T>> biFunction) throws RuntimeException {
        List<T> result = Lists.newArrayList();

        while (true) {
            ApiResponse<T> apiResponse = biFunction.apply(apiRequest, apiRequestPage);

            if (apiResponse == null || apiResponse.getCount() == 0) {
                break;
            }

            result.addAll(apiResponse.getPagedData());

            if (apiResponse.getCount() < apiRequestPage.getPageSize()) {
                break;
            }

            apiRequestPage.pagingNext();
        }

        return result;
    }
}
