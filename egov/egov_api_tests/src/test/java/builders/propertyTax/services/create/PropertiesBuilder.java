package builders.propertyTax.services.create;

import builders.propertyTax.AuditDetailsBuilder;
import entities.requests.propertyTax.AuditDetails;
import entities.requests.propertyTax.services.create.*;

import static data.ConstantData.tenantId;

public class PropertiesBuilder {

    Properties properties = new Properties();

    AuditDetails auditDetails = new AuditDetailsBuilder().build();

    Address address = new AddressBuilder().build();

    PropertyDetail propertyDetail = new PropertyDetailBuilder().build();

    VacantLand vacantLand = new VacantLandBuilder().build();

    Boundary boundary = new BoundaryBuilder().build();

    Owners[] owners = new Owners[1];

    Owners owners1 = new OwnersBuilder().build();

    public PropertiesBuilder(){
        properties.setTenantId(tenantId);
        properties.setOldUpicNumber("123456789");
        properties.setVltUpicNumber("123456789");
        properties.setCreationReason("NEWPROPERTY");
        properties.setAssessmentDate("10/04/2017");
        properties.setOccupancyDate("10/04/2017");
        properties.setGisRefNo("gfn2");
        properties.setIsAuthorised(false);
        properties.setIsUnderWorkflow(false);
        properties.setActive(false);
        properties.setChannel("SYSTEM");
        properties.setAuditDetails(auditDetails);
        properties.setAddress(address);
        properties.setPropertyDetail(propertyDetail);
        properties.setVacantLand(vacantLand);
        properties.setBoundary(boundary);
        owners[0] = owners1;
        properties.setOwners(owners);
    }

    public PropertiesBuilder withOwners(Owners[] owners){
        properties.setOwners(owners);
        return this;
    }

    public Properties build(){
        return properties;
    }
}