package mgit.attendance;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Timetable extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseHelper mydb1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        mydb1=new DatabaseHelper(this);
        Cursor res=mydb1.getAllData();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<MyClass> myclass=new ArrayList<MyClass>();
        while(res.moveToNext()){
            myclass.add(new MyClass(res.getString(2),"Hour:"+res.getString(3),"Present:","Absent:",R.drawable.ic_done_black_24dp));
        }
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myclass);
        mRecyclerView.setAdapter(mAdapter);
    }
}
