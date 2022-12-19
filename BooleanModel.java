import java.util.Scanner;

public class BooleanModel {
    public static void main(String[] args) {
        Scanner userInt = new Scanner(System.in);// For Integer data types
        Scanner userSt = new Scanner(System.in); //For String data types

        int sizeOfDoc;
        System.out.println("Boolean Model of IR");
        System.out.print("Enter Number of Document: ");
        sizeOfDoc = userInt.nextInt();

        String[] originalDoc = new String[sizeOfDoc+1]; //Initializing size of document
        String[][] originalWord = new String[sizeOfDoc+1][];
        for (int i = 0; i < originalDoc.length; i++) {
            if(i+1 == originalDoc.length) System.out.print("Enter the Query: ");
            else System.out.print("Enter Document " + (i + 1) + ": ");
            originalDoc[i] = userSt.nextLine();  //Accepting user Document
            originalWord[i] = originalDoc[i].split(" "); //Tokenization for every document
        }





    }
}
