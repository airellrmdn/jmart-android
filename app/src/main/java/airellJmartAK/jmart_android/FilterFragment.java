package airellJmartAK.jmart_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import airellJmartAK.jmart_android.model.Product;
import airellJmartAK.jmart_android.model.ProductCategory;
import airellJmartAK.jmart_android.request.RequestFactory;

public class FilterFragment extends Fragment {
    private static final Gson gson = new Gson();
    public static ArrayList<Product> filteredList = new ArrayList<>();
    public static ArrayAdapter<Product> listViewAdapter;
    ProductFragment.ProductFragmentListener fragmentListener;
    final int pageSize = 20;
    static int page = 0;
    public static int status = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View productView = inflater.inflate(R.layout.fragment_filter, container, false);
        EditText name = productView.findViewById(R.id.inputNameFilter);
        EditText lowPrice = productView.findViewById(R.id.lowInput);
        EditText highPrice = productView.findViewById(R.id.highInput);
        CheckBox usedCheck = productView.findViewById(R.id.checkBox2);
        CheckBox newCheck = productView.findViewById(R.id.checkBox);
        Spinner category = productView.findViewById(R.id.categoryDrawFilter);
        Button apply = productView.findViewById(R.id.applyFilter);
        Button clear = productView.findViewById(R.id.clearFilter);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clear button clicked", Toast.LENGTH_SHORT).show();
                if (status == 1){
                    status = 0;
                    getActivity().finish();
                    getActivity().startActivity(getActivity().getIntent());
                }
                name.setText("");
                lowPrice.setText("");
                highPrice.setText("");
                usedCheck.setChecked(false);
                newCheck.setChecked(false);
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Apply button clicked", Toast.LENGTH_SHORT).show();
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray object = new JSONArray(response);
                            if(object != null){
                                filteredList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Product>>(){}.getType());
                                listViewAdapter = new ArrayAdapter<Product>(getActivity(), android.R.layout.simple_list_item_1, filteredList);
                                ProductFragment.listView.setAdapter(listViewAdapter);
                                Toast.makeText(getActivity(),"Filtered",Toast.LENGTH_SHORT).show();
                                status = 1;
                            }else{
                                Toast.makeText(getActivity(),"No Data",Toast.LENGTH_SHORT).show();
                            }
                            getActivity().finish();
                            getActivity().overridePendingTransition(0,0);
                            getActivity().startActivity(getActivity().getIntent());
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(RequestFactory.getProductFiltered(page, pageSize, name.getText().toString(), Double.parseDouble(lowPrice.getText().toString()), Double.parseDouble(highPrice.getText().toString()), ProductCategory.valueOf(category.getSelectedItem().toString()), usedCheck.isChecked(), listener, null));
            }
        });

        return productView;
    }
}