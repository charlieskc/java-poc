package htmlunit;

import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;



public class HSBCLogin {

	public static void main(String[] args) throws Exception {
		// Create and initialize WebClient object
		// Create and initialize WebClient object
		String dualPwd = "xxxxxx";
	    WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
	    //webClient.throwFailingHttpStatusCodeExceptionIfNecessary(webResponse);
	   
	    // visit Yahoo Mail login page and get the Form object
	    HtmlPage page = (HtmlPage) webClient.getPage("https://www.ebanking.hsbc.com.hk/1/2/logon?LANGTAG=en&COUNTRYTAG=US");
	    HtmlForm form = page.getFormByName("User");

	    // Enter login and passwd
	    form.getInputByName("u_UserID").setValueAttribute("username");
	    page = (HtmlPage) form.getInputByValue("press").setValueAttribute("click");
	    List<HtmlAnchor> anchorList = page.getAnchors();
	    for(HtmlAnchor anchor: anchorList){
	    	if(anchor.getOnClickAttribute().equals("document.getElementById('submittype').value = 'click';PC_7_0G3UNU10SD0MHTI7TQA0000000000000_selectLogonMode(0)")){
	    	System.out.println(anchor.getHrefAttribute());
	    	System.out.println(anchor.getOnClickAttribute());
	    	page = anchor.click();
	    	//System.out.println(page.asXml());
	    	}
	    }
	    
	    HtmlForm passwordform = page.getFormByName("PC_7_0G3UNU10SD0MHTI7EMA0000000000000_pwd");
	    passwordform.getInputByName("memorableAnswer").setValueAttribute("z0226409");
	    
	    HtmlForm secondPasswordform = page.getFormByName("PC_7_0G3UNU10SD0MHTI7EMA0000000000000_2ndpwd");
	    //secondPasswordform.getInputByName("memorableAnswer").setValueAttribute("z0226409");
	    
	    HtmlTable table = getTableFromDomElement(secondPasswordform);
	    
	    System.out.println("Table xml" + table.asXml());
	   
	    for(HtmlElement element : secondPasswordform.getHtmlElementDescendants()){
	    	//System.out.println(element.asXml());
	    }
	    
	    
	    ScriptResult result = page.executeJavaScript("PC_7_0G3UNU10SD0MHTI7TQA0000000000000_selectLogonMode(0)");
	    page = (HtmlPage) result.getNewPage();
	    
	    //System.out.println("result: "+ result);
	    
	    //System.out.println("result: " + page.asXml());

	   // form.getInputByName("logonMode").setValueAttribute("charlieskc");
	    //form.getInputByName("passwd").setValueAttribute("@@@@@@@");

	    // Click "Sign In" button/link

	    

	    
	    
	    // Click "Inbox" link
	    HtmlAnchor anchor = (HtmlAnchor) page.getHtmlElementById("WelcomeInboxFolderLink");
	    page = (HtmlPage) anchor.click();

	    // Get the table object containing the mails
	    HtmlTable dataTable = (HtmlTable) page.getHtmlElementById("datatable");

	    // Go through each row and count the row with class=msgnew
	    int newMessageCount = 0;
	    List<HtmlTableRow> rows = (List) dataTable.getHtmlElementsByTagName("tr");
	    for (HtmlTableRow row: rows) {
	    	if (row.getAttribute("class").equals("msgnew")) {
	    		newMessageCount++;
	    	}
	    }	    

	    // Print the newMessageCount to screen
	    System.out.println("newMessageCount = " + newMessageCount);

	    //System.out.println(page.asXml());	   	     	 
	}

	
	public static HtmlTable getTableFromDomElement(DomElement element) throws Exception{
		
		if(element instanceof com.gargoylesoftware.htmlunit.html.HtmlTable){
			System.out.println("----YES!!!!-----");
			HtmlTable table = (HtmlTable) element;
			return table;
		}
			
		for(DomElement e : element.getChildElements()){			
				getTableFromDomElement(e);
		    }

		throw new Exception("No HTML table found");
	}
}
