package com.example.architecture.entities.room;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.ulling.lib.core.entities.QcBaseItem;

import java.io.Serializable;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 7. 27.
 * @description :
 * @since :
 */
@SuppressWarnings("serial")
@Entity
        (indices = {@Index("questionId")})
//public class Answer extends QcBaseItem {
    public class Answer extends QcBaseItem implements Serializable {

//        @PrimaryKey(autoGenerate = true)
//    private int id;
    @PrimaryKey
    private int answerId;

    private int lastPage;
    private int questionId;
    @Embedded
    private Owner owner;
    private Boolean isAccepted;
    private int score;
    private int lastActivityDate;
    private int lastEditDate;
    private int creationDate;
    private Boolean hasMore;
    @Ignore
    private int type = 0;

    public Answer() {

    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Answer answer = (Answer) obj;

        if (answerId !=  answer.answerId) return false;
        if (questionId !=  answer.questionId) return false;

        if (isAccepted !=  answer.isAccepted) return false;

        if (score !=  answer.score) return false;
        if (lastActivityDate !=  answer.lastActivityDate) return false;
        if (lastEditDate !=  answer.lastEditDate) return false;
        if (creationDate !=  answer.creationDate) return false;

        if (owner != null) {
            return owner.equals(answer.owner);
        } else {
            return answer.owner == null;
        }

    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    //    @Override
//    public int hashCode() {
//        int result = answerId;
//        result = result + (name != null ? name.hashCode() : 0);
//        result = result + role.hashCode();
//        return result;
//    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(int lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public int getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(int lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public int getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(int creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", lastPage=" + lastPage +
                ", questionId=" + questionId +
                ", owner=" + owner +
                ", isAccepted=" + isAccepted +
                ", score=" + score +
                ", lastActivityDate=" + lastActivityDate +
                ", lastEditDate=" + lastEditDate +
                ", creationDate=" + creationDate +
                ", hasMore=" + hasMore +
                ", type=" + type +
                '}';
    }
}
