import java.util.*;

public class main {
    public static void main(String[] args) {
        Library lib = new Library();

        System.out.println("\n1. printAllBooks()");
        lib.printAllBooks();


        System.out.println("\n2. addBook(Book book)");
        lib.addBook(new Book("Преступление и наказание", "Фёдор Достоевский", 1866));
        System.out.println("addBook(new Book(\"Преступление и наказание\", \"Фёдор Достоевский\", 1866))");

        lib.addBook(new Book("Идиот", "Фёдор Достоевский", 1869));
        System.out.println("addBook(new Book(\"Идиот\", \"Фёдор Достоевский\", 1869))");

        lib.addBook(new Book("Война и мир", "Лев Толстой", 1869));
        System.out.println("addBook(new Book(\"Война и мир\", \"Лев Толстой\", 1869))");

        lib.addBook(new Book("Анна Каренина", "Лев Толстой", 1877));
        System.out.println("addBook(new Book(\"Анна Каренина\", \"Лев Толстой\", 1877))");

        System.out.println("2.1 printAllBooks()");
        lib.printAllBooks();


        System.out.println("\n3. removeBook(Book book)");
        System.out.println("removeBook(new Book(\"Анна Каренина\", \"Лев Толстой\", 1877))");
        lib.removeBook(new Book("Анна Каренина", "Лев Толстой", 1877));

        System.out.println("3.1 printAllBooks()");
        lib.printAllBooks();


        System.out.println("\n4. printUniqueAuthors()");
        lib.printUniqueAuthors();


        System.out.println("\n5. printAuthorStatistics()");
        lib.printAuthorStatistics();


        System.out.println("\n6. findBooksByAuthor(String author)");
        System.out.println("findBooksByAuthor('Фёдор Достоевский')");
        List<Book> dost = lib.findBooksByAuthor("Фёдор Достоевский");
        dost.forEach(System.out::println);


        System.out.println("\n7. findBooksByYear(int year)");
        System.out.println("findBooksByYear(1869)");
        List<Book> y1869 = lib.findBooksByYear(1869);
        y1869.forEach(System.out::println);
    }
}

class Book {
    String title;
    String author;
    int year;

    
    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    
    @Override
    public String toString() {
        return title + " (" + author + ", " + year + ")";
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book other = (Book) o;
        return year == other.year && title.equals(other.title) && author.equals(other.author);
    }

    
    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }
}

class Library {
    private List<Book> books = new ArrayList<>();
    private Set<String> authors = new HashSet<>();
    private Map<String, Integer> countByAuthor = new HashMap<>();

    
    public void addBook(Book book) {
        books.add(book);
        authors.add(book.author);
        countByAuthor.put(book.author, countByAuthor.getOrDefault(book.author, 0) + 1);
    }

    
    public void removeBook(Book book) {
        if (books.remove(book)) {
            int cnt = countByAuthor.get(book.author) - 1;
            if (cnt <= 0) {
                countByAuthor.remove(book.author);
                authors.remove(book.author);
            } else {
                countByAuthor.put(book.author, cnt);
            }
        }
    }

    
    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.author.equals(author)) {
                result.add(b);
            }
        }
        return result;
    }

    
    public List<Book> findBooksByYear(int year) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.year == year) {
                result.add(b);
            }
        }
        return result;
    }

    
    public void printAllBooks() {
        for (Book b : books) {
            System.out.println(b);
        }
    }

    
    public void printUniqueAuthors() {
        for (String a : authors) {
            System.out.println(a);
        }
    }

    
    public void printAuthorStatistics() {
        for (Map.Entry<String, Integer> e : countByAuthor.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
    }
}
