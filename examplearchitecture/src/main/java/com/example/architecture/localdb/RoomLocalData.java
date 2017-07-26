
package com.example.architecture.localdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.architecture.entities.room.Answer;
import com.example.architecture.entities.room.AnswerDao;
import com.example.architecture.entities.room.Book;
import com.example.architecture.entities.room.BookDao;
import com.example.architecture.entities.room.Loan;
import com.example.architecture.entities.room.LoanDao;
import com.example.architecture.entities.room.User;
import com.example.architecture.entities.room.UserDao;

/**
 *
 *  데이터베이스 주석으로 표시되는 추상 클래스입니다 . 방 데이터베이스 구성 요소 정의에는 엔티티 및 DAO 목록이 포함됩니다.
 *
 *  기본적으로 룸은 코드가 주 스레드에서 실행 중인지 여부를 확인하여 예외가 발생합니다.
 *  데이터베이스 작업이 완료되는 데 오랜 시간이 걸릴 수 있으므로 주 스레드에서 실행하면
 *  응용 프로그램 성능과 사용자 경험에 영향을 미칩니다.
 *  아래 섹션은 백그라운드 스레드에서 공간을 실행하는 방법을 설명하고 보여줍니다.
 *
 *  메인 쓰레드에서 룸을 실행해야한다면 적어도 테스트 목적을 위해
 *  RoomDatabase.Builder에서 allowMainThreadQueries () 메소드를 호출하면된다.
 *
 *
 *
 * 참고
 * https://developer.android.com/topic/libraries/architecture/room.html
 *
 *  당신은 데이터베이스 홀더를 만들려면이 구성 요소를 사용할 수 있습니다. 주석은 개체의 목록을 정의하고,
 *  클래스의 내용은 데이터베이스의 데이터 액세스 개체 (DAO를)의 목록을 정의합니다. 또한 기본 연결을위한 주요 액세스 포인트입니다.
 *  주석이 달린 클래스를 확장하는 추상 클래스이어야한다
 *  RoomDatabase. 런타임시, 당신은 호출하여 인스턴스를 획득 할 수있다 Room.databaseBuilder()나 Room.inMemoryDatabaseBuilder().
 */
@Database(entities = {User.class, Book.class, Loan.class, Answer.class }, version = 1)
public abstract class RoomLocalData extends RoomDatabase {

    public abstract AnswerDao answerDatabase();
    public abstract UserDao userDatabase();
    public abstract BookDao bookDatabase();
    public abstract LoanDao loanDatabase();

}