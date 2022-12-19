import java.util.Scanner;

public class BooleanModel {

    static  int countWithNoRep(String[] a) {
        int count=0;
        boolean check = true;
        for(int i=0; i < a.length; i++){
            for(int j=i+1; j < a.length; j++){

                if(a[i].equals(a[j])){

                    for(int k=i-1; k>=0; k--){
                        if(a[k].equals(a[i])){

                            check=false;
                            break;
                        }
                    }
                    if(check) count++;
                    else check = true;
                }
            }
        }
        return a.length-count;
    }


    public static void main(String[] args) {
        Scanner userInt = new Scanner(System.in);// For Integer data types
        Scanner userSt = new Scanner(System.in); //For String data types

        int sizeOfDoc;
        System.out.println("Boolean Model of IR");
        System.out.print("Enter Number of Document: ");
        sizeOfDoc = userInt.nextInt();

        String[] originalDoc = new String[sizeOfDoc]; //Initializing size of document
        String[][] originalWord = new String[sizeOfDoc][];
        int sizeOfTerm = 0; // Count all terms in all documents

        for (int i = 0; i < originalDoc.length; i++) {
            System.out.print("Enter Document " + (i + 1) + ": ");
            originalDoc[i] = userSt.nextLine();  //Accepting user Document
            originalWord[i] = originalDoc[i].split(" "); //Tokenization for every document
            for(int j=0; j<originalWord[i].length; j++){  //Changing a word to LowerCase
                originalWord[i][j] = originalWord[i][j].toLowerCase();
                sizeOfTerm++;
            }
        }

        String[] allTerm = new String[sizeOfTerm]; // All terms in one Array List
        int p=0;
        for(String[] c: originalWord){
            for(String i: c){
                allTerm[p] = i;
                p++;
            }
        }

      String[] termNoRep= new String[countWithNoRep(allTerm)]; // Initialization Terms without repetition




    }
}
