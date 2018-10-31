package com.lix.study;

import com.lix.study.sdk.common.utils.Md5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class TestLambda {

    @Test
    public void lambdaTomap() {
        List<String> list = Arrays.asList("fdsa", "ere", "gfsv", "dfretyr", "gfsv", "qfdaf", "hgopx");
        Map<String, String> map = list.stream()
                .collect(Collectors.toMap(String :: toUpperCase, String :: toLowerCase, (a, b) -> a.concat("00000")));
        System.out.println(map);
    }


}
