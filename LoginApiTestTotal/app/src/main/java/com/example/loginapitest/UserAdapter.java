package com.example.loginapitest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<UserInfoDto> data = null;
    private LayoutInflater inflater = null;

    public UserAdapter(Context mContext, int layout, ArrayList<UserInfoDto> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
    this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = inflater.inflate(this.layout,parent,false);
        }
//        TextView tv_code = convertView.findViewById(R.id.tv_code);
//        TextView tv_name = convertView.findViewById(R.id.tv_name);
//        TextView tv_dept = convertView.findViewById(R.id.tv_dept);
//        TextView tv_tel = convertView.findViewById(R.id.tv_phone);
//
//        tv_code.setText("code : " + data.get(position).getCode());
//        tv_name.setText("name : " + data.get(position).getName());
//        tv_dept.setText("dept : " + data.get(position).getDept());
//        tv_tel.setText("phone : " + data.get(position).getPhone());
//

        return convertView;
    }
}
