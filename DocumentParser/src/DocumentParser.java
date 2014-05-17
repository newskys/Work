import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DocumentParser {
	public static void main(String[] args) {
		List<OUDoc> ouDoc = new ArrayList<OUDoc>();
		try {
			OUDoc tmpOUDoc = new OUDoc();
			Document doc = Jsoup.connect("http://todayhumor.com/?mid_1").get();
			tmpOUDoc.setRawHtml(doc.toString());
			tmpOUDoc.setTitle(doc.select("viewSubjectDiv > div:eq(1)").toString());
			tmpOUDoc.setContent(doc.select(".view_content").toString());
			//System.out.println(headLines.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
