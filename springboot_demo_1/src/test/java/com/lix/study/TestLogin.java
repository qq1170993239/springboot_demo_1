package com.lix.study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.lix.study.sdk.common.utils.Md5Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//@RunWith(SpringRunner.class)
public class TestLogin {

    @Test
    public void testGetSaltPassword() {
        System.out.println(Md5Utils.encryptPassword("123456"));
    }


    @Test
    public void testIterator() {
        List<String> str2 = new ArrayList<>();
        str2.add("gfs2");
        str2.add("gfe2s");
        str2.add("gft2s");
        str2.add("gfqs");
        str2.add("gfds");
        str2.add("gs2fs");
        str2.add("gf2ls");
        int i = 0;
        for (Iterator<String> it2 = str2.iterator(); it2.hasNext(); ) {
            if(i > str2.size()){
                break;
            }
            if (i % 2 == 0) {
                it2.next();
                it2.remove();
            }
            i++;
        }
    }


}
