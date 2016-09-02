package br.com.cursorws.model.xml;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public Date unmarshal(String value) throws Exception {
		if (value == null || value.trim().equals("")) {
			return null;
		}
		return sdf.parse(value);
	}

	@Override
	public String marshal(Date value) throws Exception {
		if (value == null) {
			return "";
		}
		return sdf.format(value);
	}
}