package com.tdd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    // Goal : To take input as a string which will tell what operation to perform with subsequent  numbers separated by delimiter mentioned in the input itself.

    public int getResult(String input) {

        if (input == null || input.isEmpty()) {
            return 0;
        }

        String delimiter=",+";

        String inputString = input;

        if(input.contains("~")) {

            String[] seperatedContent = input.split("~");

            inputString = seperatedContent[1];

            //Checking if delimiter-list is correct or not
            if(checkValidDelimiterList(seperatedContent[0])){

                StringBuffer newDelimiter = new StringBuffer();
                StringBuffer sb = new StringBuffer();

                delimiter = seperatedContent[0];

                int delimiterLength = delimiter.length();
                int iterate = 0;

                while(iterate < delimiterLength){

                    char tempChar = delimiter.charAt(iterate);
                    iterate++;

                    if(tempChar != ']'){
                        sb.append(tempChar);
                    }
                    else{
                        sb.deleteCharAt(0);
                        newDelimiter.append(Pattern.quote(sb.toString())+"+");

                        if(iterate < delimiterLength)
                            newDelimiter.append("|");
                        sb.setLength(0);
                    }
                }
                delimiter=newDelimiter.toString();

            }

        }

        String[] numbers = inputString.split(delimiter, 0);

        List<String> finalNumber = Arrays.stream(numbers).filter(number->{ return number!="";}).collect(Collectors.toList());

        System.out.println("Numbers after splitting "+finalNumber);
        ArrayList<String> ignoredValue = new ArrayList<>();
        ArrayList<Integer> negativeValue = new ArrayList<>();

        int sum = 0;
        for (String s : finalNumber) {
            //Adding exceptional-handling to take care of values other than integers
            try {
                int number = Integer.parseInt(s.trim());
                System.out.println("Number "+number);
                if (number < 0) {
                    negativeValue.add(number);
                }
                sum += number;
            } catch (Exception e) {
                System.out.println(s);
                ignoredValue.add(s);
            }
        }

        StringBuffer exceptionMessages = new StringBuffer();
        if(!negativeValue.isEmpty()){
            exceptionMessages.append("Negative numbers involved in the list : "+negativeValue);
            exceptionMessages.append("\n");
        }
        if(!ignoredValue.isEmpty()){
            exceptionMessages.append("Delimiters may not be specified correctly or Value not specified properly : "+negativeValue);
        }

        if(!negativeValue.isEmpty() || !ignoredValue.isEmpty()){
            throw new ArithmeticException("Negative numbers involved in the list : "+negativeValue);
        }

        return sum;
    }


    public boolean checkValidDelimiterList(String delimiterList){
        Pattern pattern = Pattern.compile("(\\[[^\\]0-9]+])+");
        Matcher matcher = pattern.matcher(delimiterList);
        return matcher.matches();
    }

}
