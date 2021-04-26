package com.firstapp.moneydiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder>{
    private Context mContext;
    private ArrayList<TransactionModel> mTransactionList;
    private DatabaseHelper mDatabaseHelper;
    private OnItemClickListener mListener;
    Activity activity;


    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public TransactionsAdapter (Activity activity,Context context, ArrayList<TransactionModel> transactionModels){
        this.mContext = context;
        this.mTransactionList = transactionModels;
        this.activity = activity;
        mDatabaseHelper = new DatabaseHelper(context);

    }

    public static class TransactionsViewHolder extends RecyclerView.ViewHolder {

        public TextView transactionTitle;
        public TextView transactionDescription;
        public TextView getTransactionAmount;
        Context nContext;

        public TransactionsViewHolder(@NonNull View itemView, final OnItemClickListener listener, final Context context) {
            super(itemView);
            transactionTitle = itemView.findViewById(R.id.transaction_title);
            transactionDescription = itemView.findViewById(R.id.transaction_description);
            getTransactionAmount = itemView.findViewById(R.id.transaction_amount);
            //nContext = itemView.getContext();


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
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
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        TransactionsViewHolder tvh = new TransactionsViewHolder(view, mListener, mContext);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        final TransactionModel transactions  = mTransactionList.get(position);
        holder.transactionTitle.setText(transactions.getTitle());
        holder.transactionDescription.setText(transactions.getDescription());
        holder.getTransactionAmount.setText(String.valueOf(transactions.getAmount()));



    }

    @Override
    public int getItemCount() {
        return mTransactionList.size();
    }

    public void filterList(ArrayList<TransactionModel> filteredList) {
        mTransactionList = filteredList;
        notifyDataSetChanged();
    }
}
