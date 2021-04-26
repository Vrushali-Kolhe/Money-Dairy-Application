package com.firstapp.moneydiary;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context mContext;
    private ArrayList<TaskModel> mTaskList;
    private DatabaseHelper mDatabaseHelper;
    private TaskAdapter.OnItemClickListener mListener;
    Activity activity;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(TaskAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public TaskAdapter (Activity activity,Context context, ArrayList<TaskModel> taskModels){
        this.mContext = context;
        this.mTaskList = taskModels;
        this.activity = activity;
        mDatabaseHelper = new DatabaseHelper(context);

    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView taskTitle;
        public TextView taskDescription;
        public TextView getTaskAmount;
        Context nContext;

        public TaskViewHolder(@NonNull View itemView, final TaskAdapter.OnItemClickListener listener, final Context context) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskDescription = itemView.findViewById(R.id.task_description);
            getTaskAmount = itemView.findViewById(R.id.task_amount);
            //nContext = itemView.getContext();


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
//                            Intent intent = new Intent(nContext, UpdateTransaction.class);
//                            intent.putExtra("title", String.valueOf(transactionTitle));
//                            intent.putExtra("title", String.valueOf(book_title.get(position)));
//                            intent.putExtra("author", String.valueOf(book_author.get(position)));
//                            intent.putExtra("pages", String.valueOf(book_pages.get(position)));
//                            nContext.startActivity(intent);


                        }
                    }
                }
            });

        }


    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        TaskAdapter.TaskViewHolder tvh = new TaskAdapter.TaskViewHolder(view, mListener, mContext);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
        final TaskModel tasks  = mTaskList.get(position);
        holder.taskTitle.setText(tasks.getTitle());
        holder.taskDescription.setText(tasks.getDescription());
        holder.getTaskAmount.setText(String.valueOf(tasks.getAmount()));


    }

    @Override
    public int getItemCount() {

        return mTaskList.size();
    }

    public void filterList(ArrayList<TaskModel> filteredList) {
        mTaskList = filteredList;
        notifyDataSetChanged();
    }
}
