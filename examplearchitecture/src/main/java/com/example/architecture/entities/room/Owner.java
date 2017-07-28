package com.example.architecture.entities.room;

import com.ulling.lib.core.entities.QcBaseItem;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 7. 27.
 * @description :
 * @since :
 */

public class Owner  extends QcBaseItem {

    private int reputation;
    private int userId;
    private String userType;
    private int acceptRate;
    private String profileImage;
    private String displayName;
    private String link;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Owner owner = (Owner) obj;

        if (reputation !=  owner.reputation) return false;
        if (userId !=  owner.userId) return false;
        if (!userType.equalsIgnoreCase(owner.userType)) return false;

        if (acceptRate !=  owner.acceptRate) return false;
        if (!profileImage.equalsIgnoreCase(owner.profileImage)) return false;

        if (!displayName.equalsIgnoreCase(owner.displayName)) return false;
        if (!link.equalsIgnoreCase(owner.link)) {
            return false;
        } else {
            return true;
        }
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getAcceptRate() {
        return acceptRate;
    }

    public void setAcceptRate(int acceptRate) {
        this.acceptRate = acceptRate;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
