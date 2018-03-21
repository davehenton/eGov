<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2017>  eGovernments Foundation
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
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" data-collapsed="0">
            <div class="panel-heading custom_form_panel_heading">
                <div class="panel-title">
                    <spring:message code="lbl.lp.details"/>
                </div>
            </div>
            <div class="panel-body">
                <div class="row add-border">
                    <div class="col-sm-3 add-margin">
                        <spring:message code="lbl.lpNumber"/>
                    </div>
                    <div class="col-sm-3 add-margin view-content">
                        <c:out value="${lettertoParty.lpNumber}" default="N/A"></c:out>
                    </div>
                    <%-- <c:if test="${lettertoParty.sentDate !=null }"> --%>
                    <div class="col-sm-3 add-margin">
                        <spring:message code="lbl.lpsentdate"/>
                    </div>
                    <div class="col-sm-3 add-margin view-content">
                        <fmt:formatDate value="${lettertoParty.sentDate}" pattern="dd/MM/yyyy" var="sentDate" />
                        <c:out value="${sentDate}" default="N/A"></c:out>
                    </div>
                    <%-- </c:if> --%>
                    <input type="hidden" id='lettertoParty' name="lettertoParty"
                           value="${lettertoParty.id}">
                </div>
                <div class="row add-border">
                    <div class="col-sm-3 add-margin">
                        <spring:message code="lbl.lpreason"/>
                    </div>
                    <div class="col-sm-3 add-margin view-content">
                        <c:forEach items="${lettertoParty.lpReason}" var="lpReason"
                                   varStatus="status">
                            <c:out value="${lpReason.description}"/>
                            <c:if test="${!status.last}">,</c:if>
                        </c:forEach>
                    </div>
                </div>

                <div class="row add-border">
                    <div class="col-sm-3 add-margin">
                        <spring:message code="lbl.lpdescription"/>
                    </div>
                    <div class="col-sm-9 add-margin view-content">
                        <c:out value="${lettertoParty.lpDesc}" default="N/A"></c:out>
                    </div>
                </div>

                <div class="row add-border">
                    <c:if test="${lettertoParty.replyDate !=null }">
                            <div class="col-sm-3 add-margin">
                                <spring:message code="lbl.lpreplydate"/>
                            </div>
                            <div class="col-sm-3 add-margin view-content">
                                <fmt:formatDate value="${lettertoParty.replyDate}" pattern="dd/MM/yyyy" var="replyDate" />
                                <c:out value="${replyDate}" default="N/A"></c:out>
                            </div>
                    </c:if>
                </div>
                <div class="row add-border">
                    <c:if test="${lettertoParty.lpReplyRemarks !=null }">
                        <div class="col-sm-3 add-margin">
                            <spring:message code="lbl.lpReplyRemarks"/>
                        </div>
                        <div class="col-sm-9 add-margin view-content">
                            <c:out value="${lettertoParty.lpReplyRemarks}" default="N/A"></c:out>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
            <div class="panel panel-primary" data-collapsed="0">
            <div class="panel-heading custom_form_panel_heading">
                <div class="panel-title">
                    <spring:message code="lbl.encloseddocuments"/>
                    -
                    <spring:message code="lbl.checklist"/>
                </div>
            </div>
            <div class="panel-body">
            <table class="table">
                <thead>
                <tr>
                    <th><spring:message code="lbl.documentname"/></th>
                    <th><spring:message code="lbl.isrequested"/></th>
                    <th><spring:message code="lbl.remarks"/></th>
                    <th><spring:message code="lbl.files"/></th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty lettertopartydocList}">
                        <c:forEach items="${lettertopartydocList}" var="docs"
                                   varStatus="status">
                            <tr>
                                <td><c:out value="${docs.checklistDetail.description}"
                                           default="N/A"/></td>

                                <td><c:out value="${docs.isrequested ? 'Yes' : 'No'}"/></td>
                                <td><c:out value="${docs.remarks}" default="N/A"/></td>
                                <td><c:set value="false" var="isDocFound"></c:set>
                                    <c:forEach items="${docs.getSupportDocs()}" var="file" varStatus="loop">
                                        <c:set value="true" var="isDocFound"></c:set>
                                        <a
                                                href="/egi/downloadfile?fileStoreId=${file.fileStoreId}&moduleName=BPA"
                                                target="_blank">${loop.index +1} - ${file.fileName}</a>
                                        <c:if test="${!loop.last}">,</c:if>&nbsp;
                                    </c:forEach>
                                    <c:if test="${!isDocFound}">
                                        N/A
                                    </c:if></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="col-md-12 col-xs-6  panel-title">No documents
                            found
                        </div>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            </div>
        </div>
    </div>
</div>
<div class="buttonbottom" align="center">
    <a href='javascript:void(0)' class='btn btn-default'
       onclick='self.close()'><spring:message code='lbl.close'/></a>
</div>
<script
        src="<cdn:url value='/resources/js/app/lettertoparty.js?rnd=${app_release_no}'/> "></script>

