package com.firstapp.moneydiary;

import android.content.Context;
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


    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public TransactionsAdapter (Context context, ArrayList<TransactionModel> transactionModels){
        this.mContext = context;
        this.mTransactionList = transactionModels;
        mDatabaseHelper = new DatabaseHelper(context);

    }

    public static class TransactionsViewHolder extends RecyclerView.ViewHolder {

        public TextView transactionTitle;
        public TextView transactionDescription;
        public TextView getTransactionAmount;

        public TransactionsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            transactionTitle = itemView.findViewById(R.id.transaction_title);
            transactionDescription = itemView.findViewById(R.id.transaction_description);
            getTransactionAmount = itemView.findViewById(R.id.transaction_amount);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getBindingAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            //Toast.makeText(ViewTransactionActivity.this, "Clicked!");
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
        TransactionsViewHolder tvh = new TransactionsViewHolder(view, mListener);
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
}
