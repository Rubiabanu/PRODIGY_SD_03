import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Contact {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber + ", Email Address: " + emailAddress;
    }

    // Getters and setters (optional)
}

public class ContactManager {
    private ArrayList<Contact> contacts;
    private static final String FILE_PATH = "contacts.txt";

    public ContactManager() {
        contacts = new ArrayList<>();
        loadContactsFromFile();
    }

    public void addContact(String name, String phoneNumber, String emailAddress) {
        Contact newContact = new Contact(name, phoneNumber, emailAddress);
        contacts.add(newContact);
        System.out.println("Contact added successfully!");
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    public void editContact(int index, String name, String phoneNumber, String emailAddress) {
        if (index >= 0 && index < contacts.size()) {
            Contact editedContact = new Contact(name, phoneNumber, emailAddress);
            contacts.set(index, editedContact);
            System.out.println("Contact edited successfully!");
        } else {
            System.out.println("Invalid index. Contact not found.");
        }
    }

    public void deleteContact(int index) {
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Invalid index. Contact not found.");
        }
    }

    public void saveContactsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(contacts);
            System.out.println("Contacts saved to file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving contacts to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadContactsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            contacts = (ArrayList<Contact>) ois.readObject();
            System.out.println("Contacts loaded from file successfully!");
        } catch (IOException | ClassNotFoundException e) {
            // Ignore if the file does not exist or is empty
            // or if there is an issue deserializing the contacts
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContactManager contactManager = new ContactManager();

        while (true) {
            System.out.println("\nContact Manager Menu:");
            System.out.println("1. Add a new contact");
            System.out.println("2. View contacts");
            System.out.println("3. Edit a contact");
            System.out.println("4. Delete a contact");
            System.out.println("5. Save contacts to file");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email address: ");
                    String emailAddress = scanner.nextLine();
                    contactManager.addContact(name, phoneNumber, emailAddress);
                    break;
                case 2:
                    contactManager.viewContacts();
                    break;
                case 3:
                    System.out.print("Enter the index of the contact to edit: ");
                    int editIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    System.out.print("Enter new email address: ");
                    String newEmailAddress = scanner.nextLine();
                    contactManager.editContact(editIndex, newName, newPhoneNumber, newEmailAddress);
                    break;
                case 4:
                    System.out.print("Enter the index of the contact to delete: ");
                    int deleteIndex = scanner.nextInt();
                    contactManager.deleteContact(deleteIndex);
                    break;
                case 5:
                    contactManager.saveContactsToFile();
                    break;
                case 6:
                    contactManager.saveContactsToFile(); // Save contacts before exiting
                    System.out.println("Exiting Contact Manager. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }
}
