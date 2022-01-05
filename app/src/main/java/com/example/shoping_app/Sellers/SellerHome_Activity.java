package com.example.shoping_app.Sellers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.shoping_app.MainActivity;
import com.example.shoping_app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.shoping_app.databinding.ActivitySellerHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SellerHome_Activity extends AppCompatActivity {

    private ActivitySellerHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivitySellerHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


       navView.setSelectedItemId(R.id.Seller_navigation_home);
        if (savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout111,new SellerHomeFragment()).commit();
        }

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId())
                {
                    case R.id.Seller_navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layout111,new SellerHomeFragment()).commit();
                        break;
                    case R.id.Seller_navigation_Add:
                       // getSupportFragmentManager().beginTransaction().replace(R.id.layout_id,new SellerAddFragment()).commit();
                        Intent intent2=new Intent(SellerHome_Activity.this,ProductCatagory.class);
                        startActivity(intent2);
                        finish();
                        break;

                    case R.id.Seller_navigation_Logout:
                      final FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                      firebaseAuth.signOut();
                        Intent intent=new Intent(SellerHome_Activity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                        break;

                    case R.id.Seller_navigation_orders:

                        getSupportFragmentManager().beginTransaction().replace(R.id.layout111,new SellerNewOrder()).commit();

                        break;



                }

           // getSupportFragmentManager().beginTransaction().replace(R.id.layout_id,selectedFragment).commit();
                return true;
            }
        });
    }

}