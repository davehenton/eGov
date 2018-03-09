/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
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
package org.egov.edcr.entity;

import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "EDCR_APPLICATION_DETAIL")
@SequenceGenerator(name = EdcrApplicationDetail.SEQ_EDCR_APPLICATION_DETAIL, sequenceName = EdcrApplicationDetail.SEQ_EDCR_APPLICATION_DETAIL, allocationSize = 1)
public class EdcrApplicationDetail extends AbstractAuditable {

    public static final String SEQ_EDCR_APPLICATION_DETAIL = "SEQ_EDCR_APPLICATION_DETAIL";
    private static final long serialVersionUID = 2590259455834176449L;

    @Id
    @GeneratedValue(generator = SEQ_EDCR_APPLICATION_DETAIL, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dxffileid")
    private FileStoreMapper dxfFileId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reportoutputid")
    private FileStoreMapper reportOutputId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "application")
    private EdcrApplication application;

    @Length(min = 1, max = 128)
    private String dcrNumber;

    private String status;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public EdcrApplication getApplication() {
        return application;
    }

    public void setApplication(EdcrApplication application) {
        this.application = application;
    }

    public FileStoreMapper getDxfFileId() {
        return dxfFileId;
    }

    public void setDxfFileId(FileStoreMapper dxfFileId) {
        this.dxfFileId = dxfFileId;
    }

    public FileStoreMapper getReportOutputId() {
        return reportOutputId;
    }

    public void setReportOutputId(FileStoreMapper reportOutputId) {
        this.reportOutputId = reportOutputId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDcrNumber() {
        return dcrNumber;
    }

    public void setDcrNumber(String dcrNumber) {
        this.dcrNumber = dcrNumber;
    }
}