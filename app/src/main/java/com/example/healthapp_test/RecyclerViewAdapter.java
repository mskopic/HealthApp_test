package com.example.healthapp_test;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
                        TextView dialog_name_id = (TextView) vHolder.item_contact.findViewById(R.id.name_contact);
                        String value_name = dialog_name_id.getText().toString();
                        if(value_name.trim().equals("Mood")){

                            TextView task_status = (TextView) vHolder.item_contact.findViewById(R.id.task_status);
                            task_status.setText("COMPLETE");
                            task_status.setTextColor(Color.parseColor("#33cc5a"));

                            Intent startIntent = new Intent(mContext, MoodDetails.class);
                            startIntent.putExtra("com.example.healthapp_test_DETAILS", " " + mData.get(vHolder.getAdapterPosition()).getName());
                            mContext.startActivity(startIntent);
                            myDialog.dismiss();
                        } else {
                            Intent startIntent = new Intent(mContext, DetailActivity.class);
                            startIntent.putExtra("com.example.healthapp_test_DETAILS", " " + mData.get(vHolder.getAdapterPosition()).getName());
                            mContext.startActivity(startIntent);
                            myDialog.dismiss();
                        }
                    }
                });

                // Mark as Complete
                Button dialog_complete = (Button) myDialog.findViewById(R.id.dialog_complete);
                dialog_complete.setOnClickListener(new View.OnClickListener(){
                    String value_task;
                    String value_dialog;
                    String value_name;
                    @Override
                    public void onClick(View v) {

                        //change the element as completed (on the RecyclerView)
                        TextView task_status = (TextView) vHolder.item_contact.findViewById(R.id.task_status);
                        TextView dialog_name_id = (TextView) vHolder.item_contact.findViewById(R.id.name_contact);
                        TextView dialog_status = (TextView) v.findViewById(R.id.dialog_complete);
                        value_task = task_status.getText().toString();
                        value_dialog = dialog_status.getText().toString();
                        value_name = dialog_name_id.getText().toString();

                        // Lunch
                        if(value_name.trim().equals("Lunch") && value_task.trim().equals("INCOMPLETE")) {
                            task_status.setText("COMPLETE");
                            task_status.setTextColor(Color.parseColor("#33cc5a"));

                            //dismiss dialog
                            myDialog.dismiss();

                            TextView completed = (TextView) myDialog.findViewById(R.id.dialog_complete);
                            completed.setText("MARK AS INCOMPLETE");

                            TextView status = (TextView) myDialog.findViewById(R.id.status);
                            status.setText("COMPLETE");
                            status.setTextColor(Color.GREEN);
                        } else if(value_name.trim().equals("Lunch") && value_task.trim().equals("COMPLETE")){
                            task_status.setText("INCOMPLETE");
                            task_status.setTextColor(Color.parseColor("#ff0006"));



                            //dismiss dialog
                            myDialog.dismiss();

                            TextView incompleted = (TextView) myDialog.findViewById(R.id.dialog_complete);
                            incompleted.setText("MARK AS COMPLETE");

                            TextView status = (TextView) myDialog.findViewById(R.id.status);
                            status.setText("INCOMPLETE");
                            status.setTextColor(Color.RED);
                        }

                        // Meditation
                        if(value_name.trim().equals("Meditate") && value_task.trim().equals("INCOMPLETE")) {
                            task_status.setText("COMPLETE");
                            task_status.setTextColor(Color.parseColor("#33cc5a"));



                            //dismiss dialog
                            myDialog.dismiss();

                            TextView completed = (TextView) myDialog.findViewById(R.id.dialog_complete);
                            completed.setText("MARK AS INCOMPLETE");

                            TextView status = (TextView) myDialog.findViewById(R.id.status);
                            status.setText("COMPLETE");
                            status.setTextColor(Color.GREEN);
                        } else if(value_name.trim().equals("Meditate") && value_task.trim().equals("COMPLETE")){
                            task_status.setText("INCOMPLETE");
                            task_status.setTextColor(Color.parseColor("#ff0006"));



                            //dismiss dialog
                            myDialog.dismiss();

                            TextView incompleted = (TextView) myDialog.findViewById(R.id.dialog_complete);
                            incompleted.setText("MARK AS COMPLETE");

                            TextView status = (TextView) myDialog.findViewById(R.id.status);
                            status.setText("INCOMPLETE");
                            status.setTextColor(Color.RED);
                        }

                        // Exercise
                        if(value_name.trim().equals("Exercise") && value_task.trim().equals("INCOMPLETE")) {
                            task_status.setText("COMPLETE");
                            task_status.setTextColor(Color.parseColor("#33cc5a"));



                            //dismiss dialog
                            myDialog.dismiss();

                            TextView completed = (TextView) myDialog.findViewById(R.id.dialog_complete);
                            completed.setText("MARK AS INCOMPLETE");

                            TextView status = (TextView) myDialog.findViewById(R.id.status);
                            status.setText("COMPLETE");
                            status.setTextColor(Color.GREEN);

                        } else if(value_name.trim().equals("Exercise") && value_task.trim().equals("COMPLETE")){
                            task_status.setText("INCOMPLETE");
                            task_status.setTextColor(Color.parseColor("#ff0006"));

                            //dismiss dialog
                            myDialog.dismiss();

                            TextView incompleted = (TextView) myDialog.findViewById(R.id.dialog_complete);
                            incompleted.setText("MARK AS COMPLETE");

                            TextView status = (TextView) myDialog.findViewById(R.id.status);
                            status.setText("INCOMPLETE");
                            status.setTextColor(Color.RED);

                        }

                        // Dinner
                        if(value_name.trim().equals("Dinner") && value_task.trim().equals("INCOMPLETE")) {
                            task_status.setText("COMPLETE");
                            task_status.setTextColor(Color.parseColor("#33cc5a"));

                            //dismiss dialog
                            myDialog.dismiss();

                            TextView completed = (TextView) myDialog.findViewById(R.id.dialog_complete);
                            completed.setText("MARK AS INCOMPLETE");

                            TextView status = (TextView) myDialog.findViewById(R.id.status);
                            status.setText("COMPLETE");
                            status.setTextColor(Color.GREEN);
                        } else if(value_name.trim().equals("Dinner") && value_task.trim().equals("COMPLETE")){
                            task_status.setText("INCOMPLETE");
                            task_status.setTextColor(Color.parseColor("#ff0006"));

                            //dismiss dialog
                            myDialog.dismiss();

                            TextView incompleted = (TextView) myDialog.findViewById(R.id.dialog_complete);
                            incompleted.setText("MARK AS COMPLETE");

                            TextView status = (TextView) myDialog.findViewById(R.id.status);
                            status.setText("INCOMPLETE");
                            status.setTextColor(Color.RED);
                        }

                        // Mood
                        if(value_name.trim().equals("Mood") && value_task.trim().equals("INCOMPLETE")) {
                            task_status.setText("COMPLETE");
                            task_status.setTextColor(Color.parseColor("#33cc5a"));

                            //dismiss dialog
                            myDialog.dismiss();

                            TextView completed = (TextView) myDialog.findViewById(R.id.dialog_complete);
                            completed.setText("MARK AS INCOMPLETE");

                            TextView status = (TextView) myDialog.findViewById(R.id.status);
                            status.setText("COMPLETE");
                            status.setTextColor(Color.GREEN);
                        } else if(value_name.trim().equals("Mood") && value_task.trim().equals("COMPLETE")){
                            task_status.setText("INCOMPLETE");
                            task_status.setTextColor(Color.parseColor("#ff0006"));

                            //dismiss dialog
                            myDialog.dismiss();

                            TextView incompleted = (TextView) myDialog.findViewById(R.id.dialog_complete);
                            incompleted.setText("MARK AS COMPLETE");

                            TextView status = (TextView) myDialog.findViewById(R.id.status);
                            status.setText("INCOMPLETE");
                            status.setTextColor(Color.RED);
                        }

                        // Sleep
                        if(value_name.trim().equals("Sleep") && value_task.trim().equals("INCOMPLETE")) {
                            task_status.setText("COMPLETE");
                            task_status.setTextColor(Color.parseColor("#33cc5a"));

                            //dismiss dialog
                            myDialog.dismiss();

                            TextView completed = (TextView) myDialog.findViewById(R.id.dialog_complete);
                            completed.setText("MARK AS INCOMPLETE");

                            TextView status = (TextView) myDialog.findViewById(R.id.status);
                            status.setText("COMPLETE");
                            status.setTextColor(Color.GREEN);
                        } else if(value_name.trim().equals("Sleep") && value_task.trim().equals("COMPLETE")){
                            task_status.setText("INCOMPLETE");
                            task_status.setTextColor(Color.parseColor("#ff0006"));

                            //dismiss dialog
                            myDialog.dismiss();

                            TextView incompleted = (TextView) myDialog.findViewById(R.id.dialog_complete);
                            incompleted.setText("MARK AS COMPLETE");

                            TextView status = (TextView) myDialog.findViewById(R.id.status);
                            status.setText("INCOMPLETE");
                            status.setTextColor(Color.RED);
                        }

                    }
                });

                // Delete task
                Button delete_btn = (Button) myDialog.findViewById(R.id.delete_btn);
                delete_btn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                        builder1.setMessage("Are you sure?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        removeAt(vHolder.getAdapterPosition());
                                        dialog.cancel();
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    myDialog.dismiss();
                    }
                });

                // Close the current dialog box
                ImageButton dialog_btn_close = (ImageButton) myDialog.findViewById(R.id.dialog_btn_close);
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

    public void removeAt(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_name.setText(mData.get(i).getName());
        myViewHolder.tv_phone.setText(mData.get(i).getPhone());
        myViewHolder.img.setImageResource(mData.get(i).getPhoto());
    }

    @Override
    public int getItemCount() {
        if(mData==null){
            return 0;
        }
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
