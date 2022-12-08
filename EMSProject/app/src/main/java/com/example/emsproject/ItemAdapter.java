package com.example.emsproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import Model.Candidate;
import Model.Tests;

public class ItemAdapter extends BaseAdapter {
    private TextView tvwItem_Name, tvwItem_Company, tvwItem_Id;
    private RelativeLayout rltItem;
    private View row;
    private Context context;
    private ArrayList<Tests> listOfTests;

    public ItemAdapter(Context context, ArrayList<Tests> listOfTests) {
        this.context = context;
        this.listOfTests = listOfTests;
    }

    @Override
    public int getCount() {
        return listOfTests.size();
    }

    @Override
    public Object getItem(int i) {
        return listOfTests.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.list_item, null);

        ConstraintGUI();

        Tests selectedTest = listOfTests.get(i);

        SetUpInformation(selectedTest);

        ActionListener(i);

        return row;
    }

    private void ConstraintGUI() {
        rltItem = (RelativeLayout) row.findViewById(R.id.rltItem);
        tvwItem_Name = (TextView) row.findViewById(R.id.tvwItemName);
        tvwItem_Id = (TextView) row.findViewById(R.id.tvwItemID);
        tvwItem_Company = (TextView)  row.findViewById(R.id.tvwItemCompany);
    }

    private void ActionListener(int index) {
        rltItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TestActivity.class);
                intent.putExtra("TestId", String.valueOf(index));
                context.startActivity(intent);
            }
        });
    }

    private void SetUpInformation(Tests selectedTest) {
        tvwItem_Name.setText(selectedTest.getName());
        tvwItem_Id.setText("ID : " + selectedTest.getId());
        tvwItem_Company.setText("Company : " + selectedTest.getCompany());
    }
}
