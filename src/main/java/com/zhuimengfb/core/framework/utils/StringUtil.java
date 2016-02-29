package com.zhuimengfb.core.framework.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by bo on 2016/2/28.
 */
public class StringUtil
{
    public static String[] getPlaceHoldArray(String link)
    {
        if (StringUtils.isEmpty(link)) {
            return null;
        }
        String[] result = new String[StringUtils.countMatches(link, "#{")];
        int start = 0;int end = 0;
        for (int i = 0; i < result.length; i++)
        {
            start = StringUtils.indexOf(link, "#{", end);
            end = StringUtils.indexOf(link, "}", start);
            result[i] = StringUtils.substring(link, start + 2, end);
        }
        return result;
    }

    public static void main(String[] args) {}
}
