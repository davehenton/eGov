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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>

<div class="panel-heading custom_form_panel_heading">
    <div class="panel-title">
    </div>
</div>
<div class="panel-body">
    <div class="form-group">
        <label class="col-sm-3 control-label text-right"><spring:message code="lbl.occupancy" /> <span
                class="mandatory"></span></label>
        <div class="col-sm-3 add-margin">
            <input type="hidden" id="planInfoOccupancy" name="planInformation.occupancy">
            <input type="hidden" id="architectInformation" name="planInformation.architectInformation" value="${edcrApplication.planInformation.architectInformation}">
            <form:select path="planInformation.occupancy" data-first-option="false"
                         id="occupancy" cssClass="form-control" disabled="true" required="required">
                <form:option value="">
                    <spring:message code="lbl.select" />
                </form:option>
                <form:options items="${occupancyList}" itemValue="description"
                              itemLabel="description" />
            </form:select>
            <form:errors path="planInformation.occupancy" cssClass="add-margin error-msg" />
        </div>
        <label class="col-sm-2 control-label text-right"><spring:message code="lbl.service.type" /> <span class="mandatory"></span>
        </label>
        <div class="col-sm-3 add-margin">
            <input type="hidden" id="planInfoServiceType" name="planInformation.serviceType">
            <form:select path="planInformation.serviceType" data-first-option="false"
                         id="serviceType" cssClass="form-control serviceType" disabled="true"
                         required="required">
                <form:option value="">
                    <spring:message code="lbl.select" />
                </form:option>
                <form:options items="${serviceTypeList}" itemValue="description"
                              itemLabel="description" />
            </form:select>
            <form:errors path="planInformation.serviceType" cssClass="add-margin error-msg" />
        </div>
    </div>
    <div class="form-group">
            <label class="col-sm-3 control-label text-right"><spring:message code="lbl.amenity.type" />
            </label>
        <div class="col-sm-3 add-margin">
            <form:hidden path="planInformation.amenities" id="amenities"></form:hidden>
            <select name="" multiple id="applicationAmenity"
                    class="form-control applicationAmenity tick-indicator">
                <c:forEach items="${amenityTypeList}" var="amenity">
                    <option value="${amenity.description}" title="${amenity.description}" >${amenity.description}</option>
                </c:forEach>
            </select>

            <form:errors path="planInformation.amenities"
                         cssClass="add-margin error-msg" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label text-right">E-Dcr Upload<span class="mandatory"></span></label>
        <div class="col-sm-4 add-margin">
            <div class="fileSection col-md77-4">
                <input type="file" required="required" name="dxfFile" id="myfile" style="display:none;">
                <p class="hide"><i class="fa fa-file-text"></i>&nbsp;&nbsp;<span id="fileName"></span></p>
                <button type="button" id="fileTrigger" class="btn btn-primary fullWidth">
                    <span class="glyphicon glyphicon glyphicon-cloud-upload"></span> &nbsp;Choose a file
                </button>
                <div class="row hide fileActions">
                    <div class="col-md-6">
                        <button type="button" id="fileDelete" class="btn btn-primary fullWidth">
                            <i class="fa fa-trash-o"></i> &nbsp;Delete
                        </button>
                    </div>
                </div>
            </div>
            <small class="error-msg"><spring:message
                    code="lbl.dcr.upload.help" /></small>
        </div>
    </div>

</div>

<link rel="stylesheet" href="<c:url value='/resources/app/css/edcr-style.css?rnd=${app_release_no}'/>">
<script
        src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>