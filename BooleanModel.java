import java.util.Scanner;
import java.util.Arrays;
public class BooleanModel {

    static void menu(int[] tempResult){
        Scanner userInt = new Scanner(System.in);
        System.out.println("Menu");
        System.out.println("1.For relevant Document");
        System.out.println("2.For non-relevant Document");
        System.out.println("3.For Exit");
        System.out.print("Enter Option: ");
        int option=userInt.nextInt();
        if(option==1){
            int docID=1;
            for(int i:tempResult){
                if(i==1) System.out.println("Document "+ docID + " is relevant");
                docID++;
            }

            System.out.println(" ");
            menu(tempResult);
        }else if(option==2){

            int docID=1;
            for(int i:tempResult){
                if(i==0) System.out.println("Document "+ docID + " is non-relevant");
                docID++;
            }
            System.out.println(" ");
            menu(tempResult);
        }else if(option == 3){
            System.exit(0);
        }
        else{
            System.out.print("Incorrect Input");
            System.out.println(" ");
            menu(tempResult);
        }
    }

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

      //  System.out.println("Term Tokenization");
        int operationQuery=0;// to define how many operation will perform
        for(String i: queryTerm){
            //System.out.println(i);
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

       /* for(String[] i: termMatrix){
            for (String q: i){
                System.out.print(q + " ");
            }
            System.out.println(" ");
        }*/
        //Query Terms without operator
        String[][] queryMatrix = new String[queryTerm.length - operation.length][sizeOfDoc+1];
        int z=0;
        for(int i=0; i < queryTerm.length; i++){
            if (z < queryMatrix.length && !queryTerm[i].equals("AND") && !queryTerm[i].equals("OR") && !queryTerm[i].equals("NOT")) {
                queryMatrix[z][0] = queryTerm[i];
                z++;
            }
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

/*
        for(String[] i:queryMatrix){
            for(String m: i){
                System.out.print(m + " ");
            }
            System.out.println(" ");
        }
        System.out.println("\n\n");*/

      //  for(String i:operation){
        //    System.out.print(i + " ");
       // }
      //  System.out.println("\n\n");

        //int op=0;
        int indexTemp=0;
        int indexRow=0;
        int[] tempResult = new int[sizeOfDoc];

        for(int op=0; op < operationQuery; op++){
            if(op == 0){
                if(operation[op].equals("AND")){
                    for(int i=indexRow; i < indexRow+2 && i < queryMatrix.length-1; i++){
                        for(int j=1;j < queryMatrix[i].length; j++){
                            if(indexTemp < tempResult.length){
                                if((queryMatrix[i][j].equals("1") && queryMatrix[i+1][j].equals("1") || (queryMatrix[i][j].equals("0") && queryMatrix[i+1][j].equals("0") ))){
                                    tempResult[indexTemp] = 1;
                                }else {
                                    tempResult[indexTemp] = 0;
                                }
                                indexTemp++;
                            }
                        }
                    }
                    // op++;
                    indexRow+=2;
                }else if(operation[op].equals("OR")){
                    for(int i=indexRow; i < indexRow+2 && i < queryMatrix.length-1; i++){
                        for(int j=1;j < queryMatrix[i].length; j++) {
                            if (indexTemp < tempResult.length) {
                                if ((queryMatrix[i][j].equals("0") && queryMatrix[i + 1][j].equals("0"))) {
                                    tempResult[indexTemp] = 0;
                                } else {
                                    tempResult[indexTemp] = 1;
                                }
                                indexTemp+=2;
                            }
                        }
                    }
                    indexRow++;
                }else if (operation[op].equals("NOT")){

                    for(int j=1; j < sizeOfDoc+1; j++) {
                        if(queryMatrix[0][j].equals("1")){
                            tempResult[indexTemp]=0;
                        }else {
                            tempResult[indexTemp]=1;
                        }
                        indexTemp++;
                    }
                    indexRow+=2;
                }
            }else{
                int indexMatrix =1;
                indexTemp=0;
                int[] tempResult1 = new int[tempResult.length];
                int[] tempResult3 = new int[tempResult.length];
                tempResult1 = tempResult;


                if(operation[op].equals("AND")){
                    for(int i=0; i < sizeOfDoc; i++){
                        if(queryMatrix[indexRow][indexMatrix].equals("1") && tempResult1[indexTemp]==1){
                            tempResult[indexTemp] = 1;
                        }else{
                            tempResult[indexTemp] = 0;
                        }
                        indexMatrix++;
                        indexTemp++;
                    }
                }else if(operation[op].equals("OR")){
                    for(int i=0; i < sizeOfDoc; i++){
                        if(queryMatrix[indexRow][indexMatrix].equals("0") && tempResult1[indexTemp]==0){
                            tempResult[indexTemp] = 0;
                        }else{
                            tempResult[indexTemp] = 1;
                        }
                        indexMatrix++;
                        indexTemp++;
                    }
                }else if (operation[op].equals("NOT")) {
                    for (int j = 1; j < queryMatrix[indexRow].length; j++) {
                        if (queryMatrix[indexRow][j].equals("1")) {
                            tempResult3[indexTemp] = 0;
                        } else {
                            tempResult3[indexTemp] = 1;
                        }
                        indexTemp++;
                    }
                    // Here may have some problem's
                    //Start from this part

                }
                indexRow++;
            }
        }
          menu(tempResult);



    }
}
