<%--
  ~    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) 2017  eGovernments Foundation
  ~
  ~     The updated version of eGov suite of products as by eGovernments Foundation
  ~     is available at http://www.egovernments.org
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program. If not, see http://www.gnu.org/licenses/ or
  ~     http://www.gnu.org/licenses/gpl.html .
  ~
  ~     In addition to the terms of the GPL license to be adhered to in using this
  ~     program, the following additional terms are to be complied with:
  ~
  ~         1) All versions of this program, verbatim or modified must carry this
  ~            Legal Notice.
  ~            Further, all user interfaces, including but not limited to citizen facing interfaces,
  ~            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
  ~            derived works should carry eGovernments Foundation logo on the top right corner.
  ~
  ~            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
  ~            For any further queries on attribution, including queries on brand guidelines,
  ~            please contact contact@egovernments.org
  ~
  ~         2) Any misrepresentation of the origin of the material is prohibited. It
  ~            is required that all modified versions of this material be marked in
  ~            reasonable ways as different from the original version.
  ~
  ~         3) This license does not grant any rights to any user of the program
  ~            with regards to rights under trademark law for use of the trade names
  ~            or trademarks of eGovernments Foundation.
  ~
  ~   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  ~
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<form:form role="form" action="/bpa/holiday/update"
	modelAttribute="holiday" id="holidayListUpdateform"
	cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">
	<input type="hidden" name="holiday" id="holidayId"
		value="${holiday.id}">

	<div class="tab-content">
		<div id="applicant-info" class="tab-pane fade in active">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="form-group">
					<label class="col-sm-3 control-label text-right"><spring:message
							code="lbl.holidayDate" /></label>
					<div class="col-sm-3 add-margin">
						<form:input path="holidayDate" id="holidayDate"
							data-inputmask="'mask': 'd/m/y'" readonly="true" />
						<form:errors path="holidayDate" cssClass="add-margin error-msg" />
					</div>
					<label class="col-sm-2 control-label text-right"><spring:message
							code="lbl.holidayType" /></label>
					<div class="col-sm-3 add-margin">

						<form:input path="holidayType" id="holidayType" readonly="true" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label text-right"><spring:message
							code="lbl.description" /></label>
					<div class="col-sm-2 add-margin">
						<form:textarea path="description" rows="3" cols="30" id="description" readonly="true"  maxlength="150" />
						<form:errors path="description" cssClass="add-margin error-msg" />
					</div>
					<label class="col-sm-3 control-label text-right"><spring:message
							code="lbl.year" /></label>
					<div class="col-sm-3 add-margin">
						<form:input path="year" id="year" readonly="true" />
						<form:errors path="year" cssClass="add-margin error-msg" />
					</div>
				</div>

			</div>
		</div>
	</div>
	<div class="text-center">
		<a href='javascript:void(0)' class='btn btn-default'
			onclick='self.close()'><spring:message code='lbl.close' /></a>
	</div>
</form:form>
<script
	src="<cdn:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"></script>
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egi'/>">
