package com.learnjava.parallelstreams;

import com.learnjava.util.CommonUtil;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParallelStreamsExampleTest {

    ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

    @Test
    void stringTransform() {
        CommonUtil commonUtil = new CommonUtil();
        //given
        List<String> inputList = DataSet.namesList();

        //when
        commonUtil.startTimer();
        List<String> resultList = parallelStreamsExample.stringTransform(inputList);
        commonUtil.timeTaken();

        //then
        assertEquals(4, resultList.size());
        resultList.forEach(name -> {
            assertTrue(name.contains("-"));
                });
    }

    @ParameterizedTest
    @ValueSource(booleans = {false,true})
    void stringTransformDynamic(boolean isParallel) {
        //given
        List<String> inputList = DataSet.namesList();

        //when
        List<String> resultList = parallelStreamsExample.stringTransformDynamic(inputList,isParallel);

        //then
        assertEquals(4, resultList.size());
        resultList.forEach(name -> {
            assertTrue(name.contains("-"));
        });
    }
}
