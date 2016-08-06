package connect.exzeo.com.connect.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import connect.exzeo.com.connect.R;
import connect.exzeo.com.connect.data.remote.ConnectServiceClient;
import connect.exzeo.com.connect.modal.CurrentLocation;
import connect.exzeo.com.connect.modal.UserNew;
import connect.exzeo.com.connect.modal.ViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner searchSpinner = null;

    private Button searchButton = null;

    public static ListView listView = null;

    private ArrayList <HashMap<String, Object>> availableUser;

    public ListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        searchButton = (Button)findViewById(R.id.search_profile);
        searchButton.setOnClickListener(this);

        searchSpinner = (Spinner)findViewById(R.id.skills_spinner);


        ArrayAdapter<CharSequence> skillsAdapter = ArrayAdapter.createFromResource(this, R.array.skills_array, android.R.layout.simple_spinner_item);
        skillsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSpinner.setAdapter(skillsAdapter);

        listView = (ListView)findViewById(R.id.searchResultlList);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.search_profile:
                search();
                break;
        }
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

    public void search(){
        String selectedSkill =  String.valueOf(searchSpinner.getSelectedItemPosition() + 1);
        CurrentLocation currentLocation = getLocation();
        ConnectServiceClient.getConnectAPI().getSearchResult(selectedSkill,currentLocation.getLattitude(),currentLocation.getLongitude()).enqueue(new Callback<List<UserNew>>() {
            @Override
            public void onResponse(Call<List<UserNew>> call, Response<List<UserNew>> response) {
                List<UserNew> userList = response.body();
                displaySearchResult(userList);
            }

            @Override
            public void onFailure(Call<List<UserNew>> call, Throwable t) {
            }
        });
    }

    public void displaySearchResult(List<UserNew> userList){

        HashMap<String, Object> userData = null;
        UserNew user = null;
        availableUser = new ArrayList<HashMap<String,Object>>();
        for(int i = 0 ; i < userList.size() ; i++){
            user = userList.get(i);
            userData = new HashMap<String, Object>();
            userData.put("name",user.getFirstName() + " " + user.getLastName());
            userData.put("ratings",user.getRating());
            userData.put("phoneNumber",user.getMobile());
            availableUser.add(userData);
        }

        adapter = new ListAdapter(availableUser,SearchActivity.this);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position,long id) {
                @SuppressWarnings("unchecked")
                HashMap<String, Object> user = (HashMap<String, Object>) adapter.getAdapter().getItem(position);
                try	{
                    Intent commentIntent = new Intent(view.getContext(), CommentActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundleObj = new Bundle();
                    bundleObj.putString("phoneNumber", (String)user.get("phoneNumber"));
                    commentIntent.putExtras(bundleObj);
                    view.getContext().startActivity(commentIntent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);		//Specify an explicit transition animation to perform next

                } catch(Exception ex) {
                }
            }
        };
        listView.setOnItemClickListener (listener);
    }


    private class ListAdapter extends BaseAdapter {

        private ArrayList<HashMap<String, Object>> lisns;
        private LayoutInflater mInflater;


        public ListAdapter(ArrayList<HashMap<String, Object>> lisns, Context context){
            this.lisns = lisns;
            mInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return lisns.size();
        }

        @Override
        public Object getItem(int position) {
            return lisns.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // A ViewHolder keeps references to children views to avoid unneccessary calls
            // to findViewById() on each row.
            ViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row_layout, null);
                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.image = (ImageView) convertView.findViewById(R.id.image);
                holder.ratings = (RatingBar) convertView.findViewById(R.id.ratings);
                holder.phoneNumber = (TextView) convertView.findViewById(R.id.phone_number);
                holder.callButton = (Button) convertView.findViewById(R.id.call);
                convertView.setTag(holder);

            }else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }


            // Bind the data with the holder.
//            String letter = String.valueOf(((String) lisns.get(position).get("name")).charAt(0));
//            ColorGenerator generator = ColorGenerator.MATERIAL;
//            TextDrawable drawable = TextDrawable.builder()
//                    .buildRound(letter, generator.getRandomColor());
//            holder.image.setImageDrawable(drawable);
            //holder.lisnDetail.setText((String) lisns.get(position).get(NowLisnActivity.LISN_DETAIL_KEY));
            holder.name.setText((String) lisns.get(position).get("name"));

            holder.phoneNumber.setText((String) lisns.get(position).get("phoneNumber"));


            holder.ratings.setRating((Float)lisns.get(position).get("ratings"));
            holder.ratings.setStepSize(Float.parseFloat("0.5"));

            holder.callButton.setFocusable(false);
            holder.callButton.setFocusableInTouchMode(false);


            holder.callButton.setOnClickListener( new View.OnClickListener(){
                public void onClick(View view){
                    int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CALL_PHONE);
                    if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + ((String) lisns.get(position).get("phoneNumber"))));
                        startActivity(callIntent);
                    }
                }});

            return convertView;
        }

    }//End of list adapter
}
