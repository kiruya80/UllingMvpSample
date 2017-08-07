
package com.example.architecture.entities.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.arch.persistence.room.Ignore;

import com.ulling.lib.core.entities.QcBaseItem;

public class ItemResponse extends QcBaseItem {
//    public class Item extends LiveData implements BaseObservable {

//    @Bindable
    @SerializedName("owner")
    @Expose
    private OwnerResponse ownerResponse;
    @SerializedName("is_accepted")
    @Expose
    private Boolean isAccepted;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("last_activity_date")
    @Expose
    private Integer lastActivityDate;
    @SerializedName("last_edit_date")
    @Expose
    private Integer lastEditDate;
    @SerializedName("creation_date")
    @Expose
    private Integer creationDate;
    @SerializedName("answer_id")
    @Expose
    private Integer answerId;
    @SerializedName("question_id")
    @Expose
    private Integer questionId;

    public ItemResponse() {
    }

    @Ignore
    public ItemResponse(int type) {
        this.type = type;
    }

    public OwnerResponse getOwnerResponse() {
        return ownerResponse;
    }

    public void setOwnerResponse(OwnerResponse ownerResponse) {
        this.ownerResponse = ownerResponse;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Integer lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public Integer getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Integer lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public Integer getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Integer creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }


    @Override
    public String toString() {
        return "ItemResponse{" +
                "type=" + type +
                ", ownerResponse=" + ownerResponse +
                ", isAccepted=" + isAccepted +
                ", score=" + score +
                ", lastActivityDate=" + lastActivityDate +
                ", lastEditDate=" + lastEditDate +
                ", creationDate=" + creationDate +
                ", answerId=" + answerId +
                ", questionId=" + questionId +
                '}';
    }
}
