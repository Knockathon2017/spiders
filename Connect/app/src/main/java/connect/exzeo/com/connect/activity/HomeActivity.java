package connect.exzeo.com.connect.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import connect.exzeo.com.connect.R;
public class HomeActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button searchButton = null;
    private Button editProfileButton = null;
    private Button broadcastButton = null;
    private Button bidStatusButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        searchButton = (Button)findViewById(R.id.search);
        searchButton.setOnClickListener(this);

        editProfileButton = (Button)findViewById(R.id.edit_profile);
        editProfileButton.setOnClickListener(this);

        broadcastButton = (Button)findViewById(R.id.broadcast);
        broadcastButton.setOnClickListener(this);

        bidStatusButton = (Button)findViewById(R.id.bid_status);
        bidStatusButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.search:
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_profile:
                break;
            case R.id.broadcast:
                break;
            case R.id.bid_status:
                break;
        }
    }
}
