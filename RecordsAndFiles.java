import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;


public class RecordsAndFiles {


  public static class TBook { // Type Record
    String title;
    String isbn;
    int available; // Stores the number available in the system
    int borrowed; // Stores the number that are currently available - cannot be more than available

    public TBook() {}
  }


  private static TBook[] books = new TBook[25]; // Array of type Record
  private static int numBooks = 0;

  public static void main(String[] args) {
    Scanner kbReader = new Scanner(System.in);
    boolean end = false;

    while (!end) {
        System.out.println("Choose an option:");
        System.out.println("1. Add a new book");
        System.out.println("2. Edit a book");
        System.out.println("3. Remove a book");
        System.out.println("4. Exit");

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
                writeBookData(); // rewrite file
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

  }


// function to read file and work with array
  public static void readBookData() {
    try (Scanner fileScanner = new Scanner(new FileReader("books.txt"))) {
      
      numBooks = 0;

      while (fileScanner.hasNextLine() && numBooks < books.length) {

          TBook book = new TBook();

          // Hacking bible978-1-238-72635-9Gaming history978-1-547-56743-1
          // use split

          book.title = fileScanner.nextLine();
          book.isbn = fileScanner.nextLine();
          book.available = fileScanner.nextInt();
          book.borrowed = fileScanner.nextInt();
          books[numBooks] = book;

          numBooks++;

      }

  } catch (IOException e) {

      System.out.println("ERROR !! " + e.getMessage());

  }
  }


  // function to write all books' data to the file
  public static void writeBookData() {

    try (FileWriter writer = new FileWriter("books.txt", false)) {
      // false above for overwritting

      for (int i = 0; i < numBooks; i++) {

          TBook book = books[i];
          writer.write(book.title);
          writer.write(book.isbn);
          writer.write(book.available);
          writer.write(book.borrowed);

      }

    } catch (IOException e) {

      System.out.println("ERROR !! " + e.getMessage());

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
    

    for (int i = bookIndex; i < numBooks - 1; i++) { // shift all the elements to replace deleting book
        books[i] = books[i + 1];
    }

    books[numBooks - 1] = null; // create a space 
    numBooks--;
}

}
