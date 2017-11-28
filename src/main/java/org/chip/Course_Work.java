package org.chip;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by c1526449 on 27/03/2017.
 */

public class Course_Work {

    public static Path Coursework() {
        Path File = Paths.get("data1.csv");

        return File;
    }

    public static void main(String[] args) {
        String [] AllData = ReadFile();
        Scanner scanner = new Scanner(System.in);

        System.out.println("[0] = To get data file filename.");
        System.out.println("[1] = To get employee id.");
        System.out.println("[2] = To get n for the top n salaries.");
        System.out.println("[3] = To get department no. the id of the department.");
        System.out.print("Enter the number you want: ");

        int Answer = scanner.nextInt();

        if (Answer == 0) {
            System.out.println("Reading file " + Coursework());
        } else if (Answer == 1) {
            System.out.print("Enter the Id of the user you want to find: ");
            int EmployeeId = scanner.nextInt();
            System.out.print(getEmployee(AllData,EmployeeId));
        } else if (Answer == 2) {
            System.out.print("How many top salaries do you want: ");
            int AmountOfSalaries = scanner.nextInt();
            int[] SortedSalaries = getTopNSalaries(AllData);
            for(int i = 0; i<AmountOfSalaries; i++){
                System.out.println("£"+SortedSalaries[i]);
            }
        } else if (Answer == 3) {
            System.out.print("Enter the department do you want: ");
            int debarment = scanner.nextInt();
            int DepartmentCost = getDepartmentCost(AllData, debarment);
            System.out.println("Department " + debarment + " spends £" + DepartmentCost + " on staff");
        } else {
            System.out.println("Wrong number");
        }
    }

    public static String[] ReadFile () {

        String[] AllEmployees = new String[1000];

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
        return AllEmployees;
    }


    public static String getEmployee(String[] GetEmployees, int Employee) {
        String EmployeeName;
        String[] split = GetEmployees[Employee].split(",");
        EmployeeName = (split[1] + " " + split[2]);
        return EmployeeName;
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
