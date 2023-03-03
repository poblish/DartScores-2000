<!DOCTYPE html>

<%@ page language="java" import="org.hiatusuk.darts.*, java.util.*, java.util.stream.*" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en">
<head>
	<%@ include file = "headertags.html" %>
	<title>Darts 2000</title>
</head>

<%@ include file = "introtable.html" %>
<%@ include file = "entrytable.html" %>

<%	String		theTotParam = request.getParameter("total");
	String		theLeftParam = request.getParameter("darts_left");
	String		theMaxParam = request.getParameter("max_results");
	String		theExactParam = request.getParameter("exactly_n");

	int			theTotal = ( theTotParam != null) ? Integer.valueOf(theTotParam).intValue() : 170;
	int			theNumDartsLeft = ( theLeftParam != null) ? Integer.valueOf(theLeftParam).intValue() : 3;
	boolean		exactlyThatNumDarts = ( theExactParam != null) ? theExactParam.equalsIgnoreCase("Yes") : false;
	int			theMaxNumResultstoShow = ( theMaxParam != null) ? Integer.valueOf(theMaxParam).intValue() : 500;

	//////////////////////////////////////////////////

	DartsOutEnquiry		theEnquiry = new DartsOutEnquiry( theTotal, theNumDartsLeft, exactlyThatNumDarts);
	List<DartScoreSet>  theResults = theEnquiry.GetScores();

	if ( theResults.size() > 0) {
		int	theNumMatchesToShow = ( theResults.size() > theMaxNumResultstoShow) ? theMaxNumResultstoShow : theResults.size();
%>

	<p class="emphasis">
		<%= "You can reach " + theTotal + " with " + theNumDartsLeft + " darts ... " + theNumMatchesToShow + " combinations found." %>
	</p>

	<div class="andyscentre">
	<table border="1" width="80%">
		<tr>
			<th class="matchheader" width="60">Match</th> <th class="dartsheader">Combination</th>
		</tr>
<%

        int i = 0;

		for (/* Yuk, DartScoreSet not parsed */ Object eachSet : theResults.stream().limit(theNumMatchesToShow).collect( Collectors.toList() )) {
%>		<tr>
			<td class="matchcell"><%= ++i %></td>
			<td class="dartsresultcell"><%= eachSet %>.</td>
		</tr>
<%		} %>

	</table>
	</div>
<%
	}
	else {
%>
	<p class="emphasis">Sorry, you cannot score <%= theTotal %> with <%= theNumDartsLeft %> darts!</p>

<% } %>

<%@ include file = "pageend.html" %>
