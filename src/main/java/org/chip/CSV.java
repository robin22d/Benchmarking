package org.chip;

import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by C1526449 on 13/02/2017.
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(4)

@State(Scope.Benchmark)
public class CSV{


    @State(Scope.Thread)
    public static class ThreadState {

        String[] AllEmployees = new String[1000];

        @Param({"5","100","500","600"})
        int getEmployee;

        @Param({"5","100","500","600"})
        int department;

        @Setup
        public void setup() {
            Path DataFile = Paths.get("./data.csv");
            for (int i = 0; i < 1000; i++) {
                try (InputStream in = Files.newInputStream(DataFile);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                    String line = null;
                    int q = 0;
                    int recordNo = i;
                    String headerLine = reader.readLine();
                    while ((line = reader.readLine()) != null) {
                        if (q == recordNo) {
                            AllEmployees[i] = line;
                        }
                        i++;
                    }
                } catch (IOException x) {
                    System.err.println(x);
                }
            }


        }


    }


    @Benchmark
    public static String GetEmployeeCall(CSV.ThreadState state) {
        return GetEmployee(state.AllEmployees, state.getEmployee);
    }

    public static String GetEmployee(String[] GetEmployees, int Employee) {
        String EmployeeName;
        String[] split = GetEmployees[Employee].split(",");
        EmployeeName = (split[1] + " " + split[2]);
        return EmployeeName;
    }

    @Benchmark
    public static int[] GetTopNSalariesCall(CSV.ThreadState state) {
        return getTopNSalaries(state.AllEmployees);
    }

    public static int[] getTopNSalaries(String[] GetEmployees) {
        double[] allSaleries = new double[1000];
        for (int i = 0; i < 999; i++) {
            String[] splitting = GetEmployees[i].split(",");
            allSaleries[i] = Double.parseDouble(splitting[5]);
        }
        //  bubbleSort(allSaleries);
        Arrays.sort(allSaleries);
        int [] allSortedSaleries = new int[1000];
        for(int i = 0; i<allSortedSaleries.length; i++){
            allSortedSaleries[i] = (int) Math.round(allSaleries[(allSaleries.length-1) - i]);
        }
        return allSortedSaleries;
    }

    public static double[] bubbleSort(double[] unsortedSalaries) {
        double sortedSalaries[] = new double[unsortedSalaries.length];
        double n = unsortedSalaries.length;
        double temp;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (unsortedSalaries[j - 1] > unsortedSalaries[j]) {
                    temp = unsortedSalaries[j - 1];
                    unsortedSalaries[j - 1] = unsortedSalaries[j];
                    unsortedSalaries[j] = temp;
                }

            }
        }
        for (int i = 0; i < 1000; i++) {
            sortedSalaries[i] = unsortedSalaries[i];
        }
        return sortedSalaries;
    }


    @Benchmark
    public static int GetDepartmentCostCall(CSV.ThreadState state) {
        return getDepartmentCost(state.AllEmployees, state.department);
    }

    public static int getDepartmentCost(String[] GetEmployees, int departmentId)  {
        double department = departmentId;
        double[] departmentCosts = new double[1000];

        int a = 0;
        int t = 0;
        while(t < 1000) {
            String[] splitting1 = GetEmployees[t].split(",");
            if(Double.parseDouble(splitting1[4]) == department) {
                departmentCosts[a] = Double.parseDouble(splitting1[5]);
                a++;
            }
            t++;
        }
        double totalCost = 0;
        int i = 0;
        while (i < 100){
            totalCost = departmentCosts[i] + totalCost;
            i++;
        }

        int departmentTotalCosts = (int) Math.round(totalCost);
        return departmentTotalCosts;

    }



}