package com.mobile.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CompteAdapter extends BaseAdapter {

    private Context context;
    private List<Compte> compteList;

    public CompteAdapter(Context context, List<Compte> compteList) {
        this.context = context;
        this.compteList = compteList;
    }

    @Override
    public int getCount() {
        return compteList.size();
    }

    @Override
    public Object getItem(int position) {
        return compteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_view_item, parent, false);
        }

        // Get references to UI components
        TextView txt_Solde = convertView.findViewById(R.id.solde);
       TextView txt_DateCreation = convertView.findViewById(R.id.dateCreation);
       TextView tvType = convertView.findViewById(R.id.typeCompte);

        // Populate data
        Compte compte = compteList.get(position);
        txt_Solde.setText("SOLDE:"+compte.getSolde());
        txt_DateCreation.setText("Date: " + compte.getDateCreation());
        tvType.setText("Type: " + compte.getType());

        return convertView;
    }
    public  void clear(){
        compteList.clear();
    }
}
