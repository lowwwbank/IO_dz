import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static final String pathname = "src/";


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Insert input filename (it must be in \"src\" directory)");
        File inputF = new File(pathname + in.nextLine());
        if (!inputF.exists()) {
            System.out.println("Unable to find input file");
            return;
        }
        else System.out.println("<file found successfully>");
        System.out.println("Insert output filename (it must be in \"src\" directory)");
        File outputF = new File(pathname + in.nextLine());
        if (!outputF.exists() || outputF.isDirectory()) {
            try {
                boolean flag = outputF.createNewFile();
                if (flag) {
                    System.out.println("<file created successfully>");
                } else System.out.println("<file found successfully>");
            } catch (IOException e) {
                System.out.println("Unable to create output file");
                return;
            }
        }
        HashMap<Character, Integer> map = countLettersFromFile(inputF);

        if (map == null) return;
        else writeCounted(map, outputF);
        System.out.println("Success!");
    }

    static HashMap<Character, Integer> countLettersFromFile(File file) {
        HashMap<Character, Integer> count = new HashMap<>();
        FileReader fileR = null;
        try {
            fileR = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileR);
            int buffer;
            while ((buffer = reader.read()) != -1) {
                char c = (char) buffer;
                if (IsLatin(c)) {
                    if (!count.containsKey(c)) count.put(c, 1);
                    else count.put(c, count.get(c) + 1);
                }

            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error occurred while reading from the input file");
            return null;
        } finally {
            if (fileR != null) {
                try {
                    fileR.close();
                } catch (IOException e) {
                    System.out.println("Error occurred while closing the input file");
                }
            }
        }
        return count;
    }

    static boolean IsLatin(char letter) {
        return (letter >= 'A' && letter <= 'Z') || (letter >= 'a' && letter <= 'z');
    }

    static void writeCounted(HashMap<Character, Integer> map, File file) {
        FileWriter fileW = null;
        try  {
            fileW = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileW);
            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                writer.write(entry.getKey() + " : " + entry.getValue());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error occurred while writing to the output file");
        }finally {
            try {
                if (fileW != null) fileW.close();
            } catch (IOException e) {
                System.out.println("Error occurred while writing to the output file");
            }
        }
    }
}