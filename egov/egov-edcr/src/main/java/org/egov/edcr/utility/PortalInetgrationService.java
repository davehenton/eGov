package org.egov.edcr.utility;

import static org.egov.edcr.utility.DcrConstants.APPLICATION_MODULE_TYPE;

import java.util.Date;
import java.util.List;

import org.egov.edcr.entity.EdcrApplication;
import org.egov.infra.admin.master.entity.Module;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.ModuleService;
import org.egov.portal.entity.PortalInbox;
import org.egov.portal.entity.PortalInboxBuilder;
import org.egov.portal.service.PortalInboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PortalInetgrationService {
    @Autowired
    private PortalInboxService portalInboxService;
    @Autowired
    private ModuleService moduleService;

    @Transactional
    public void updatePortalUserinbox(final EdcrApplication application, final User additionalPortalInboxUser) {
        Module module = moduleService.getModuleByName(APPLICATION_MODULE_TYPE);
        String url = "/edcr/edcrapplication/view/" + application.getApplicationNumber();
        if (application != null)
            portalInboxService.updateInboxMessage(application.getApplicationNumber(), module.getId(),
                    "Approved", true, new Date(), null,
                    additionalPortalInboxUser, application.getDcrNumber(), url);
    }

    @Transactional
    public void createPortalUserinbox(final EdcrApplication application, final List<User> portalInboxUser) {

        Module module = moduleService.getModuleByName(APPLICATION_MODULE_TYPE);
        boolean isResolved = false;
        String url = "/edcr/edcrapplication/view/" + application.getApplicationNumber();
        final PortalInboxBuilder portalInboxBuilder = new PortalInboxBuilder(module,
                application.getPlanInformation().getServiceType(), application.getApplicationNumber(),
                application.getDcrNumber(), application.getId(), "Sucess", "Sucess", url, isResolved,
                "Submitted", new Date(), null, portalInboxUser);

        final PortalInbox portalInbox = portalInboxBuilder.build();
        portalInboxService.pushInboxMessage(portalInbox);
    }
}
