package com.example.appbank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "RecyclerAdapter";
    List<BankAccount> bankAccountList;
    private List<BankAccount> bankAccountsListAll;

    private RecyclerViewClickInterface recyclerViewClickInterface;

    public RecyclerAdapter(List<BankAccount> bankAccountList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.bankAccountList = bankAccountList;
        this.bankAccountsListAll = new ArrayList<>(bankAccountList);
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.clientName.setText(bankAccountList.get(position).getClientName());
        holder.accountNumber.setText(bankAccountList.get(position).getAccountNumber());
        holder.accountType.setText(bankAccountList.get(position).getAccountType());
        holder.balance.setText( bankAccountList.get(position).getBalance() );
    }

    @Override
    public int getItemCount() {
        return bankAccountList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<BankAccount> filteredList = new ArrayList<BankAccount>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(bankAccountsListAll);
            }else{
                for(BankAccount ba : bankAccountsListAll){
                    if(ba.getClientName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(ba);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            bankAccountList.clear();
            bankAccountList.addAll((Collection<? extends BankAccount>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView clientName, accountNumber, accountType, balance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            clientName = itemView.findViewById(R.id.clientName);
            accountNumber = itemView.findViewById(R.id.accountNumber);
            accountType = itemView.findViewById(R.id.accountType);
            balance = itemView.findViewById(R.id.balance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
//                    moviesList.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());

                    recyclerViewClickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });

        }

    }
}
