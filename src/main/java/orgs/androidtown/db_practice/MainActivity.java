package orgs.androidtown.db_practice;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText mDisplayDbEt = null;
    public StudentsDBManager mDbManager = null;

    public StudentsDBManager getmDbManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDisplayDbEt = (EditText) findViewById(R.id.edit_text);
        mDbManager = StudentsDBManager.getInstance(this);


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert: {
                ContentValues[] insertDataList = new ContentValues[1000];

                for(int i=0; i<1000; i++)
                {
                    ContentValues addRowValue = new ContentValues();
                    addRowValue.put("number", "200106054");
                    addRowValue.put("name", "홍길동");
                    addRowValue.put("department", "컴퓨터");
                    addRowValue.put("age", "18");
                    addRowValue.put("grade", 3);

                    insertDataList[i] = addRowValue;
                }
                // 1000개의 레코드를 DB에 삽입한다.
                // --------------------------------------------------------
                mDbManager.insertAll(insertDataList);
                mDisplayDbEt.setText("1000개의 레코드 추가 완료");

                break;
            }

            case R.id.query: {
                String[] columns = new String[]{"_id", "number", "name", "department", "age", "grade"};

                Cursor c = mDbManager.query(columns, null, null, null, null, null);

                if (c != null) {
                    mDisplayDbEt.setText("");

                    while (c.moveToNext()) {
                        int id = c.getInt(0);
                        String number = c.getString(1);
                        String name = c.getString(2);
                        String department = c.getString(3);
                        String age = c.getString(4);
                        int grade = c.getInt(4);
                        mDisplayDbEt.append("id : " + id + "\n" +
                                "number : " + number + "\n" +
                                "name : " + name + "\n" +
                                "department : " + department + "\n" +
                                "age : " +
                                "grade" + grade + "\n" + "-----------------------");
                    }

                    mDisplayDbEt.append("\n Total : " + c.getCount());
                    c.close();
                }
                break;
            }

            case R.id.update:
            {
                ContentValues updateRowValue = new ContentValues();
                updateRowValue.put("name", "고길동");
                int updateRecordCnt = mDbManager.update(updateRowValue, "number=200106054",null);
                mDisplayDbEt.setText("레코드 갱신 : "+ updateRecordCnt);
                break;
            }
            // 특정 레코드 삭제하기
            // =================================================

            case R.id.delete:

            {
            int deleteRecordCnt = mDbManager.delete(null, null);
                mDisplayDbEt.setText("삭제된 레코드 수 : " + deleteRecordCnt);
                break;
            }
        }
    }


}
