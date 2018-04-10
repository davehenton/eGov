/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
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
 */

package org.egov.bpa.web.controller.master;

import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.service.StakeHolderService;
import org.egov.bpa.transaction.entity.StakeHolderDocument;
import org.egov.bpa.transaction.entity.enums.StakeHolderType;
import org.egov.bpa.transaction.service.BPADocumentService;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.web.controller.adaptor.StakeHolderJsonAdaptor;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.persistence.entity.Address;
import org.egov.infra.persistence.entity.CorrespondenceAddress;
import org.egov.infra.persistence.entity.PermanentAddress;
import org.egov.infra.persistence.entity.enums.AddressType;
import org.egov.infra.persistence.entity.enums.Gender;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.portal.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.egov.infra.utils.JsonUtils.toJSON;

@Controller
@RequestMapping(value = "/stakeholder")
public class StakeHolderController {
    private static final String STAKEHOLDER_RESULT = "stakeholder-result";
    private static final String SEARCH_STAKEHOLDER_EDIT = "search-stakeholder-edit";
    private static final String STAKE_HOLDER = "stakeHolder";
    private static final String STAKEHOLDER_UPDATE = "stakeholder-update";
    private static final String STAKEHOLDER_VIEW = "stakeholder-view";
    private static final String SEARCH_STAKEHOLDER_VIEW = "search-stakeholder-view";
    private static final String DATA = "{ \"data\":";

    @Autowired
    private StakeHolderService stakeHolderService;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BPADocumentService bpaDocumentService;

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private BPASmsAndEmailService bpaSmsAndEmailService;
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityUtils securityUtils;

    private static final String STAKEHOLDER_NEW = "stakeholder-new";
    private static final String STAKEHOLDER_NEW_BY_CITIZEN = "stakeholder-new-bycitizen";

    @ModelAttribute("stakeHolderDocumentList")
    public List<StakeHolderDocument> getStakeHolderDocuments() {
        return bpaDocumentService.getStakeHolderDocuments();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showStakeHolder(final Model model) {
        model.addAttribute(STAKE_HOLDER, new StakeHolder());
        prepareModel(model);
        return STAKEHOLDER_NEW;
    }

    private void prepareModel(final Model model) {
        model.addAttribute("isBusinessUser",  securityUtils.getCurrentUser().getType().equals(UserType.SYSTEM) ? Boolean.TRUE : Boolean.FALSE);
        model.addAttribute("genderList", Arrays.asList(Gender.values()));
        model.addAttribute("stakeHolderTypes", Arrays.asList(StakeHolderType.values()));
        model.addAttribute("isOnbehalfOfOrganization", false);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createStakeholder(@ModelAttribute(STAKE_HOLDER) final StakeHolder stakeHolder,
                                    final Model model,
                                    final HttpServletRequest request,
                                    final BindingResult errors, final RedirectAttributes redirectAttributes) {
        validateStakeholder(stakeHolder, errors);
        if (checkIsUserExists(stakeHolder, model)) return STAKEHOLDER_NEW;
        if (errors.hasErrors()) {
            prepareModel(model);
            return STAKEHOLDER_NEW;
        }
        StakeHolder stakeHolderRes = stakeHolderService.save(stakeHolder);
        bpaSmsAndEmailService.sendSMSForStakeHolder(stakeHolderRes);
        bpaSmsAndEmailService.sendEmailForStakeHolder(stakeHolderRes);
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("msg.create.stakeholder.success", null, null));
        return "redirect:/stakeholder/result/" + stakeHolderRes.getId();
    }

    @RequestMapping(value = "/createbycitizen", method = RequestMethod.GET)
    public String showOnlineStakeHolder(final Model model) {
        model.addAttribute(STAKE_HOLDER, new StakeHolder());
        prepareModel(model);
        return STAKEHOLDER_NEW_BY_CITIZEN;
    }

    @RequestMapping(value = "/createbycitizen", method = RequestMethod.POST)
    public String createOnlineStakeholder(@ModelAttribute(STAKE_HOLDER) final StakeHolder stakeHolder,
                                          final Model model,
                                          final HttpServletRequest request,
                                          final BindingResult errors, final RedirectAttributes redirectAttributes) {
        validateStakeholder(stakeHolder, errors);
        if (checkIsUserExists(stakeHolder, model)) return STAKEHOLDER_NEW;

        if (securityUtils.getCurrentUser().getType().equals(UserType.SYSTEM)) {
            if (!citizenService.isValidOTP(stakeHolder.getActivationCode(), stakeHolder.getMobileNumber()))
                errors.rejectValue("activationCode", "error.otp.verification.failed");
        }
        if (errors.hasErrors()) {
            prepareModel(model);
            return STAKEHOLDER_NEW_BY_CITIZEN;
        }

        StakeHolder stakeHolderRes = stakeHolderService.save(stakeHolder);
        bpaSmsAndEmailService.sendSMSForStakeHolder(stakeHolderRes);
        bpaSmsAndEmailService.sendEmailForStakeHolder(stakeHolderRes);
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("msg.create.stakeholder.success", null, null));
        return "redirect:/stakeholder/result/" + stakeHolderRes.getId();
    }

    private boolean checkIsUserExists(@ModelAttribute(STAKE_HOLDER) StakeHolder stakeHolder, Model model) {
        List<User> users = userService.getUserByNameAndMobileNumberAndGenderForUserType(stakeHolder.getName(),
                stakeHolder.getMobileNumber(), stakeHolder.getGender(), UserType.BUSINESS);
        if (!users.isEmpty()) {
            String message = messageSource.getMessage("msg.name.mobile.exists",
                    new String[] { users.get(0).getName(), users.get(0).getMobileNumber(), users.get(0).getGender().name() },
                    LocaleContextHolder.getLocale());
            model.addAttribute("invalidBuildingLicensee", message);
            prepareModel(model);
            return true;
        }
        return false;
    }


    private void validateStakeholder(final StakeHolder stakeHolder, final BindingResult errors) {
        if(stakeHolderService.checkIsStakeholderCodeAlreadyExists(stakeHolder)) {
            errors.rejectValue("code", "msg.code.exists");
        }
        if(stakeHolderService.checkIsEmailAlreadyExists(stakeHolder)) {
            errors.rejectValue("emailId", "msg.email.exists");
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editStakeholder(@PathVariable final Long id, final Model model) {
        final StakeHolder stakeHolder = stakeHolderService.findById(id);
        preapreUpdateModel(stakeHolder, model);
        model.addAttribute("stakeHolderDocumentList", stakeHolder.getStakeHolderDocument());
        return STAKEHOLDER_UPDATE;
    }

    private void preapreUpdateModel(final StakeHolder stakeHolder, final Model model) {
        for (final Address address : stakeHolder.getAddress())
            if (AddressType.CORRESPONDENCE.equals(address.getType()))
                stakeHolder.setCorrespondenceAddress((CorrespondenceAddress) address);
            else
                stakeHolder.setPermanentAddress((PermanentAddress) address);
        model.addAttribute(STAKE_HOLDER, stakeHolder);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateStakeholder(@ModelAttribute(STAKE_HOLDER) final StakeHolder stakeHolder,
                                    final Model model,
                                    final HttpServletRequest request,
                                    final BindingResult errors, final RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            preapreUpdateModel(stakeHolder, model);
            return STAKEHOLDER_UPDATE;
        }
        stakeHolderService.update(stakeHolder);
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("msg.update.stakeholder.success", null, null));
        return "redirect:/stakeholder/result/" + stakeHolder.getId();
    }

    @RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
    public String resultStakeHolder(@PathVariable final Long id, final Model model) {
        model.addAttribute(STAKE_HOLDER, stakeHolderService.findById(id));
        return STAKEHOLDER_RESULT;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String viewStakeHolder(@PathVariable final Long id, final Model model) {
        model.addAttribute(STAKE_HOLDER, stakeHolderService.findById(id));
        return STAKEHOLDER_VIEW;
    }

    @RequestMapping(value = "/search/update", method = RequestMethod.GET)
    public String searchEditStakeHolder(final Model model) {
        model.addAttribute(STAKE_HOLDER, new StakeHolder());
        return SEARCH_STAKEHOLDER_EDIT;
    }

    @RequestMapping(value = "/search/update", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getStakeHolderResultForEdit(@ModelAttribute final StakeHolder stakeHolder, final Model model) {
        final List<StakeHolder> searchResultList = stakeHolderService.search(stakeHolder);
        return new StringBuilder(DATA).append(toJSON(searchResultList, StakeHolder.class, StakeHolderJsonAdaptor.class))
                .append("}")
                .toString();
    }

    @RequestMapping(value = "/search/view", method = RequestMethod.GET)
    public String searchViewStakeHolder(final Model model) {
        model.addAttribute(STAKE_HOLDER, new StakeHolder());
        return SEARCH_STAKEHOLDER_VIEW;
    }

    @RequestMapping(value = "/search/view", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getStakeHolderForView(@ModelAttribute final StakeHolder stakeHolder, final Model model) {
        final List<StakeHolder> searchResultList = stakeHolderService.search(stakeHolder);
        return new StringBuilder(DATA).append(toJSON(searchResultList, StakeHolder.class, StakeHolderJsonAdaptor.class))
                .append("}")
                .toString();
    }
}