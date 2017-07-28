/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.architecture.entities.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.ulling.lib.core.entities.QcBaseItem;

@Entity
        (foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id"),
                indices = {@Index("id"), @Index("user_id")}   )
public class Book extends QcBaseItem {
    @PrimaryKey
    public String id;
    public String title;

    @ColumnInfo(name = "user_id") // Book 테이블에서 사용할 이름
    public int userId;

    public Book() {

    }
}
