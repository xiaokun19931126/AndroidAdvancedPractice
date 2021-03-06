package com.xiaokun.advance_practive.ui.multi_rv_sample.entity;

import com.xiaokun.advance_practive.ui.multi_rv_sample.holder.TypeAHolder;
import com.xiaokun.baselib.muti_rv.MultiItem;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/06/27
 *      描述  ：
 *      版本  ：1.0
 * </pre>
 */
public class ItemA implements MultiItem {
    private String title;

    public ItemA(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return TypeAHolder.LAYOUT;
    }
}
