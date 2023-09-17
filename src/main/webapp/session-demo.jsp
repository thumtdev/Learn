<%@ page import="java.util.*"%>
<form action="session-demo.jsp">
	Add new item: <input type="text" name="theItem" /> <input
		type="submit" value="Submit" />
</form>
<%
List<String> items = (List<String>) session.getAttribute("myTodo");
if (items == null) {
	items = new ArrayList<String>();
	session.setAttribute("myTodo", items);
}

String theItem = request.getParameter("theItem");
boolean isItemNotEmpty = theItem != null && theItem.trim().length() > 0;
boolean isItemNotDuplicate = theItem != null && !items.contains(theItem.trim());

if (isItemNotEmpty && isItemNotDuplicate) {
	items.add(theItem.trim());
}
%>
<hr>
<b>To List Items:</b>
<br />

<ol>
	<%
	for (String temp : items) {
		out.println("<li>" + temp + "</li>");
	}
	%>
</ol>