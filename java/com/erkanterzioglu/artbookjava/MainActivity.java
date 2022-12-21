package com.erkanterzioglu.artbookjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.erkanterzioglu.artbookjava.databinding.ActivityMainBinding;

import java.security.spec.ECField;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ArtAdapter artAdapter;

    ArrayList<Art> artArraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        artArraylist=new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artAdapter=new ArtAdapter(artArraylist);
        binding.recyclerView.setAdapter(artAdapter);


        getData();
    }


    private void getData(){
        try {

            SQLiteDatabase sqLiteDatabase=this.openOrCreateDatabase("Arts",MODE_PRIVATE,null);

            Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM arts",null);
            int nameIx=cursor.getColumnIndex("artname");
            int idIx=cursor.getColumnIndex("id");

            while(cursor.moveToNext()){
                String name=cursor.getString(nameIx);
                int id=cursor.getInt(idIx);
                Art art=new Art(name,id);
                artArraylist.add(art);



            }
                artAdapter.notifyDataSetChanged();
                cursor.close();

        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.art_menu,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_art){
            Intent intent=new Intent(this,ArtActivity.class);
            intent.putExtra("info","new");

            startActivity(intent);
        }else if(item.getItemId() == R.id.exit_app){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}