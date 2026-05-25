package quickchat;

/*
*
@author Ntatiso
*
*/

import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

public class MessageTest {

    private static final String RECIPIENT_1     = "+27718693002";
    private static final String MESSAGE_TEXT_1  = "Hi Mike, can you join us for dinner tonight?";
    private static final String RECIPIENT_2     = "08575975889";
    private static final String MESSAGE_TEXT_2  = "Hi Keegan, did you receive the payment?";
    private static final String RECIPIENT_SHORT = "+2783";
    private static final String RECIPIENT_LONG  = "+2781234567890123";

    private Message message1;
    private Message message2;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        message1 = new Message(Message.generateMessageID(), 1, RECIPIENT_1, MESSAGE_TEXT_1);
        message2 = new Message(Message.generateMessageID(), 2, RECIPIENT_2, MESSAGE_TEXT_2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", message1.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        String longText = new String(new char[260]).replace('\0', 'A');
        Message longMessage = new Message(Message.generateMessageID(), 1, RECIPIENT_1, longText);
        assertTrue(longMessage.checkMessageLength().contains("Message exceeds 250 characters by"));
    }

    @Test
    public void testRecipientCellSuccess() {
        assertEquals("Cell phone number successfully captured.", message1.checkRecipientCell());
    }

    @Test
    public void testRecipientCellFailureNoInternationalCode() {
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            message2.checkRecipientCell()
        );
    }

    @Test
    public void testRecipientCellFailureTooShort() {
        Message m = new Message(Message.generateMessageID(), 0, RECIPIENT_SHORT, MESSAGE_TEXT_1);
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            m.checkRecipientCell()
        );
    }

    @Test
    public void testRecipientCellFailureTooLong() {
        Message m = new Message(Message.generateMessageID(), 0, RECIPIENT_LONG, MESSAGE_TEXT_1);
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            m.checkRecipientCell()
        );
    }

    @Test
    public void testMessageHashCorrect() {
        Message testMessage = new Message("0012345678", 0, RECIPIENT_1, MESSAGE_TEXT_1);
        assertEquals("00:0:HITONIGHT?", testMessage.createMessageHash());
    }

    @Test
    public void testMessageHashesInLoop() {
        String[] ids      = {"0012345678", "1122334455"};
        String[] texts    = {MESSAGE_TEXT_1, MESSAGE_TEXT_2};
        String[] expected = {"00:0:HITONIGHT?", "11:0:HIPAYMENT?"};

        for (int i = 0; i < ids.length; i++) {
            Message m = new Message(ids[i], 0, RECIPIENT_1, texts[i]);
            assertEquals(expected[i], m.createMessageHash());
        }
    }

    @Test
    public void testMessageIDGenerated() {
        String id = Message.generateMessageID();
        assertNotNull(id);
        assertTrue(id.length() <= 10);
    }

    @Test
    public void testCheckMessageIDValid() {
        assertTrue(message1.checkMessageID());
    }

    @Test
    public void testSentMessageSend() {
        Message m = new Message(Message.generateMessageID(), 0, RECIPIENT_1, MESSAGE_TEXT_1);
        assertEquals("Message successfully sent.", m.SentMessage(1));
    }

    @Test
    public void testSentMessageDisregard() {
        Message m = new Message(Message.generateMessageID(), 0, RECIPIENT_1, MESSAGE_TEXT_1);
        assertEquals("Press 0 to delete the message.", m.SentMessage(2));
    }

    @Test
    public void testSentMessageStore() {
        Message m = new Message(Message.generateMessageID(), 0, RECIPIENT_1, MESSAGE_TEXT_1);
        assertEquals("Message successfully stored.", m.SentMessage(3));
    }

    @Test
    public void testReturnTotalMessages() {
        int before = Message.returnTotalMessages();
        Message m = new Message(Message.generateMessageID(), 0, RECIPIENT_1, MESSAGE_TEXT_1);
        m.SentMessage(1);
        assertEquals(before + 1, Message.returnTotalMessages());
    }

    /**
     * Test of checkMessageID method, of class Message.
     */
    @Test
    public void testCheckMessageID() {
        System.out.println("checkMessageID");
        Message instance = null;
        boolean expResult = false;
        boolean result = instance.checkMessageID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkRecipientCell method, of class Message.
     */
    @Test
    public void testCheckRecipientCell() {
        System.out.println("checkRecipientCell");
        Message instance = null;
        String expResult = "";
        String result = instance.checkRecipientCell();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createMessageHash method, of class Message.
     */
    @Test
    public void testCreateMessageHash() {
        System.out.println("createMessageHash");
        Message instance = null;
        String expResult = "";
        String result = instance.createMessageHash();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkMessageLength method, of class Message.
     */
    @Test
    public void testCheckMessageLength() {
        System.out.println("checkMessageLength");
        Message instance = null;
        String expResult = "";
        String result = instance.checkMessageLength();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SentMessage method, of class Message.
     */
    @Test
    public void testSentMessage() {
        System.out.println("SentMessage");
        int choice = 0;
        Message instance = null;
        String expResult = "";
        String result = instance.SentMessage(choice);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printMessages method, of class Message.
     */
    @Test
    public void testPrintMessages() {
        System.out.println("printMessages");
        String expResult = "";
        String result = Message.printMessages();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of storeMessage method, of class Message.
     */
    @Test
    public void testStoreMessage() {
        System.out.println("storeMessage");
        Message instance = null;
        instance.storeMessage();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateMessageID method, of class Message.
     */
    @Test
    public void testGenerateMessageID() {
        System.out.println("generateMessageID");
        String expResult = "";
        String result = Message.generateMessageID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMessageFlow method, of class Message.
     */
    @Test
    public void testSendMessageFlow() {
        System.out.println("sendMessageFlow");
        Scanner scanner = null;
        Message.sendMessageFlow(scanner);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageID method, of class Message.
     */
    @Test
    public void testGetMessageID() {
        System.out.println("getMessageID");
        Message instance = null;
        String expResult = "";
        String result = instance.getMessageID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecipient method, of class Message.
     */
    @Test
    public void testGetRecipient() {
        System.out.println("getRecipient");
        Message instance = null;
        String expResult = "";
        String result = instance.getRecipient();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageText method, of class Message.
     */
    @Test
    public void testGetMessageText() {
        System.out.println("getMessageText");
        Message instance = null;
        String expResult = "";
        String result = instance.getMessageText();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageHash method, of class Message.
     */
    @Test
    public void testGetMessageHash() {
        System.out.println("getMessageHash");
        Message instance = null;
        String expResult = "";
        String result = instance.getMessageHash();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageNumber method, of class Message.
     */
    @Test
    public void testGetMessageNumber() {
        System.out.println("getMessageNumber");
        Message instance = null;
        int expResult = 0;
        int result = instance.getMessageNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}