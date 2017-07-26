
package com.example.architecture.enty.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

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
 * private String name;
 *
 * @PrimaryKey private String mobile; private String email;
 * @Embedded private Address address; private String type;
 * @Ignore private String middleName;
 */
@Entity
public class User {
    @PrimaryKey
    public String id;
    public String name;
    public String lastName;
    public int age;

//    @Ignore
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

//    @Ignore
//    public User(String id, String name, String lastName, int age) {
//        this.id = id;
//        this.name = name;
//        this.lastName = lastName;
//        this.age = age;
//    }

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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}' + "\n\n";
    }
}