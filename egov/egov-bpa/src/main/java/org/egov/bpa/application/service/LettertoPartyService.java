/**
 * eGov suite of products aim to improve the internal efficiency,transparency,
   accountability and the service delivery of the government  organizations.
    Copyright (C) <2015>  eGovernments Foundation
    The updated version of eGov suite of products as by eGovernments Foundation
    is available at http://www.egovernments.org
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or
    http://www.gnu.org/licenses/gpl.html .
    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:
        1) All versions of this program, verbatim or modified must carry this
           Legal Notice.
        2) Any misrepresentation of the origin of the material is prohibited. It
           is required that all modified versions of this material be marked in
           reasonable ways as different from the original version.
        3) This license does not grant any rights to any user of the program
           with regards to rights under trademark law for use of the trade names
           or trademarks of eGovernments Foundation.
  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.application.service;

import java.util.Date;
import java.util.List;

import org.egov.bpa.application.autonumber.LettertoPartyNumberGenerator;
import org.egov.bpa.application.autonumber.LettertoPartyReplyAckNumberGenerator;
import org.egov.bpa.application.entity.BpaApplication;
import org.egov.bpa.application.entity.LettertoParty;
import org.egov.bpa.application.repository.LettertoPartyRepository;
import org.egov.bpa.service.BpaStatusService;
import org.egov.bpa.service.BpaUtils;
import org.egov.bpa.utils.BPASmsAndEmailService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.commons.service.FinancialYearService;
import org.egov.infra.utils.autonumber.AutonumberServiceBeanResolver;
import org.egov.infra.workflow.entity.StateHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LettertoPartyService {

    private final LettertoPartyRepository lettertoPartyRepository;
    @Autowired
    private FinancialYearService financialYearService;
    @Autowired
    private AutonumberServiceBeanResolver beanResolver;
    @Autowired
    BpaUtils bpaUtils;
    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    private BPASmsAndEmailService bpaSmsAndEmailService;

    @Autowired
    public LettertoPartyService(final LettertoPartyRepository lettertoPartyRepository) {
        this.lettertoPartyRepository = lettertoPartyRepository;
    }

    @Transactional
    public LettertoParty save(final LettertoParty lettertoParty) {
        Long approverPosition;
        if (lettertoParty.getLpNumber() == null || lettertoParty.getLpNumber().equals("")) {
            lettertoParty.setLetterDate(new Date());
            lettertoParty.setLpNumber(generateLettertpPartyNumber());
            approverPosition = getDocScutinyUser(lettertoParty.getApplication());
            bpaUtils.redirectToBpaWorkFlow(approverPosition, lettertoParty.getApplication(), BpaConstants.LETTERTOPARTYINITIATE,
                    "Letter to party initiate", BpaConstants.LETTERTOPARTYINITIATE);
            bpaSmsAndEmailService.sendSMSAndEmailToApplicantForLettertoparty(lettertoParty.getApplication());
        }

        if (lettertoParty.getReplyDate() != null) {
            lettertoParty.setAcknowledgementNumber(generateLettertpPartyReplyAck());
            lettertoParty.getApplication().setStatus(bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE, BpaConstants.LETTERTOPARTY_REPLY_RECEIVED));
        }
        return lettertoPartyRepository.save(lettertoParty);
    }

    public String generateLettertpPartyNumber() {
        final String financialYearRange = financialYearService
                .getCurrentFinancialYear().getFinYearRange();
        final LettertoPartyNumberGenerator lettertoPartyNumberGenerator = beanResolver
                .getAutoNumberServiceFor(LettertoPartyNumberGenerator.class);
        return lettertoPartyNumberGenerator.generateLettertoPartyNumber(financialYearRange);
    }

    public LettertoParty findById(final Long id) {
        return lettertoPartyRepository.findOne(id);
    }

    public Long getDocScutinyUser(BpaApplication bpaApplication) {
        Long docSrcuityUserPos = null;
        if (!bpaApplication.getStateHistory().isEmpty()) {
            for (final StateHistory stateHistory : bpaApplication.getStateHistory()) {
                if (stateHistory.getValue().equals(BpaConstants.BPA_STATUS_SUPERINDENT_APPROVED)) {
                    docSrcuityUserPos = stateHistory.getOwnerPosition().getId();
                    break;
                }
            }
        }
        return docSrcuityUserPos;
    }

    public List<LettertoParty> findByBpaApplicationOrderByIdAsc(final BpaApplication application) {
        return lettertoPartyRepository.findByApplicationOrderByIdDesc(application);
    }

    public String generateLettertpPartyReplyAck() {
        final String financialYearRange = financialYearService
                .getCurrentFinancialYear().getFinYearRange();
        final LettertoPartyReplyAckNumberGenerator lettertoPartyReplyAckNumberGenerator = beanResolver
                .getAutoNumberServiceFor(LettertoPartyReplyAckNumberGenerator.class);
        return lettertoPartyReplyAckNumberGenerator.generateLettertoPartyReplyAckNumber(financialYearRange);
    }

}