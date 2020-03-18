package com.example.myawesomequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.myawesomequiz.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="MyQuiz.db";
    private static final int DATABASE_VERSION=1;
    private SQLiteDatabase db;

    public QuizDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        final String SQL_CREATE_QUESTIONS_TABLE="CREATE TABLE "+
                QuestionsTable.TABLE_NAME + " ( "+
                QuestionsTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER + " INTEGER " +
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable(){
        Question q1=new Question("Who is the captain of Indian cricket team?","M.S DHONI","Virat Kohli","Sachin tendulkar",2);
        addQuestion(q1);
        Question q2=new Question("Who is the prime minister of india?","Narendra Modi","Donald Trump","Rajiv Gandhi",1);
        addQuestion(q2);
        Question q3=new Question("What is the shape of the earth?","Square","triangle","Oval",3);
        addQuestion(q3);
        Question q4=new Question("Where is the Eiffel tower located?","Londan","Paris","Italy",2);
        addQuestion(q4);
        Question q5=new Question("Who is the CEO of amazon?","Sundar pichai","Jeff Bezos","Narayan murthy",2);
        addQuestion(q5);
        Question q6=new Question("2+2=?","3","4","5",2);
        addQuestion(q6);
    }
    private void addQuestion(Question question){
        ContentValues cv=new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER,question.getAnswer());
        db.insert(QuestionsTable.TABLE_NAME,null,cv);
    }
    public ArrayList<Question> getAllQuestions(){
        ArrayList<Question>questionList =new ArrayList<>();
        db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+ QuestionsTable.TABLE_NAME,null);

        if(c.moveToFirst()){
            do{
                Question question =new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswer(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER)));
                questionList.add(question);
            }while(c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
