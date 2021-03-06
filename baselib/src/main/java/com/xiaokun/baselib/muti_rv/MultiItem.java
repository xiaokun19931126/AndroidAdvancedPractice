package com.xiaokun.baselib.muti_rv;

import android.support.annotation.LayoutRes;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/06/27
 *      描述  ：多类型item接口
 *      版本  ：1.0
 * </pre>
 */
public interface MultiItem {

    @LayoutRes
    int getItemType();
}
