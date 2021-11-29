import java.io.*;
import java.util.*;
import java.nio.file.*;

public class Invertedindex {
   private ArrayList<String> myFiles;
   public String[] stoparr;
   ArrayList<String> termList;
   ArrayList<ArrayList<Integer>> docLists;
   ArrayList<Integer> docList;


   public  String readFileAsString(String fileName)throws Exception 
   { 
     String data = ""; 
     data = new String(Files.readAllBytes(Paths.get(fileName))); 
     return data; 
   } 

    //Binary search for a stop word
    public int searchStopWord(String key,String[] stopWords) {
       Arrays.sort(stopWords);
       int lo = 0;
       int hi = stopWords.length -1;
       while(lo <= hi) {
          int mid = lo + (hi-lo)/2;
          int result = key.compareTo(stopWords[mid]);
          if (result < 0) hi = mid-1;
          else if(result > 0) lo = mid +1;
          else return mid;
       }
       return -1;
    }

    //Tokenization
    public ArrayList<String> parse(File fileName,String data) throws IOException {
      //#5
      String[] tokens;
      ArrayList<String> pureTokens = new ArrayList<String>();
      ArrayList<String> stemms = new ArrayList<String>();

      stoparr=data.split("\n");
      Scanner scan = new Scanner(fileName);
      String allLines = new String();
      
      while(scan.hasNextLine()) {
         allLines += scan.nextLine().toLowerCase();    // case foldering
      }
      // System.out.println(allLines);
      //Tokenization
      allLines= allLines.replaceAll("\\t|,|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/|\\n|\'|\\s|\"", " ");
      allLines=allLines.replaceAll("\\s+"," ").trim();
      //System.out.println(allLines);
      tokens=allLines.split(" ");
  
      //Remove stop words
      for(String token:tokens) {
         //System.out.println(token);
         if(searchStopWord(token,stoparr) == -1) {
            pureTokens.add(token);
         }
      }
      
      //Stemming
      Stemmer st = new Stemmer();
      for(String token:pureTokens) {
         st.add(token.toCharArray(), token.length());
         st.stem();
         stemms.add(st.toString());
         st = new Stemmer();
      //   }
      }
      
      return stemms;
      //return null;
   }

public void invertedIndex (ArrayList<ArrayList<String>> result)  {

   termList = new ArrayList<String>();
   docLists = new ArrayList<ArrayList<Integer>>();

     for(int i=0;i<result.size();i++) {
     ArrayList<String> temp = result.get(i);
      for(String word:temp) {
         if(!termList.contains(word)) {
            termList.add(word);
            //int[] docList = new int[myDocs.length];
            docList = new ArrayList<Integer>();
            
            //docList[i] = 1;
            docList.add(i);
            docLists.add(docList);
         }
         else {
            int index = termList.indexOf(word);
            //int[] docList = docLists.remove(index);
            docList = docLists.get(index);
            //docList[i] = 1;
            if(!docList.contains(i)) {
               docList.add(i);
               docLists.set(index, docList);
            }
            
         }
      }
      
     }
     //System.out.println(docLists);
     String outputString = new String();
     for(int i=0;i<termList.size();i++) {
        outputString += String.format("%-15s", termList.get(i));
        //int[] docList = docLists.get(i);
        ArrayList<Integer> doc = docLists.get(i);
        //for(int j=0;j<docList.length;j++) {
        for(int j=0;j<doc.size();j++) {
  
           //outputString += docList[j] + "\t";
           outputString += doc.get(j) + "\t";
        }
        outputString += "\n";
     }
   System.out.println(outputString);
}

//search a keyword 

   public void searchTerm(String term){

   	  Stemmer s = new Stemmer();
         s.add(term.toCharArray(),term.length());
         s.stem();
         term = s.toString();


      if(termList.contains(term)){
         System.out.println("\nOutput for term : " +term);
         int index= termList.indexOf(term);
         docList=docLists.get(index);
         for(int i=0;i<docList.size();i++){
            switch(docList.get(i)){
               case 0: 
                  System.out.println(" Term present in 1.txt");
                  break;
               case 1: 
                  System.out.println(" Term present in 2.txt");
                  break;
               case 2: 
                  System.out.println(" Term present in 3.txt"); 
                  break;
               case 3: 
                  System.out.println(" Term present in 4.txt");
                  break;
               case 4: 
                  System.out.println(" Term present in 5.txt");
                  break;
               default:
                  System.out.println("Term not found");
            }

            }

         }
         else{
            System.out.println("\nTerm " +term +" not found in any documents");
      }

   }


//AND ing keywords
   public void andCase(String str1,String str2){

   	   	 Stemmer s = new Stemmer();
         s.add(str1.toCharArray(),str1.length());
         s.stem();
         str1 = s.toString();

         s.add(str2.toCharArray(),str2.length());
         s.stem();
         str2 = s.toString();


         if (termList.contains(str1) && termList.contains(str2)){
            System.out.println("\nOutput for terms : " +str1 +" and " +str2 );
            int index1= termList.indexOf(str1);
            int index2= termList.indexOf(str2);
            int flag=0;
            ArrayList<Integer> list1=docLists.get(index1);
            ArrayList<Integer> list2=docLists.get(index2);
            ArrayList<Integer> outarr = new ArrayList<Integer>();
            for(int i=0;i<list1.size();i++){
               for(int j=0;j<list2.size();j++){
                  // System.out.println(list2.get(j));
                  if(list1.get(i)==list2.get(j)){
                  	if(!outarr.contains(list2.get(j))){
                     outarr.add(list2.get(j));
                     flag=1;
                    }
                  }
               }
            }
            if(flag==0) System.out.println("The give terms does not coexists in any documents");
            else {
               for(int j=0;j<outarr.size();j++){

               System.out.println("Both the terms are present in "+ ((outarr.get(j))+1));
               }
         }
      

   }
   else System.out.println("The either of the give terms does not exists in any documents");
}
  
  //OR ing keywords
public void orCase(String str1,String str2){
	Stemmer s = new Stemmer();
    	 s.add(str1.toCharArray(),str1.length());
         s.stem();
         str1 = s.toString();

         s.add(str2.toCharArray(),str2.length());
         s.stem();
         str2 = s.toString();

   if (termList.contains(str1) || termList.contains(str2)){
      System.out.println("\nOutput for terms : " +str1 +" OR " +str2 );
      int index1= termList.indexOf(str1);
      int index2= termList.indexOf(str2);
      ArrayList<Integer> list1=docLists.get(index1);
      ArrayList<Integer> list2=docLists.get(index2);
      ArrayList<Integer> outarr = new ArrayList<Integer>();
      for(int i=0;i<list1.size();i++){
         if (!outarr.contains(list1.get(i)))
         {
            outarr.add(list1.get(i));
         }
         for(int j=0;j<list2.size();j++){
            if (!outarr.contains(list2.get(j)))
            {
               outarr.add(list2.get(j));
            }
               else{
               continue;
            }
         }
      }
    
      if (outarr.size() == 0) {
         System.out.println("Term not present in any file");
      }
      else
      {
         for(int j=0;j<outarr.size();j++)
         {
            //System.out.println(outarr);
            System.out.println("Term might be present in "+ ((outarr.get(j))+1));
         }
      }
   }


}

// 3 or more

// public void findinlist(String[] arr){
// int[] index = new int[arr.length];
// ArrayList<ArrayList<Integer>> arrin = new ArrayList<ArrayList<Integer>> ();
//    for(int i=0;i<arr.length;i++){
//       if(termList.contains(arr[i])){
//          index[i]=termList.indexOf(arr[i]);
//          //System.out.println(docLists.get(index[i]));
//              for(int num:index) {
//                 if(!arrin.contains(num)) {
//                   arrin.add(docLists.get(index[i]));
//                 }
//       }
//    }

//    }
//    System.out.println(arrin);
//       }

 public void findinlist(String[] arr){

 	String[] in= new String[arr.length];
 	int[] index = new int[arr.length];
 	ArrayList<ArrayList<Integer>> arrin = new ArrayList<ArrayList<Integer>> ();

 	Stemmer st = new Stemmer();
    for(int i=0; i<arr.length;i++) {
        // System.out.println(arr[i]);
         st.add(arr[i].toCharArray(), arr[i].length());
         st.stem();
         in[i] = (st.toString());
         // System.out.println(in[i]);
         st = new Stemmer();

         index[i]=(termList.indexOf(arr[i]));
         arrin.add(docLists.get(index[i]));  
      //   }
         
      }
      
    // System.out.println((arrin.get(i)).size());
    // int[] size = new int[arr.length];
    ArrayList<Integer> temp = new ArrayList<Integer>();
    String temp2;
	   for(int i = 0; i<arr.length; i++ ){
         for(int j = i+1; j<arr.length; j++){
            if(((arrin.get(i)).size())>(arrin.get(j)).size()){

               temp = arrin.get(i);
               temp2 = arr[i];
               arrin.set(i,arrin.get(j));
               arr[i]=arr[j];
               arrin.set(j,temp);
               arr[j]=temp2;
            }
         }
         System.out.println(arr[i]);
      }   
    
    ArrayList<Integer> outarr = new ArrayList<Integer>();
    ArrayList<Integer> list1 = new ArrayList<Integer>();
    ArrayList<Integer> list2 = new ArrayList<Integer>();
    int flag=0;
    for(int z=0; z<arr.length-1;z++){

    	if(z==0)  list1=arrin.get(z);
    	else
    	list1 = outarr;	
      	list2=arrin.get(z+1);
      // System.out.println(list1);
      // System.out.println(list2);
			for(int i=0;i<list1.size();i++){
               for(int j=0;j<list2.size();j++){
                  // System.out.println(list2.get(j));
                  if(list1.get(i)==list2.get(j)){
                  	if(!outarr.contains(list2.get(j))){
                     outarr.add(list2.get(j));
                     flag=1;
                 	}
                  }
               }
            }


    }
    	System.out.println("\nOutput for multi-term query :" );
		if(flag==0) System.out.println("The give terms does not coexists in any documents");
        else {
            for(int j=0;j<outarr.size();j++){

				System.out.println("All the terms are present in "+ ((outarr.get(j))+1));
            }
		}		


}





    public static void main(String[] args) throws Exception 
      { 
      
      Invertedindex x = new Invertedindex();
      String data = x.readFileAsString("stopwords.txt"); 
   //For 1 file
      // try {
         
      //    File file = new File("1.txt");
      //    // File stopwords = new File("stopwords.txt");
      //    ArrayList<String> stemmed = x.parse(file,data);

      //    for(String st:stemmed) {
      //       System.out.println(st);
      //    }
      // }
      // catch(IOException ioe) {
      //    ioe.printStackTrace();
      // } 

      String target_dir = "./Data";
      File dir = new File(target_dir);
      File[] files = dir.listFiles();
      Arrays.sort(files);
      ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
      for (File file : files) {
          if(file.isFile()) {
            try {
         
      //    File file = new File("1.txt");
      //    // File stopwords = new File("stopwords.txt");
            ArrayList<String>stemmed  = x.parse(file,data);
            result.add(stemmed);
         // for(String st:stemmed) {
         //    System.out.println(st);

         // }
      }
      catch(IOException ioe) {
         ioe.printStackTrace();
      } 
          }
//      System.out.println("End of file");
      }
//      System.out.println("\n\n"+result);

// Displaying Inverted Index
      x.invertedIndex(result);

//Task2 Part1 : Searching a keyword (TestCase1)
      x.searchTerm("jumbled");

//Task2 Part1 : Searching a keyword (TestCase2)
      x.searchTerm("young");
//Task2 Part1 : Searching a keyword (TestCase3)
      x.searchTerm("ninad");

//Task2 Part2 : AND operation (TestCase1)
      x.andCase("reason","fall");
//Task2 Part2 : AND operation  (TestCase2)
      x.andCase("time","stories");  
//Task2 Part2 : AND operation  (TestCase3)
      x.andCase("ninad","stories"); 

//Task2 Part3 : OR operation (TestCase1)
      x.orCase("bored","man");
//Task2 Part3 : OR operation (TestCase2)
      x.orCase("end","mind");

// Task2 Part4 : 3 or more terms (TestCase1)
      String[] arr = {"rate","won","target"};

      x.findinlist(arr);

   } 
}
       
