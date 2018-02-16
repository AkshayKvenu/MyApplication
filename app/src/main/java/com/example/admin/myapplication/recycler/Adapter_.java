package com.example.admin.myapplication.recycler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.myapplication.LoginActivity;
import com.example.admin.myapplication.MapsActivity;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.SyncActivity;
import com.example.admin.myapplication.TrackMemberActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;



public class Adapter_ extends RecyclerView.Adapter<ViewHolder_>
{
    // Adapters provide a binding from an app-specific data set (Here, the data set is, our Data Holding Class's data) to views that are displayed within a RecyclerView.


    private ArrayList<Data_Holding_Class> holdingClassArraylist;

    private Context mContext;
    String Lognumber;

    public Adapter_(Context context, ArrayList<Data_Holding_Class> arrayListElements,String phnumer)
    {
        this.holdingClassArraylist = arrayListElements;
        this.mContext = context;
        this.Lognumber= phnumer;
        Log.e("number",""+Lognumber);

    }

    // onCreateViewHolder is called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.

    // This new ViewHolder should be constructed(on created) with a new View(list_row) that can represent the items of the type that have declared. (like imageview , textview).
    // A ViewGroup is a special view that can contain other views (called children.) Here our view in activity_main is the parent
    // parent : The ViewGroup into which the new View will be added after it is bound to an adapter position.
    // int viewType: is the view type of the new View.
    @Override
    public ViewHolder_ onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.e("In","onCreateViewHolder");
        Log.d("ViewGroup size", String.valueOf(parent.getScrollBarSize()));
        Log.d("viewType", String.valueOf(viewType));
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, null); // .from means, Obtains the LayoutInflater from the given context.

        return new ViewHolder_(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder_ holder, final int position)
    {

    // onBindViewHolder is called by RecyclerView to display the data at the specified position.
   // int i : The position of the item within the adapter's data set.
        Log.e("In","onBindViewHolder");
        Log.d("ViewHolder_ holder", String.valueOf(holder));
        Log.d("position", String.valueOf(position));
        Data_Holding_Class Data_Holding_Class_Object = holdingClassArraylist.get(position);

        switch (position)
        {
        case 0:
            holder.title.setText("Add members");
            holder.thumbnail.setImageResource(R.drawable.addmember);
            holder.thumbnail.setTag(position);
            break;
            case 1:
                holder.title.setText("Track lost phone");
                holder.thumbnail.setImageResource(R.drawable.track);

                break;
            case 2:
                holder.title.setText("Add members");
                break;
            case 3:
                holder.title.setText("Logout");
                holder.thumbnail.setImageResource(R.drawable.logout);
                break;
             default:
            holder.title.setText(Data_Holding_Class_Object.getTitle()+ String.valueOf(position));
            break;

        }

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (position) {
                    case 0:
                        Intent intent = new Intent(mContext, SyncActivity.class);
                        intent.putExtra("pnumber","");
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(mContext,TrackMemberActivity.class);
                        intent1.putExtra("pnumber","");
                        mContext.startActivity(intent1);
                        break;
                    case 2:

                        break;
                    case 3:
                        FirebaseAuth fAuth = FirebaseAuth.getInstance(); fAuth.signOut();
                        Intent intent2=new Intent(mContext,LoginActivity.class);
                        mContext.startActivity(intent2);

                }

            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                    Intent intent = new Intent(mContext, SyncActivity.class);
                        intent.putExtra("pnumber","");
                    mContext.startActivity(intent);
                    break;
                    case 1:
                        Intent intent1=new Intent(mContext,TrackMemberActivity.class);
                        intent1.putExtra("pnumber","");
                        mContext.startActivity(intent1);
                        break;
                    case 2:
                        break;
                    case 3:
                        FirebaseAuth fAuth = FirebaseAuth.getInstance();
                        fAuth.signOut();
                        Intent intent2=new Intent(mContext,LoginActivity.class);
                        //intent1.putExtra("uname",username.getText().toString());
                        mContext.startActivity(intent2);

                }

            }
        });

     /*   Picasso.with(mContext).load(Data_Holding_Class_Object.getThumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);*/

    }

    @Override
    public int getItemCount()
    {
        Log.e("In","getItemCount");
        Log.e("getItemCount is", String.valueOf((holdingClassArraylist != null ? holdingClassArraylist.size() : 0)));
        return (holdingClassArraylist != null ? holdingClassArraylist.size() : 0);
    }
}
