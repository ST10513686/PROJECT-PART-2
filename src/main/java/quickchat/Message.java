/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package quickchat;

/**
 *
 * @author Ntatiso
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Message {

    private String messageID;
    private int    messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static ArrayList<Message> sentMessages   = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID     = messageID;
        this.messageNumber = messageNumber;
        this.recipient     = recipient;
        this.messageText   = messageText;
        this.messageHash   = createMessageHash();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }

    boolean hasInternationalCode = recipient.startsWith("+");
    boolean correctLength = recipient.length() == 12;
    String digitsOnly = recipient.substring(1);
    boolean onlyDigits = digitsOnly.matches("\\d+");


        if (hasInternationalCode && correctLength && onlyDigits) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        if (messageID == null || messageText == null || messageText.trim().isEmpty()) {
            return "";
        }
        String idPart    = messageID.substring(0, Math.min(2, messageID.length()));
        String[] words   = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord  = words[words.length - 1];
        String hash      = idPart + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    public String checkMessageLength() {
        if (messageText == null || messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + " characters; please reduce the size.";
        }
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                this.messageNumber = totalMessagesSent;
                this.messageHash   = createMessageHash();
                sentMessages.add(this);
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storedMessages.add(this);
                return "Message successfully stored.";
            default:
                return "Invalid choice. Please select 1, 2, or 3.";
        }
    }

    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== Sent Messages =====\n");
        for (Message m : sentMessages) {
            sb.append("Message ID   : ").append(m.messageID).append("\n");
            sb.append("Message Hash : ").append(m.messageHash).append("\n");
            sb.append("Recipient    : ").append(m.recipient).append("\n");
            sb.append("Message      : ").append(m.messageText).append("\n");
            sb.append("-------------------------\n");
        }
        return sb.toString();
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public void storeMessage() {
        String jsonEntry = "{\n"
                + "  \"messageID\": \""   + messageID   + "\",\n"
                + "  \"messageHash\": \"" + messageHash + "\",\n"
                + "  \"recipient\": \""   + recipient   + "\",\n"
                + "  \"message\": \""     + messageText + "\",\n"
                + "  \"status\": \"stored\"\n"
                + "}\n";
        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(jsonEntry);
            System.out.println("Message saved to messages.json");
        } catch (IOException e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }

    public static String generateMessageID() {
        Random random = new Random();
        long id = (long)(random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    public static void sendMessageFlow(Scanner scanner) {
        System.out.println("\n--- Send a New Message ---");

        String msgID = generateMessageID();
        System.out.println("Message ID generated: " + msgID);

        String recipient;
        while (true) {
            System.out.print("Enter recipient cell number (with international code e.g. +27821234567): ");
            recipient = scanner.nextLine().trim();
            Message temp = new Message(msgID, 0, recipient, "test");
            String cellCheck = temp.checkRecipientCell();
            System.out.println(cellCheck);
            if (cellCheck.equals("Cell phone number successfully captured.")) break;
        }

        String messageText;
        while (true) {
            System.out.print("Enter your message (max 250 characters): ");
            messageText = scanner.nextLine().trim();
            Message temp = new Message(msgID, 0, recipient, messageText);
            String lengthCheck = temp.checkMessageLength();
            System.out.println(lengthCheck);
            if (lengthCheck.equals("Message ready to send.")) break;
        }

        Message message = new Message(msgID, totalMessagesSent, recipient, messageText);

        System.out.println("\nMessage Details:");
        System.out.println("  Message ID   : " + message.messageID);
        System.out.println("  Message Hash : " + message.messageHash);
        System.out.println("  Recipient    : " + message.recipient);
        System.out.println("  Message      : " + message.messageText);

        System.out.println("\nWhat would you like to do?");
        System.out.println("  1) Send Message");
        System.out.println("  2) Disregard Message");
        System.out.println("  3) Store Message to send later");
        System.out.print("Choose an option: ");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Message discarded.");
            return;
        }

        String result = message.SentMessage(choice);
        System.out.println(result);

        if (choice == 3) {
            message.storeMessage();
        }
    }

    public String getMessageID()     { return messageID; }
    public String getRecipient()     { return recipient; }
    public String getMessageText()   { return messageText; }
    public String getMessageHash()   { return messageHash; }
    public int    getMessageNumber() { return messageNumber; }
}