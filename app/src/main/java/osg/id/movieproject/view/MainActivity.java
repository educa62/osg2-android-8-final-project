package osg.id.movieproject.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import osg.id.movieproject.Injection;
import osg.id.movieproject.R;
import osg.id.movieproject.adapter.MovieAdapter;
import osg.id.movieproject.main.MainContract;
import osg.id.movieproject.main.MainPresenter;
import osg.id.movieproject.model.Movies;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.rv_movies)
    public RecyclerView recyclerView;

    private ProgressDialog progressDialog;
    private MovieAdapter adapter;
    private final List<Movies> moviesArrayList = new ArrayList<>();
    private final MainPresenter mainPresenter = new MainPresenter(Injection.provideRepository(), this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAdapter();
        mainPresenter.getDataListMovies(MainActivity.this);
    }

    private void initAdapter() {
        adapter = new MovieAdapter(getApplicationContext(), moviesArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.setTitle("Harap tunggu");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showDataList(List<Movies> moviesList) {
        moviesArrayList.addAll(moviesList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showFailureMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
