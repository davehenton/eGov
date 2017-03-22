package builders.pgrCollection;

import entities.requests.pgrCollections.Values;

public final class ValuesBuilder {

    Values values = new Values();

    public ValuesBuilder() {
        values.setChild_location_id("173");
        values.setStatus("60");
        values.setAssignee_id("2");
        values.setLocation_id("REGISTERED");
    }

    public ValuesBuilder(String update) {
        values.setChildLocationId("169");
        values.setStatus("FORWARDED");
        values.setLocationId("178");
        values.setDepartmentName("Health");
        values.setStateId("165");
        values.setUserId("18");
        values.setAssignment_id("4");
        values.setChildLocationName("Adarashanagar");
        values.setReceivingMode("WEBSITE");
        values.setLocationName("Election Ward No 6");
    }

    public static ValuesBuilder aValues() {
        return new ValuesBuilder();
    }

    public ValuesBuilder withChild_location_id(String child_location_id) {
        values.setChild_location_id(child_location_id);
        return this;
    }

    public ValuesBuilder withStatus(String status) {
        values.setStatus(status);
        return this;
    }

    public ValuesBuilder withAssignee_id(String assignee_id) {
        values.setAssignee_id(assignee_id);
        return this;
    }

    public ValuesBuilder withLocation_id(String location_id) {
        values.setLocation_id(location_id);
        return this;
    }

    public Values build() {
        return values;
    }
}
