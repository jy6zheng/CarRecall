package academy.learnprogramming.carrecall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class CarSelection extends AppCompatActivity {
    private Spinner cartypeSpinner;
    private ArrayAdapter<String> cartypesAdapter;
    private String cartypes;
    private Button go;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_selection);

        cartypeSpinner = (Spinner) findViewById(R.id.cartypespinner);
        cartypesAdapter = new ArrayAdapter<String>(CarSelection.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.carbrand));
        cartypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cartypeSpinner.setAdapter(cartypesAdapter);

        cartypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        cartypes = "Toyota";
                        break;
                    case 1:
                        cartypes = "Audi";
                        break;
                    case 2:
                        cartypes = "Honda";
                        break;


                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        go = (Button) findViewById(R.id.search_button);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(getApplicationContext(), MainActivity.class);
                go.putExtra("zheng.joyce.cartype", cartypes);
                startActivity(go);
            }
        });

    }
}
