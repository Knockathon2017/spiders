package connect.exzeo.com.connect.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import connect.exzeo.com.connect.R;
import connect.exzeo.com.connect.data.remote.ConnectServiceClient;
import connect.exzeo.com.connect.modal.User;
import modal.ADDRESS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener{

    private ProgressDialog progress;
    private EditText verificationCode;
    private EditText phoneNumber;
    private Button verifyButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        phoneNumber = (EditText)findViewById(R.id.phone_number);

        verifyButton = (Button)findViewById(R.id.phone_number_verification_button);
        verifyButton.setOnClickListener(this);

        registerButton = (Button)findViewById(R.id.register_button);
        registerButton.setOnClickListener(this);

        registerButton.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        String inputPhoneNumber = phoneNumber.getText().toString().trim();

        switch(view.getId()){
            case R.id.phone_number_verification_button:
                if (inputPhoneNumber.matches("")) {
                    Toast.makeText(this, "Please enter phone number.", Toast.LENGTH_SHORT).show();
                    return;
                }
                progress = ProgressDialog.show(this, "Verification", "Please wait for verification code...", true);
                verifyUser();
                break;

            case R.id.register_button:
                if (inputPhoneNumber.matches("")) {
                    Toast.makeText(this, "Please enter phone number.", Toast.LENGTH_SHORT).show();
                    return;
                }
                progress = ProgressDialog.show(this, "Register", "Please wait while registering...", true);
                registerUser();
                break;
        }
    }

    public void verifyUser(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.dismiss();
                verificationCode = (EditText)findViewById(R.id.verification_code);
                Random randomNumber = new Random();
                int randomVerificationCode = 100000 + randomNumber.nextInt(900000);
                verificationCode.setText(String.valueOf(randomVerificationCode));
                verifyButton.setVisibility(View.GONE);
                registerButton.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    public void registerUser(){
//        new LongOperation().execute(phoneNumber.getText().toString().trim());
        ConnectServiceClient.getConnectAPI().registerUser(phoneNumber.getText().toString().trim()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//                response.body();
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Registered successfully.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                phoneNumber = (EditText)findViewById(R.id.phone_number);
                intent.putExtra("phoneNumber", phoneNumber.getText().toString().trim());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Some error occured.", Toast.LENGTH_SHORT).show();
            }
        });

        /*Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
        phoneNumber = (EditText)findViewById(R.id.phone_number);
        intent.putExtra("phoneNumber", phoneNumber.getText().toString().trim());
        startActivity(intent);*/
        /*ConnectServiceClient.getConnectAPI().gobarSearch("john").enqueue(new Callback<List<ADDRESS>>() {
            @Override
            public void onResponse(Call<List<ADDRESS>> call, Response<List<ADDRESS>> response) {
//                response.body();
            }

            @Override
            public void onFailure(Call<List<ADDRESS>> call, Throwable t) {

            }
        });*/

    }

    /*private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            System.out.println("============= params[0] ============ "+ params[0]);
            ConnectServiceClient.getConnectAPI().registerUser(params[0]).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
//                response.body();
                    System.out.println("========= success ==============");
                    Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                    phoneNumber = (EditText)findViewById(R.id.phone_number);
                    intent.putExtra("phoneNumber", phoneNumber.getText().toString().trim());
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println("========= fail ==============" );
                    t.printStackTrace();
                    Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                    phoneNumber = (EditText)findViewById(R.id.phone_number);
                    intent.putExtra("phoneNumber", phoneNumber.getText().toString().trim());
                    startActivity(intent);
                }
            });
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }*/
}
