package com.example.appbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {
    ArrayList<BankAccount> bankAccountList;
    public EditText clientName, accountNumber, accountType, balance;
    public Button save;
    public RadioGroup selected;
    public RadioButton rbAS,rbCA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        clientName = (EditText) findViewById(R.id.editText_name);
        accountNumber = (EditText) findViewById(R.id.editText_accountNumber);
        balance = (EditText) findViewById(R.id.editText_balance);
        selected = (RadioGroup) findViewById(R.id.account_type);
        rbAS = (RadioButton) findViewById(R.id.savings_account);
        rbCA = (RadioButton) findViewById(R.id.current_account);

        Intent intent = getIntent();
        ArrayList<BankAccount> bankAccountList2 = intent.getParcelableArrayListExtra("accounts");

        if (bankAccountList2 != null && bankAccountList2.size() > 0) {
            bankAccountList = bankAccountList2;
            /*BankAccount bankAccount = new BankAccount("Pamela Machado", "675-001-925-78","Corriente", "5721000");
            bankAccountList.add(bankAccount);
            for(BankAccount ba : bankAccountList){
                Log.d("myTag", "Client Name: "+ba.getClientName()+", Acount Number"+ba.getAccountNumber()+" ");
            }*/
        } else {
            bankAccountList = new ArrayList<BankAccount>();
        }

        save = (Button) findViewById(R.id.button_add_account);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clientName.getText().toString().trim().isEmpty() && !accountNumber.getText().toString().trim().isEmpty() && check(rbAS,rbCA) && !balance.getText().toString().trim().isEmpty()){
                    BankAccount newAccount;

                    String client_Name = clientName.getText().toString().trim();
                    String account_Number = accountNumber.getText().toString().trim();
                    String Balance = balance.getText().toString().trim();

                    newAccount = new BankAccount(client_Name, account_Number, Type(rbAS,rbCA), Balance);
                    bankAccountList.add(newAccount);

                    Toast.makeText(getApplicationContext(), "Success, Added Account", Toast.LENGTH_SHORT).show();

                    clientName.setText("");
                    accountNumber.setText("");
                    balance.setText("");
                }else{
                    Toast.makeText(getApplicationContext(),"All Field are required", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public boolean check(RadioButton rbAS, RadioButton rbCA){
        boolean data =false;
        if(rbAS.isChecked()){
            data = true;
        }
        if(rbCA.isChecked()){
            data = true;
        }
        return data;
    }

    public String Type(RadioButton rbAS, RadioButton rbCA){
        String data="";
        if(rbAS.isChecked()){
            data = "Saving Account";
        }
        if(rbCA.isChecked()){
            data = "Current Account";
        }
        return data;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent (this, MainActivity.class);
        intent.putParcelableArrayListExtra("accounts", bankAccountList);
        startActivityForResult(intent, 0);
    }
}
