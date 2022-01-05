package com.example.shoping_app.FrontSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.shoping_app.LoginActivity;
import com.example.shoping_app.Prevalent.Prevalent;
import com.example.shoping_app.ProductSetFragment;
import com.example.shoping_app.R;
import com.example.shoping_app.SearchProductActivity;
import com.example.shoping_app.databinding.ActivityHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private Toolbar toolbar;
    private DatabaseReference databaseReference;
    public TextView UserProfileNameView;
   public CircleImageView UserProfileImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_home);

        Paper.init(this);


        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Flaoting Action Button", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent=new Intent(HomeActivity.this, CartActivity.class);
               startActivity(intent);
            }
        });

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        UserProfileNameView = headerview.findViewById(R.id.user_profile_name_id);
        UserProfileImageView = headerview.findViewById(R.id.user_profile_image_id);
        UserProfileNameView.setText(Prevalent.currentUser.getName());
       Picasso.get().load(Prevalent.currentUser.getImage()).placeholder(R.drawable.profile).into(UserProfileImageView);



         ProductSetFragment fragment=new ProductSetFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_activity_id,fragment);
        transaction.commit();


    }

    @Override
    protected void onStart() {
        super.onStart();


            }



        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item){
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            switch (item.getItemId()) {
                case R.id.cart_home_id:
                    Intent intent1=new Intent(HomeActivity.this,CartActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.orders_home_id:
                    Intent intent2=new Intent(HomeActivity.this, MyOrders.class);
                    startActivity(intent2);
                    break;



                case R.id.setting_home_id:
                    Intent intent4=new Intent(HomeActivity.this, SettingActivity.class);
                    startActivity(intent4);

                    break;
                case R.id.logout_home_id:
                    Paper.book().destroy();
                    Intent intent5 = new Intent(HomeActivity.this, LoginActivity.class);
                    intent5.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent5);
                    finish();
                    break;
                case R.id.search_id:
                    Intent intent6=new Intent(HomeActivity.this, SearchProductActivity.class);
                    startActivity(intent6);
                    break;
            }


            return true;
        }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.home, menu);
            return true;
        }


    }
