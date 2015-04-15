/*
 * Copyright (C) 2014 Disrupted Systems
 * This file is part of Rumble.
 * Rumble is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Rumble is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with Rumble.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.disrupted.rumble.userinterface.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.qrcode.encoder.QRCode;
import com.squareup.picasso.Picasso;

import org.disrupted.rumble.R;
import org.disrupted.rumble.database.objects.Group;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * @author Marlinski
 */
public class GroupListAdapter extends BaseAdapter {

    public static final String TAG = "GroupListAdapter";

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Group> groupList;


    public GroupListAdapter(Activity activity) {
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupList = null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.group_list_item, null, true);

        LinearLayout title = (LinearLayout) layout.findViewById(R.id.group_title);
        ImageView group_lock  = (ImageView) layout.findViewById(R.id.group_lock_image);
        TextView  group_name  = (TextView)  layout.findViewById(R.id.group_name);
        TextView  group_gid   = (TextView)  layout.findViewById(R.id.group_gid);
        TextView  group_desc   = (TextView)  layout.findViewById(R.id.group_desc);
        TextView  unread_messages  = (TextView)  layout.findViewById(R.id.group_unread_messages);
        ImageView group_invite  = (ImageView) layout.findViewById(R.id.group_invite);

        //group_name.setTextColor(ColorGenerator.DEFAULT.getColor(groupList.get(i).getName()));
        if(groupList.get(i).isIsprivate())
            Picasso.with(activity)
                    .load(R.drawable.ic_lock_grey600_24dp)
                    .into(group_lock);
        else
            Picasso.with(activity)
                    .load(R.drawable.ic_lock_open_grey600_24dp)
                    .into(group_lock);

        group_name.setText(groupList.get(i).getName());
        group_gid.setText("Group ID: "+groupList.get(i).getGid());
        group_desc.setText("Description: "+groupList.get(i).getDesc());

        title.setOnClickListener(onGroupClicked);

        if(groupList.get(i).isIsprivate()) {
            final byte[] key = groupList.get(i).getGroupKey().getEncoded();
            final String gid = groupList.get(i).getGid();
            final String name = groupList.get(i).getName();
            group_invite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(2+name.length()+gid.length()+key.length);
                    byteBuffer.put((byte)name.length());
                    byteBuffer.put(name.getBytes(),0,name.length());
                    byteBuffer.put((byte)gid.length());
                    byteBuffer.put(gid.getBytes());
                    byteBuffer.put(key);
                    String barcode = Base64.encodeToString(byteBuffer.array(),Base64.NO_WRAP);
                    IntentIntegrator.shareText(activity,barcode);
                }
            });
        }

        return layout;
    }

    @Override
    public long getItemId(int i) {
        if(groupList == null)
            return 0;
        if(groupList.isEmpty())
            return 0;
        return i;
    }


    View.OnClickListener onGroupClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.group_information);
            if(linearLayout != null) {
                if(linearLayout.getVisibility() == View.VISIBLE)
                    linearLayout.setVisibility(View.GONE);
                else
                    linearLayout.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public Object getItem(int i) {
        return groupList.get(i);
    }

    @Override
    public int getCount() {
        if(groupList == null)
            return 0;
        else
            return groupList.size();
    }

    public void swap(ArrayList<Group> groupList) {
        if(this.groupList != null)
            this.groupList.clear();
        this.groupList = groupList;
        notifyDataSetChanged();
    }

}
