package com.mg.util.communication;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigService;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.io.FileUtils;

public class Mail {

	private static final Logger log = LogManager.getLogger(Mail.class);
	private static ConfigService configService;
	static {
		try {
			configService = ServiceLocator.getService(ConfigServiceImpl.class);
		} catch (ServiceLocatorException e) {
			log.error(
					"A problem has happened while looking up the config service.",
					e);
		}
	}
	/** set the STMP HELO address **/
	public static final String SMTP_HELO = "latelierdeleo.com";

	/** set the Mail SMTP Server that we are using */
	// public static final String HOST = "127.0.0.1";
	public static final String HOST = "smtp.office365.com";
	// public static final String HOST = configService.getMailSMTP();
	/** set the Mail SMTP port that we are using, null means port 25 */
	public static String SMTP_PORT = "587";

	// public static final String fromWebmaster =
	// configService.getMailFromWebmaster();
	public static final String fromWebmaster = "info@latelierdeleo.com";
	/**
	 * the default debuggers address is something that I am putting here because
	 * I will remember to change it if needed.
	 */
	public static final String[] defaultDebugger = configService
			.getMailDefaultDebugger();

	private static final String username = "raphaele.geismar@latelierdeleo.com";
	private static final String password = "i2o5i98i_";

	private static Properties getMailProperties() {
		// create some properties and get the default Session
		Properties props = new Properties();
		// Set the mail.smtp.host property
		props.put("mail.smtp.host", HOST);
		// Set the HELO name
		props.put("mail.smtp.localhost", SMTP_HELO);
		// Set the smtp port if it exists
		if (SMTP_PORT != null) {
			props.put("mail.smtp.port", SMTP_PORT);
		}
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.mime.charset", "UTF-8");
		return props;
	}

	/**
	 * Use this method until we have a mail server that can specify the from
	 * address
	 */
	public static boolean send(String toWhom, String subject, String body) {
		return (send(toWhom, fromWebmaster, subject, body));
	}

	public static boolean send(String toWhom, String subject, String body,
			StringBuffer htmlPart) {
		String toArray[] = { toWhom };
		return (send(toArray, fromWebmaster, subject, body, null,
				htmlPart.toString()));
	}

	public static boolean send(String toWhom, String subject, File bodyFile) {
		String fileStr = null;
		try {
			fileStr = FileUtils.fileContentsToString(bodyFile);
		} catch (Exception e) {
			log.error("Mail.send() Exception:", e);
			return (false);
		}

		return (send(toWhom, subject, fileStr));
	}

	// ARRAY VERSION, Takes three parameters
	public static boolean send(String toWhom[], String subject, String body) {
		return (send(toWhom, fromWebmaster, subject, body, null));
	}

	// ARRAY VERSION, Takes four parameters
	public static boolean send(String toWhom, String fromWhom, String subject,
			String body) {
		String address[] = { toWhom };
		return (send(address, fromWhom, subject, body, null));
	}

	public static boolean send(String toWhom[], String subject,
			HashMap<String, String> keyValues) {
		return (send(toWhom, subject, "", keyValues));
	}

	public static boolean send(String toWhom[], String subject,
			String leadingText, HashMap<String, String> keyValues) {
		String body = leadingText + "\n";
		Set<String> keys = keyValues.keySet();
		Iterator<String> keyIter = keys.iterator();

		while (keyIter.hasNext()) {
			body += keyIter.next() + ":" + keyValues.get(keyIter.next()) + "\n";
		}
		return (send(toWhom, subject, body));
	}

	public static boolean send(String toWhom, String fromWhom, String subject,
			String body, File theFile) {
		String address[] = { toWhom };
		return (send(address, fromWhom, subject, body, theFile));
	}

	public static boolean send(String toWhom[], String fromWhom,
			String subject, String body) {
		return (send(toWhom, fromWhom, subject, body, null));
	}

	public static boolean send(String toWhom[], String fromWhom,
			String subject, String body, File theFile) {
		return (send(toWhom, fromWhom, subject, body, theFile, null));
	}

	/**
	 * This method allows you to specify all six parameters. If htmlString is
	 * not null then the text clients will see the text, the html clients will
	 * see the html.
	 **/
	public static boolean send(String toWhom[], String fromWhom,
			String subject, String body, File theFile, String htmlString) {

		if (toWhom == null || toWhom.length == 0) {
			return false;
		}

		String recipients = new String();
		for (int q = 0; q < toWhom.length; q++) {
			recipients += toWhom[q];
			// are we at the end?
			if ((q + 1) != toWhom.length)
				recipients += ",";
		}

		log.debug("mg.mail.send()\n To=" + recipients + "\n Subject="
				+ subject + "\n Body=\n\n" + body);

		// Session session = Session.getDefaultInstance(getMailProperties(),
		// null);

		Session session = Session.getInstance(getMailProperties(),
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			// create a message
			// Message msg = new MimeMessage(session);
			MimeMessage msg = new MimeMessage(session);

			// set the from
			msg.setFrom(new InternetAddress(fromWhom));

			// set the to
			InternetAddress address[] = new InternetAddress[toWhom.length];
			for (int i = 0; i < toWhom.length; i++) {
				address[i] = new InternetAddress(toWhom[i]);
			}

			msg.setRecipients(Message.RecipientType.TO, address);

			// msg.setSubject(subject);
			msg.setSubject(subject, "UTF-8");

			msg.setSentDate(new Date());
			// If the desired charset is known, you can use
			// setText(text, charset)

			// msg.setText(body);
			msg.setText(body, "UTF-8");

			// MS: Set the header to work around the special characters issue
			// (They appear ? instead).
			msg.setHeader("Content-Type", "text/plain; charset=UTF-8");

			if (theFile != null) {
				DataSource source = new FileDataSource(theFile);
				Multipart multipart = new MimeMultipart();
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(theFile.getName());
				multipart.addBodyPart(messageBodyPart);

				// Add it to the message
				msg.setContent(multipart);
			}

			if ((htmlString != null) && (htmlString.length() > 0)) {
				msg.setContent(htmlString, "text/html");
			}

			Transport.send(msg);
		}

		// ********************************************
		// ALL OF THE REST IS EXCEPTION HANDLING
		// ********************************************

		catch (MessagingException mex) {
			log.error("\n--Exception handling in msgsendsample.java");

			mex.printStackTrace();
			System.out.println();
			Exception ex = mex;
			do {
				if (ex instanceof SendFailedException) {
					SendFailedException sfex = (SendFailedException) ex;
					Address[] invalid = sfex.getInvalidAddresses();
					if (invalid != null) {
						System.out.println("    ** Invalid Addresses");
						if (invalid != null) {
							for (int i = 0; i < invalid.length; i++)
								System.out.println("         " + invalid[i]);
						}
					}
					Address[] validUnsent = sfex.getValidUnsentAddresses();
					if (validUnsent != null) {
						System.out.println("    ** ValidUnsent Addresses");
						if (validUnsent != null) {
							for (int i = 0; i < validUnsent.length; i++)
								System.out
										.println("         " + validUnsent[i]);
						}
					}
					Address[] validSent = sfex.getValidSentAddresses();
					if (validSent != null) {
						System.out.println("    ** ValidSent Addresses");
						if (validSent != null) {
							for (int i = 0; i < validSent.length; i++)
								System.out.println("         " + validSent[i]);
						}
					}
				}
				System.out.println();
				if (ex instanceof MessagingException)
					ex = ((MessagingException) ex).getNextException();
				else
					ex = null;
			} while (ex != null);

			// caught an exception...return false
			return (false);
		}

		// Did not catch an Exception...return true
		return (true);
	}

	public static boolean sendHtml(String toWhom, String subject,
			String htmlBody, Map<String, String> mapInlineImages) {
		String toArray[] = { toWhom };
		return (sendHtml(toArray, fromWebmaster, subject, htmlBody,
				mapInlineImages));
	}

	public static boolean sendHtml(String toWhom[], String fromWhom,
			String subject, String htmlBody, Map<String, String> mapInlineImages) {

		if (toWhom == null || toWhom.length == 0) {
			return false;
		}

		String recipients = new String();
		for (int q = 0; q < toWhom.length; q++) {
			recipients += toWhom[q];
			// are we at the end?
			if ((q + 1) != toWhom.length)
				recipients += ",";
		}

		System.out.println("mg.mail.send()\n To=" + recipients + "\n Subject="
				+ subject + "\n Body=\n\n" + htmlBody);

		// Session session = Session.getDefaultInstance(getMailProperties(),
		// null);

		Session session = Session.getInstance(getMailProperties(),
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			// create a message
			// creates a new e-mail message
			Message msg = new MimeMessage(session);

			// set the from
			msg.setFrom(new InternetAddress(fromWhom));
			// set the to
			InternetAddress address[] = new InternetAddress[toWhom.length];
			for (int i = 0; i < toWhom.length; i++) {
				address[i] = new InternetAddress(toWhom[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, address);
			// msg.setSubject(subject);
			msg.setSubject(subject);
			msg.setSentDate(new Date());

			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(htmlBody, "text/html; charset=UTF-8");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// adds inline image attachments
			if (mapInlineImages != null && mapInlineImages.size() > 0) {
				Set<String> setImageID = mapInlineImages.keySet();

				for (String contentId : setImageID) {
					MimeBodyPart imagePart = new MimeBodyPart();
					imagePart.setHeader("Content-Type", "text/plain; charset=UTF-8");
					imagePart.setHeader("Content-ID", "<" + contentId + ">");
					imagePart.setDisposition(MimeBodyPart.INLINE);

					String imageFilePath = mapInlineImages.get(contentId);
					try {
						imagePart.attachFile(imageFilePath);
					} catch (IOException ex) {
						ex.printStackTrace();
					}

					multipart.addBodyPart(imagePart);
				}
			}

			msg.setContent(multipart);

			Transport.send(msg);
		}

		// ********************************************
		// ALL OF THE REST IS EXCEPTION HANDLING
		// ********************************************

		catch (MessagingException mex) {
			System.out.println("\n--Exception handling in msgsendsample.java");

			mex.printStackTrace();
			System.out.println();
			Exception ex = mex;
			do {
				if (ex instanceof SendFailedException) {
					SendFailedException sfex = (SendFailedException) ex;
					Address[] invalid = sfex.getInvalidAddresses();
					if (invalid != null) {
						System.out.println("    ** Invalid Addresses");
						if (invalid != null) {
							for (int i = 0; i < invalid.length; i++)
								System.out.println("         " + invalid[i]);
						}
					}
					Address[] validUnsent = sfex.getValidUnsentAddresses();
					if (validUnsent != null) {
						System.out.println("    ** ValidUnsent Addresses");
						if (validUnsent != null) {
							for (int i = 0; i < validUnsent.length; i++)
								System.out
										.println("         " + validUnsent[i]);
						}
					}
					Address[] validSent = sfex.getValidSentAddresses();
					if (validSent != null) {
						System.out.println("    ** ValidSent Addresses");
						if (validSent != null) {
							for (int i = 0; i < validSent.length; i++)
								System.out.println("         " + validSent[i]);
						}
					}
				}
				System.out.println();
				if (ex instanceof MessagingException)
					ex = ((MessagingException) ex).getNextException();
				else
					ex = null;
			} while (ex != null);

			// caught an exception...return false
			return (false);
		}

		// Did not catch an Exception...return true
		return (true);
	}

	public static void main(String args[]) {
		if (args.length > 0) {
			if (args.length == 3) {
				File theFile = new File(args[2]);
				Mail.send(args[0], args[1], theFile);
			} else // usage
			{
				System.out
						.println("USAGE: java Mail.java <to> <subject> <filename-body>");
			}
		} else {
			Mail.send("raphaele.geismar@latelierdeleo.com", "Test Subject",
					"Test Body");
			// Mail.send("helocheck@cbl.abuseat.org", "Test Subject",
			// "Test Body");

			Hashtable<String, String> keyValue = new Hashtable<String, String>();
			File receiptFileHTML = new File(
					"D:/dev/workspace/mg/mgweb/src/com/mg/web/communication/template/en/customer-order.html");
			String emailBodyHTML;
			try {
				emailBodyHTML = FileUtils.fileContentsToString(receiptFileHTML);
				boolean success = Mail.send(
						new String[] { "raphaele.geismar@latelierdeleo.com" },
						fromWebmaster, "Test Subject", "Test Body", null,
						emailBodyHTML);

				System.out.println("Send : " + success);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
