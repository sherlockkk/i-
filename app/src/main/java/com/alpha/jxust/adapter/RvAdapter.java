package com.alpha.jxust.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alpha.jxust.adapterItem.StatusAdapterItem;
import com.alpha.jxust.model.Status;

import java.util.List;

import kale.adapter.AdapterItem;
import kale.recycler.ExCommonRcvAdapter;

/**
 * @author SongJian
 * @created 2016/3/4.
 * @e-mail 1129574214@qq.com
 */
public class RvAdapter extends ExCommonRcvAdapter<Status> {
    private Context context;
    public RvAdapter(List<Status> data, Context context) {
        super(data);
        this.context = context;
    }

    @NonNull
    @Override
    protected AdapterItem initItemView(Object o) {
        return new StatusAdapterItem(context,this);
    }

}
