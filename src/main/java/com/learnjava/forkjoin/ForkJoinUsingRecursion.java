package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {
    private List<String> inputList;
    // Fork join is introduced in java 7
    public ForkJoinUsingRecursion(List<String> names) {
        this.inputList = names;
    }

    public static void main(String[] args) {
        stopWatch.start();
        List<String> resultList;
        List<String> names = DataSet.namesList();
        log("names : "+ names);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(names);
        resultList = forkJoinPool.invoke(forkJoinUsingRecursion);
        stopWatch.stop();
        log("Final Result : "+ resultList);
        log("Total Time Taken : "+ stopWatch.getTime());
    }


    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }

    // This is the method to implement the fork and join method
    // This is not developer friendly. because of how complex it is
    // Then streaming API was released as part of java 8 for data parallelism to replace this
    @Override
    protected List<String> compute() {
        // this is the process of transformation when the recursion method gets input list of size <= 1
        if (inputList.size() <= 1) {
            List<String> resultList = new ArrayList<>();
            inputList.forEach(name -> resultList.add((addNameLengthTransform(name))));
            return resultList;
        }
        int midPoint = inputList.size()/2;
        // get left names list using sublist 0, midPoint
        ForkJoinTask<List<String>> leftInputList = new ForkJoinUsingRecursion(inputList.subList(0, midPoint)).fork();
        // Get the rest of names or right names list
        inputList = inputList.subList(midPoint, inputList.size());
        // this is where the recursion happens
        List<String> rightResult = compute();
        // this is where it's going to start adding the results back to the original form
        List<String> leftResult = leftInputList.join();
        // This is joining to get the finalResult
        leftResult.addAll(rightResult);
        return leftResult;
    }
}
