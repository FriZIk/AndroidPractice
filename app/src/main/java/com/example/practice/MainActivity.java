package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity
{
    private TextView ErrorText,LoginText,PasswordText;
    private static final String Tag = "Практика";
    private String Login,Password;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ErrorText = findViewById(R.id.ErrorMessage);
        LoginText = findViewById(R.id.LoginText);
        PasswordText = findViewById(R.id.PasswordText);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        setTitle("DIY helper");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("Login", LoginText.getText().toString());
        outState.putString("Password",PasswordText.getText().toString());
        Log.i(Tag,LoginText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        LoginText.setText(savedInstanceState.getString("Login"));
        PasswordText.setText(savedInstanceState.getString("Password"));
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    public void LoginButton(View view) {
        Login = LoginText.getText().toString();
        Password = PasswordText.getText().toString();

        // Проверка на совпадение с одинм из пароль/логинов
        int CountOfUsers = 3;
        String[] LoginsList = getResources().getStringArray(R.array.Logins);
        String[] PasswordsList = getResources().getStringArray(R.array.Passwords);
        int IndexOfСoincidence = -100,i;

        for (i = 0;i < CountOfUsers; i++)
            if(LoginsList[i].equals(Login))break;

        if(i < CountOfUsers)IndexOfСoincidence = i;

        if(IndexOfСoincidence == -100)
        {
            Log.i(Tag,"Неверный логин или пароль!");
            ErrorText.setVisibility(View.VISIBLE);
        }
        else
        {
            if (PasswordsList[IndexOfСoincidence].equals(Password))
            {
                ErrorText.setVisibility(View.INVISIBLE);
                Log.i(Tag, "Выполнен вход! Запускаю следующий активити");
                //Intent intent = new Intent(MainActivity.this, web.class);
                Intent intent = new Intent(MainActivity.this, web.class);
                intent.putExtra("Index", IndexOfСoincidence);
                startActivity(intent);
                ErrorText.setVisibility(View.INVISIBLE);
                LoginText.setText("");
                PasswordText.setText("");
            }
            else
            {
                Log.i(Tag, "Неверный логин или пароль!");
                ErrorText.setVisibility(View.VISIBLE);
            }
        }
    }
}