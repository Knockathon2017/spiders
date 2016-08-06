package connect.exzeo.com.connect.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import connect.exzeo.com.connect.R;
import connect.exzeo.com.connect.dao.DatabaseHelper;
import connect.exzeo.com.connect.dao.DatabaseUtility;
import connect.exzeo.com.connect.data.remote.ConnectServiceClient;
import connect.exzeo.com.connect.modal.CommentViewHolder;
import connect.exzeo.com.connect.modal.Comments;
import connect.exzeo.com.connect.modal.User;
import connect.exzeo.com.connect.modal.ViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener{

    public static ListView listView = null;

    private ArrayList<HashMap<String, Object>> availableComment;

    public ListAdapter adapter = null;

    private Button postCommentButton = null;

    String phoneNumber ="";

    DatabaseHelper helper = null;

    private EditText comment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        postCommentButton = (Button)findViewById(R.id.comment_button);
        postCommentButton.setOnClickListener(this);

        comment = (EditText)findViewById(R.id.comment);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");

        ConnectServiceClient.getConnectAPI().getComments(phoneNumber).enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                List<Comments> commentsList = response.body();
                displayComments(commentsList);
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
            }
        });

        listView = (ListView)findViewById(R.id.commentList);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.comment_button:
                postComment();
                break;
        }
    }

    public void postComment(){

        helper = new DatabaseHelper(this);
        helper.open();
        DatabaseUtility database = new DatabaseUtility(helper);
        User user = database.getUserData();


        ConnectServiceClient.getConnectAPI().postComment(phoneNumber,user.getMobile(),comment.getText().toString(),"0").enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(getApplicationContext(), "Comment posted successfully.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CommentActivity.this, SearchActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occured.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void displayComments(List<Comments> commentList){

        HashMap<String, Object> commentData = null;
        Comments comments = null;
        availableComment = new ArrayList<HashMap<String,Object>>();
        for(int i = 0 ; i < commentList.size() ; i++){
            comments = commentList.get(i);
            commentData = new HashMap<String, Object>();
            commentData.put("name",comments.getCommentedBy());
            commentData.put("comment",comments.getComment());
            commentData.put("rating",comments.getRating());
            availableComment.add(commentData);
        }

        adapter = new ListAdapter(availableComment,CommentActivity.this);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position,long id) {
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
            CommentViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.comment_row_layout, null);
                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new CommentViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.comment = (TextView) convertView.findViewById(R.id.comment);
                convertView.setTag(holder);

            }else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (CommentViewHolder) convertView.getTag();
            }


            holder.name.setText("Commented By : " + (String) lisns.get(position).get("name"));

            holder.comment.setText((String) lisns.get(position).get("comment"));

            return convertView;
        }

    }//End of list adapter
}
