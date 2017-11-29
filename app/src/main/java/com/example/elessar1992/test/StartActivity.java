package com.example.elessar1992.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by elessar1992 on 11/27/17.
 */

/*public class StartActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";
    Button start;
    Button showmap;
    private TextView textView;
    private Button button;
    public static final String ANONYMOUS = "anonymous";
    //Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String mUsername;
    private GoogleApiClient mGoogleApiClient;
    private String mPhotoUrl;



	/*EditText etGeolocation, etQuery;
	Button btnSearch;
	ListView listView;
	String Client_ID = "BSQDX22EU4YYHOGSSPF2NHFC4QWNM1XWYNRUTTWRB3DJTBPW";
	String Client_Secret = "IRP4Y2X0CENP0QDV24ASTRG3M4WTYUPPPE2YMKO2CVVSXRJV";
	String apiVersion = "20161010";
	String geoLocation = "40.7,-74";
	String query = "cafe";

c
	List<Item_> item_list = new ArrayList<Item_>();*/

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.start);
        showmap = (Button) findViewById(R.id.showmap);

        textView = (TextView) findViewById(R.id.textView);
        mUsername = ANONYMOUS;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mUser.getDisplayName();
            if (mUser.getPhotoUrl() != null) {
                mPhotoUrl = mUser.getPhotoUrl().toString();
            }
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                //.enableAutoManage(this /* FragmentActivity , /*this /* OnConnectionFailedListener )*/
                //.addApi(Auth.GOOGLE_SIGN_IN_API)
                //.build();




		/*start.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent myIntent = new Intent(MainActivity.this, ShowEvent.class);
				MainActivity.this.startActivity(myIntent);
			}
		});

		showmap.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
				MainActivity.this.startActivity(myIntent);
			}
		});

		showmap.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent myIntent = new Intent(MainActivity.this, MapsEvents.class);
				MainActivity.this.startActivity(myIntent);
			}
		});*/

		/*findViewByIds();
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ExploreAsyncTask exploreAsyncTask = new ExploreAsyncTask();
				exploreAsyncTask.execute();

				FourSquareService fourSquareService = FourSquareService.retrofit.create(FourSquareService.class);
				final Call<Explore> call = fourSquareService.requestExplore(Client_ID, Client_Secret, apiVersion, geoLocation, query);
				call.enqueue(new Callback<Explore>() {
					@Override
					public void onResponse(Call<Explore> call, Response<Explore> response) {
						item_list = response.body().getResponse().getGroups().get(0).getItems();
						ExploreListAdapter exploreListAdapter = new ExploreListAdapter(getApplicationContext(), R.layout.item_list, item_list);
						listView.setAdapter(exploreListAdapter);
					}

					@Override
					public void onFailure(Call<Explore> call, Throwable t) {

					}
				});
			}
		});
	}
	void findViewByIds()
	{
		etGeolocation = (EditText) findViewById(R.id.et_geolocation);
		etQuery = (EditText) findViewById(R.id.et_query);
		btnSearch = (Button) findViewById(R.id.btn_search);
		listView = (ListView)findViewById(R.id.listivew);

	}

	public class ExploreAsyncTask extends AsyncTask<Void,Void,List<Item_>> {

		public ExploreAsyncTask() {
			super();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected List<Item_> doInBackground(Void... voids) {
			FourSquareService fourSquareService = FourSquareService.retrofit.create(FourSquareService.class);
			final Call<Explore> call = fourSquareService.requestExplore(Client_ID, Client_Secret, apiVersion, geoLocation, query);

			try {
				Explore explore =  call.execute().body();
				item_list = explore.getResponse().getGroups().get(0).getItems();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return item_list;
		}

		@Override
		protected void onPostExecute(List<Item_> item_s) {
			super.onPostExecute(item_s);
			ExploreListAdapter exploreListAdapter = new ExploreListAdapter(getApplicationContext(), R.layout.item_list, item_s);
			listView.setAdapter(exploreListAdapter);
		}
	}

    }

    @Override
    public void onStart()
    {
        super.onStart();
        String currentUser = mUser.getDisplayName();
        textView.setText(currentUser);
        //startActivity(new Intent(this, MapsEvents.class));
        //startActivity(new Intent(this, MyMapActivity.class));
        // Check if user is signed in.
        // TODO: Add code to check if user is signed in.
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.sign_out_menu:
                mAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            case R.id.showmap:
                startActivity(new Intent(this, MapsEvents.class));
                return true;

            case R.id.data:
                startActivity(new Intent(this, ShowEvent.class));


            default:
                return super.onOptionsItemSelected(item);
        }

    }
}*/
