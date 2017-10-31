/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author connor
 */
public class UserIOConsoleImpl implements UserIO {
    Scanner sc = new Scanner(System.in);
    
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }
    
    @Override
    public void tabPrintLn(String message) {
        System.out.println("\t" + message);
    }

    @Override
    public double readDouble(String prompt) {
        boolean isValid = false;
        double result = 0;
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            
            try {
                result = Double.parseDouble(userInput);
                isValid = true;
            } catch (NumberFormatException | NullPointerException ex) {
                println("This is an invalid number. Please try again: ");
            }
        }
        return result;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        boolean isValid = false;
        double result = 0;
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            
            try {
                result = Double.parseDouble(userInput);
                isValid = result >= min && result <=max;
            } catch (NumberFormatException | NullPointerException ex) {
                println("Invalid input. Please enter a number from " + min + " to "
                + max);
            }
        }
        return result;
    }

    @Override
    public float readFloat(String prompt) {
        boolean isValid = false;
        float result = 0;
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            
            try {
                result = Float.parseFloat(userInput);
                isValid = true;
            } catch (NumberFormatException | NullPointerException ex) {
                println("This is an invalid number. Please try again: ");
            }
        }
        return result;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        boolean isValid = false;
        float result = 0;
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            
            try {
                result = Float.parseFloat(userInput);
                isValid = result >= min && result <=max;
            } catch (NumberFormatException | NullPointerException ex) {
                println("Invalid input. Please enter a number from " + min + " to "
                + max);
            }
        }
        return result;
    }

    @Override
    public int readInt(String prompt) {
        boolean isValid = false;
        int result = 0;
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            
            try {
                result = Integer.parseInt(userInput);
                isValid = true;
            } catch (NumberFormatException | NullPointerException ex) {
                println("This is an invalid number. Please try again: ");
            }
        }
        return result;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        boolean isValid = false;
        int result = 0;
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            
            try {
                result = Integer.parseInt(userInput);
                isValid = result >= min && result <=max;
            } catch (NumberFormatException | NullPointerException ex) {
                println("Invalid input. Please enter a number from " + min + " to "
                + max);
            }
        }
        return result;
    }
    
    @Override
    public Integer readInt(String prompt, ArrayList<Integer> ints) {
        boolean isValid = false;
        Integer result = 0;
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            
            try {
                result = Integer.parseInt(userInput);
                isValid = ints.contains(result);
            } catch (NumberFormatException | NullPointerException ex) {
                println("Invalid input. Please try again");
            }
        }
        return result;
    }
    
    @Override
    public int readInt(String prompt, int min, int max,
                boolean acceptsNullInput) {
        boolean isValid = false;
        int result = 0;
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            
            try {
                result = Integer.parseInt(userInput);
                isValid = result >= min && result <=max;
            } catch (NumberFormatException | NullPointerException ex) {
                if (acceptsNullInput && userInput.equals("")) {
                    return 0;
                } else {
                    println("Invalid input. Please enter a number from " + min + " to "
                    + max);
                }
            }
        }
        return result;
    }

    @Override
    public long readLong(String prompt) {
        boolean isValid = false;
        long result = 0;
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            
            try {
                result = Long.parseLong(userInput);
                isValid = true;
            } catch (NumberFormatException | NullPointerException ex) {
                println("This is an invalid number. Please try again: ");
            }
        }
        return result;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        boolean isValid = false;
        long result = 0;
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            
            try {
                result = Long.parseLong(userInput);
                isValid = result >= min && result <=max;
            } catch (NumberFormatException | NullPointerException ex) {
                println("Invalid input. Please enter a number from " + min + " to "
                + max);
            }
        }
        return result;
    }
    
    @Override
    public BigDecimal readBigDecimal(String prompt) {
        boolean isValid = false;
        BigDecimal result = null;
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            
            try {
                BigDecimal str = new BigDecimal(userInput);
                result = str.setScale(2, RoundingMode.HALF_UP);
                isValid = result.compareTo(BigDecimal.ZERO) >= 0;
            } catch (NumberFormatException ex) {
                println("Invalid Input. Please try again.");
            }
        }
        return result;
    }
    
    @Override
    public BigDecimal readBigDecimal(String prompt, boolean acceptsNullInput) {
        boolean isValid = false;
        BigDecimal result = null;
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            
            try {
                BigDecimal str = new BigDecimal(userInput);
                result = str.setScale(2, RoundingMode.HALF_UP);
                isValid = result.compareTo(BigDecimal.ZERO) >= 0;
            } catch (NumberFormatException ex) {
                if (acceptsNullInput && userInput.equals("")) {
                    return null;
                } else {
                    println("Invalid Input. Please try again.");
                }
            }
        }
        return result;
    }

    @Override
    public String readString(String prompt, boolean acceptsNullInput) {
        boolean isValid = false;
        String result = "";
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            result = userInput;
            
            if ((result.equals("") && acceptsNullInput)
                    || (acceptsNullInput == false)
                    || (!result.equals(""))) {
                isValid = true;
            }
        }
        return result;
    }
    
    @Override
    public String readString(String prompt, String choice1, String choice2) {
        boolean isValid = false;
        String result = "";
        
        while (!isValid) {
            println(prompt + " (" + choice1 + "/" + choice2 + "):\t");
            String userInput = sc.nextLine();
            result = userInput;
            isValid = userInput.equalsIgnoreCase(choice1) || userInput.equalsIgnoreCase(choice2);
        }
        return result;
    }
    
    @Override
    public String readString(String prompt, ArrayList<String> strings,
            boolean acceptsNullInput) {
        boolean isValid = false;
        String result = "";
        
        while (!isValid) {
            println(prompt);
            String userInput = sc.nextLine();
            result = userInput;
            
            if ((result.equals("") && acceptsNullInput) || strings.contains(result)) {
                isValid = true;
            }
        }
        return result;
    }
    
    @Override
    public LocalDate readLocalDate(String prompt, String format) {
        boolean isValid = false;
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern(format);
        LocalDate result = null;
        
        while (!isValid) {
            println(prompt + " (" + format.toUpperCase() + "):\t");
            String userInput = sc.nextLine();
            
            try {
                result = LocalDate.parse(userInput, dtFormat);
                isValid = true;
            } catch (DateTimeException ex) {
                println("Invalid Input. Please check to see if your input is "
                        + "in the correct format (" + format.toUpperCase() + ")");
            }
        }
        return result;
    }
    
    @Override
    public LocalDate readLocalDate(String prompt, String format,
            boolean acceptsNullInput) {
        boolean isValid = false;
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern(format);
        LocalDate result = null;
        
        while (!isValid) {
            println(prompt + " (" + format.toUpperCase() + "):\t");
            String userInput = sc.nextLine();
            
            try {
                result = LocalDate.parse(userInput, dtFormat);
                isValid = true;
            } catch (DateTimeException ex) {
                if (userInput.equals("") && acceptsNullInput) {
                    return null;
                } else {
                    println("Invalid Input. Please check to see if your input is "
                            + "in the correct format (" + format.toUpperCase() + ")");
                }
            }
        }
        return result;
    }
}
