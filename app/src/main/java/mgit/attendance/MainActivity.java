package mgit.attendance;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button add,ok,cancel,display,attendance;
    EditText day,hour,subj;
    DatabaseHelper mydb;
    ListView lv,attLv;
    ArrayList<String> strArr;
    ArrayAdapter<String> adapter,adapter2;
    String[] att={"Present","Absent","Holiday"};
    public static double present=0,absent=0,total=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb=new DatabaseHelper(this);


        attendance=(Button)findViewById(R.id.timetable);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,Timetable.class);
                startActivity(in);
            }
        });
        lv=(ListView)findViewById(R.id.listView);
        strArr=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,strArr);
        lv.setAdapter(adapter);

        display=(Button)findViewById(R.id.display);
        add=(Button)findViewById(R.id.add);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setTitle("AddDetails");
                dialog.setContentView(R.layout.detaildialog);
                dialog.show();

                day=(EditText)dialog.findViewById(R.id.day);
                hour=(EditText)dialog.findViewById(R.id.hour);
                subj=(EditText)dialog.findViewById(R.id.subj);
                ok=(Button)dialog.findViewById(R.id.ok);
                cancel=(Button)dialog.findViewById(R.id.cancel);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(day.getText().toString().matches("")){
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                        }
                        else {
                            boolean isInserted = mydb.insertData(day.getText().toString(), subj.getText().toString(), hour.getText().toString());
                            day.setText("");
                            subj.setText("");
                            hour.setText("");
                            if (isInserted == true) {
                                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=mydb.getAllData();
                if(res.getCount()==0){
                    showMessage("Error","no data inserted");
                    return;
                }

                while(res.moveToNext()){

                    strArr.add(res.getString(2) + "\n"+ res.getString(3));

                    adapter.notifyDataSetChanged();
                }
                display.setClickable(false);
            }
        });
        //for attendance
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int index=parent.getSelectedItemPosition();
                //Toast.makeText(MainActivity.this,"Selected"+position,Toast.LENGTH_LONG).show();
                if(position== 0 || position==1){
                    final Dialog dialog1=new Dialog(MainActivity.this);
                    dialog1.setTitle("MarkAttendance");
                    dialog1.setContentView(R.layout.dialoglistview);
                    dialog1.show();
                    attLv=(ListView)dialog1.findViewById(R.id.attList);
                    adapter2=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,att);
                    attLv.setAdapter(adapter2);
                    attLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(position==0){
                                present++;total++;
                                double percent=(present/total)*100;
                                Toast.makeText(getApplicationContext(),"Present:"+percent,Toast.LENGTH_LONG).show();
                                String pr,ab,t;
                                pr=Double.toString(present);
                                ab=Double.toString(absent);
                                t=Double.toString(total);
                                /*int pr,ab,t;
                                pr=(int)present;
                                ab=(int)absent;
                                t=(int)total;*/
                               boolean inserted= mydb.insertData2(pr,ab,t);
                                if(inserted==true)
                                {
                                    Toast.makeText(getApplicationContext(),"Data inserted",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Not inserted",Toast.LENGTH_LONG).show();
                                }

                            }
                            else if(position==1){
                                absent++;total++;
                                double percent=((total-absent)/total)*100;
                                Toast.makeText(getApplicationContext(),"Present:"+percent,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
