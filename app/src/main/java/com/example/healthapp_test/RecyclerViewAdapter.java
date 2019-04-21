package com.example.healthapp_test;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Schedule> mData;
    Dialog myDialog;

    public RecyclerViewAdapter(Context mContext, List<Schedule> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_contact, viewGroup, false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        // Dialog initiate

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_task);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        vHolder.item_contact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final TextView dialog_name_tv = (TextView) myDialog.findViewById(R.id.dialog_name_id);
                TextView dialog_phone_tv = (TextView) myDialog.findViewById(R.id.dialog_phone_id);
                ImageView dialog_contact_img = (ImageView) myDialog.findViewById(R.id.dialog_img);
                dialog_name_tv.setText(mData.get(vHolder.getAdapterPosition()).getName());
                dialog_phone_tv.setText(mData.get(vHolder.getAdapterPosition()).getPhone());
                dialog_contact_img.setImageResource(mData.get(vHolder.getAdapterPosition()).getPhoto());

                // Go to next Activity (for details)
                Button dialog_btn_call = (Button) myDialog.findViewById(R.id.dialog_btn_call);
                dialog_btn_call.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent startIntent = new Intent(mContext, DetailActivity.class);
                        startIntent.putExtra("com.example.healthapp_test_DETAILS",  " " + mData.get(vHolder.getAdapterPosition()).getName());
                        mContext.startActivity(startIntent);
                    }
                });


                // Close the current dialog box
                Button dialog_btn_close = (Button) myDialog.findViewById(R.id.dialog_btn_close);
                dialog_btn_close.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.show();
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_name.setText(mData.get(i).getName());
        myViewHolder.tv_phone.setText(mData.get(i).getPhone());
        myViewHolder.img.setImageResource(mData.get(i).getPhoto());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item_contact;
        private TextView tv_name;
        private TextView tv_phone;
        private ImageView img;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_contact = (LinearLayout) itemView.findViewById(R.id.contact_item_id);
            tv_name = (TextView) itemView.findViewById(R.id.name_contact);
            tv_phone = (TextView) itemView.findViewById(R.id.phone_contact);
            img = (ImageView) itemView.findViewById(R.id.img_contact);
        }
    }
}
