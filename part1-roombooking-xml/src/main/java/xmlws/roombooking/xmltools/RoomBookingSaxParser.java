package xmlws.roombooking.xmltools;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RoomBookingSaxParser implements RoomBookingParser {

	private RoomBooking roomBooking = new RoomBooking();

	@Override
	public RoomBooking parse(InputStream inputStream) {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setNamespaceAware(true);
			SAXParser saxParser = spf.newSAXParser();
			saxParser.parse(inputStream, new RoomBookingBasicHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomBooking;
	}

	private class RoomBookingBasicHandler extends DefaultHandler {
		private String localNameTemp;
		public void startElement(String namespaceURI,
		                         String localName,
		                         String qName,
		                         Attributes atts) {
			localNameTemp = localName;
		}
		public void characters(char ch[], int start, int length) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			String temp = new String(ch, start, length);
			if (!temp.startsWith("\n")) {
				if (localNameTemp.equals("label")) {
					roomBooking.setRoomLabel(temp);
				}
				if (localNameTemp.equals("username")) {
					roomBooking.setUsername(temp);
				}
				if (localNameTemp.equals("startDate")) {
					try {
						roomBooking.setStartDate(sdf.parse(temp));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (localNameTemp.equals("endDate")) {
					try {
						roomBooking.setEndDate(sdf.parse(temp));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
