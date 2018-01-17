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

package org.egov.tl.web.response.adaptor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.egov.infra.web.support.json.adapter.DataTableJsonAdapter;
import org.egov.infra.web.support.ui.DataTable;
import org.egov.tl.entity.contracts.SearchForm;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchTradeResultHelperAdaptor implements DataTableJsonAdapter<SearchForm> {

    @Override
    public JsonElement serialize(final DataTable<SearchForm> searchFormDate, final Type type,
                                 final JsonSerializationContext jsc) {
        final List<SearchForm> searchFormResponse = searchFormDate.getData();
        final JsonArray searchTradeResult = new JsonArray();
        searchFormResponse.forEach(searchFormObj -> {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("licenseId", searchFormObj.getLicenseId());
            jsonObject.addProperty("applicationNumber", searchFormObj.getApplicationNumber());
            jsonObject.addProperty("tlNumber", searchFormObj.getLicenseNumber());
            jsonObject.addProperty("oldTLNumber", searchFormObj.getOldLicenseNumber());
            jsonObject.addProperty("category", searchFormObj.getCategoryName());
            jsonObject.addProperty("subCategory", searchFormObj.getSubCategoryName());
            jsonObject.addProperty("tradeTitle", searchFormObj.getTradeTitle());
            jsonObject.addProperty("tradeOwner", searchFormObj.getTradeOwnerName());
            jsonObject.addProperty("mobileNumber", searchFormObj.getMobileNo());
            jsonObject.addProperty("propertyAssmntNo", searchFormObj.getPropertyAssessmentNo());
            jsonObject.addProperty("status", searchFormObj.getStatus());
            jsonObject.addProperty("active", searchFormObj.getActive());
            jsonObject.addProperty("expiryYear", searchFormObj.getExpiryYear());
            jsonObject.addProperty("ownerName", searchFormObj.getOwnerName());
            // To add set of actions for search results
            final Gson gson = new Gson();
            final ArrayList<JsonObject> list = new ArrayList<>();
            for (int i = 0; i < searchFormObj.getActions().size(); i++) {
                final JsonObject objectInList = new JsonObject();
                objectInList.addProperty("key", searchFormObj.getActions().get(i));
                list.add(objectInList);
            }
            jsonObject.addProperty("actions", gson.toJson(list));
            searchTradeResult.add(jsonObject);
        });
        return enhance(searchTradeResult, searchFormDate);
    }
}