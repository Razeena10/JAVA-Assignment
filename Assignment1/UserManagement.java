import java.util.*;
enum UserRole {
    ADMIN,
    USER
}
class DuplicateUserException extends Exception {
    public DuplicateUserException(String message) {
        super(message);
    }
}
class User {
    private static int idGenerator = 1;
    private int id;
    private String name;
    private String email;
    private UserRole role;
    private boolean active;

    public User(String name, String email, UserRole role, boolean active) {
        this.id = idGenerator++;
        this.name = name;
        this.email = email;
        this.role = role;
        this.active = active;
    }

    public String getEmail() {
        return email;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User user = (User) obj;
        return email.equalsIgnoreCase(user.email);
    }

    public int hashCode() {
        return Objects.hash(email);
    }

    public String toString() {
        return "User { id = " + id + ", name = " + name + ", email = " + email + ", role = " + role + ", active = " + active + " }";
    }
}

class UserService {

    private User[] users;
    private int count;

    public UserService(int size) {
        users = new User[size];
        count = 0;
    }

    public void addUser(User user) throws DuplicateUserException {

        if (count == users.length) {
            System.out.println("User storage is full!!!");
            return;
        }

        for (int i = 0; i < count; i++) {
            if (users[i].equals(user)) {
                throw new DuplicateUserException(
                        "User with email " + user.getEmail() + " already exists!"
                );
            }
        }
        users[count++] = user;
        System.out.println("User added successfully");
    }

    public void displayUsers() {
        if (count == 0) {
            System.out.println("No users available");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println(users[i]);
        }
    }
}

public class UserManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserService service = new UserService(5);
        while (true) {
            System.out.println("\n==== USER MANAGEMENT ====");
            System.out.println("1. Add User");
            System.out.println("2. View Users");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 
            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter role (ADMIN/USER): ");
                        UserRole role = UserRole.valueOf(sc.nextLine().toUpperCase());
                        System.out.print("Is active (true/false): ");
                        boolean active = sc.nextBoolean();
                        User user = new User(name, email, role, active);
                        service.addUser(user);
                    } catch (DuplicateUserException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid role entered!");
                    }
                    break;
                case 2:
                    service.displayUsers();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
