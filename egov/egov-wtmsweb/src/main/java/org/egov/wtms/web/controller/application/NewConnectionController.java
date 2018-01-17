/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */
package org.egov.wtms.web.controller.application;

import static org.egov.commons.entity.Source.MEESEVA;
import static org.egov.commons.entity.Source.ONLINE;
import static org.egov.wtms.utils.constants.WaterTaxConstants.SOURCECHANNEL_ONLINE;
import static org.egov.wtms.utils.constants.WaterTaxConstants.WATERCHARGES_CONSUMERCODE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.pims.commons.Position;
import org.egov.wtms.application.entity.ApplicationDocuments;
import org.egov.wtms.application.entity.WaterConnectionDetails;
import org.egov.wtms.application.service.ConnectionDemandService;
import org.egov.wtms.application.service.NewConnectionService;
import org.egov.wtms.application.service.WaterConnectionDetailsService;
import org.egov.wtms.application.service.WaterConnectionService;
import org.egov.wtms.masters.entity.DocumentNames;
import org.egov.wtms.masters.entity.enums.ConnectionStatus;
import org.egov.wtms.masters.service.ApplicationTypeService;
import org.egov.wtms.utils.WaterTaxUtils;
import org.egov.wtms.utils.constants.WaterTaxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/application")
public class NewConnectionController extends GenericConnectionController {
    private static final Logger LOG = LoggerFactory.getLogger(NewConnectionController.class);
    private static final String CONNECTION_PROPERTYID = "connection.propertyIdentifier";
    private static final String ADDITIONALRULE = "additionalRule";
    private static final String CURRENTUSER = "currentUser";
    private static final String RADIOBUTTONMAP = "radioButtonMap";
    private static final String STATETYPE = "stateType";
    private static final String NEWCONNECTION_FORM = "newconnection-form";
    private static final String VALIDIFPTDUEEXISTS = "validateIfPTDueExists";
    private static final String APPROVALPOSITION = "approvalPosition";

    private final WaterConnectionDetailsService waterConnectionDtlsService;
    private final ApplicationTypeService applicationTypeService;
    private final ConnectionDemandService connectionDemandService;
    private final NewConnectionService newConnectionService;
    private final WaterTaxUtils waterTaxUtils;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    public NewConnectionController(final WaterConnectionDetailsService waterConnectionDtlsService,
            final ApplicationTypeService applicationTypeService, final ConnectionDemandService connectionDemandService,
            final WaterTaxUtils waterTaxUtils, final NewConnectionService newConnectionService,
            final WaterConnectionService waterConnectionService) {
        this.waterConnectionDtlsService = waterConnectionDtlsService;
        this.applicationTypeService = applicationTypeService;
        this.connectionDemandService = connectionDemandService;
        this.waterTaxUtils = waterTaxUtils;
        this.newConnectionService = newConnectionService;
    }

    @ModelAttribute("documentNamesList")
    public List<DocumentNames> documentNamesList(
            @ModelAttribute final WaterConnectionDetails waterConnectionDetails) {
        waterConnectionDetails.setApplicationType(applicationTypeService.findByCode(WaterTaxConstants.NEWCONNECTION));
        return waterConnectionDtlsService.getAllActiveDocumentNames(waterConnectionDetails.getApplicationType());
    }

    @RequestMapping(value = "/newConnection-newform", method = GET)
    public String showNewApplicationForm(@ModelAttribute final WaterConnectionDetails waterConnectionDetails,
            final Model model, final HttpServletRequest request) {
        Boolean loggedUserIsMeesevaUser;
        waterConnectionDetails.setApplicationDate(new Date());
        waterConnectionDetails.setConnectionStatus(ConnectionStatus.INPROGRESS);
        model.addAttribute("allowIfPTDueExists", waterTaxUtils.isNewConnectionAllowedIfPTDuePresent());
        model.addAttribute(ADDITIONALRULE, waterConnectionDetails.getApplicationType().getCode());
        final WorkflowContainer workflowContainer = new WorkflowContainer();
        workflowContainer.setAdditionalRule(waterConnectionDetails.getApplicationType().getCode());
        prepareWorkflow(model, waterConnectionDetails, workflowContainer);
        model.addAttribute(CURRENTUSER, waterTaxUtils.getCurrentUserRole(securityUtils.getCurrentUser()));
        model.addAttribute(STATETYPE, waterConnectionDetails.getClass().getSimpleName());
        model.addAttribute("documentName", waterTaxUtils.documentRequiredForBPLCategory());
        model.addAttribute("typeOfConnection", WaterTaxConstants.NEWCONNECTION);
        model.addAttribute("citizenPortalUser", waterTaxUtils.isCitizenPortalUser(securityUtils.getCurrentUser()));
        model.addAttribute("isAnonymousUser", waterTaxUtils.isAnonymousUser(securityUtils.getCurrentUser()));
        loggedUserIsMeesevaUser = waterTaxUtils.isMeesevaUser(securityUtils.getCurrentUser());
        if (loggedUserIsMeesevaUser)
            if (request.getParameter("applicationNo") == null)
                throw new ApplicationRuntimeException("MEESEVA.005");
            else
                waterConnectionDetails.setMeesevaApplicationNumber(request.getParameter("applicationNo"));
        return NEWCONNECTION_FORM;
    }

    @RequestMapping(value = "/newConnection-dataEntryForm", method = GET)
    public String dataEntryForm(@ModelAttribute final WaterConnectionDetails waterConnectionDetails,
            final Model model) {
        waterConnectionDetails.setApplicationDate(new Date());
        waterConnectionDetails.setConnectionStatus(ConnectionStatus.ACTIVE);
        final Map<Long, String> connectionTypeMap = new HashMap<>();
        connectionTypeMap.put(applicationTypeService.findByCode(WaterTaxConstants.NEWCONNECTION).getId(),
                WaterTaxConstants.PRIMARYCONNECTION);
        connectionTypeMap.put(applicationTypeService.findByCode(WaterTaxConstants.ADDNLCONNECTION).getId(),
                WaterTaxConstants.CONN_NAME_ADDNLCONNECTION);
        model.addAttribute(RADIOBUTTONMAP, connectionTypeMap);
        model.addAttribute("mode", "dataEntry");
        model.addAttribute("typeOfConnection", WaterTaxConstants.NEWCONNECTION);
        return "newconnection-dataEntryForm";
    }

    @RequestMapping(value = "/newConnection-existingMessage/{consumerCode}", method = GET)
    public String dataEntryMessage(final Model model, @PathVariable final String consumerCode) {
        model.addAttribute(WATERCHARGES_CONSUMERCODE, consumerCode);
        final WaterConnectionDetails waterConnectionDetails = waterConnectionDtlsService
                .findByApplicationNumberOrConsumerCode(consumerCode);
        model.addAttribute("connectionType", waterConnectionDtlsService.getConnectionTypesMap()
                .get(waterConnectionDetails.getConnectionType().name()));
        if (waterConnectionDetails.getId() != null)
            model.addAttribute("mode", "edit");
        else
            model.addAttribute("mode", "");
        return "newconnection-dataEntryMessage";
    }

    @RequestMapping(value = "/newConnection-create", method = POST)
    public String createNewConnection(@Valid @ModelAttribute final WaterConnectionDetails waterConnectionDetails,
            final BindingResult resultBinder, final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final Model model, @RequestParam String workFlowAction,
            final BindingResult errors) {
        final Boolean loggedUserIsMeesevaUser = waterTaxUtils.isMeesevaUser(securityUtils.getCurrentUser());
        final Boolean isCSCOperator = waterTaxUtils.isCSCoperator(securityUtils.getCurrentUser());
        final boolean citizenPortalUser = waterTaxUtils.isCitizenPortalUser(securityUtils.getCurrentUser());
        model.addAttribute("citizenPortalUser", citizenPortalUser);
        final Boolean isAnonymousUser = waterTaxUtils.isAnonymousUser(securityUtils.getCurrentUser());
        model.addAttribute("isAnonymousUser", isAnonymousUser);
        if (!isCSCOperator && !citizenPortalUser && !loggedUserIsMeesevaUser && !isAnonymousUser) {
            final Boolean isJuniorAsstOrSeniorAsst = waterTaxUtils
                    .isLoggedInUserJuniorOrSeniorAssistant(securityUtils.getCurrentUser().getId());
            if (!isJuniorAsstOrSeniorAsst)
                throw new ValidationException("err.creator.application");
        }

        final Boolean applicationByOthers = waterTaxUtils.getCurrentUserRole(securityUtils.getCurrentUser());

        String sourceChannel = request.getParameter("Source");
        newConnectionService.validatePropertyID(waterConnectionDetails, resultBinder);
        waterConnectionDtlsService.validateWaterRateAndDonationHeader(waterConnectionDetails);
        final List<ApplicationDocuments> applicationDocs = new ArrayList<>();
        int i = 0;
        final String documentRequired = waterTaxUtils.documentRequiredForBPLCategory();
        if (!waterConnectionDetails.getApplicationDocs().isEmpty())
            for (final ApplicationDocuments applicationDocument : waterConnectionDetails.getApplicationDocs()) {
                newConnectionService.validateDocuments(applicationDocs, applicationDocument, i, resultBinder,
                        waterConnectionDetails.getCategory().getId(), documentRequired);
                i++;
            }
        if (waterConnectionDetails.getState() == null)
            waterConnectionDetails.setStatus(waterTaxUtils.getStatusByCodeAndModuleType(
                    WaterTaxConstants.APPLICATION_STATUS_CREATED, WaterTaxConstants.MODULETYPE));
        if (LOG.isDebugEnabled())
            LOG.debug("Model Level Validation occurs = " + resultBinder);
        if (resultBinder.hasErrors()) {
            waterConnectionDetails.setApplicationDate(new Date());
            model.addAttribute(VALIDIFPTDUEEXISTS, waterTaxUtils.isNewConnectionAllowedIfPTDuePresent());
            final WorkflowContainer workflowContainer = new WorkflowContainer();
            workflowContainer.setAdditionalRule(waterConnectionDetails.getApplicationType().getCode());
            prepareWorkflow(model, waterConnectionDetails, workflowContainer);
            model.addAttribute(ADDITIONALRULE, waterConnectionDetails.getApplicationType().getCode());
            model.addAttribute(CURRENTUSER, waterTaxUtils.getCurrentUserRole(securityUtils.getCurrentUser()));
            model.addAttribute("approvalPosOnValidate", request.getParameter(APPROVALPOSITION));
            model.addAttribute("typeOfConnection", WaterTaxConstants.NEWCONNECTION);
            model.addAttribute(STATETYPE, waterConnectionDetails.getClass().getSimpleName());
            model.addAttribute("documentName", waterTaxUtils.documentRequiredForBPLCategory());
            return NEWCONNECTION_FORM;
        }

        waterConnectionDetails.getApplicationDocs().clear();
        waterConnectionDetails.setApplicationDocs(applicationDocs);

        processAndStoreApplicationDocuments(waterConnectionDetails);

        Long approvalPosition = 0l;
        String approvalComent = "";

        if (request.getParameter("approvalComent") != null)
            approvalComent = request.getParameter("approvalComent");
        if (request.getParameter("workFlowAction") != null)
            workFlowAction = request.getParameter("workFlowAction");
        if (request.getParameter(APPROVALPOSITION) != null && !request.getParameter(APPROVALPOSITION).isEmpty())
            approvalPosition = Long.valueOf(request.getParameter(APPROVALPOSITION));

        if (applicationByOthers != null && applicationByOthers.equals(true) || citizenPortalUser || isAnonymousUser) {
            final Position userPosition = waterTaxUtils
                    .getZonalLevelClerkForLoggedInUser(waterConnectionDetails.getConnection().getPropertyIdentifier());
            if (userPosition != null)
                approvalPosition = userPosition.getId();
            else {
                model.addAttribute(VALIDIFPTDUEEXISTS, waterTaxUtils.isNewConnectionAllowedIfPTDuePresent());
                final WorkflowContainer workflowContainer = new WorkflowContainer();
                workflowContainer.setAdditionalRule(waterConnectionDetails.getApplicationType().getCode());
                prepareWorkflow(model, waterConnectionDetails, workflowContainer);
                model.addAttribute(ADDITIONALRULE, waterConnectionDetails.getApplicationType().getCode());
                model.addAttribute("approvalPosOnValidate", request.getParameter(APPROVALPOSITION));
                model.addAttribute(CURRENTUSER, waterTaxUtils.getCurrentUserRole(securityUtils.getCurrentUser()));
                model.addAttribute(STATETYPE, waterConnectionDetails.getClass().getSimpleName());
                errors.rejectValue(CONNECTION_PROPERTYID, "err.validate.connection.user.mapping",
                        "err.validate.connection.user.mapping");
                model.addAttribute("noJAORSAMessage", "No JA/SA exists to forward the application.");
                return NEWCONNECTION_FORM;
            }

        }

        if (isAnonymousUser) {
            waterConnectionDetails.setSource(ONLINE);
            sourceChannel = SOURCECHANNEL_ONLINE;
        }

        if (citizenPortalUser && (waterConnectionDetails.getSource() == null
                || StringUtils.isBlank(waterConnectionDetails.getSource().toString())))
            waterConnectionDetails.setSource(waterTaxUtils.setSourceOfConnection(securityUtils.getCurrentUser()));

        if (loggedUserIsMeesevaUser) {
            waterConnectionDetails.setSource(MEESEVA);
            if (waterConnectionDetails.getMeesevaApplicationNumber() != null)
                waterConnectionDetails.setApplicationNumber(waterConnectionDetails.getMeesevaApplicationNumber());
        }

        waterConnectionDtlsService.createNewWaterConnection(waterConnectionDetails, approvalPosition,
                approvalComent, waterConnectionDetails.getApplicationType().getCode(), workFlowAction,
                sourceChannel);

        if (LOG.isDebugEnabled())
            LOG.debug("createNewWaterConnection is completed ");

        if (loggedUserIsMeesevaUser)
            return "redirect:/application/generate-meesevareceipt?transactionServiceNumber="
                    + waterConnectionDetails.getApplicationNumber();
        else
            return "redirect:/application/citizeenAcknowledgement?pathVars=" + waterConnectionDetails.getApplicationNumber();

    }

    @ModelAttribute
    public WaterConnectionDetails loadByConsumerNo(@RequestParam(name = "id", required = false) final Long id) {
        if (id != null)
            return waterConnectionDtlsService.findBy(id);
        else
            return new WaterConnectionDetails();
    }

    // used to create/update existing details
    @RequestMapping(value = "/newConnection-dataEntryForm", method = POST)
    public String createExisting(@Valid @ModelAttribute final WaterConnectionDetails waterConnectionDetails,
            final BindingResult resultBinder, final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final Model model) {

        return createAndUpdateDataEntryRecord(waterConnectionDetails, resultBinder, model);
    }

    private String createAndUpdateDataEntryRecord(final WaterConnectionDetails waterConnectionDetails,
            final BindingResult resultBinder, final Model model) {
        newConnectionService.validatePropertyIDForDataEntry(waterConnectionDetails, resultBinder);
        newConnectionService.validateExisting(waterConnectionDetails, resultBinder);
        if (resultBinder.hasErrors()) {
            model.addAttribute(VALIDIFPTDUEEXISTS, waterTaxUtils.isNewConnectionAllowedIfPTDuePresent());
            final Map<Long, String> connectionTypeMap = new HashMap<>();

            connectionTypeMap.put(applicationTypeService.findByCode(WaterTaxConstants.NEWCONNECTION).getId(),
                    WaterTaxConstants.PRIMARYCONNECTION);
            connectionTypeMap.put(applicationTypeService.findByCode(WaterTaxConstants.ADDNLCONNECTION).getId(),
                    WaterTaxConstants.CONN_NAME_ADDNLCONNECTION);
            model.addAttribute(RADIOBUTTONMAP, connectionTypeMap);
            model.addAttribute(RADIOBUTTONMAP, connectionTypeMap);
            model.addAttribute("usageTypes", usageTypeService.getActiveUsageTypes());
            model.addAttribute("connectionCategories", connectionCategoryService.getAllActiveConnectionCategory());
            model.addAttribute("pipeSizes", pipeSizeService.getAllActivePipeSize());
            if (waterConnectionDetails.getId() == null)
                return "newconnection-dataEntryForm";
            else
                return "newconnection-dataEntryEditForm";
        }
        waterConnectionDtlsService.createExisting(waterConnectionDetails);
        return "redirect:newConnection-existingMessage/" + waterConnectionDetails.getConnection().getConsumerCode();
    }

    @RequestMapping(value = "/generate-meesevareceipt", method = GET)
    public RedirectView generateMeesevaReceipt(@ModelAttribute final WaterConnectionDetails waterConnectionDetails,
            final HttpServletRequest request, final Model model) {
        final String keyNameArray = request.getParameter("transactionServiceNumber");

        final RedirectView redirect = new RedirectView(WaterTaxConstants.MEESEVA_REDIRECT_URL + keyNameArray, false);
        final FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null)
            outputFlashMap.put("url", request.getRequestURL());
        return redirect;
    }

    @RequestMapping(value = "/application-success", method = GET)
    public ModelAndView successView(@ModelAttribute WaterConnectionDetails waterConnectionDetails,
            final HttpServletRequest request, final Model model) {

        final String[] keyNameArray = request.getParameter("pathVars").split(",");
        String applicationNumber = "";
        String approverName = "";
        String currentUserDesgn = "";
        String nextDesign = "";
        if (keyNameArray.length != 0 && keyNameArray.length > 0)
            if (keyNameArray.length == 1)
                applicationNumber = keyNameArray[0];
            else if (keyNameArray.length == 3) {
                applicationNumber = keyNameArray[0];
                approverName = keyNameArray[1];
                currentUserDesgn = keyNameArray[2];
            } else {
                applicationNumber = keyNameArray[0];
                approverName = keyNameArray[1];
                currentUserDesgn = keyNameArray[2];
                nextDesign = keyNameArray[3];
            }
        if (applicationNumber != null)
            waterConnectionDetails = waterConnectionDtlsService.findByApplicationNumber(applicationNumber);
        model.addAttribute("approverName", approverName);
        model.addAttribute("currentUserDesgn", currentUserDesgn);
        model.addAttribute("nextDesign", nextDesign);
        model.addAttribute("connectionType", waterConnectionDtlsService.getConnectionTypesMap()
                .get(waterConnectionDetails.getConnectionType().name()));
        model.addAttribute("cityName", waterTaxUtils.getMunicipalityName());
        model.addAttribute("applicationDocList",
                waterConnectionDtlsService.getApplicationDocForExceptClosureAndReConnection(waterConnectionDetails));
        model.addAttribute("feeDetails", connectionDemandService.getSplitFee(waterConnectionDetails));

        model.addAttribute("mode", "ack");
        return new ModelAndView("application/application-success", "waterConnectionDetails", waterConnectionDetails);

    }

    @RequestMapping(value = "/newConnection-editExisting/{consumerCode}", method = GET)
    public String editExisting(@ModelAttribute WaterConnectionDetails waterConnectionDetails,
            @PathVariable final String consumerCode, final Model model) {
        waterConnectionDetails = waterConnectionDtlsService.findByApplicationNumberOrConsumerCode(consumerCode);
        model.addAttribute("allowIfPTDueExists", waterTaxUtils.isNewConnectionAllowedIfPTDuePresent());
        model.addAttribute(ADDITIONALRULE, waterConnectionDetails.getApplicationType().getCode());
        model.addAttribute(CURRENTUSER, waterTaxUtils.getCurrentUserRole(securityUtils.getCurrentUser()));
        model.addAttribute(STATETYPE, waterConnectionDetails.getClass().getSimpleName());
        final Map<Long, String> connectionTypeMap = new HashMap<>();

        connectionTypeMap.put(applicationTypeService.findByCode(WaterTaxConstants.NEWCONNECTION).getId(),
                WaterTaxConstants.PRIMARYCONNECTION);
        connectionTypeMap.put(applicationTypeService.findByCode(WaterTaxConstants.ADDNLCONNECTION).getId(),
                WaterTaxConstants.CONN_NAME_ADDNLCONNECTION);
        model.addAttribute(RADIOBUTTONMAP, connectionTypeMap);
        model.addAttribute("mode", "dataEntry");
        model.addAttribute("waterConnectionDetails", waterConnectionDetails);
        model.addAttribute("usageTypes", usageTypeService.getActiveUsageTypes());
        model.addAttribute("connectionCategories", connectionCategoryService.getAllActiveConnectionCategory());
        model.addAttribute("pipeSizes", pipeSizeService.getAllActivePipeSize());
        return "newconnection-dataEntryEditForm";
    }

    @RequestMapping(value = "/newConnection-editExisting/{consumerCode}", method = POST)
    public String modifyExisting(@Valid @ModelAttribute final WaterConnectionDetails waterConnectionDetails,
            @PathVariable final String consumerCode, final BindingResult resultBinder,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final Model model) {
        return createAndUpdateDataEntryRecord(waterConnectionDetails, resultBinder, model);
    }
}