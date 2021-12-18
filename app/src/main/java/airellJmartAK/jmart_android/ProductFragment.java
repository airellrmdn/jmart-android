package airellJmartAK.jmart_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import airellJmartAK.jmart_android.request.RequestFactory;

/**
 * Product Fragment
 *
 * Digunakan untuk menampilkan informasi produk
 * yang tersedia pada aplikasi
 *
 * @author Airell Ramadhan B
 * @version 0.1
 */

public class ProductFragment extends Fragment {
    private static final Gson gson = new Gson();
    public static ArrayList<Product> productsList = new ArrayList<>();
    ProductFragmentListener fragmentListener;
    public static ArrayAdapter<Product> listViewAdapter;
    public static ArrayAdapter<Product> listViewAdapter2;
    final int pageSize = 20;
    static int page = 0;
    public static Product productClicked = null;
    public static ListView listView;

    public interface ProductFragmentListener {
        void getProductList(ArrayAdapter<Product> listViewAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View productView = inflater.inflate(R.layout.fragment_product,container,false);

        Button prevButton = productView.findViewById(R.id.buttonPrev);
        Button nextButton = productView.findViewById(R.id.buttonNext);
        Button goButton = productView.findViewById(R.id.buttonGo);
        EditText inputPage = productView.findViewById(R.id.inputPage);
        listView = productView.findViewById(R.id.list_product);

        if (FilterFragment.status == 1) {
            listViewAdapter2 = new ArrayAdapter<Product>(getActivity(), android.R.layout.simple_list_item_1, FilterFragment.filteredList);
            listView.setAdapter(listViewAdapter2);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    productClicked = (Product) listView.getItemAtPosition(position);
                    Toast.makeText(getActivity(), "Product Selected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProductFragment.this.getActivity(), ProductDetailActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray object = new JSONArray(response);
                        if (object != null) {
                            productsList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Product>>() {}.getType());
                            System.out.println(productsList);
                            listViewAdapter = new ArrayAdapter<Product>(getActivity(), android.R.layout.simple_list_item_1, productsList);
                            listView.setAdapter(listViewAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    productClicked = (Product) listView.getItemAtPosition(position);
                                    Toast.makeText(getActivity(), "Product Selected", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ProductFragment.this.getActivity(), ProductDetailActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Next Page", Toast.LENGTH_SHORT).show();
                    page += 1;
                    getActivity().finish();
                    getActivity().startActivity(getActivity().getIntent());
                }
            });

            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Previous Page", Toast.LENGTH_SHORT).show();
                    page -= 1;
                    if (page < 0) {
                        page = 0;
                    }
                    getActivity().finish();
                    getActivity().startActivity(getActivity().getIntent());
                }
            });

            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Go!", Toast.LENGTH_SHORT).show();
                    page = Integer.parseInt(inputPage.getText().toString()) - 1;
                    getActivity().finish();
                    getActivity().startActivity(getActivity().getIntent());
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requestQueue.add(RequestFactory.getPage("product", page, pageSize, listener, null));
        }
        return productView;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof ProductFragmentListener){
            fragmentListener = (ProductFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ProductFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener = null;
    }
}