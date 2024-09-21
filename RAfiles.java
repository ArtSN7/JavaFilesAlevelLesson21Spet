import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;


public class RAfiles {

    public static class TBook { // Type Record
        String title;
        String isbn;
        int available; // Stores the number available in the system
        int borrowed; // Stores the number that are currently available - cannot be more than available
    
        public TBook() {}
      }
    
    
      private static TBook[] books = new TBook[25]; // Array of type Record
      private static int numBooks = 0;
    
      private static int rSize = 50;
    
      public static void main(String[] args) {
        Scanner kbReader = new Scanner(System.in);
        boolean end = false;
    
        while (!end) {
            System.out.println("Choose an option:");
            System.out.println("1. Add a new book");
            System.out.println("2. Edit a book");
            System.out.println("3. Remove a book");
            System.out.println("4. Exit");
            System.out.println("5. Read");
    
            int choice = Integer.parseInt(kbReader.nextLine());
    
            switch (choice) {
    
                case 1:
    
                    populateBook();
                    break;
    
                case 2:
    
                    editBook();
                    break;
    
                case 3:
    
                    removeBook();
                    break;
    
                case 4:
    
                    end = true;
                    writeBookDataRA(); // rewrite file
                    break;

                case 5:

                    end = true;
                    readBookDataRA();
                    break;
    
                default:
    
                    System.out.println("ERROR !! CHECK THE INTEGER");
            }
        }
    
      }
    
    
      public static void populateBook() {
    
        if (numBooks < books.length - 1) {
    
          Scanner kbReader = new Scanner(System.in);
          TBook book = new TBook();
    
          System.out.println("Please enter the book details:");
          System.out.println("Title:");
          book.title = kbReader.nextLine();
    
          System.out.println("ISBN:");
          book.isbn = kbReader.nextLine();
    
          System.out.println("Number Available:");
          book.available = Integer.parseInt(kbReader.nextLine());
    
          book.borrowed = 0;
    
          books[numBooks] = book;
          numBooks += 1;
    
        } else {
    
          System.out.println("The books array is full!");
    
        }
      }
    

    // function to change book data
      public static void editBook() {
    
         Scanner kbReader = new Scanner(System.in);
    
         // range is from 1 to num of all books as it is easier for user to understand, for human-beings it all starts with 1 
         System.out.println("Enter the index of the book you want to edit in the range 1 - " + (numBooks) + ":");
         int book_num = Integer.parseInt(kbReader.nextLine());
     
         if (book_num < 1 || book_num > numBooks) {
             System.out.println("ERROR !! CHECK THE RANGE");
             return;
         }
     
         TBook book = books[book_num - 1]; // -1 as the first value in the list will have input = 1 and not 0
     
         System.out.println("Which field would you like to edit?");
         System.out.println("Please choose:\n");
         System.out.println("1. Title");
         System.out.println("2. ISBN");
         System.out.println("3. Number Available");
         System.out.println("4. Number Borrowed\n");
     
         int choice = Integer.parseInt(kbReader.nextLine());
     
         switch (choice) {
    
             case 1:
    
                 System.out.println("Enter new title:");
                 book.title = kbReader.nextLine();
                 break;
    
             case 2:
    
                 System.out.println("Enter new ISBN:");
                 book.isbn = kbReader.nextLine();
                 break;
    
             case 3:
    
                 System.out.println("Enter new number available:");
                 book.available = Integer.parseInt(kbReader.nextLine());
                 break;
    
             case 4:
    
                 System.out.println("Enter new number borrowed:");
                 int borrowed = Integer.parseInt(kbReader.nextLine());
    
                 if (borrowed <= book.available) {
    
                     book.borrowed = borrowed;
    
                 } else {
    
                     System.out.println("ERROR !! BORROWED IS MORE THAN AVAILABLE");
    
                 }
    
                 break;
    
             default:
    
                 System.out.println("ERROR !! CHOOSE THE PROPER INTEGER");
                 break;
         }


         try {
    
            RandomAccessFile file = new RandomAccessFile("book.txt", "rw");
      
            
            file.seek(rSize * (book_num - 1)); // putting the value into appropriate byte range
      
            file.writeUTF(books[book_num - 1].title);
            file.writeUTF(books[book_num - 1].isbn);
            file.writeInt(books[book_num - 1].available);
            file.writeInt(books[book_num - 1].borrowed);
      
            file.close();
            
          } catch (IOException e) {
            System.out.println("ERROR !! FILE DOESN'T EXIST");
          }
    }
    
    
    
    // function to remove the book from the list
      public static void removeBook() {
    
        Scanner kbReader = new Scanner(System.in);
        System.out.println("Enter the index of the book you want to remove in the range from 1 - " + (numBooks) + ":");
    
        int bookIndex = Integer.parseInt(kbReader.nextLine());
    
        if (bookIndex < 1 || bookIndex > numBooks) {
            System.out.println("ERROR !! CHECK THE RANGE");
            return;
        }


        // deleting stuff from the list
        for (int i = bookIndex - 1; i < numBooks - 1; i++) { // shift all the elements to replace deleting book
            books[i] = books[i + 1];
        }
    
        books[numBooks - 1] = null; // create a space 
        numBooks--;


        // deleting stuff from the file
        try {
    
          RandomAccessFile file = new RandomAccessFile("book.txt", "rw");
    
            file.seek(rSize * (bookIndex - 1)); // putting the value into appropriate byte range
    
            file.writeUTF("");
            file.writeUTF("");
            file.writeUTF("");
            file.writeUTF("");
        
          file.close();
          
        } catch (IOException e) {
          System.out.println("ERROR !! FILE DOESN'T EXIST");
        }


    }
    
    
    
    
    
    public static void writeBookDataRA(){
        int recordNum = 0;
        try {
    
          RandomAccessFile file = new RandomAccessFile("book.txt", "rw");
    
          for(int i = 0; i < books.length; i++){
            file.seek(rSize * recordNum); // putting the value into appropriate byte range
    
            file.writeUTF(books[i].title);
            file.writeUTF(books[i].isbn);
            file.writeInt(books[i].available);
            file.writeInt(books[i].borrowed);
            recordNum += 1;
    
          }
          file.close();
          
        } catch (IOException e) {
          System.out.println("ERROR !! FILE DOESN'T EXIST");
        }
    }


    // function to read file and work with array
    public static void readBookDataRA() {
        try {

            RandomAccessFile file = new RandomAccessFile("books.txt", "rw");
              
            int recordNum = 0;

                
            for(int i = 0; i < books.length; i++){
    
                file.seek(rSize * recordNum); // reading the value from appropriate byte range

                byte[] bytes = new byte[50];
                file.read(bytes);

                String s = new String(bytes, StandardCharsets.UTF_8);
                System.out.println("Output : " + s);

                recordNum += 1;
    
          }
          file.close();
        
        } catch (IOException e) {
        
            System.out.println("ERROR !! " + e.getMessage());
        
        }
    }
    
}
