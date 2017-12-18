
package com.example.architecture.entities.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.ulling.lib.core.entities.QcBaseItem;

/**
 * PrimaryKey: marks a field as primary key field.
 * 필드를 기본 키 필드로 표시합니다.
 *
 * ForeignKey: Sets foreign key constraint.
 * 외래 키 제약 조건을 설정합니다.
 *
 * Ignore: Ignores marked field, won’t be part of table.
 * 표시된 필드를 무시하고 테이블에 포함되지 않습니다.
 *
 * ColumnInfo: Specifies column name in database, instead of using field name as column name.
 * 필드 이름을 열 이름으로 사용하는 대신 데이터베이스의 열 이름을 지정합니다.
 *
 * Index: Creates index to speed up queries.
 * 색인을 생성하여 조회 속도를 높입니다.
 *
 * Embedded: Marks a field as embedded that makes all subfields of
 * the embedded class as columns of entity.
 * 필드를 임베디드 클래스로 표시하여 임베디드 클래스의 모든 하위 필드를 엔터티의 열로 만듭니다.
 *
 * Relation: Specifies relations, useful to fetch related entities.
 * 관련 엔터티를 가져 오는 데 유용한 관계를 지정합니다.
 *
 *
 *
 * Embedded 참고
 * https://android.jlelse.eu/room-store-your-data-c6d49b4d53a3
 *
 * https://commonsware.com/AndroidArch/previews/room-and-custom-types
 *
 *
 *
 * 이 구성 요소는 데이터베이스 행을 보유하는 클래스를 나타냅니다.
 * 각 엔티티를 들어, 데이터베이스 테이블 항목을 저장하기 위해 만든됩니다.
 * 당신은을 통해 엔티티 클래스를 참조해야합니다 entities 의 배열 Database 클래스입니다.
 * 엔티티의 각 필드는 당신이 그것을 주석을하지 않는 데이터베이스에 유지됩니다 @Ignore.
 *
 *
 * private String name;
 *
 * @PrimaryKey private String mobile; private String email;
 * @Embedded private Address address; private String type;
 * @Ignore private String middleName;
 */
@Entity
//        (tableName = "users")
//        (primaryKeys = {"firstName", "lastName"})

        (indices = {@Index("id")})
//        (indices = {@Index("name"),
//                @Index(value = {"last_name", "address"})})

//        (indices = {@Index(value = {"first_name", "last_name"},
//                unique = true)})
public class User extends QcBaseItem {
//    @PrimaryKey(autoGenerate = true)
//    private int slNo;

    @PrimaryKey @NonNull
    public String id;
    public String name;
    @ColumnInfo(name = "last_name") // DB저장되는 필드명
    public String lastName;
    public int age;

    @Ignore
    Bitmap picture;

//    @Embedded
////    @Embedded(prefix = "clg") // prefix + Address ex) clgstreet
//    public Address address;
//    @Ignore
    public User() {
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", picture=" + picture +
                '}';
    }
}