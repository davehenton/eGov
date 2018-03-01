/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2017>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.web.controller.transaction;
import static org.egov.bpa.utils.BpaConstants.FILESTORE_MODULECODE;
import static org.egov.infra.utils.JsonUtils.toJSON;

import java.util.*;
import java.util.stream.*;

import javax.servlet.http.HttpServletRequest;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.service.InspectionService;
import org.egov.bpa.transaction.service.LettertoPartyService;
import org.egov.bpa.transaction.service.SearchBpaApplicationService;
import org.egov.bpa.utils.*;
import org.egov.bpa.web.controller.adaptor.SearchBpaApplicationFormAdaptor;
import org.egov.eis.entity.*;
import org.egov.eis.service.*;
import org.egov.infra.admin.master.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/application")
public class SearchBpaApplicationController extends BpaGenericApplicationController {

    private static final String DATA = "{ \"data\":";
    private static final String APPLICATION_HISTORY = "applicationHistory";

    @Autowired
    private SearchBpaApplicationService searchBpaApplicationService;
    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private LettertoPartyService lettertoPartyService;
    @Autowired
    private EmployeeService employeeService;


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String showSearchApprovedforFee(final Model model) {
        model.addAttribute("searchBpaApplicationForm", new SearchBpaApplicationForm());
        return "search-bpa-application";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String searchRegisterStatusMarriageRecords(final Model model,
            @ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
        final List<SearchBpaApplicationForm> searchResultList = searchBpaApplicationService.search(searchBpaApplicationForm);
        return new StringBuilder(DATA)
                .append(toJSON(searchResultList, SearchBpaApplicationForm.class, SearchBpaApplicationFormAdaptor.class))
                .append("}")
                .toString();
    }

    @RequestMapping(value = "/view/{applicationNumber}", method = RequestMethod.GET)
    public String viewApplicationForm(final Model model, @PathVariable final String applicationNumber,
            final HttpServletRequest request) {
        BpaApplication application = applicationBpaService.findByApplicationNumber(applicationNumber);
        model.addAttribute("bpaApplication", application);
        model.addAttribute("citizenOrBusinessUser", bpaUtils.logedInuseCitizenOrBusinessUser());
        model.addAttribute(APPLICATION_HISTORY,
                bpaThirdPartyService.getHistory(application));
        model.addAttribute("inspectionList", inspectionService.findByBpaApplicationOrderByIdAsc(application));
        model.addAttribute("lettertopartylist", lettertoPartyService.findByBpaApplicationOrderByIdDesc(application));
        buildReceiptDetails(application);
        return "viewapplication-form";
    }

    @RequestMapping(value = "/downloadfile/{fileStoreId}")
    public ResponseEntity<InputStreamResource> download(@PathVariable final String fileStoreId) {
        return fileStoreUtils.fileAsResponseEntity(fileStoreId, FILESTORE_MODULECODE, true);
    }

    @RequestMapping(value = "/bpacollectfee", method = RequestMethod.GET)
    public String showCollectionPendingRecords(final Model model) {
        model.addAttribute("searchBpaApplicationForm", new SearchBpaApplicationForm());
        return "search-collect-fee";
    }

    @RequestMapping(value = "/bpacollectfee", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String searchCollectionPendingRecords(final Model model,
            @ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
        final List<SearchBpaApplicationForm> searchResultList = searchBpaApplicationService
                .searchForCollectionPending(searchBpaApplicationForm);
        return new StringBuilder(DATA)
                .append(toJSON(searchResultList, SearchBpaApplicationForm.class, SearchBpaApplicationFormAdaptor.class))
                .append("}")
                .toString();
    }
    
    @RequestMapping(value = "/bpadocumentscrutiny", method = RequestMethod.GET)
    public String showDocumentScrutinyPendingRecords(final Model model) {
        model.addAttribute("searchBpaApplicationForm", new SearchBpaApplicationForm());
        model.addAttribute("employeeRevMappedBndries", getLoginEmpMappedBoundaries());
        return "search-document-scrutiny";
    }

    private List<Boundary> getLoginEmpMappedBoundaries() {
        List<Boundary> employeeRevMappedBndries = new ArrayList<>();
        final Employee employee = employeeService.getEmployeeById(securityUtils.getCurrentUser().getId());
        for(Jurisdiction jurisdiction : employee.getJurisdictions()) {
            if(!BpaConstants.BOUNDARY_TYPE_CITY.equals(jurisdiction.getBoundaryType().getName()))
                employeeRevMappedBndries.add(jurisdiction.getBoundary());
        }
        return employeeRevMappedBndries;
    }

    @RequestMapping(value = "/bpadocumentscrutiny", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String searchDocumentScrutinyPendingRecords(final Model model,
            @ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
        List<Long> bndryIds = getLoginEmpMappedBoundaries().stream().map(Boundary :: getId).collect(Collectors.toList());
        final List<SearchBpaApplicationForm> searchResultList = searchBpaApplicationService
                .searchForDocumentScrutinyPending(searchBpaApplicationForm, bndryIds);
        return new StringBuilder(DATA)
                .append(toJSON(searchResultList, SearchBpaApplicationForm.class, SearchBpaApplicationFormAdaptor.class))
                .append("}")
                .toString();
    }
    
}
