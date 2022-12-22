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
           if(i.equals("AND") || i.equals("OR") || i.equals("NOT")) operationQuery++;
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
        // Term Matrix
        String[][] termMatrix = matrix(termNoRep,originalWord,sizeOfDoc+1);
        for(String[] i: termMatrix){
            for (String k: i){
                System.out.print(k + " ");
            }
            System.out.println(" ");
        }

        int[] relevanceTem = new int[sizeOfDoc];
        String[] querySmall = new String[3];
        String[] querySmall2 = new String[2];
       int c=0;
       int centerQuery=queryTerm.length/2;
        for(int i=0; i < querySmall.length; i++){
            if(queryTerm.length > 3){
                for(int j=c; j<=centerQuery; j++){
                    if(queryTerm[centerQuery/2].equals("AND")){
                        querySmall[i]=queryTerm[c];
                    }
                }
            }else if(queryTerm.length==3){
                querySmall[i]=queryTerm[i];
            }else {
                querySmall2[1] = queryTerm[queryTerm.length-1];
                querySmall2[0] = queryTerm[queryTerm.length-2];
            }

        }

        for (String k: queryTerm){
            System.out.println(k);
        }

        String[][] tempResult = new String[2][sizeOfDoc];
        int i=0,j=0,k=0,l=0;
        while(operationQuery == 1){
            while(i < 3){
              if(querySmall[i].equals(termMatrix[j][0]) && i!=1){
                    for(int q=1; q < termMatrix[j].length; q++){
                       tempResult[l][k]=termMatrix[j][q];
                        k++;
                    }
                    k=0;
                    l++;
                    i++;
                    j=0;
                }else if(i==1) i++;
                 else j++;
            }
            for(int a=0; a<relevanceTem.length; a++){
                if(querySmall[1].equals("OR")){
                    if (!(tempResult[0][a].equals(tempResult[1][a]))) relevanceTem[a] = 1;
                    else if (tempResult[0][a].equals(tempResult[1][a]) && tempResult[0][a].equals("0")) relevanceTem[a] = 0;
                    else if (tempResult[0][a].equals(tempResult[1][a])) relevanceTem[a] = 1;
                    else relevanceTem[a] = 0;
                }else if (querySmall[1].equals("AND")){
                    if((tempResult[0][a].equals(tempResult[1][a]))) relevanceTem[a] = 1;
                    else {
                        relevanceTem[a] = 0;
                    }
                }

            }
          operationQuery++;
        }


            for(int g: relevanceTem){
                System.out.print(g +" ");
            }

    }
}
