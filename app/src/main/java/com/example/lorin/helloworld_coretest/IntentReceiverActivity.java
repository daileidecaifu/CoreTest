package com.example.lorin.helloworld_coretest;

import android.app.Activity;
import android.os.Bundle;

import com.example.lorin.helloworld_coretest.object.Student;

import java.util.ArrayList;

/**
 * Created by lorin on 16/1/25.
 */
public class IntentReceiverActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Student> list = getIntent().getParcelableArrayListExtra("student_list");
        if (list != null && list.size() > 0) {
            String str = "";
            for (Student s : list) {
                str += s.getName() + " | ";
            }
        }

    }
}
