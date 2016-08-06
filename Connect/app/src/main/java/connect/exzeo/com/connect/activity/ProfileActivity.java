package connect.exzeo.com.connect.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import connect.exzeo.com.connect.R;
import connect.exzeo.com.connect.dao.DatabaseHelper;
import connect.exzeo.com.connect.dao.DatabaseUtility;
import connect.exzeo.com.connect.data.remote.ConnectServiceClient;
import connect.exzeo.com.connect.modal.CurrentLocation;
import connect.exzeo.com.connect.modal.User;
import connect.exzeo.com.connect.modal.UserSkills;
import connect.exzeo.com.connect.ui.MultiSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper helper = null;

    private EditText firstName = null;
    private EditText lastName = null;
    private EditText password = null;
    private EditText reEnterPassword = null;
    private EditText email = null;
    private Spinner gender = null;
    private MultiSpinner skills = null;
    private CheckBox isServiceProvider = null;
    private TextView phoneNumber = null;
    private Button saveButton = null;

    private boolean isSkillsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String receivedPhoneNumber ="";
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            receivedPhoneNumber = extras.getString("phoneNumber");
        }

        saveButton = (Button)findViewById(R.id.save_profile);
        saveButton.setOnClickListener(this);

        firstName = (EditText)findViewById(R.id.first_name);
        lastName = (EditText)findViewById(R.id.last_name);
        phoneNumber = (TextView)findViewById(R.id.phone_number);
        password = (EditText)findViewById(R.id.password);
        reEnterPassword = (EditText)findViewById(R.id.re_enter_password);
        email = (EditText)findViewById(R.id.email);
        gender = (Spinner)findViewById(R.id.gender_spinner);
        skills = (MultiSpinner)findViewById(R.id.skills_spinner);
        isServiceProvider = (CheckBox)findViewById(R.id.is_service_provider);

        phoneNumber.setText(receivedPhoneNumber);
        skills.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);

        String[] skillsArray = getResources().getStringArray(R.array.skills_array);

        skills.setItems(skillsArray);


        isServiceProvider.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked){
                    isSkillsChecked = true;
                    skills.setVisibility(View.VISIBLE);

                }
                else{
                    isSkillsChecked = false;
                    skills.setVisibility(View.GONE);
                }

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.save_profile:
                saveProfile();
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                break;

        }
    }


    public void saveProfile(){
        saveSkills(skills.getSelectedIndicies());
        saveProfileData(skills.getSelectedIndicies());
//        getDBData();
    }

    public void saveSkills(List<Integer> skillId){
        List<UserSkills> userSkillsList = new ArrayList<>();

        for(Integer i : skillId){
            UserSkills userSkills = new UserSkills();
            userSkills.setPhoneNumber(phoneNumber.getText().toString());
            userSkills.setUserSkills(String.valueOf(i + 1));
            userSkillsList.add(userSkills);
        }

        helper = new DatabaseHelper(this);
        helper.open();

        DatabaseUtility database = new DatabaseUtility(helper);
        database.populateUserSkillsTable(userSkillsList);

    }

    public void saveProfileData(List<Integer> skillId){

        User user = new User();

        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setMobile(phoneNumber.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setGender(gender.getSelectedItem().toString());
        if(isSkillsChecked) {
            user.setRole("1");
        }
        else{
            user.setRole("2");
        }

        CurrentLocation currentLocation = getLocation();

        user.setLatitude(currentLocation.getLattitude());
        user.setLongitude(currentLocation.getLongitude());

        user.setRatings("0");

        helper = new DatabaseHelper(this);
        helper.open();

        DatabaseUtility database = new DatabaseUtility(helper);
        database.populateUserTable(user);

        ArrayList<String> userSkillsList = new ArrayList<>();

        for(Integer i : skillId){
            userSkillsList.add(String.valueOf(i + 1));
        }

        ConnectServiceClient.getConnectAPI().updateUser(user.getFirstName(),user.getLastName(),user.getMobile(),user.getEmail(),user.getPassword(),user.getGender(),user.getRole(),user.getLatitude()
                ,user.getLongitude(),userSkillsList,user.getRatings()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//                response.body();
                Toast.makeText(getApplicationContext(), "Profile created successfully.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occured.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public CurrentLocation getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                CurrentLocation currentLocation = new CurrentLocation();

                currentLocation.setLongitude(String.valueOf(location.getLongitude()));
                currentLocation.setLattitude(String.valueOf(location.getLatitude()));

                return currentLocation;
            }
        }
        return null;
    }

}
