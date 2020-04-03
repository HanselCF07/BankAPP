package com.example.appbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickInterface {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    ArrayList<BankAccount> bankAccountList;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bankAccountList = new ArrayList<BankAccount>();

        Intent intent = getIntent();
        ArrayList<BankAccount> bankAccountList2 = intent.getParcelableArrayListExtra("accounts");

        if (bankAccountList2 != null && bankAccountList2.size() > 0) {
            bankAccountList = bankAccountList2;
        } else {
            BankAccount bankAccount = new BankAccount("Hansel Castro", "748-693-548-79","Saving Account", "9850000");
            bankAccountList.add(bankAccount);
            BankAccount bankAccount_2 = new BankAccount("Ivan Xiques", "621-533-648-01","Current Account", "2721000");
            bankAccountList.add(bankAccount_2);
        }

        Button btn_sort = (Button) findViewById(R.id.button_sort);
        btn_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(bankAccountList);
                cargarRecyclerView();
            }
        });

        cargarRecyclerView();

        Button btn = (Button) findViewById(R.id.button_addAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), CreateAccount.class);
                intent.putParcelableArrayListExtra("accounts", bankAccountList);
                startActivityForResult(intent, 0);
            }
        });
    }

    public void cargarRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(bankAccountList, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, bankAccountList.get(position).getAccountType(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongItemClick(int position) {
        bankAccountList.remove(position);
        recyclerAdapter.notifyItemRemoved(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}
