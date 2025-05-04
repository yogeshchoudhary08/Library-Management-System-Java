import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SimpleLibrary {
    
    static ArrayList<Book> books = new ArrayList<>();
    static String dataFile = "library_data.txt";
    
    static class Book implements Serializable {
        String title;
        String author;
        boolean available;
        
        public Book(String title, String author) {
            this.title = title;
            this.author = author;
            this.available = true;
        }
        
        @Override
        public String toString() {
            return title + " by " + author + " - " + (available ? "Available" : "Borrowed");
        }
    }
    
    public static void main(String[] args) {
        loadData();
        Scanner scanner = new Scanner(System.in);
        
        while(true) {
            System.out.println("\n=== SIMPLE LIBRARY ===");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch(choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    books.add(new Book(title, author));
                    System.out.println("Book added!");
                    break;
                    
                case 2:
                    if(books.isEmpty()) {
                        System.out.println("No books in library!");
                    } else {
                        System.out.println("\n--- ALL BOOKS ---");
                        for(int i = 0; i < books.size(); i++) {
                            System.out.println((i+1) + ". " + books.get(i));
                        }
                    }
                    break;
                    
                case 3:
                    System.out.print("Enter book number to borrow: ");
                    int borrowIndex = scanner.nextInt()-1;
                    if(borrowIndex >= 0 && borrowIndex < books.size()) {
                        if(books.get(borrowIndex).available) {
                            books.get(borrowIndex).available = false;
                            System.out.println("Book borrowed!");
                        } else {
                            System.out.println("Book already borrowed!");
                        }
                    } else {
                        System.out.println("Invalid book number!");
                    }
                    break;
                    
                case 4:
                    System.out.print("Enter book number to return: ");
                    int returnIndex = scanner.nextInt()-1;
                    if(returnIndex >= 0 && returnIndex < books.size()) {
                        if(!books.get(returnIndex).available) {
                            books.get(returnIndex).available = true;
                            System.out.println("Book returned!");
                        } else {
                            System.out.println("Book wasn't borrowed!");
                        }
                    } else {
                        System.out.println("Invalid book number!");
                    }
                    break;
                    
                case 5:
                    saveData();
                    System.out.println("Goodbye!");
                    System.exit(0);
                    
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
            books = (ArrayList<Book>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}