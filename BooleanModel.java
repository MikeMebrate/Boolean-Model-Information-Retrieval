import java.util.Scanner;
import java.util.Arrays;
public class BooleanModel {

    static String[][] matrix(String[] termNoRep,String[][] originalWord,int sizeOfDoc){
        String[][] termMatrix = new String[termNoRep.length][sizeOfDoc];
        int w=1;
        for(int i=0; i < termMatrix.length; i++){
            termMatrix[i][0] = termNoRep[i];
        }
        for(int i=0; i < termNoRep.length; i++){
            for(String[] j:originalWord){
                for(int k=0; k<j.length; k++){
                    if(j[k].equals(termNoRep[i])){
                        termMatrix[i][w] ="1";
                        w++;
                        break;
                    }else if(j[k].equals(j[j.length-1])){
                        termMatrix[i][w] ="0";
                        w++;
                    }
                }
            }
            w=1;
        }
        return termMatrix;
    }



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

    static String[] removeRep(String[] a){
        int noRepSize = countWithNoRep(a);
        String[] removedRep = new String[noRepSize];

        Arrays.fill(removedRep,a[0]);
       int j=1;
        for(String i: a){
            a:
            for(int k=0; k < removedRep.length; k++){
                if(removedRep[k].equals(i) && j < noRepSize) break;
                else if(!removedRep[k].equals(i) && j < noRepSize){
                    for(int l=0; l<noRepSize; l++){
                        if(removedRep[l].equals(i)) break a;
                    }
                    removedRep[j] = i;
                    j++;
                }
            }
        }
        return removedRep;
    }

    public static void main(String[] args) {
        Scanner userInt = new Scanner(System.in);// For Integer data types
        Scanner userSt = new Scanner(System.in); //For String data types

        int sizeOfDoc;   // size of document
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

        //Query
        String originalQuery;
        System.out.print("Enter the Query: ");
        originalQuery = userSt.nextLine();

        //Query Tokenization
        String[] queryTerm;
        queryTerm = originalQuery.split(" ");

       System.out.println("Term Tokenization");
       int operationQuery=0;// to define how many operation will perform
       for(String i: queryTerm){
           System.out.println(i);
           if(i.equals("AND") || i.equals("OR") || i.equals("NOT")) operationQuery++; //Count the operation
       }

       String[] operation = new String[operationQuery];  // Initializing Operation
       int k=0;
        for(String i: queryTerm) {
           if(i.equals("AND") || i.equals("OR") || i.equals("NOT")){
               operation[k] = i; //Assigning Operation by their order
               k++;
           }
       }



       System.out.println("\n\n");



        String[] allTerm = new String[sizeOfTerm]; // All terms into one Array List

        int p=0;
        for(String[] c: originalWord){  //Assign values
            for(String i: c){
                allTerm[p] = i;
                p++;
            }
        }
         Arrays.sort(allTerm); // Sort terms

        String[] termNoRep = removeRep(allTerm);// Initialization Terms without repetition

        String[][] termMatrix = matrix(termNoRep,originalWord,sizeOfDoc+1);   // Term Matrix

        for(String[] i: termMatrix){
            for (String q: i){
                System.out.print(q + " ");
            }
            System.out.println(" ");
        }

        //Query Terms without operator
        String[][] queryMatrix = new String[queryTerm.length - operationQuery][sizeOfDoc+1];
        int z=0;
        for(int j=0; j<queryMatrix.length; j++){
            if (!queryTerm[z].equals("AND") && !queryTerm[z].equals("OR") && !queryTerm[z].equals("NOT")) {
                queryMatrix[j][0] = queryTerm[z]; // Assign Query term as row
                z++;
            }
            z++;
        }

       int a=1;
        for(int j=0; j<queryMatrix.length; j++){
                for(int b=0; b < termMatrix.length; b++){
                    for(int c=1; c<termMatrix[b].length; c++){
                        if(queryMatrix[j][0].equals(termMatrix[b][0]) && a < queryMatrix[j].length){
                            queryMatrix[j][a] = termMatrix[b][c];
                            a++;
                        }
                    }
               }
            a=1;
        }



        for(String[] i:queryMatrix){
            for(String m: i){
                System.out.print(m + " ");
            }
            System.out.println(" ");
        }





    }
}
