
package com.example.architecture.entities.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ulling.lib.core.entities.QcBaseResponse;

import java.util.List;

public class AnswersResponse extends QcBaseResponse {

    @SerializedName("items")
    @Expose
    private List<ItemResponse> itemResponses = null;
    @SerializedName("has_more")
    @Expose
    private Boolean hasMore;
    @SerializedName("quota_max")
    @Expose
    private Integer quotaMax;
    @SerializedName("quota_remaining")
    @Expose
    private Integer quotaRemaining;

    public List<ItemResponse> getItemResponses() {
        return itemResponses;
    }

    public void setItemResponses(List<ItemResponse> itemResponses) {
        this.itemResponses = itemResponses;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public Integer getQuotaMax() {
        return quotaMax;
    }

    public void setQuotaMax(Integer quotaMax) {
        this.quotaMax = quotaMax;
    }

    public Integer getQuotaRemaining() {
        return quotaRemaining;
    }

    public void setQuotaRemaining(Integer quotaRemaining) {
        this.quotaRemaining = quotaRemaining;
    }

    @Override
    public String toString() {
        return "SOAnswersResponse{" +
                "items=" + itemResponses +
                ", hasMore=" + hasMore +
                ", quotaMax=" + quotaMax +
                ", quotaRemaining=" + quotaRemaining +
                '}';
    }
}
