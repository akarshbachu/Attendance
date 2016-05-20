package mgit.attendance;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by bachu_000 on 05-03-2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    int pr=0,ab=0,total=0;


    private ArrayList<MyClass> myclass;


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<MyClass> myclass) {

        this.myclass = myclass;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView title= (TextView) holder.view.findViewById(R.id.title);
        final TextView desc=(TextView) holder.view.findViewById(R.id.desc);
        final TextView present=(TextView) holder.view.findViewById(R.id.present);
        final TextView absent=(TextView) holder.view.findViewById(R.id.absent);
        final ImageView imgview=(ImageView) holder.view.findViewById(R.id.imageView);
        title.setText(myclass.get(position).getTitle());
        desc.setText(myclass.get(position).getDesc());
        present.setText(myclass.get(position).getPresent());
        absent.setText(myclass.get(position).getAbsent());
        imgview.setImageResource(myclass.get(position).getImg());
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgview.setImageResource(R.drawable.ic_done_all_black_24dp);
                pr++;total++;
                present.setText("Present:" + pr);
                pr--;
                imgview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgview.setImageResource(R.drawable.ic_done_black_24dp);
                        ab++;
                        total++;
                        absent.setText("Absent:" + ab);
                        ab--;
                    }
                });
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return myclass.size();
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;


        public ViewHolder(View v) {
            super(v);

            view = v;
        }

    }
}

