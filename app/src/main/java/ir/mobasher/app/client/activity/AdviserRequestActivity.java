package ir.mobasher.app.client.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ir.mobasher.app.client.R;
import ir.mobasher.app.client.adapter.AdviserTypesExpandableListAdapter;
import ir.mobasher.app.client.api.APIInterface;
import ir.mobasher.app.client.api.adviseType.AdviseTypeParents;
import ir.mobasher.app.client.api.adviseType.AdviseTypeChild;
import ir.mobasher.app.client.api.adviseType.AdviseType;
import ir.mobasher.app.client.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdviserRequestActivity extends AppCompatActivity {

    AdviserTypesExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listDataId;
    private int lastExpandedPosition = -1;
    private AdviseType adviseTypeList;
    private List<AdviseTypeParents> parents;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adviser_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.adviserRequestsToolbar);
        setSupportActionBar(toolbar);
        forceRTLIfSupported();

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.adviserTypesExpList);

        // preparing list data
       // prepareListData();

        getAdviseTypes();

     }

    private void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listDataId = new HashMap<String, List<String>>();


        parents = adviseTypeList.getData();

        for (int i = 0; i < parents.size(); i++) {
            listDataHeader.add(parents.get(i).getName());
            List<AdviseTypeChild> child = parents.get(i).getData();
            List<String> childsName = new ArrayList<String>();
            List<String> childAdviseTypeId = new ArrayList<String>();
            for (int j = 0 ; j < child.size(); j++) {
                childsName.add(child.get(j).getName());
                childAdviseTypeId.add(child.get(j).getAdviceTypeId());
            }

            listDataChild.put(listDataHeader.get(i), childsName);
            listDataId.put(listDataHeader.get(i), childAdviseTypeId);

        }


        listAdapter = new AdviserTypesExpandableListAdapter(this, listDataHeader, listDataChild, listDataId);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition)
                                + " : "
                                + listDataId.get(listDataHeader.get(groupPosition)).get(childPosition)
                                , Toast.LENGTH_SHORT)
                        .show();

                Intent i = new Intent(AdviserRequestActivity.this, WaitForRequestActivity.class);
                finish();
                startActivity(i);
                return false;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void getAdviseTypes(){
        APIInterface service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);
        Call<AdviseType> responseCall = service.getAdviseTypes();

        responseCall.enqueue(new Callback<AdviseType>() {
            @Override
            public void onResponse(Call<AdviseType> call, Response<AdviseType> response) {
                if (response.isSuccessful()){
                    adviseTypeList = response.body();
                    prepareListData();

                }else {

                }
            }

            @Override
            public void onFailure(Call<AdviseType> call, Throwable t) {
                Toast.makeText(getBaseContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

