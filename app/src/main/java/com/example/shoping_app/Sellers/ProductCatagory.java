package com.example.shoping_app.Sellers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.shoping_app.R;

public class ProductCatagory extends AppCompatActivity {
    private ImageView headphone,watch,laptop;
    private ImageView mobile,radio,tv;
    private ImageView femaledreses,sunglass,hat;
    private ImageView purces_bag,shoes,sport_shirt;
    private ImageView sweather,tshirt,hollyball;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catagory);
        headphone=findViewById(R.id.headphones_id);
        watch=findViewById(R.id.watch_id);
        laptop=findViewById(R.id.laptop_id);
        mobile=findViewById(R.id.mobiles_id);
        radio=findViewById(R.id.radios_id);
        tv=findViewById(R.id.Tvs_id);
        femaledreses=findViewById(R.id.female_dresses_id);
        sunglass=findViewById(R.id.sun_glasses_id);
        hat=findViewById(R.id.hats_id);
        purces_bag=findViewById(R.id.purses_bags_id);
        shoes=findViewById(R.id.shoes_id);
        sport_shirt=findViewById(R.id.sports_shirt_id);
        sweather=findViewById(R.id.sweathers_id);
        tshirt=findViewById(R.id.tshirts_id);
        hollyball=findViewById(R.id.hollyball_id);

        headphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
                intent.putExtra("category","Headphones");
                startActivity(intent);
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
                intent.putExtra("category","Watches");
                startActivity(intent);
            }
        });
        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
                intent.putExtra("category","Laptops");
                startActivity(intent);
            }
        });
       mobile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
               intent.putExtra("category","Mobiles");
               startActivity(intent);
           }
       });
       radio.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
               intent.putExtra("category","Radios");
               startActivity(intent);
           }
       });
      tv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
              intent.putExtra("category","Tv's");
              startActivity(intent);
          }
      });
      femaledreses.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
              intent.putExtra("category","Female Dresses");
              startActivity(intent);
          }
      });
     sunglass.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
             intent.putExtra("category","Sun Glasses");
             startActivity(intent);
         }
     });
     hat.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
             intent.putExtra("category","Hats");
             startActivity(intent);
         }
     });
     purces_bag.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
             intent.putExtra("category","Purses and bags");
             startActivity(intent);
         }
     });
     shoes.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
             intent.putExtra("category","Shoes");
             startActivity(intent);
         }
     });
     sport_shirt.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
             intent.putExtra("category","Sport Shirts");
             startActivity(intent);
         }
     });
     sweather.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
             intent.putExtra("category","Sweaters");
             startActivity(intent);
         }
     });
     tshirt.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
             intent.putExtra("category","T-Shirts");
             startActivity(intent);
         }
     });
     hollyball.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(ProductCatagory.this, AddProductActivity.class);
             intent.putExtra("category","Balls");
             startActivity(intent);
         }
     });
    }
}