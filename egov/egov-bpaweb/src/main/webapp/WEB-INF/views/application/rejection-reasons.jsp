<%--
  ~ eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
  ~ accountability and the service delivery of the government  organizations.
  ~
  ~  Copyright (C) <2017>  eGovernments Foundation
  ~
  ~  The updated version of eGov suite of products as by eGovernments Foundation
  ~  is available at http://www.egovernments.org
  ~
  ~  This program is free software: you can redistribute it and/or modify
  ~  it under the terms of the GNU General Public License as published by
  ~  the Free Software Foundation, either version 3 of the License, or
  ~  any later version.
  ~
  ~  This program is distributed in the hope that it will be useful,
  ~  but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~  GNU General Public License for more details.
  ~
  ~  You should have received a copy of the GNU General Public License
  ~  along with this program. If not, see http://www.gnu.org/licenses/ or
  ~  http://www.gnu.org/licenses/gpl.html .
  ~
  ~  In addition to the terms of the GPL license to be adhered to in using this
  ~  program, the following additional terms are to be complied with:
  ~
  ~      1) All versions of this program, verbatim or modified must carry this
  ~         Legal Notice.
  ~ 	Further, all user interfaces, including but not limited to citizen facing interfaces,
  ~         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
  ~         derived works should carry eGovernments Foundation logo on the top right corner.
  ~
  ~ 	For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
  ~ 	For any further queries on attribution, including queries on brand guidelines,
  ~         please contact contact@egovernments.org
  ~
  ~      2) Any misrepresentation of the origin of the material is prohibited. It
  ~         is required that all modified versions of this material be marked in
  ~         reasonable ways as different from the original version.
  ~
  ~      3) This license does not grant any rights to any user of the program
  ~         with regards to rights under trademark law for use of the trade names
  ~         or trademarks of eGovernments Foundation.
  ~
  ~  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>

<%@page import="org.python.modules.jarray"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="panel-heading toggle-header custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.rejection.reasons" />
	</div>
	<div class="history-icon toggle-icon">
		<i class="fa fa-angle-up fa-2x"></i>
	</div>
</div>
<div class="panel-body display-hide">
	<table class="table table-bordered  multiheadertbl" id=rejectionReasons>
		<thead>
			<tr>
				<th><spring:message code="lbl.srl.no" /></th>
				<th><spring:message code="lbl.isrequired" /></th>
				<th><spring:message code="lbl.condition" /></th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty bpaApplication.permitConditions}">
					<c:forEach var="rejectReason" items="${rejectionReasons}"
						varStatus="status">
						<tr>
							<td><c:out value="${status.index+1}"></c:out></td>
							<td><input type="checkbox" name="permitConditions"
								id="rejectionReasonsId" class="rejectionReasons"
								value="${rejectReason.id}"
								<c:if test="${fn:contains(bpaApplication.permitConditions, rejectReason)}"> checked </c:if>></td>
							<td><c:out value="${rejectReason.description}"></c:out></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach var="rejectReason" items="${rejectionReasons}"
						varStatus="status">
						<tr>
							<td><c:out value="${status.index+1}"></c:out></td>
							<td><form:checkbox path="permitConditions"
									id="rejectionReasonsId" class="rejectionReasons"
									value="${rejectReason.id}"></form:checkbox></td>
							<td><c:out value="${rejectReason.description}"></c:out></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.addnl.reject.reasons" /> </label>
		<div class="col-sm-9 add-margin">
			<form:textarea path="additionalRejectionReasons" rows="4"
				class="form-control patternvalidation"
				id="additionalRejectionReasons"
				value="${additionalRejectionReasons}" />
		</div>
	</div>
</div>