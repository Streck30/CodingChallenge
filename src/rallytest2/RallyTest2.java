/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rallytest2;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Streck
 */
public class RallyTest2 {

  /**
   * @param args the command line arguments
   */
  //checks the number and the immediate adjacent number to find out which case
  //best fits that
  public static int getCaseTest(String testVal, int index){
        if(testVal.charAt(index)!='0'&&testVal.charAt(index+1)!='0')
          return 0;
        if(testVal.charAt(index)!='0'&&testVal.charAt(index+1)=='0')
          return 1;
        if(testVal.charAt(index)=='0'&&testVal.charAt(index+1)!='0')
          return 2;
        return 3;
  }
  //finally does all the math to determine what the value from the 
  //problem results as
  public static int finalTest(String testVal, int [] altCase){
    int startNum = testVal.charAt(0)-'0';
    Deque<Integer> pemdasHoldNum = new ArrayDeque<Integer>(); 
    Deque<Integer> pemdasHoldOp = new ArrayDeque<Integer>();
    boolean invalidCase = false;
    for(int i = 1; i < testVal.length(); i++){
      if(altCase[i-1]==0){
        if(startNum == 0){
          invalidCase = true;
          break;
        }          
        startNum = startNum * 10 + testVal.charAt(i)-'0';
      }
      else {
        pemdasHoldNum.push(startNum);
        startNum = testVal.charAt(i)-'0';
        pemdasHoldOp.push(altCase[i-1]);
       }
    }
    pemdasHoldNum.push(startNum);
    while(!pemdasHoldOp.isEmpty()){
      int opCheck = pemdasHoldOp.pop();
      startNum = pemdasHoldNum.pop();
      if(opCheck == 2){
        startNum = startNum * pemdasHoldNum.pop();
        pemdasHoldNum.push(startNum);
      }
      else if(opCheck == 3){
        if(pemdasHoldOp.isEmpty()){
          startNum = pemdasHoldNum.pop()-startNum;
          pemdasHoldNum.push(startNum);
        }
        else if(pemdasHoldOp.peek()!=2){
          startNum = pemdasHoldNum.pop()-startNum;
          pemdasHoldNum.push(startNum);
        }
        else{
          int tempNum = pemdasHoldNum.pop() * pemdasHoldNum.pop();
          pemdasHoldOp.pop();
          pemdasHoldNum.push(tempNum);
        }
      }
      else if(opCheck == 1){
        if(pemdasHoldOp.isEmpty()){
          startNum = pemdasHoldNum.pop()+startNum;
          pemdasHoldNum.push(startNum);
        }
        else if(pemdasHoldOp.peek()!=2){
          startNum = pemdasHoldNum.pop()+startNum;
          pemdasHoldNum.push(startNum);
        }
        else{
          int tempNum = pemdasHoldNum.pop() * pemdasHoldNum.pop();
          pemdasHoldOp.pop();
          pemdasHoldNum.push(tempNum);
        }
      }       
    }
    if(invalidCase)
      return Integer.MIN_VALUE;
    return startNum;
  }
  //runs all possible tests.  The alt case refers to the operator 
  //in between each number, and the for loop runs through all possible configurations
  //of said number, so a bit inefficient.
  public static void runAllTests(String testVal, int[] altCase, int solVal){
    boolean noSolFound = true;
    for(int i = 0; i < Math.pow(4, altCase.length);i++){
      for(int j = 0; j < altCase.length; j++){
        altCase[j] = (int)(i/Math.pow(4, j))%4;
      }
      int testFinal = finalTest(testVal, altCase);
      
      if(testFinal == solVal){
        printSolution(testVal, altCase);
        noSolFound = false;
      }
      
    }
    if(noSolFound){
        System.out.println("impossible");
      }
      System.out.println("");
  }
  //if a value is found to match, prints the solution according to the rules
  public static void printSolution(String testVal, int [] altCase){
    for(int i = 0; i < testVal.length()-1;i++){
      System.out.print(testVal.charAt(i));
      switch(altCase[i]){
        case 0:
          break;
        case 1:
          System.out.print("+");
          break;
        case 2:
          System.out.print("*");
          break;
        case 3:
          System.out.print("-");
          break;
      }         
    }
    System.out.println(testVal.charAt(testVal.length()-1));
  }
  public static void main(String[] args) {
    // TODO code application logic here
    Scanner scan = new Scanner(System.in);
    int solVal = scan.nextInt();
    Scanner otherScan = new Scanner(System.in);
    Deque<String> allOps = new ArrayDeque<String>(); 
    String testVal = otherScan.nextLine();
    allOps.push(testVal);
    while(!testVal.equals("")){
      testVal = otherScan.nextLine();
      if(testVal.equals(""))
        break;
      allOps.push(testVal);
    }
    while(!allOps.isEmpty()){
      testVal = allOps.pollLast();
      int [] altCase = new int[testVal.length()-1];
      runAllTests(testVal,altCase,solVal);
    }
  }
}
     
 
