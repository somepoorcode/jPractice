import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final List<Cinema> cinemas = new ArrayList<>();
    private static final List<Movie> movies = new ArrayList<>();

    public static void main(String[] args) {
        initializeTestData();

        System.out.println("ВХОД");
        System.out.println("Админ: admin / admin");
        System.out.println("Пользователь: user / user\n");


        while (true) {
            System.out.print("Логин: ");
            String login = scanner.nextLine().trim();
            System.out.print("Пароль: ");
            String password = scanner.nextLine().trim();

            String role = AuthService.login(login, password);
            if (role == null) {
                System.out.println("Неверные учетные данные. Попробуйте снова.\n");
                continue;
            }

            if (role.equals("admin")) {
                adminMenu();
            } else {
                userMenu();
            }

            System.out.print("1 - Войти снова, 2 - Выйти: ");
            String choice = scanner.nextLine().trim();
            if (choice.equals("2")) {
                System.out.println("ВЫХОД");
                System.out.println();
                break;
            }
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("МЕНЮ АДМИНИСТРАТОРА");
            System.out.println("1. Добавить кинотеатр");
            System.out.println("2. Добавить зал");
            System.out.println("3. Добавить фильм");
            System.out.println("4. Добавить сеанс");
            System.out.println("5. Выход");
            System.out.print("Выбор: ");

            String cmd = scanner.nextLine().trim();
            switch (cmd) {
                case "1":
                    System.out.print("Название кинотеатра: ");
                    String cinemaName = scanner.nextLine().trim();
                    cinemas.add(new Cinema(cinemaName));
                    System.out.println("Кинотеатр добавлен.");
                    break;
                case "2":
                    Cinema cinema = selectCinema();
                    if (cinema == null) break;
                    System.out.print("Название зала: ");
                    String hallName = scanner.nextLine().trim();
                    System.out.print("Рядов: ");
                    int rows = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Мест в ряду: ");
                    int cols = Integer.parseInt(scanner.nextLine().trim());
                    cinema.addHall(new Hall(hallName, rows, cols));
                    System.out.println("Зал добавлен.");
                    break;
                case "3":
                    System.out.print("Название фильма: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Длительность (минут): ");
                    int duration = Integer.parseInt(scanner.nextLine().trim());
                    movies.add(new Movie(title, duration));
                    System.out.println("Фильм добавлен.");
                    break;
                case "4":
                    addSession();
                    break;
                case "5":
                    System.out.println();
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }


    private static void addSession() {
        System.out.println("ДОБАВЛЕНИЕ СЕАНСА");
        Cinema cinema = selectCinema();
        if (cinema == null) return;
        Hall hall = selectHall(cinema);
        if (hall == null) return;
        Movie movie = selectMovie();
        if (movie == null) return;

        while (true) {
            try {
                System.out.print("Дата (yyyy-MM-dd или 0 - отмена): ");
                String dateInput = scanner.nextLine().trim();
                if (dateInput.equals("0")) return;
                System.out.print("Время (HH:mm или 0 - отмена): ");
                String timeInput = scanner.nextLine().trim();
                if (timeInput.equals("0")) return;

                LocalDateTime start = LocalDateTime.parse(dateInput + "T" + timeInput);

                boolean conflict = false;
                for (Session s : hall.getSessions()) {
                    LocalDateTime existStart = s.getDateTime();
                    LocalDateTime existEnd = existStart.plusMinutes(s.getMovie().duration());
                    LocalDateTime newEnd = start.plusMinutes(movie.duration());
                    if (!(start.isAfter(existEnd) || newEnd.isBefore(existStart))) {
                        conflict = true;
                        break;
                    }
                }

                if (conflict) {
                    System.out.println("Ошибка: время пересекается с другим сеансом.");
                } else {
                    hall.addSession(movie, start);
                    System.out.println("Сеанс добавлен.");
                    break;
                }

            } catch (Exception e) {
                System.out.println("Неверный формат даты или времени.");
            }
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("МЕНЮ ПОЛЬЗОВАТЕЛЯ");
            System.out.println("1. Список фильмов");
            System.out.println("2. Купить билет");
            System.out.println("3. Посмотреть план зала");
            System.out.println("4. Выход");
            System.out.print("Выбор: ");

            String cmd = scanner.nextLine().trim();
            switch (cmd) {
                case "1":
                    for (Movie m : movies) {
                        System.out.println(m);
                    }
                    break;
                case "2":
                    buyTicket();
                    break;
                case "3":
                    Cinema cinema = selectCinema();
                    if (cinema == null) break;
                    Hall hall = selectHall(cinema);
                    if (hall == null) break;
                    Session session = selectSession(hall);
                    if (session == null) break;
                    session.printSeatPlan();
                    break;
                case "4":
                    System.out.println();
                    return;
                default:
                    System.out.println("Неверный ввод.");
            }
        }
    }


    private static void buyTicket() {
        System.out.println("ПОКУПКА БИЛЕТА");
        Movie movie = selectMovie();
        if (movie == null) return;

        List<SessionChoice> choices = getSessionChoices(movie);

        if (choices.isEmpty()) {
            System.out.println("Нет доступных сеансов.");
            return;
        }

        for (int i = 0; i < choices.size(); i++) {
            SessionChoice sc = choices.get(i);
            System.out.printf(
                    "%d. Кинотеатр: %s, Зал: %s, %s — %s%n",
                    i+1,
                    sc.cinema.getName(),
                    sc.hall.getName(),
                    sc.session.getDateTime().format(DTF),
                    sc.session.getMovie().title()
            );
        }

        Session session;
        int idx;
        while (true) {
            System.out.print("Выберите сеанс (0 - отмена): ");
            String in = scanner.nextLine().trim();
            if (in.equals("0")) {
                return;
            }
            try {
                idx = Integer.parseInt(in) - 1;
                if (idx >= 0 && idx < choices.size()) {
                    session = choices.get(idx).session;
                    break;
                }
            } catch (Exception ignored) {}
            System.out.println("Неверный выбор.");
        }

        session.printSeatPlan();

        int count;
        while (true) {
            System.out.print("Сколько билетов (0 - отмена): ");
            String in = scanner.nextLine().trim();
            if (in.equals("0")) {
                return;
            }
            try {
                count = Integer.parseInt(in);
                if (count > 0) {
                    break;
                }
            } catch (Exception ignored) {}
            System.out.println("Неверное количество.");
        }

        for (int i = 0; i < count; i++) {
            int row;
            while (true) {
                System.out.printf("Ряд (1-%d, 0 - отмена): ", session.getSeats().length);
                String in = scanner.nextLine().trim();
                if (in.equals("0")) {
                    return;
                }
                try {
                    row = Integer.parseInt(in) - 1;
                    if (row >= 0 && row < session.getSeats().length) {
                        break;
                    }
                } catch (Exception ignored) {}
                System.out.println("Неверный ряд.");
            }

            int col;
            while (true) {
                System.out.printf("Место (1-%d, 0 - отмена): ", session.getSeats()[0].length);
                String in = scanner.nextLine().trim();
                if (in.equals("0")) {
                    return;
                }
                try {
                    col = Integer.parseInt(in) - 1;
                    if (col >= 0 && col < session.getSeats()[0].length) {
                        break;
                    }
                } catch (Exception ignored) {}
                System.out.println("Неверное место.");
            }

            if (session.getSeats()[row][col].isOccupied()) {
                System.out.println("Занято. Выберите другое место.");
                i--;
                continue;
            }

            session.getSeats()[row][col].occupy();
            System.out.printf("Билет куплен: ряд %d, место %d%n", row+1, col+1);
        }
    }


    private static List<SessionChoice> getSessionChoices(Movie movie) {
        List<SessionChoice> choices = new ArrayList<>();
        for (Cinema c : cinemas) {
            for (Hall h : c.getHalls()) {
                for (Session s : h.getSessions()) {
                    if (s.getMovie().equals(movie)) {
                        boolean hasFree = false;
                        for (Seat[] row : s.getSeats()) {
                            for (Seat seat : row) {
                                if (!seat.isOccupied()) {
                                    hasFree = true;
                                    break;
                                }
                            }
                            if (hasFree) break;
                        }
                        if (hasFree) {
                            choices.add(new SessionChoice(c, h, s));
                        }
                    }
                }
            }
        }
        return choices;
    }


    private static Cinema selectCinema() {
        if (cinemas.isEmpty()) {
            System.out.println("Нет кинотеатров.");
            return null;
        }
        while (true) {
            for (int i = 0; i < cinemas.size(); i++) {
                System.out.printf("%d. %s%n", i+1, cinemas.get(i).getName());
            }
            System.out.print("Выберите кинотеатр (0 - отмена): ");
            String in = scanner.nextLine().trim();
            if (in.equals("0")) {
                return null;
            }
            try {
                int idx = Integer.parseInt(in) - 1;
                if (idx >= 0 && idx < cinemas.size()) {
                    return cinemas.get(idx);
                }
            } catch (Exception ignored) {}
            System.out.println("Неверный ввод.");
        }
    }


    private static Hall selectHall(Cinema cinema) {
        List<Hall> halls = cinema.getHalls();
        if (halls.isEmpty()) {
            System.out.println("Нет залов.");
            return null;
        }
        while (true) {
            for (int i = 0; i < halls.size(); i++) {
                System.out.printf("%d. %s%n", i+1, halls.get(i).getName());
            }
            System.out.print("Выберите зал (0 - отмена): ");
            String in = scanner.nextLine().trim();
            if (in.equals("0")) {
                return null;
            }
            try {
                int idx = Integer.parseInt(in) - 1;
                if (idx >= 0 && idx < halls.size()) {
                    return halls.get(idx);
                }
            } catch (Exception ignored) {}
            System.out.println("Неверный ввод.");
        }
    }


    private static Movie selectMovie() {
        if (movies.isEmpty()) {
            System.out.println("Нет фильмов.");
            return null;
        }
        while (true) {
            for (int i = 0; i < movies.size(); i++) {
                System.out.printf("%d. %s%n", i+1, movies.get(i));
            }
            System.out.print("Выберите фильм (0 - отмена): ");
            String in = scanner.nextLine().trim();
            if (in.equals("0")) {
                return null;
            }
            try {
                int idx = Integer.parseInt(in) - 1;
                if (idx >= 0 && idx < movies.size()) {
                    return movies.get(idx);
                }
            } catch (Exception ignored) {}
            System.out.println("Неверный ввод.");
        }
    }


    private static Session selectSession(Hall hall) {
        List<Session> sessions = hall.getSessions();
        if (sessions.isEmpty()) {
            System.out.println("Нет сеансов.");
            return null;
        }
        while (true) {
            for (int i = 0; i < sessions.size(); i++) {
                Session s = sessions.get(i);
                System.out.printf("%d. %s — %s%n", i+1, s.getMovie().title(), s.getDateTime().format(DTF));
            }
            System.out.print("Выберите сеанс (0 - отмена): ");
            String in = scanner.nextLine().trim();
            if (in.equals("0")) {
                return null;
            }
            try {
                int idx = Integer.parseInt(in) - 1;
                if (idx >= 0 && idx < sessions.size()) {
                    return sessions.get(idx);
                }
            } catch (Exception ignored) {}
            System.out.println("Неверный ввод.");
        }
    }


    private static void initializeTestData() {
        Movie m1 = new Movie("Майнкрафт в кино (Дубак)", 100);
        Movie m2 = new Movie("Барби (Вьюга)", 117);
        movies.clear();
        movies.add(m1);
        movies.add(m2);

        Cinema cinema = new Cinema("Эпицентр");
        Hall hall = new Hall("Зал 1", 5, 5);
        hall.addSession(m1, LocalDateTime.of(2025, 6, 15, 14, 0));
        hall.addSession(m2, LocalDateTime.of(2025, 6, 15, 17, 30));
        cinema.addHall(hall);

        cinemas.clear();
        cinemas.add(cinema);
    }


    static class Cinema {
        private final String name;
        private final List<Hall> halls = new ArrayList<>();

        public Cinema(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public List<Hall> getHalls() {
            return halls;
        }

        public void addHall(Hall hall) {
            halls.add(hall);
        }
    }


    static class Hall {
        private final String name;
        private final int rows;
        private final int cols;
        private final List<Session> sessions = new ArrayList<>();

        public Hall(String name, int rows, int cols) {
            this.name = name;
            this.rows = rows;
            this.cols = cols;
        }

        public String getName() {
            return name;
        }

        public List<Session> getSessions() {
            return sessions;
        }

        public void addSession(Movie movie, LocalDateTime dateTime) {
            Session session = new Session(movie, dateTime, rows, cols);
            sessions.add(session);
        }
    }


    record Movie(String title, int duration) {

        @Override
            public String toString() {
                return title + " (" + duration + " мин)";
            }
        }


    static class Session {
        private final Movie movie;
        private final LocalDateTime dateTime;
        private final Seat[][] seats;

        public Session(Movie movie, LocalDateTime dateTime, int rows, int cols) {
            this.movie = movie;
            this.dateTime = dateTime;
            this.seats = new Seat[rows][cols];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    seats[r][c] = new Seat();
                }
            }
        }

        public Movie getMovie() {
            return movie;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public Seat[][] getSeats() {
            return seats;
        }

        public void printSeatPlan() {
            System.out.println("План зала:");
            for (Seat[] row : seats) {
                for (Seat seat : row) {
                    System.out.print(seat.isOccupied() ? "[X]" : "[ ]");
                }
                System.out.println();
            }
        }
    }


    static class Seat {
        private boolean occupied;

        public Seat() {
            this.occupied = false;
        }

        public boolean isOccupied() {
            return occupied;
        }

        public void occupy() {
            occupied = true;
        }
    }


    static class AuthService {
        public static String login(String login, String password) {
            if (login.equals("admin") && password.equals("admin")) {
                return "admin";
            } else if (login.equals("user") && password.equals("user")) {
                return "user";
            } else {
                return null;
            }
        }
    }


    private record SessionChoice(Cinema cinema, Hall hall, Session session) {
    }
}
