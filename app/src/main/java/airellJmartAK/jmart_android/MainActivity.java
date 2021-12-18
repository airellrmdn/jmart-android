package airellJmartAK.jmart_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import airellJmartAK.jmart_android.model.Product;

/**
 * MainActivity Class
 *
 * Digunakan untuk menampilkan halaman utama aplikasi.
 *
 * @author Airell Ramadhan B
 */

public class MainActivity extends AppCompatActivity implements ProductFragment.ProductFragmentListener {
    ArrayAdapter<Product> listViewAdapterMain;

    @Override
    public void getProductList(ArrayAdapter<Product> listViewAdapter) {
        listViewAdapterMain = listViewAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("JMART");

        TabLayout tabLayout = findViewById(R.id.tabLayoutMain);
        ViewPager viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new ProductFragment(), "Product");
        vpAdapter.addFragment(new FilterFragment(), "Filter");
        viewPager.setAdapter(vpAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

      /** Membuat fungsi search menu pada list product */
        MenuItem search = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (FilterFragment.status == 1){
                    ProductFragment.listViewAdapter2.getFilter().filter(newText);
                } else {
                    ProductFragment.listViewAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

        /** Show create button only if the account has a store */
        MenuItem create = menu.findItem(R.id.create_button);
        create.setVisible(LoginActivity.getLoggedAccount().store != null);

        return true;
    }

    /** Membuat menu yang dipilih dapat membuka activity yang sesuai */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.account_button) {
            Toast.makeText(this, "Account Menu", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.create_button) {
            Toast.makeText(this, "Create Product", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, CreateProductActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}